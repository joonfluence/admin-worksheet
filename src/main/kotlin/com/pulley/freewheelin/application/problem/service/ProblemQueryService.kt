package com.pulley.freewheelin.application.problem.service

import com.pulley.freewheelin.application.problem.dto.ProblemDto
import com.pulley.freewheelin.domain.repository.ProblemRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ProblemQueryService(
    private val problemRepository: ProblemRepository
) {

    fun findAllProblems(): List<ProblemDto> {
        val entities = problemRepository.findAll()
        return entities.map { ProblemDto.from(it) }
    }
}