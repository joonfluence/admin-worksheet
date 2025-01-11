package com.pulley.freewheelin.application.worksheet.service

import com.pulley.freewheelin.application.worksheet.dto.UserWorksheetDto
import com.pulley.freewheelin.application.worksheet.dto.WorksheetDto
import com.pulley.freewheelin.application.worksheet.request.UserWorksheetCreateRequestDto
import com.pulley.freewheelin.application.worksheet.request.WorksheetCreateRequestDto
import com.pulley.freewheelin.domain.entity.UserWorksheetEntity
import com.pulley.freewheelin.domain.entity.WorksheetEntity
import com.pulley.freewheelin.domain.entity.exception.BadRequestException
import com.pulley.freewheelin.domain.repository.UserWorksheetRepository
import com.pulley.freewheelin.domain.repository.WorksheetRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class WorksheetCommandService(
    private val worksheetRepository: WorksheetRepository,
    private val userWorksheetRepository: UserWorksheetRepository,
) {

    fun saveWorksheet(request: WorksheetCreateRequestDto): WorksheetDto {
        val dto = WorksheetDto.from(request)
        val worksheet = worksheetRepository.save(WorksheetEntity.from(dto))
        return WorksheetDto.fromEntity(worksheet)
    }

    fun saveUserWorksheet(worksheetId: Long, request: UserWorksheetCreateRequestDto): List<UserWorksheetDto> {
        validateWorksheet(worksheetId)
        val dtos = request.userIds.map { UserWorksheetDto.from(it, worksheetId) }
        val worksheets = dtos.map { UserWorksheetEntity.from(it) }
        val worksheet = userWorksheetRepository.saveAll(worksheets)
        return worksheet.map { UserWorksheetDto.fromEntity(it) }
    }

    private fun validateWorksheet(worksheetId: Long) {
        worksheetRepository.existsById(worksheetId).takeIf { it }
            ?: throw BadRequestException("학습지가 존재하지 않습니다")
    }
}