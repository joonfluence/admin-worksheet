package com.pulley.freewheelin.application.worksheet.dto

import com.pulley.freewheelin.domain.entity.WorksheetProblemEntity

data class WorksheetProblemDto(
    val id: Long = 0,
    val worksheetId: Long,
    val problemId: Long,
) {
    companion object {
        fun from(entity: WorksheetProblemEntity): WorksheetProblemDto {
            return WorksheetProblemDto(
                id = entity.id,
                worksheetId = entity.worksheetId,
                problemId = entity.problemId,
            )
        }
    }
}