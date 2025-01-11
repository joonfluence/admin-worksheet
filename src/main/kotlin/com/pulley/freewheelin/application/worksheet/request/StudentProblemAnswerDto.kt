package com.pulley.freewheelin.application.worksheet.request

data class StudentProblemAnswerDto(
    val problemId: Long,
    val isSubjective: Boolean,
    val answerSelectionId: Long?,
    val answer: String?,
) {
    fun isCorrect(correctAnswer: String): Boolean {
        return if (isSubjective) {
            answer == correctAnswer
        } else {
            answerSelectionId == correctAnswer.toLong()
        }
    }
}
