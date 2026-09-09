package com.example.gainstracker.domain.model

import java.time.LocalDateTime

data class WorkoutSession(
    val id: Long = 0,
    val userId: Long,
    val routineId: Long? = null,
    val routineName: String? = null,
    val startTime: LocalDateTime = LocalDateTime.now(),
    val endTime: LocalDateTime? = null,
    val isCompleted: Boolean = false
)