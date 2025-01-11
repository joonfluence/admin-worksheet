package com.pulley.freewheelin.application.problem.dto

import com.pulley.freewheelin.domain.entity.ProblemEntity
import com.pulley.freewheelin.domain.enums.ProblemType
import com.pulley.freewheelin.domain.enums.UnitCode
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.ReportingPolicy
import org.mapstruct.factory.Mappers

data class ProblemDto(
    val id: Long,
    val unitCode: UnitCode,
    val level: Int,
    val problemType: ProblemType,
) {
    companion object {
        fun from(entity: ProblemEntity): ProblemDto {
            return ProblemDtoMapper.INSTANCE.from(entity)
        }
    }
}

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface ProblemDtoMapper {
    @Mapping(target = "problemType", source = "type")
    fun from(entity: ProblemEntity): ProblemDto

    companion object {
        val INSTANCE: ProblemDtoMapper = Mappers.getMapper(ProblemDtoMapper::class.java)
    }
}