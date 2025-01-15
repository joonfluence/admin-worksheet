package com.pulley.freewheelin.application.worksheet.request

data class StudentProblemSelectiveAnswerDto(
    val problemId: Long,
    val answerSelectionId: Long,
) {
    fun isCorrect(answer: Long): Boolean {
        requireNotNull(answerSelectionId) { "객관식 답안이 없습니다" }
        return answerSelectionId == answer
    }

    companion object {
        fun from(request: StudentProblemAnswerRequest): StudentProblemSelectiveAnswerDto {
            requireNotNull(request.answerSelectionId) { "객관식 답안이 없습니다" }
            return StudentProblemSelectiveAnswerDto(
                problemId = request.problemId,
                answerSelectionId = request.answerSelectionId,
            )
        }
    }
}
