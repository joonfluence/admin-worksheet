package com.pulley.freewheelin.domain.entity

import com.pulley.freewheelin.application.worksheet.dto.StudentWorksheetDto
import com.pulley.freewheelin.domain.entity.base.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy
import org.mapstruct.factory.Mappers

@Entity
@Table(
    name = "user_worksheets",
    uniqueConstraints = [UniqueConstraint(columnNames = ["user_id", "worksheet_id"])]
)
class StudentWorksheetEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long = 0,
    @Column(name = "user_id")
    val userId: Long,
    @Column(name = "worksheet_id")
    val worksheetId: Long,
) : BaseEntity() {
    companion object {
        fun from(dto: StudentWorksheetDto): StudentWorksheetEntity {
            return StudentWorksheetEntityMapper.INSTANCE.from(dto)
        }
    }
}

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface StudentWorksheetEntityMapper {
    fun from(dto: StudentWorksheetDto): StudentWorksheetEntity

    companion object {
        val INSTANCE: StudentWorksheetEntityMapper = Mappers.getMapper(StudentWorksheetEntityMapper::class.java)
    }
}
