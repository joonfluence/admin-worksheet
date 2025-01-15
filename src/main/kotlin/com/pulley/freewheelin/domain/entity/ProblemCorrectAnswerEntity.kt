package com.pulley.freewheelin.domain.entity

import com.pulley.freewheelin.domain.entity.base.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.Table

@Entity
@Table(
    name = "problem_correct_answers",
    indexes = [
        Index(name = "idx_problem_id", columnList = "problem_id"),
        Index(name = "idx_selection_id", columnList = "selection_id")
    ]
)
class ProblemCorrectAnswerEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long = 0,
    val problemId: Long,
    val answer: String?,
    val selectionId: Long?,
) : BaseEntity()
