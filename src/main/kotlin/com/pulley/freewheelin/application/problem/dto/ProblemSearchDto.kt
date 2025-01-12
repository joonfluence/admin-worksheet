package com.pulley.freewheelin.application.problem.dto

import com.pulley.freewheelin.domain.enums.DifficultyLevel
import com.pulley.freewheelin.domain.enums.ProblemType
import io.swagger.v3.oas.annotations.media.Schema

data class ProblemSearchDto(
    @Schema(description = "최대 문제 수")
    var totalCount: Int = 0,
    @Schema(description = "단위 코드 리스트")
    var unitCodeList: String = "",
    @Schema(description = "난이도")
    var level: DifficultyLevel,
    @Schema(description = "문제 유형")
    var problemType: ProblemType,
)
