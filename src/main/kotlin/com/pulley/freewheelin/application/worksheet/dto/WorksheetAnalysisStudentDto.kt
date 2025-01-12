package com.pulley.freewheelin.application.worksheet.dto

data class WorksheetAnalysisStudentDto(
    val userId: Long,
    val worksheetAccuracyRate: Double,
) {
    companion object {
        fun of(
            userId: Long,
            worksheetAccuracyRate: Double
        ): WorksheetAnalysisStudentDto {
            return WorksheetAnalysisStudentDto(
                userId = userId,
                worksheetAccuracyRate = worksheetAccuracyRate,
            )
        }
    }
}
