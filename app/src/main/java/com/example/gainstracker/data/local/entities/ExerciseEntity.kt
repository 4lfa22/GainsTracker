package com.example.gainstracker.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercises")
data class ExerciseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: Long? = null, // null = predeterminado; not null = personalizado
    val name: String,
    val muscleGroup: String,  // ej: "Pecho", "Espalda", "Piernas"
    val equipment: String,    // ej: "Barra", "Mancuernas", "Polea", "Máquina"
    val notes: String? = null
)