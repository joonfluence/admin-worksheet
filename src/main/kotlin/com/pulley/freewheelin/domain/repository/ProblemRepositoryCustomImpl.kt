package com.pulley.freewheelin.domain.repository

import com.pulley.freewheelin.application.problem.dto.ProblemSearchDto
import com.pulley.freewheelin.domain.entity.ProblemEntity
import com.pulley.freewheelin.domain.entity.QProblemEntity
import com.pulley.freewheelin.domain.enums.DifficultyLevel
import com.pulley.freewheelin.domain.enums.ProblemType
import org.springframework.data.domain.PageImpl
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport

class ProblemRepositoryCustomImpl : ProblemRepositoryCustom, QuerydslRepositorySupport(ProblemEntity::class.java) {
    val problem = QProblemEntity.problemEntity

    override fun searchAllProblemsByDto(dto: ProblemSearchDto): PageImpl<ProblemEntity> {
        val query = from(QProblemEntity.problemEntity)
            .where(problem.unitCode.`in`(dto.unitCodes))

        if (dto.problemType != ProblemType.ALL) {
            query.where(problem.type.eq(dto.problemType))
        }

        val problems = query.fetch()

        val (lowRatio, midRatio, highRatio) = getDifficultyRatios(dto.level)

        val lowProblemsMax = (dto.totalCount * lowRatio).toInt()
        val midProblemsMax = (dto.totalCount * midRatio).toInt()
        val highProblemsMax = (dto.totalCount * highRatio).toInt()

        val lowResults = filterProblemsByDifficulty(problems, DifficultyLevel.LOW, lowProblemsMax)
        val midResults = filterProblemsByDifficulty(problems, DifficultyLevel.MIDDLE, midProblemsMax)
        val highResults = filterProblemsByDifficulty(problems, DifficultyLevel.HIGH, highProblemsMax)

        val totalResults = lowResults + midResults + highResults

        if (totalResults.size < dto.totalCount) {
            val remainingProblems = dto.totalCount - totalResults.size
            val additionalResults = problems.filter {
                DifficultyLevel.fromLevel(it.level) != dto.level &&
                    !totalResults.contains(it)
            }.take(remainingProblems)
            return PageImpl(totalResults + additionalResults)
        }

        return PageImpl(totalResults)
    }

    private fun getDifficultyRatios(level: DifficultyLevel): Triple<Double, Double, Double> {
        return when (level) {
            DifficultyLevel.HIGH -> Triple(0.2, 0.3, 0.5)
            DifficultyLevel.MIDDLE -> Triple(0.25, 0.5, 0.25)
            DifficultyLevel.LOW -> Triple(0.5, 0.3, 0.2)
        }
    }

    fun filterProblemsByDifficulty(problems: List<ProblemEntity>, difficultyLevel: DifficultyLevel, maxCount: Int): List<ProblemEntity> {
        return problems.filter { difficultyLevel == DifficultyLevel.fromLevel(it.level) }.take(maxCount)
    }
}
