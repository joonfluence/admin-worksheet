package com.pulley.freewheelin.domain.entity

import com.pulley.freewheelin.domain.entity.base.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint

@Entity
@Table(
    name = "worksheet_problems",
    uniqueConstraints = [UniqueConstraint(columnNames = ["worksheet_id", "problem_id"])],
    indexes = [
        Index(name = "idx_worksheet_id", columnList = "worksheet_id"),
        Index(name = "idx_problem_id", columnList = "problem_id")
    ]
)
class WorksheetProblemEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val worksheetId: Long,
    val problemId: Long,
) : BaseEntity()