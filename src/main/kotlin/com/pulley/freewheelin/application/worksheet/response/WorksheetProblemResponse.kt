package com.pulley.freewheelin.application.worksheet.response

import com.pulley.freewheelin.application.worksheet.dto.WorksheetProblemDto
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy
import org.mapstruct.factory.Mappers

data class WorksheetProblemResponse(
    val id: Long = 0,
    val worksheetId: Long,
    val problemId: Long,
) {
    companion object {
        fun from(dto: WorksheetProblemDto): WorksheetProblemResponse {
            return WorksheetProblemResponseMapper.INSTANCE.from(dto)
        }
    }
}

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface WorksheetProblemResponseMapper {
    fun from(dto: WorksheetProblemDto): WorksheetProblemResponse

    companion object {
        val INSTANCE: WorksheetProblemResponseMapper = Mappers.getMapper(WorksheetProblemResponseMapper::class.java)
    }
}
