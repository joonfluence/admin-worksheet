package com.pulley.freewheelin.application.worksheet.controller

import com.pulley.freewheelin.application.worksheet.request.WorksheetRequestDto
import com.pulley.freewheelin.application.worksheet.response.WorksheetProblemResponse
import com.pulley.freewheelin.application.worksheet.response.WorksheetResponse
import com.pulley.freewheelin.application.worksheet.service.WorksheetCommandService
import com.pulley.freewheelin.application.worksheet.service.WorksheetQueryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/worksheets")
class WorksheetCommandController(private val worksheetCommandService: WorksheetCommandService) {

    @PostMapping
    fun saveWorksheet(
        @RequestBody request: WorksheetRequestDto,
    ): ResponseEntity<WorksheetResponse> {
        val worksheet = worksheetCommandService.saveWorksheet(request)
        val response = WorksheetResponse.from(worksheet)
        return ResponseEntity.ok(response)
    }
}
