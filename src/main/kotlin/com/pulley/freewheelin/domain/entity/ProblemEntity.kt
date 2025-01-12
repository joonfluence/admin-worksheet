package com.pulley.freewheelin.domain.entity

import com.pulley.freewheelin.domain.entity.base.BaseEntity
import com.pulley.freewheelin.domain.enums.ProblemType
import com.pulley.freewheelin.domain.enums.UnitCode
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "problems")
class ProblemEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long = 0,
    val title: String?,
    val description: String?,
    @Enumerated(EnumType.STRING)
    val unitCode: UnitCode,
    val level: Int,
    @Enumerated(EnumType.STRING)
    val type: ProblemType,
) : BaseEntity() {
    companion object {
        fun of(
            unitCode: UnitCode,
            level: Int,
            type: ProblemType,
            title: String?,
            description: String?,
        ) = ProblemEntity(
            unitCode = unitCode,
            level = level,
            type = type,
            title = title,
            description = description,
        )
    }
}