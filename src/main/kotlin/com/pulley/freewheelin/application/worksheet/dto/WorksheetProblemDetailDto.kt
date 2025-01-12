package com.pulley.freewheelin.application.worksheet.dto

data class WorksheetProblemDetailDto(
    val id: Long,
    val problemAccuracyRate: Double,
) {
    companion object {
        fun of(
            id: Long,
            problemAccuracyRate: Double,
        ): WorksheetProblemDetailDto {
            return WorksheetProblemDetailDto(
                id = id,
                problemAccuracyRate = problemAccuracyRate,
            )
        }
    }
}

