package com.example.gainstracker.domain.model

data class Exercise(
    val id: Long = 0,
    val userId: Long,
    val name: String,
    val muscleGroup: String,
    val equipment: String = "Ninguno",
    val notes: String? = null
)