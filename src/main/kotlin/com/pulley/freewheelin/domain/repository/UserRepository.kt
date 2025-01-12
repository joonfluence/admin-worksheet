package com.pulley.freewheelin.domain.repository

import com.pulley.freewheelin.domain.entity.UserEntity
import com.pulley.freewheelin.domain.enums.UserType
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<UserEntity, Long> {
    fun findByType(type: UserType): List<UserEntity>
}
