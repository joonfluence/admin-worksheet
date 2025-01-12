package com.pulley.freewheelin.application.worksheet.request

import io.swagger.v3.oas.annotations.media.Schema

data class StudentWorksheetCreateRequestDto(
    @Schema(description = "유저 목록")
    val userIds: List<Long>,
)
