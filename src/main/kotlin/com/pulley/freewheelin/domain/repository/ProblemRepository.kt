package com.pulley.freewheelin.domain.repository

import com.pulley.freewheelin.domain.entity.ProblemEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ProblemRepository : JpaRepository<ProblemEntity, Long>, ProblemRepositoryCustom {
    fun findByIdIn(ids: List<Long>): List<ProblemEntity>
}