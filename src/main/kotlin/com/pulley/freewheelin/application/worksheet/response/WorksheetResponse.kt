package com.pulley.freewheelin.application.worksheet.response

import com.pulley.freewheelin.application.worksheet.dto.WorksheetDto
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy
import org.mapstruct.factory.Mappers

data class WorksheetResponse(
    val id: Long = 0,
    val title: String,
    val description: String?,
    val userId: Long,
) {
    companion object {
        fun from(dto: WorksheetDto): WorksheetResponse {
            return WorksheetResponseMapper.INSTANCE.from(dto)
        }
    }
}

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface WorksheetResponseMapper {
    fun from(dto: WorksheetDto): WorksheetResponse

    companion object {
        val INSTANCE: WorksheetResponseMapper = Mappers.getMapper(WorksheetResponseMapper::class.java)
    }
}
