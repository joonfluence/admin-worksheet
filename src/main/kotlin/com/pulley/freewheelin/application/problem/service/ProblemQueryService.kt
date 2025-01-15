package com.pulley.freewheelin.application.problem.service

import com.pulley.freewheelin.application.problem.dto.ProblemDto
import com.pulley.freewheelin.application.problem.dto.ProblemSearchDto
import com.pulley.freewheelin.application.problem.request.ProblemSearchRequest
import com.pulley.freewheelin.domain.repository.ProblemRepository
import org.springframework.data.domain.PageImpl
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ProblemQueryService(
    private val problemRepository: ProblemRepository
) {

    fun findAllProblems(request: ProblemSearchRequest): PageImpl<ProblemDto> {
        val dto = ProblemSearchDto.from(request)
        val problems = problemRepository.searchAllProblemsByDto(dto)
        return PageImpl(problems.content.map { ProblemDto.from(it) })
    }
}
