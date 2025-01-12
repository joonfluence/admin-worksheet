package com.pulley.freewheelin.domain.repository

import com.pulley.freewheelin.domain.entity.ProblemCorrectAnswerEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ProblemCorrectAnswerRepository : JpaRepository<ProblemCorrectAnswerEntity, Long> {
    fun findByProblemIdIn(problemIds: List<Long>): List<ProblemCorrectAnswerEntity>
}
