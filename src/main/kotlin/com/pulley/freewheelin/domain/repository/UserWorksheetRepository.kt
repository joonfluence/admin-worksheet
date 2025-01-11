package com.pulley.freewheelin.domain.repository

import com.pulley.freewheelin.domain.entity.UserWorksheetEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserWorksheetRepository : JpaRepository<UserWorksheetEntity, Long>