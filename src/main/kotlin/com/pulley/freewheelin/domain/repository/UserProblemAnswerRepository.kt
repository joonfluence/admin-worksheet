package com.pulley.freewheelin.domain.repository

import com.pulley.freewheelin.domain.entity.StudentProblemAnswerEntity
import org.springframework.data.jpa.repository.JpaRepository

interface StudentProblemAnswerRepository : JpaRepository<StudentProblemAnswerEntity, Long> {
    fun findByUserId(userId: Long): List<StudentProblemAnswerEntity>
    fun findByProblemId(problemId: Long): List<StudentProblemAnswerEntity>
    fun findByUserIdAndProblemId(userId: Long, problemId: Long): StudentProblemAnswerEntity?
}