package com.pulley.freewheelin.application.worksheet.request

data class StudentProblemGradingRequest(
    val userId: Long,
    val answers: List<StudentProblemAnswerRequest>,
)
