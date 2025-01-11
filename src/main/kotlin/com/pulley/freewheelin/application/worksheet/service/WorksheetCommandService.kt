package com.pulley.freewheelin.application.worksheet.service

import com.pulley.freewheelin.application.worksheet.dto.WorksheetDto
import com.pulley.freewheelin.application.worksheet.request.WorksheetCreateRequestDto
import com.pulley.freewheelin.domain.entity.WorksheetEntity
import com.pulley.freewheelin.domain.repository.WorksheetRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class WorksheetCommandService(
    private val worksheetRepository: WorksheetRepository,
) {

    fun saveWorksheet(request: WorksheetCreateRequestDto): WorksheetDto {
        val dto = WorksheetDto.from(request)
        val worksheet = worksheetRepository.save(WorksheetEntity.from(dto))
        return WorksheetDto.fromEntity(worksheet)
    }
}