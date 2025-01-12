package com.pulley.freewheelin.global.config

import com.pulley.freewheelin.domain.enums.ProblemType
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class ProblemTypeConverter : Converter<String, ProblemType> {
    override fun convert(source: String): ProblemType? {
        return ProblemType.valueOf(source)
    }
}
