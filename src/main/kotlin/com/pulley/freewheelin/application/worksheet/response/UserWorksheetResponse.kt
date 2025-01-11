package com.pulley.freewheelin.application.worksheet.response

import com.pulley.freewheelin.application.worksheet.dto.UserWorksheetDto
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy
import org.mapstruct.factory.Mappers

data class UserWorksheetResponse(
    val id: Long? = null,
    val worksheetId: Long,
    val userId: Long,
) {
    companion object {
        fun from(dto: UserWorksheetDto): UserWorksheetResponse {
            return UserWorksheetResponseMapper.INSTANCE.from(dto)
        }
    }
}

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface UserWorksheetResponseMapper {
    fun from(dto: UserWorksheetDto): UserWorksheetResponse

    companion object {
        val INSTANCE: UserWorksheetResponseMapper = Mappers.getMapper(UserWorksheetResponseMapper::class.java)
    }
}
