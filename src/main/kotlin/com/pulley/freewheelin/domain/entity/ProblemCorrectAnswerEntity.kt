package com.pulley.freewheelin.domain.entity

import com.pulley.freewheelin.domain.entity.base.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "problem_correct_answers")
class ProblemCorrectAnswerEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long = 0,
    val problemId: Long,
    val answer: String?,
    val selectionId: Long?,
) : BaseEntity()