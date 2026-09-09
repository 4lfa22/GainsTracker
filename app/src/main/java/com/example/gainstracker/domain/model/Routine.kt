package com.example.gainstracker.domain.model

data class Routine(
    val id: Long = 0,
    val userId: Long,
    val name: String,
    val description: String? = null
)