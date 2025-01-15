package com.pulley.freewheelin.application.students.service

import com.pulley.freewheelin.application.worksheet.dto.StudentProblemAnswerDto
import com.pulley.freewheelin.application.worksheet.dto.StudentWorksheetDto
import com.pulley.freewheelin.application.worksheet.request.StudentProblemAnswerRequest
import com.pulley.freewheelin.application.worksheet.request.StudentProblemGradingRequest
import com.pulley.freewheelin.application.worksheet.request.StudentProblemSelectiveAnswerDto
import com.pulley.freewheelin.application.worksheet.request.StudentProblemSubjectiveAnswerDto
import com.pulley.freewheelin.application.worksheet.request.StudentWorksheetCreateRequest
import com.pulley.freewheelin.domain.entity.StudentProblemAnswerEntity
import com.pulley.freewheelin.domain.entity.StudentWorksheetEntity
import com.pulley.freewheelin.domain.repository.ProblemCorrectAnswerRepository
import com.pulley.freewheelin.domain.repository.ProblemRepository
import com.pulley.freewheelin.domain.repository.StudentProblemAnswerRepository
import com.pulley.freewheelin.domain.repository.StudentWorksheetRepository
import com.pulley.freewheelin.domain.repository.WorksheetRepository
import com.pulley.freewheelin.global.annotation.DistributedLock
import com.pulley.freewheelin.global.exception.BadRequestException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class StudentWorksheetCommandService(
    private val problemCorrectAnswerRepository: ProblemCorrectAnswerRepository,
    private val studentProblemAnswerRepository: StudentProblemAnswerRepository,
    private val studentWorksheetRepository: StudentWorksheetRepository,
    private val problemRepository: ProblemRepository,
    private val worksheetRepository: WorksheetRepository,
) {

    @DistributedLock(lockKey = "save_user_worksheet", keyParameter = "worksheetId")
    fun saveUserWorksheet(
        worksheetId: Long,
        request: StudentWorksheetCreateRequest
    ): List<StudentWorksheetDto> {
        validateWorksheet(worksheetId)
        val dtos = request.userIds.map { StudentWorksheetDto.from(it, worksheetId) }
        val worksheets = dtos.map { StudentWorksheetEntity.from(it) }
        val worksheet = studentWorksheetRepository.saveAll(worksheets)
        return worksheet.map { StudentWorksheetDto.fromEntity(it) }
    }

    @DistributedLock(lockKey = "grade_user_worksheet", keyParameter = "worksheetId")
    fun gradeUserWorksheet(
        worksheetId: Long,
        request: StudentProblemGradingRequest
    ): List<StudentProblemAnswerDto> {
        validateWorksheet(worksheetId)
        val problemIds = request.answers.map { it.problemId }.toList()
        validateProblems(problemIds)
        val problemAnswers = problemCorrectAnswerRepository.findByProblemIdIn(problemIds)
            .takeIf { it.isNotEmpty() }
            ?: throw BadRequestException("문제에 대한 답안이 존재하지 않습니다")

        val problemAnswerMap = problemAnswers.associate { it.id to (it.answer to it.selectionId) }

        val userProblemAnswers = gradeAnswer(request, problemAnswerMap)
        val entities = studentProblemAnswerRepository.saveAll(userProblemAnswers)
        return entities.map { StudentProblemAnswerDto.from(it) }
    }

    private fun gradeAnswer(
        request: StudentProblemGradingRequest,
        problemAnswerMap: Map<Long, Pair<String?, Long?>>
    ): List<StudentProblemAnswerEntity> {
        return request.answers.map { gradingDto ->
            val isCorrect = problemAnswerMap[gradingDto.problemId]?.let { (correctAnswer, correctSelectionId) ->
                if (gradingDto.isSubjective) {
                    checkSubjectiveAnswer(gradingDto, correctAnswer)
                } else {
                    checkSelectiveAnswer(gradingDto, correctSelectionId)
                }
            } ?: false
            StudentProblemAnswerEntity.of(gradingDto, request.userId, isCorrect)
        }
    }

    private fun checkSubjectiveAnswer(
        gradingDto: StudentProblemAnswerRequest,
        correctAnswer: String?
    ): Boolean {
        return correctAnswer?.let { StudentProblemSubjectiveAnswerDto.from(gradingDto).isCorrect(it) } ?: false
    }

    private fun checkSelectiveAnswer(
        gradingDto: StudentProblemAnswerRequest,
        correctSelectionId: Long?
    ): Boolean {
        return correctSelectionId?.let { StudentProblemSelectiveAnswerDto.from(gradingDto).isCorrect(it) } ?: false
    }

    private fun validateWorksheet(worksheetId: Long) {
        worksheetRepository.existsById(worksheetId).takeIf { it }
            ?: throw BadRequestException("학습지가 존재하지 않습니다.")
    }

    private fun validateProblems(problemIds: List<Long>) {
        val entities = problemRepository.findByIdIn(problemIds)
        if (entities.size != problemIds.size) {
            throw BadRequestException("존재하지 않은 문제들이 포함되어 있습니다.")
        }
    }
}
