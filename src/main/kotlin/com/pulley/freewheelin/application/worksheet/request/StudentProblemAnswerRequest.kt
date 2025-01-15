package com.pulley.freewheelin.application.worksheet.request

data class StudentProblemAnswerRequest(
    val problemId: Long,
    val isSubjective: Boolean,
    val answerSelectionId: Long?,
    val answer: String?,
)
