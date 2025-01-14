package com.pulley.freewheelin.application.worksheet.controller

import com.pulley.freewheelin.application.worksheet.response.WorksheetAnalysisResponse
import com.pulley.freewheelin.application.worksheet.response.WorksheetProblemResponse
import com.pulley.freewheelin.application.worksheet.service.WorksheetQueryService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/worksheets")
class WorksheetQueryController(private val worksheetQueryService: WorksheetQueryService) {

    @Operation(summary = "학습지 문제 조회")
    @GetMapping("/{worksheetId}/problems")
    fun findProblemsByWorksheetId(
        @PathVariable worksheetId: Long,
    ): ResponseEntity<List<WorksheetProblemResponse>> {
        val problems = worksheetQueryService.findByWorksheetId(worksheetId)
        val responses = problems.map { WorksheetProblemResponse.from(it) }
        return ResponseEntity.ok(responses)
    }

    @Operation(summary = "학습지 통계 분석 조회")
    @GetMapping("/{worksheetId}/analyze")
    fun findWorksheetAnalysisById(
        @PathVariable worksheetId: Long,
    ): ResponseEntity<WorksheetAnalysisResponse> {
        val analysis = worksheetQueryService.findAnalysisByWorksheetId(worksheetId)
        return ResponseEntity.ok(WorksheetAnalysisResponse.from(analysis))
    }
}
