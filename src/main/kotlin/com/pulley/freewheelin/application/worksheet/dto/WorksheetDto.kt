package com.pulley.freewheelin.application.worksheet.dto

import com.pulley.freewheelin.application.worksheet.request.WorksheetCreateRequestDto
import com.pulley.freewheelin.domain.entity.WorksheetEntity
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy
import org.mapstruct.factory.Mappers

data class WorksheetDto(
    val id: Long = 0,
    val title: String,
    val description: String?,
    val userId: Long,
) {
    companion object {
        fun from(entity: WorksheetCreateRequestDto): WorksheetDto {
            return WorksheetDtoMapper.INSTANCE.from(entity)
        }

        fun fromEntity(entity: WorksheetEntity): WorksheetDto {
            return WorksheetDto(
                id = entity.id,
                title = entity.title,
                description = entity.description,
                userId = entity.userId,
            )
        }
    }
}

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface WorksheetDtoMapper {
    fun from(dto: WorksheetCreateRequestDto): WorksheetDto

    companion object {
        val INSTANCE: WorksheetDtoMapper = Mappers.getMapper(WorksheetDtoMapper::class.java)
    }
}
