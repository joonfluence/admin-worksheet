package com.pulley.freewheelin.domain.entity

import com.pulley.freewheelin.domain.entity.base.BaseEntity
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(
    name = "worksheet_problems",
    uniqueConstraints = [UniqueConstraint(columnNames = ["worksheet_id", "problem_id"])]
)
class WorksheetProblemEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long = 0,

    @Column(name = "worksheet_id", nullable = false)
    val worksheetId: Long,

    @Column(name = "problem_id", nullable = false)
    val problemId: Long,
) : BaseEntity()