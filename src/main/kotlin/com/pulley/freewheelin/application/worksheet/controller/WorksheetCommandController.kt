package com.pulley.freewheelin.application.worksheet.controller

import com.pulley.freewheelin.application.worksheet.request.WorksheetCreateRequest
import com.pulley.freewheelin.application.worksheet.response.WorksheetResponse
import com.pulley.freewheelin.application.worksheet.service.WorksheetCommandService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/worksheets")
class WorksheetCommandController(
    private val worksheetCommandService: WorksheetCommandService
) {
    @PostMapping
    @Operation(summary = "학습지 생성")
    fun saveWorksheet(
        @RequestBody request: WorksheetCreateRequest,
    ): ResponseEntity<WorksheetResponse> {
        val worksheet = worksheetCommandService.saveWorksheet(request)
        val response = WorksheetResponse.from(worksheet)
        return ResponseEntity.ok(response)
    }
}
