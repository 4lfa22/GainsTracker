package com.example.gainstracker.domain.model

import java.time.LocalDateTime

data class Workout(
    val id: Long = 0,
    val name: String,
    val date: LocalDateTime = LocalDateTime.now(),
    val durationInMinutes: Int = 0
)