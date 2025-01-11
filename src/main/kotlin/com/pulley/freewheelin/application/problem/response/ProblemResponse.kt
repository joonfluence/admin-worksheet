package com.pulley.freewheelin.application.problem.response

import com.pulley.freewheelin.application.problem.dto.ProblemDto
import com.pulley.freewheelin.domain.enums.ProblemType
import io.swagger.v3.oas.annotations.media.Schema
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy
import org.mapstruct.factory.Mappers

data class ProblemResponse(
    @Schema(description = "문제 ID")
    val id: Long,
    @Schema(description = "문제 내용")
    val answer: String,
    @Schema(description = "문제 유형")
    val unitCode: String,
    @Schema(description = "난이도")
    val level: Int,
    @Schema(description = "문제 유형")
    val problemType: ProblemType,
) {
    companion object {
        fun from(dto: ProblemDto): ProblemResponse {
            return ProblemResponseMapper.INSTANCE.from(dto)
        }
    }
}

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface ProblemResponseMapper {
    fun from(dto: ProblemDto): ProblemResponse

    companion object {
        val INSTANCE: ProblemResponseMapper = Mappers.getMapper(ProblemResponseMapper::class.java)
    }
}
