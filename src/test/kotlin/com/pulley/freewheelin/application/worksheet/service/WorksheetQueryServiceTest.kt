package com.pulley.freewheelin.application.worksheet.service

import com.pulley.freewheelin.application.SpringBootBaseIntegrationTest
import com.pulley.freewheelin.application.worksheet.dto.StudentWorksheetDto
import com.pulley.freewheelin.application.worksheet.dto.WorksheetAnalysisDto
import com.pulley.freewheelin.application.worksheet.dto.WorksheetDto
import com.pulley.freewheelin.application.worksheet.request.StudentProblemAnswerDto
import com.pulley.freewheelin.application.worksheet.request.WorksheetCreateRequestDto
import com.pulley.freewheelin.domain.entity.*
import com.pulley.freewheelin.domain.enums.UserType
import com.pulley.freewheelin.domain.repository.*
import com.pulley.freewheelin.global.exception.BadRequestException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired

class WorksheetQueryServiceIntegrationTest : SpringBootBaseIntegrationTest() {

    @Autowired
    private lateinit var worksheetQueryService: WorksheetQueryService

    @Autowired
    private lateinit var worksheetRepository: WorksheetRepository

    @Autowired
    private lateinit var worksheetProblemRepository: WorksheetProblemRepository

    @Autowired
    private lateinit var studentWorksheetRepository: StudentWorksheetRepository

    @Autowired
    private lateinit var studentProblemAnswerRepository: StudentProblemAnswerRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    @BeforeEach
    fun setUp() {
        val student = createUser("이준호", "joonfluence.dev@gmail.com", UserType.STUDENT)
        val worksheet = createWorksheet("샘플 시험지", "샘플 시험지 입니다.", student.id)
        val problems = createProblems(worksheet.id, listOf(1L, 2L))
        createStudentWorksheet(student.id, worksheet.id)
        createStudentAnswers(student.id, problems)
    }

    @Nested
    @DisplayName("학습지 통계 조회")
    inner class WorksheetAnalysisTests {
        @Nested
        @DisplayName("존재하지 않는 학습지 ID로 조회 시")
        inner class NonExistentWorksheetTests {

            @Test
            @DisplayName("BadRequestException 발생한다.")
            fun `test findAnalysisByWorksheetId with non-existent worksheet`() {
                val invalidWorksheetId = 999L
                val exception = assertThrows<BadRequestException> {
                    worksheetQueryService.findAnalysisByWorksheetId(invalidWorksheetId)
                }.message
                assertEquals("학습지가 존재하지 않습니다", exception)
            }
        }

        @Nested
        @DisplayName("학생과 문제가 없는 경우")
        inner class NoStudentsAndProblemsTests {

            @Test
            @DisplayName("빈 학습지 문제 목록 반환한다.")
            fun `test findAnalysisByWorksheetId with no user and problem`() {
                val worksheet = WorksheetEntity.of(
                    title = "빈 학습지",
                    userId = 0L,
                    description = "문제가 존재하지 않습니다."
                )
                worksheetRepository.save(worksheet)

                val analysis: WorksheetAnalysisDto = worksheetQueryService.findAnalysisByWorksheetId(worksheet.id)
                assertEquals(worksheet.id, analysis.worksheetId)
                assertEquals("빈 학습지", analysis.worksheetName)
                assertTrue(analysis.students.isEmpty())
                assertTrue(analysis.problems.isEmpty())
            }
        }

        @Nested
        @DisplayName("학생은 존재하지만 문제가 없는 경우")
        inner class NoProblemsTests {

            @Test
            @DisplayName("학생 정보만 반환한다.")
            fun `test findAnalysisByWorksheetId with no problems`() {
                val student = createUser("이준호", "joonfluence.dev@gmail.com", UserType.STUDENT)
                val worksheet = createWorksheet("문제가 존재하지 않습니다.", "문제가 존재하지 않습니다.", student.id)
                createStudentWorksheet(student.id, worksheet.id)

                val analysis: WorksheetAnalysisDto = worksheetQueryService.findAnalysisByWorksheetId(worksheet.id)
                assertEquals(worksheet.id, analysis.worksheetId)
                assertEquals("문제가 존재하지 않습니다.", analysis.worksheetName)
                assertEquals(1, analysis.students.size)
                assertTrue(analysis.problems.isEmpty())
            }
        }

        @Nested
        @DisplayName("학생 답안이 없는 경우")
        inner class NoStudentAnswersTests {

            @Test
            @DisplayName("학생 정보와 문제 정보만 반환한다.")
            fun `test findAnalysisByWorksheetId with no student answers`() {
                val student = createUser("이준호", "joonfluence.dev@gmail.com", UserType.STUDENT)
                val worksheet = createWorksheet("응답 없는 학습지", "문제가 존재하지 않습니다.", student.id)
                val problem = WorksheetProblemEntity(worksheetId = worksheet.id, problemId = 1L)
                worksheetProblemRepository.save(problem)
                createStudentWorksheet(student.id, worksheet.id)

                val analysis: WorksheetAnalysisDto = worksheetQueryService.findAnalysisByWorksheetId(worksheet.id)
                assertEquals(worksheet.id, analysis.worksheetId)
                assertEquals("응답 없는 학습지", analysis.worksheetName)
                assertEquals(1, analysis.students.size)
                assertEquals(1, analysis.problems.size)
                assertEquals(0.0, analysis.students.first().worksheetAccuracyRate)
                assertEquals(0.0, analysis.problems.first().problemAccuracyRate)
            }
        }

        @Nested
        @DisplayName("학생 답안이 있는 경우")
        inner class StudentAnswersTests {

            @Test
            @DisplayName("정확한 통계 정보를 반환한다.")
            fun `test findAnalysisByWorksheetId with student answers`() {
                val student = createUser("이준호", "joonfluence.dev@gmail.com", UserType.STUDENT)
                val worksheet = createWorksheet("응답된 학습지", "답변이 있는 학습지 입니다.", student.id)
                val problems = createProblems(worksheet.id, listOf(1L, 2L))
                createStudentWorksheet(student.id, worksheet.id)
                createStudentAnswers(student.id, problems)

                val analysis: WorksheetAnalysisDto = worksheetQueryService.findAnalysisByWorksheetId(worksheet.id)
                assertEquals(worksheet.id, analysis.worksheetId)
                assertEquals("응답된 학습지", analysis.worksheetName)
                assertEquals(1, analysis.students.size)
                assertEquals(2, analysis.problems.size)

                val studentAnalysis = analysis.students.first()
                assertEquals(student.id, studentAnalysis.userId)
                assertEquals(0.5, studentAnalysis.worksheetAccuracyRate)

                val problem1Analysis = analysis.problems.find { it.id == 1L }
                assertEquals(1.0, problem1Analysis?.problemAccuracyRate)

                val problem2Analysis = analysis.problems.find { it.id == 2L }
                assertEquals(0.0, problem2Analysis?.problemAccuracyRate)
            }
        }
    }

    private fun createWorksheet(title: String, description: String, userId: Long): WorksheetEntity {
        val requestDto = WorksheetCreateRequestDto(title = title, description = description, userId = userId)
        val worksheet = WorksheetEntity.from(WorksheetDto.from(requestDto))
        return worksheetRepository.save(worksheet)
    }

    private fun createProblems(worksheetId: Long, problemIds: List<Long>): List<WorksheetProblemEntity> {
        val problems = problemIds.map { WorksheetProblemEntity(worksheetId = worksheetId, problemId = it) }
        return worksheetProblemRepository.saveAll(problems)
    }

    private fun createStudentWorksheet(userId: Long, worksheetId: Long): StudentWorksheetEntity {
        val studentWorksheet =
            StudentWorksheetEntity.from(StudentWorksheetDto(worksheetId = worksheetId, userId = userId))
        return studentWorksheetRepository.save(studentWorksheet)
    }

    private fun createStudentAnswers(userId: Long, problems: List<WorksheetProblemEntity>) {
        val subjectiveAnswer = StudentProblemAnswerEntity.of(
            dto = StudentProblemAnswerDto(
                problemId = problems[0].id,
                isSubjective = true,
                answer = "Test Answer",
                answerSelectionId = null
            ),
            userId = userId,
            isCorrect = true
        )
        val answer2 = StudentProblemAnswerEntity.of(
            dto = StudentProblemAnswerDto(
                problemId = problems[1].id,
                isSubjective = false,
                answer = null,
                answerSelectionId = 1
            ),
            userId = userId,
            isCorrect = false
        )
        studentProblemAnswerRepository.saveAll(listOf(subjectiveAnswer, answer2))
    }

    private fun createUser(name: String, email: String, type: UserType): UserEntity {
        val user = UserEntity(name = name, email = email, type = type)
        return userRepository.save(user)
    }
}