package com.pulley.freewheelin.application.worksheet.dto

import com.pulley.freewheelin.domain.entity.UserWorksheetEntity

data class UserWorksheetDto (
    val id: Long? = null,
    val worksheetId: Long,
    val userId: Long,
) {
    companion object {
        fun from(userId: Long, worksheetId: Long): UserWorksheetDto {
            return UserWorksheetDto(
                worksheetId = worksheetId,
                userId = userId,
            )
        }

        fun fromEntity(entity: UserWorksheetEntity): UserWorksheetDto {
            return UserWorksheetDto(
                id = entity.id,
                worksheetId = entity.worksheetId,
                userId = entity.userId,
            )
        }
    }
}