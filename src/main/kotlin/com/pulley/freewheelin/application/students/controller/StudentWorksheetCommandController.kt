package com.pulley.freewheelin.application.students.controller

import com.pulley.freewheelin.application.students.service.StudentWorksheetCommandService
import com.pulley.freewheelin.application.worksheet.request.StudentProblemGradingRequestDto
import com.pulley.freewheelin.application.worksheet.request.StudentWorksheetCreateRequestDto
import com.pulley.freewheelin.application.worksheet.response.StudentProblemAnswerResponse
import com.pulley.freewheelin.application.worksheet.response.StudentWorksheetResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/users/worksheets")
class StudentWorksheetCommandController(private val userWorksheetCommandService: StudentWorksheetCommandService) {

    @PostMapping("/{worksheetId}")
    fun saveUserWorksheet(
        @PathVariable worksheetId: Long,
        @RequestBody request: StudentWorksheetCreateRequestDto,
    ): ResponseEntity<List<StudentWorksheetResponse>> {
        val worksheets = userWorksheetCommandService.saveUserWorksheet(worksheetId, request)
        val responses = worksheets.map { StudentWorksheetResponse.from(it) }
        return ResponseEntity.ok(responses)
    }

    @PutMapping("/{worksheetId}/problems/grade")
    fun gradeUserWorksheet(
        @PathVariable worksheetId: Long,
        @RequestBody request: StudentProblemGradingRequestDto,
    ): ResponseEntity<List<StudentProblemAnswerResponse>> {
        val problems = userWorksheetCommandService.gradeUserWorksheet(worksheetId, request)
        val responses = problems.map { StudentProblemAnswerResponse.from(it) }
        return ResponseEntity.ok(responses)
    }
}