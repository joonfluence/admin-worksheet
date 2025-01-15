package com.pulley.freewheelin.application.worksheet.request

data class StudentProblemSubjectiveAnswerDto(
    val problemId: Long,
    val answer: String,
) {
    fun isCorrect(newAnswer: String): Boolean {
        requireNotNull(answer) { "주관식 답안이 없습니다" }
        return answer == newAnswer
    }

    companion object {
        fun from(request: StudentProblemAnswerRequest): StudentProblemSubjectiveAnswerDto {
            requireNotNull(request.answer) { "주관식 답안이 없습니다" }
            return StudentProblemSubjectiveAnswerDto(
                problemId = request.problemId,
                answer = request.answer,
            )
        }
    }
}
