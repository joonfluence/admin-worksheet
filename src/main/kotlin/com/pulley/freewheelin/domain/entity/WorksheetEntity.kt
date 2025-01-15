package com.pulley.freewheelin.domain.entity

import com.pulley.freewheelin.application.worksheet.dto.WorksheetDto
import com.pulley.freewheelin.domain.entity.base.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.Table
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy
import org.mapstruct.factory.Mappers

@Entity
@Table(
    name = "worksheets",
    indexes = [
        Index(name = "idx_user_id", columnList = "user_id")
    ]
)
class WorksheetEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long = 0,
    val title: String,
    val description: String?,
    val userId: Long,
) : BaseEntity() {
    companion object {
        fun of(
            title: String,
            description: String?,
            userId: Long,
        ): WorksheetEntity {
            return WorksheetEntity(
                title = title,
                description = description,
                userId = userId,
            )
        }

        fun from(dto: WorksheetDto): WorksheetEntity {
            return WorksheetEntityMapper.INSTANCE.from(dto)
        }
    }
}

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface WorksheetEntityMapper {
    fun from(dto: WorksheetDto): WorksheetEntity

    companion object {
        val INSTANCE: WorksheetEntityMapper = Mappers.getMapper(WorksheetEntityMapper::class.java)
    }
}
