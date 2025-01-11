package com.pulley.freewheelin.application.problem.dto

import com.pulley.freewheelin.domain.entity.ProblemEntity
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy
import org.mapstruct.factory.Mappers

data class ProblemDto(
    val id: Long,
    val answer: String,
    val unitCode: String,
    val level: Int,
    val problemType: String,
) {
    companion object {
        fun from(entity: ProblemEntity): ProblemDto {
            return ProblemDtoMapper.INSTANCE.from(entity)
        }
    }
}

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface ProblemDtoMapper {
    fun from(entity: ProblemEntity): ProblemDto

    companion object {
        val INSTANCE: ProblemDtoMapper = Mappers.getMapper(ProblemDtoMapper::class.java)
    }
}