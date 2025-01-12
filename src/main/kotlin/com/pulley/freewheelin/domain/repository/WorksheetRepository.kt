package com.pulley.freewheelin.domain.repository

import com.pulley.freewheelin.domain.entity.WorksheetEntity
import org.springframework.data.jpa.repository.JpaRepository

interface WorksheetRepository : JpaRepository<WorksheetEntity, Long>
