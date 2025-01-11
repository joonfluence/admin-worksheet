package com.pulley.freewheelin.application.worksheet.response

import com.pulley.freewheelin.application.worksheet.dto.StudentProblemAnswerDto
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy
import org.mapstruct.factory.Mappers

data class StudentProblemAnswerResponse(
    val id: Long = 0,
    val userId: Long,
    val problemId: Long,
    val answer: String?,
    val answerSelectionId: Long?,
    val isCorrect: Boolean,
)   {
    companion object {
        fun from(entity: StudentProblemAnswerDto): StudentProblemAnswerResponse {
            return UserProblemAnswerResponseMapper.INSTANCE.from(entity)
        }
    }
}

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface UserProblemAnswerResponseMapper {
    fun from(dto: StudentProblemAnswerDto): StudentProblemAnswerResponse

    companion object {
        val INSTANCE: UserProblemAnswerResponseMapper = Mappers.getMapper(UserProblemAnswerResponseMapper::class.java)
    }
}
