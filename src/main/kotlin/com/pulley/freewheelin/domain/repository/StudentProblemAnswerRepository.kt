package com.pulley.freewheelin.domain.repository

import com.pulley.freewheelin.domain.entity.StudentProblemAnswerEntity
import org.springframework.data.jpa.repository.JpaRepository

interface StudentProblemAnswerRepository : JpaRepository<StudentProblemAnswerEntity, Long> {
    fun findByProblemIdIn(problemIds: List<Long>): List<StudentProblemAnswerEntity>
}
