package com.example.gainstracker.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val avatarUri: String? = null,
    val targetWeightKg: Double? = null,
    val isCurrentActive: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)