package com.pulley.freewheelin.application.worksheet.controller

import com.pulley.freewheelin.application.worksheet.request.UserWorksheetCreateRequestDto
import com.pulley.freewheelin.application.worksheet.request.WorksheetCreateRequestDto
import com.pulley.freewheelin.application.worksheet.response.UserWorksheetResponse
import com.pulley.freewheelin.application.worksheet.response.WorksheetResponse
import com.pulley.freewheelin.application.worksheet.service.WorksheetCommandService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/worksheets")
class WorksheetCommandController(private val worksheetCommandService: WorksheetCommandService) {

    @PostMapping
    @Operation(summary = "학습지 생성")
    fun saveWorksheet(
        @RequestBody request: WorksheetCreateRequestDto,
    ): ResponseEntity<WorksheetResponse> {
        val worksheet = worksheetCommandService.saveWorksheet(request)
        val response = WorksheetResponse.from(worksheet)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/{worksheetId}")
    fun saveUserWorksheet(
        @PathVariable worksheetId: Long,
        @RequestBody request: UserWorksheetCreateRequestDto,
    ): ResponseEntity<List<UserWorksheetResponse>> {
        val worksheets = worksheetCommandService.saveUserWorksheet(worksheetId, request)
        val responses = worksheets.map { UserWorksheetResponse.from(it) }
        return ResponseEntity.ok(responses)
    }
}
