package com.pulley.freewheelin.application.worksheet.service

import com.pulley.freewheelin.application.BaseJpaTest
import com.pulley.freewheelin.application.worksheet.request.WorksheetCreateRequest
import com.pulley.freewheelin.domain.repository.WorksheetRepository
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class WorksheetCommandServiceTest : BaseJpaTest() {
    @Autowired
    private lateinit var worksheetRepository: WorksheetRepository
    private lateinit var worksheetCommandService: WorksheetCommandService

    @BeforeTest
    fun setUp() {
        worksheetCommandService = WorksheetCommandService(worksheetRepository)
    }

    @DisplayName("학습지 생성 시, 정상적으로 학습지 데이터가 생성되어야 한다.")
    @Test
    fun test() {
        // given & when
        val worksheet = worksheetCommandService.saveWorksheet(
            WorksheetCreateRequest(
                title = "title",
                description = "description",
                userId = 1L,
            )
        )
        // then
        assertEquals("title", worksheet.title)
        assertEquals("description", worksheet.description)
    }
}
