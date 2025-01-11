package com.pulley.freewheelin.application.worksheet.response

import com.pulley.freewheelin.application.worksheet.dto.StudentWorksheetDto
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy
import org.mapstruct.factory.Mappers

data class StudentWorksheetResponse(
    val id: Long? = null,
    val worksheetId: Long,
    val userId: Long,
) {
    companion object {
        fun from(dto: StudentWorksheetDto): StudentWorksheetResponse {
            return StudentWorksheetResponseMapper.INSTANCE.from(dto)
        }
    }
}

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface StudentWorksheetResponseMapper {
    fun from(dto: StudentWorksheetDto): StudentWorksheetResponse

    companion object {
        val INSTANCE: StudentWorksheetResponseMapper = Mappers.getMapper(StudentWorksheetResponseMapper::class.java)
    }
}
