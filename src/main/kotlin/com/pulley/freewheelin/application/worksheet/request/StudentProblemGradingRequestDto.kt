package com.pulley.freewheelin.application.worksheet.request

data class StudentProblemGradingRequestDto(
    val userId: Long,
    val answers: List<StudentProblemAnswerDto>,
)
