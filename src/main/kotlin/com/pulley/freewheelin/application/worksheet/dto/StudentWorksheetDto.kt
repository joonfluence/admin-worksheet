package com.pulley.freewheelin.application.worksheet.dto

import com.pulley.freewheelin.domain.entity.StudentWorksheetEntity

data class StudentWorksheetDto(
    val id: Long? = null,
    val worksheetId: Long,
    val userId: Long,
) {
    companion object {
        fun from(userId: Long, worksheetId: Long): StudentWorksheetDto {
            return StudentWorksheetDto(
                worksheetId = worksheetId,
                userId = userId,
            )
        }

        fun fromEntity(entity: StudentWorksheetEntity): StudentWorksheetDto {
            return StudentWorksheetDto(
                id = entity.id,
                worksheetId = entity.worksheetId,
                userId = entity.userId,
            )
        }
    }
}