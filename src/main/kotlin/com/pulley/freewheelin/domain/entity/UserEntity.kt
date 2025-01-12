package com.pulley.freewheelin.domain.entity

import com.pulley.freewheelin.domain.entity.base.BaseEntity
import com.pulley.freewheelin.domain.enums.UserType
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "users")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val name: String,
    val email: String,
    @Enumerated(EnumType.STRING)
    val type: UserType,
) : BaseEntity()
