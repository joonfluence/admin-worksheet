package com.pulley.freewheelin.domain.repository

import com.pulley.freewheelin.application.problem.dto.ProblemSearchDto
import com.pulley.freewheelin.domain.entity.ProblemEntity
import org.springframework.data.domain.Page

interface ProblemRepositoryCustom {
    fun searchAllProblemsByDto(
        dto: ProblemSearchDto
    ): Page<ProblemEntity>
}
