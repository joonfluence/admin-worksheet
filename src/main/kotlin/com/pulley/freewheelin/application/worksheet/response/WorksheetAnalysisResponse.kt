package com.pulley.freewheelin.application.worksheet.response

import com.pulley.freewheelin.application.worksheet.dto.WorksheetAnalysisDto
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy
import org.mapstruct.factory.Mappers

data class WorksheetAnalysisResponse (
    val worksheetId: Long,
    val worksheetName: String,
    val students: List<WorksheetAnalysisStudentResponse>,
    val problems: List<WorksheetProblemDetailResponse>,
) {
    companion object {
        fun from(dto: WorksheetAnalysisDto): WorksheetAnalysisResponse {
            return WorksheetAnalysisResponseMapper.INSTANCE.from(dto)
        }
    }
}

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface WorksheetAnalysisResponseMapper {
    fun from(dto: WorksheetAnalysisDto): WorksheetAnalysisResponse

    companion object {
        val INSTANCE: WorksheetAnalysisResponseMapper = Mappers.getMapper(WorksheetAnalysisResponseMapper::class.java)
    }
}
