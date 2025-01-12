package com.pulley.freewheelin.application.worksheet.dto

import com.pulley.freewheelin.domain.entity.StudentProblemAnswerEntity
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy
import org.mapstruct.factory.Mappers

data class StudentProblemAnswerDto(
    val id: Long = 0,
    val userId: Long,
    val problemId: Long,
    val answer: String?,
    val answerSelectionId: Long?,
    val isCorrect: Boolean,
) {
    companion object {
        fun from(entity: StudentProblemAnswerEntity): StudentProblemAnswerDto {
            return UserProblemAnswerDtoMapper.INSTANCE.from(entity)
        }
    }
}

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface UserProblemAnswerDtoMapper {
    fun from(dto: StudentProblemAnswerEntity): StudentProblemAnswerDto

    companion object {
        val INSTANCE: UserProblemAnswerDtoMapper = Mappers.getMapper(UserProblemAnswerDtoMapper::class.java)
    }
}
