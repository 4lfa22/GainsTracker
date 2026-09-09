package com.example.gainstracker.data.local.entities

import androidx.room.Entity

@Entity(
    tableName = "routine_exercise_cross_ref",
    primaryKeys = ["routineId", "exerciseId", "orderIndex"]
)
data class RoutineExerciseCrossRef(
    val routineId: Long,
    val exerciseId: Long,
    val orderIndex: Int,      // Orden dentro de la rutina
    val targetSets: Int = 3,  // Series objetivo
    val targetReps: Int = 10  // Repeticiones objetivo
)