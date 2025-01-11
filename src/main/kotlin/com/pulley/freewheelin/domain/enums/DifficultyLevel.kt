package com.pulley.freewheelin.domain.enums

enum class DifficultyLevel(val description: String) {
    LOW("하"),
    MIDDLE("중"),
    HIGH("상");

    companion object {
        fun fromLevel(level: Int): DifficultyLevel {
            return when (level) {
                1 -> LOW
                2, 3, 4 -> MIDDLE
                5 -> HIGH
                else -> throw IllegalArgumentException("Invalid level: $level")
            }
        }
    }
}