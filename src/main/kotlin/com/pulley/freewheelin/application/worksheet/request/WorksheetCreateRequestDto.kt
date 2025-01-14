package com.pulley.freewheelin.application.worksheet.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

data class WorksheetCreateRequestDto(
    @field:NotBlank(message = "제목은 필수 값입니다.")
    @Schema(description = "제목")
    val title: String,
    @Schema(description = "설명")
    val description: String? = null,
    @Schema(description = "사용자 Id")
    val userId: Long,
) {
    companion object {
        fun of(title: String, description: String?, userId: Long): WorksheetCreateRequestDto {
            return WorksheetCreateRequestDto(title, description, userId)
        }
    }
}
