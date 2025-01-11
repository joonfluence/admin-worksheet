package com.pulley.freewheelin.application.problem.controller

import com.pulley.freewheelin.application.problem.dto.ProblemSearchDto
import com.pulley.freewheelin.application.problem.response.ProblemResponse
import com.pulley.freewheelin.application.problem.service.ProblemQueryService
import org.springframework.data.domain.PageImpl
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/problems")
class ProblemQueryController(private val problemQueryService: ProblemQueryService) {

    @GetMapping
    fun findAllProblems(
        dto: ProblemSearchDto
    ): ResponseEntity<PageImpl<ProblemResponse>> {
        val problems = problemQueryService.findAllProblems(dto)
        val responses = problems.content.map { ProblemResponse.from(it) }
        return ResponseEntity.ok(
            PageImpl(responses)
        )
    }
}
