package com.pulley.freewheelin.domain.repository

import com.pulley.freewheelin.domain.entity.WorksheetProblemEntity
import org.springframework.data.jpa.repository.JpaRepository

interface WorksheetProblemRepository : JpaRepository<WorksheetProblemEntity, Long> {
    fun findByWorksheetId(worksheetId: Long): List<WorksheetProblemEntity>
}