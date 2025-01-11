package com.pulley.freewheelin.domain.entity.base

import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import java.time.LocalDateTime

@MappedSuperclass
class BaseEntity(
    var createdAt: LocalDateTime? = null,
    var createdBy: String? = null,
    var updatedAt: LocalDateTime? = null,
    var updatedBy: String? = null
) {
    @PrePersist
    fun prePersist() {
        val now = LocalDateTime.now()
        createdAt = now
        updatedAt = now
    }

    @PreUpdate
    fun preUpdate() {
        updatedAt = LocalDateTime.now()
    }
}