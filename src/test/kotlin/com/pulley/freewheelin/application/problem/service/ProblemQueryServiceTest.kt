package com.pulley.freewheelin.application.problem.service

import com.pulley.freewheelin.application.SpringBootBaseIntegrationTest
import com.pulley.freewheelin.application.problem.dto.ProblemDto
import com.pulley.freewheelin.application.problem.dto.ProblemSearchDto
import com.pulley.freewheelin.domain.entity.ProblemEntity
import com.pulley.freewheelin.domain.enums.DifficultyLevel
import com.pulley.freewheelin.domain.enums.ProblemType
import com.pulley.freewheelin.domain.enums.UnitCode
import com.pulley.freewheelin.domain.repository.ProblemRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageImpl

class ProblemQueryServiceIntegrationTest: SpringBootBaseIntegrationTest() {

    @Autowired
    private lateinit var problemQueryService: ProblemQueryService

    @Autowired
    private lateinit var problemRepository: ProblemRepository

    @Nested
    @DisplayName("문제 조회 테스트")
    inner class ProblemQueryServiceTests {
        @Test
        @DisplayName("문제가 정상적으로 입력된 상태라면, 모든 문제 조회가 정상적으로 조회 되어야 한다.")
        fun `test findAllProblems with search criteria`() {
            val unitCode = UnitCode.UC1503
            val level = 1
            val subjectiveCount = 1
            val selectionCount = 1
            createProblems(unitCode, level, subjectiveCount, selectionCount)

            val searchDto = ProblemSearchDto(
                unitCodeList = unitCode.toString(),
                level = DifficultyLevel.LOW,
                problemType = ProblemType.SUBJECTIVE,
                totalCount = 10,
            )
            val result: PageImpl<ProblemDto> = problemQueryService.findAllProblems(searchDto)

            assertEquals(subjectiveCount.toLong(), result.totalElements)
        }

        @Test
        @DisplayName("난이도가 HIGH인 경우, 비율(0.2, 0.3, 0.5)에 맞게 문제 조회가 정상적으로 조회 되어야 한다.")
        fun `test findAllProblems with high difficulty`() {
            val unitCode = UnitCode.UC1503
            val lowLevel = 1
            val midLevel = 3
            val highLevel = 5
            val subjectiveCount = 2
            val selectionCount = 3

            createProblems(unitCode, lowLevel, subjectiveCount, selectionCount)
            createProblems(unitCode, highLevel, subjectiveCount, selectionCount)
            createProblems(unitCode, midLevel, subjectiveCount, selectionCount)

            val searchDto = ProblemSearchDto(
                unitCodeList = unitCode.toString(),
                level = DifficultyLevel.HIGH,
                problemType = ProblemType.ALL,
                totalCount = 10,
            )
            val result: PageImpl<ProblemDto> = problemQueryService.findAllProblems(searchDto)

            val lowLevelResult = result.filter { it.level == lowLevel }.toList()
            val midLevelResult = result.filter { it.level == midLevel }.toList()
            val highLevelResult = result.filter { it.level == highLevel }.toList()

            assertEquals(5, highLevelResult.size)
            assertEquals(3, midLevelResult.size)
            assertEquals(2, lowLevelResult.size)
        }

        @Test
        @DisplayName("난이도가 MID인 경우, 비율(0.25, 0.5, 0.25)에 맞게 문제 조회가 정상적으로 조회 되어야 한다.")
        fun `test findAllProblems with mid difficulty`() {
            val unitCode = UnitCode.UC1503
            val lowLevel = 1
            val midLevel = 3
            val highLevel = 5
            val subjectiveCount = 2
            val selectionCount = 3

            createProblems(unitCode, lowLevel, subjectiveCount, selectionCount)
            createProblems(unitCode, highLevel, subjectiveCount, selectionCount)
            createProblems(unitCode, midLevel, subjectiveCount, selectionCount)

            val searchDto = ProblemSearchDto(
                unitCodeList = unitCode.toString(),
                level = DifficultyLevel.MIDDLE,
                problemType = ProblemType.ALL,
                totalCount = 10,
            )
            val result: PageImpl<ProblemDto> = problemQueryService.findAllProblems(searchDto)

            val lowLevelResult = result.filter { it.level == lowLevel }.toList()
            val midLevelResult = result.filter { it.level == midLevel }.toList()
            val highLevelResult = result.filter { it.level == highLevel }.toList()

            assertEquals(2, highLevelResult.size)
            assertEquals(5, midLevelResult.size)
            assertEquals(3, lowLevelResult.size)
        }

        @Test
        @DisplayName("난이도가 LOW인 경우, 비율(0.5, 0.3, 0.2)에 맞게 문제 조회가 정상적으로 조회 되어야 한다.")
        fun `test findAllProblems with low difficulty`() {
            val unitCode = UnitCode.UC1503
            val lowLevel = 1
            val midLevel = 3
            val highLevel = 5
            val subjectiveCount = 2
            val selectionCount = 3

            createProblems(unitCode, lowLevel, subjectiveCount, selectionCount)
            createProblems(unitCode, highLevel, subjectiveCount, selectionCount)
            createProblems(unitCode, midLevel, subjectiveCount, selectionCount)

            val searchDto = ProblemSearchDto(
                unitCodeList = unitCode.toString(),
                level = DifficultyLevel.LOW,
                problemType = ProblemType.ALL,
                totalCount = 10,
            )
            val result: PageImpl<ProblemDto> = problemQueryService.findAllProblems(searchDto)

            val lowLevelResult = result.filter { it.level == lowLevel }.toList()
            val midLevelResult = result.filter { it.level == midLevel }.toList()
            val highLevelResult = result.filter { it.level == highLevel }.toList()

            assertEquals(2, highLevelResult.size)
            assertEquals(3, midLevelResult.size)
            assertEquals(5, lowLevelResult.size)
        }
    }

    fun createProblems(unitCode: UnitCode, level: Int, subjectiveCount: Int, selectionCount: Int) {
        val problems = mutableListOf<ProblemEntity>()

        repeat(subjectiveCount) {
            problems.add(ProblemEntity.of(
                title = "주관식 문제 ${it + 1}",
                description = "주관식 설명 ${it + 1}",
                unitCode = unitCode,
                level = level,
                type = ProblemType.SUBJECTIVE
            ))
        }

        repeat(selectionCount) {
            problems.add(ProblemEntity.of(
                title = "객관식 문제 ${it + 1}",
                description = "객관식 설명 ${it + 1}",
                unitCode = unitCode,
                level = level,
                type = ProblemType.SELECTION
            ))
        }

        problemRepository.saveAll(problems)
    }
}