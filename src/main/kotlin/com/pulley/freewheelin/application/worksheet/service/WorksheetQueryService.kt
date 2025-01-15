package com.pulley.freewheelin.application.worksheet.service

import com.pulley.freewheelin.application.worksheet.dto.WorksheetAnalysisDto
import com.pulley.freewheelin.application.worksheet.dto.WorksheetAnalysisStudentDto
import com.pulley.freewheelin.application.worksheet.dto.WorksheetProblemDetailDto
import com.pulley.freewheelin.application.worksheet.dto.WorksheetProblemDto
import com.pulley.freewheelin.domain.entity.StudentProblemAnswerEntity
import com.pulley.freewheelin.domain.repository.StudentProblemAnswerRepository
import com.pulley.freewheelin.domain.repository.StudentWorksheetRepository
import com.pulley.freewheelin.domain.repository.WorksheetProblemRepository
import com.pulley.freewheelin.domain.repository.WorksheetRepository
import com.pulley.freewheelin.global.exception.BadRequestException
import com.pulley.freewheelin.global.extension.findByIdOrThrow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class WorksheetQueryService(
    private val worksheetRepository: WorksheetRepository,
    private val worksheetProblemRepository: WorksheetProblemRepository,
    private val studentWorksheetRepository: StudentWorksheetRepository,
    private val studentProblemAnswerRepository: StudentProblemAnswerRepository,
) {
    fun findByWorksheetId(worksheetId: Long): List<WorksheetProblemDto> {
        val problems = worksheetProblemRepository.findByWorksheetId(worksheetId)
        return problems.map { WorksheetProblemDto.from(it) }
    }

    fun findAnalysisByWorksheetId(worksheetId: Long): WorksheetAnalysisDto {
        val worksheet = worksheetRepository.findByIdOrThrow(worksheetId, BadRequestException("학습지가 존재하지 않습니다"))
        val students = studentWorksheetRepository.findByWorksheetId(worksheetId)
        val problems = worksheetProblemRepository.findByWorksheetId(worksheetId)

        val problemIds = problems.map { it.id }
        val studentAnswers = studentProblemAnswerRepository.findByProblemIdIn(problemIds)
        val studentAnswerUserMap = studentAnswers.groupBy { it.userId }
        val studentAnswerProblemMap = studentAnswers.groupBy { it.problemId }

        val studentDtos = students.map { student ->
            val answers = studentAnswerUserMap[student.userId] ?: emptyList()
            val accuracyRate = calculateAccuracyRate(answers)
            WorksheetAnalysisStudentDto.of(student.userId, accuracyRate)
        }

        val problemAccuracyRates = problems.map { problem ->
            val answers = studentAnswerProblemMap[problem.id] ?: emptyList()
            val accuracyRate = calculateAccuracyRate(answers)
            WorksheetProblemDetailDto.of(problem.problemId, accuracyRate)
        }

        return WorksheetAnalysisDto.of(worksheetId, worksheet.title, studentDtos, problemAccuracyRates)
    }

    private fun calculateAccuracyRate(answers: List<StudentProblemAnswerEntity>): Double {
        val correctAnswers = answers.count { it.isCorrect }
        return if (answers.isNotEmpty()) correctAnswers.toDouble() / answers.size else 0.0
    }
}
