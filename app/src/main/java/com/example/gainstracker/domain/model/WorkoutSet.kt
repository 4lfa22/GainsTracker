package com.example.gainstracker.domain.model

data class WorkoutSet(
    val id: Long = 0,
    val sessionId: Long,
    val exerciseId: Long,
    val setNumber: Int = 1,
    val reps: Int,
    val weightKg: Double,
    val isCompleted: Boolean = false
)