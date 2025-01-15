package com.pulley.freewheelin.application.worksheet.service

import com.pulley.freewheelin.application.BaseJpaTest
import com.pulley.freewheelin.application.students.service.StudentWorksheetCommandService
import com.pulley.freewheelin.application.worksheet.request.StudentProblemAnswerRequest
import com.pulley.freewheelin.application.worksheet.request.StudentProblemGradingRequest
import com.pulley.freewheelin.application.worksheet.request.StudentWorksheetCreateRequest
import com.pulley.freewheelin.domain.entity.ProblemCorrectAnswerEntity
import com.pulley.freewheelin.domain.entity.ProblemEntity
import com.pulley.freewheelin.domain.entity.WorksheetEntity
import com.pulley.freewheelin.domain.enums.ProblemType
import com.pulley.freewheelin.domain.enums.UnitCode
import com.pulley.freewheelin.domain.repository.ProblemCorrectAnswerRepository
import com.pulley.freewheelin.domain.repository.ProblemRepository
import com.pulley.freewheelin.domain.repository.StudentProblemAnswerRepository
import com.pulley.freewheelin.domain.repository.StudentWorksheetRepository
import com.pulley.freewheelin.domain.repository.WorksheetRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import kotlin.test.BeforeTest
import kotlin.test.Test

class StudentWorksheetCommandServiceTest : BaseJpaTest() {
    @Autowired
    private lateinit var problemCorrectAnswerRepository: ProblemCorrectAnswerRepository

    @Autowired
    private lateinit var studentProblemAnswerRepository: StudentProblemAnswerRepository

    @Autowired
    private lateinit var studentWorksheetRepository: StudentWorksheetRepository

    @Autowired
    private lateinit var problemRepository: ProblemRepository

    @Autowired
    private lateinit var worksheetRepository: WorksheetRepository
    private lateinit var studentWorksheetCommandService: StudentWorksheetCommandService

    @BeforeTest
    fun setUp() {
        studentWorksheetCommandService = StudentWorksheetCommandService(
            problemCorrectAnswerRepository,
            studentProblemAnswerRepository,
            studentWorksheetRepository,
            problemRepository,
            worksheetRepository
        )
    }

    @DisplayName("유저 학습지 출제 테스트 - 유저 학습지를 생성한다.")
    @Test
    fun `test saveUserWorksheet with valid worksheetId`() {
        // Given
        val worksheetEntity = worksheetRepository.save(
            WorksheetEntity(
                title = "worksheet title",
                description = "worksheet description",
                userId = 1L,
            )
        )
        val request = StudentWorksheetCreateRequest(
            userIds = listOf(1L, 2L, 3L)
        )

        // When
        val result = studentWorksheetCommandService.saveUserWorksheet(worksheetEntity.id, request.userIds)

        // Then
        assertEquals(3, result.size)
    }

    @DisplayName("유저 학습지 채점 테스트 - 주관식 문제 일 때, 정답이 맞는지 확인한다.")
    @Test
    fun `test gradeUserWorksheet with valid worksheetId`() {
        // Given
        val worksheetEntity = worksheetRepository.save(
            WorksheetEntity(
                title = "worksheet title",
                description = "worksheet description",
                userId = 1L,
            )
        )

        val problems = createProblems(UnitCode.UC1503, 1, 3, 0)
        val problemIds = problems.map { it.id }
        createCorrectAnswers(problemIds, true)
        val request = StudentProblemGradingRequest(
            userId = 1L,
            answers =
            problemIds.map {
                StudentProblemAnswerRequest(
                    problemId = it,
                    answer = "answer$it",
                    answerSelectionId = null,
                    isSubjective = true,
                )
            }
        )

        // When
        val result = studentWorksheetCommandService.gradeUserWorksheet(worksheetEntity.id, request.userId, request.answers)

        // Then
        assertEquals(3, result.size)
    }

    @DisplayName("유저 학습지 채점 테스트 - 객관식 문제 일 때, 정답이 맞는지 확인한다.")
    @Test
    fun `test gradeUserWorksheet with valid worksheetId and selection problem`() {
        // Given
        val worksheetEntity = worksheetRepository.save(
            WorksheetEntity(
                title = "worksheet title",
                description = "worksheet description",
                userId = 1L,
            )
        )

        val problems = createProblems(UnitCode.UC1503, 1, 0, 3)
        val problemIds = problems.map { it.id }
        createCorrectAnswers(problemIds, false)
        val request = StudentProblemGradingRequest(
            userId = 1L,
            answers =
            problemIds.map {
                StudentProblemAnswerRequest(
                    problemId = it,
                    answer = null,
                    answerSelectionId = it,
                    isSubjective = false,
                )
            }
        )

        // When
        val result = studentWorksheetCommandService.gradeUserWorksheet(worksheetEntity.id, request.userId, request.answers)

        // Then
        assertEquals(3, result.size)
    }

    private fun createCorrectAnswers(problemIds: List<Long>, isSubjective: Boolean) {
        if (isSubjective) {
            problemIds.forEach {
                problemCorrectAnswerRepository.save(
                    ProblemCorrectAnswerEntity(
                        problemId = it,
                        answer = "answer$it",
                        selectionId = null,
                    )
                )
            }
        } else {
            problemIds.forEach {
                problemCorrectAnswerRepository.save(
                    ProblemCorrectAnswerEntity(
                        problemId = it,
                        answer = null,
                        selectionId = it,
                    )
                )
            }
        }
    }

    fun createProblems(
        unitCode: UnitCode,
        level: Int,
        subjectiveCount: Int,
        selectionCount: Int
    ): List<ProblemEntity> {
        val problems = mutableListOf<ProblemEntity>()

        repeat(subjectiveCount) {
            problems.add(
                ProblemEntity.of(
                    title = "주관식 문제 ${it + 1}",
                    description = "주관식 설명 ${it + 1}",
                    unitCode = unitCode,
                    level = level,
                    type = ProblemType.SUBJECTIVE
                )
            )
        }

        repeat(selectionCount) {
            problems.add(
                ProblemEntity.of(
                    title = "객관식 문제 ${it + 1}",
                    description = "객관식 설명 ${it + 1}",
                    unitCode = unitCode,
                    level = level,
                    type = ProblemType.SELECTION
                )
            )
        }

        return problemRepository.saveAll(problems)
    }
}
