package com.pulley.freewheelin.application.worksheet.dto

data class WorksheetAnalysisDto(
    val worksheetId: Long,
    val worksheetName: String,
    val students: List<WorksheetAnalysisStudentDto>,
    val problems: List<WorksheetProblemDetailDto>,
) {
    companion object {
        fun of(
            worksheetId: Long,
            worksheetName: String,
            students: List<WorksheetAnalysisStudentDto>,
            problems: List<WorksheetProblemDetailDto>
        ): WorksheetAnalysisDto {
            return WorksheetAnalysisDto(
                worksheetId = worksheetId,
                worksheetName = worksheetName,
                students = students,
                problems = problems,
            )
        }
    }
}
