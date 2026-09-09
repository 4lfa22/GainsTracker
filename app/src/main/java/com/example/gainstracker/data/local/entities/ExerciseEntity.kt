package com.example.gainstracker.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercises")
data class ExerciseEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val muscleGroup: String, // ej: "Chest", "Back", "Legs"
    val equipment: String,   // ej: "Barbell", "Dumbbell", "Machine"
    val isCustom: Boolean = false
)