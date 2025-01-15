package com.pulley.freewheelin.application.problem.dto

import com.pulley.freewheelin.application.problem.request.ProblemSearchRequest
import com.pulley.freewheelin.domain.enums.DifficultyLevel
import com.pulley.freewheelin.domain.enums.ProblemType
import com.pulley.freewheelin.domain.enums.UnitCode

data class ProblemSearchDto(
    val totalCount: Int,
    val unitCodes: List<UnitCode>,
    val level: DifficultyLevel,
    val problemType: ProblemType,
) {
    companion object {
        fun from(request: ProblemSearchRequest): ProblemSearchDto {
            return ProblemSearchDto(
                totalCount = request.totalCount,
                unitCodes = request.unitCodeList.split(",").map { UnitCode.valueOf(it) },
                level = request.level,
                problemType = request.problemType,
            )
        }
    }
}
