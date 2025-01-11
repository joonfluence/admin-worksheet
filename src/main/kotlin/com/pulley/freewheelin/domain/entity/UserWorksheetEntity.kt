package com.pulley.freewheelin.domain.entity

import com.pulley.freewheelin.application.worksheet.dto.UserWorksheetDto
import com.pulley.freewheelin.application.worksheet.dto.WorksheetDto
import com.pulley.freewheelin.domain.entity.base.BaseEntity

import jakarta.persistence.*
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy
import org.mapstruct.factory.Mappers
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
) : BaseEntity() {
    companion object {
        fun from(dto: UserWorksheetDto): UserWorksheetEntity {
            return UserWorksheetEntityMapper.INSTANCE.from(dto)
        }
    }
}

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface UserWorksheetEntityMapper {
    fun from(dto: UserWorksheetDto): UserWorksheetEntity

    companion object {
        val INSTANCE: UserWorksheetEntityMapper = Mappers.getMapper(UserWorksheetEntityMapper::class.java)
    }
}