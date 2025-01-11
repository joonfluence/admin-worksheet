package com.pulley.freewheelin.domain.entity

import com.pulley.freewheelin.domain.entity.base.BaseEntity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "user_worksheets")
class UserWorksheetEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long = 0,
    @Column(name = "user_id")
    val userId: Long,
    @Column(name = "worksheet_id")
    val worksheetId: Long,
) : BaseEntity()