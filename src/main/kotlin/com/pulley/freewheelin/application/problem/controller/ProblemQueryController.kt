package com.pulley.freewheelin.application.problem.controller

import com.pulley.freewheelin.application.problem.response.ProblemResponse
import com.pulley.freewheelin.application.problem.service.ProblemQueryService
import com.pulley.freewheelin.domain.enums.DifficultyLevel
import com.pulley.freewheelin.domain.enums.ProblemType
import com.pulley.freewheelin.domain.enums.UnitCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/problems")
class ProblemQueryController(private val problemQueryService: ProblemQueryService) {

    @GetMapping
    fun findAllProblems(
        @RequestParam totalCount: Int,
        @RequestParam unitCodeList: String,
        @RequestParam level: DifficultyLevel,
        @RequestParam problemType: ProblemType,
    ): ResponseEntity<List<ProblemResponse>> {
        val problems = problemQueryService.findAllProblems()
        val responses = problems.map { ProblemResponse.from(it) }
        return ResponseEntity.ok(responses)
    }
}
