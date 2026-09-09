package com.example.gainstracker.data.local.entities

import androidx.room.Entity

@Entity(
    tableName = "routine_exercise_cross_ref",
    primaryKeys = ["routineId", "exerciseId", "orderInRoutine"]
)
data class RoutineExerciseCrossRef(
    val routineId: Long,
    val exerciseId: Long,
    val orderInRoutine: Int,
    val targetSets: Int,
    val targetRepRange: String,
    val targetRestSeconds: Int
)