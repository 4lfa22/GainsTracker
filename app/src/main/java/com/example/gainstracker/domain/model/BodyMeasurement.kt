package com.example.gainstracker.domain.model

import java.time.LocalDateTime

data class BodyMeasurement(
    val id: Long = 0,
    val userId: Long,
    val weightKg: Double,
    val bodyFatPercentage: Double? = null,
    val date: LocalDateTime = LocalDateTime.now()
)