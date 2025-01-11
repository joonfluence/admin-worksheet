package com.pulley.freewheelin.domain.entity

import jakarta.persistence.*

@Entity
@Table(name = "problems")
class ProblemEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long = 0,
    val unitCode: String,
    val level: Int,
    val type: String,
    val answer: String
)