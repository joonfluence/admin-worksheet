package com.pulley.freewheelin.application.worksheet.service

import com.pulley.freewheelin.application.worksheet.dto.WorksheetProblemDto
import com.pulley.freewheelin.domain.repository.WorksheetProblemRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class WorksheetQueryService(
    private val worksheetProblemRepository: WorksheetProblemRepository
) {

    fun findByWorksheetId(worksheetId: Long): List<WorksheetProblemDto> {
        val problems = worksheetProblemRepository.findByWorksheetId(worksheetId)
        return problems.map { WorksheetProblemDto.from(it) }
    }
}