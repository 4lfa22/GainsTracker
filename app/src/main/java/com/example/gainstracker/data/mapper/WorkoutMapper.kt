package com.example.gainstracker.data.mapper

import com.example.gainstracker.data.local.entities.ExerciseEntity
import com.example.gainstracker.data.local.entities.RoutineEntity
import com.example.gainstracker.data.local.entities.WorkoutSessionEntity
import com.example.gainstracker.domain.model.Exercise
import com.example.gainstracker.domain.model.Routine
import com.example.gainstracker.domain.model.WorkoutSession
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

// ==========================================
// EXERCISE MAPPERS
// ==========================================
fun ExerciseEntity.toDomain(userId: Long = 1L): Exercise {
    return Exercise(
        id = id,
        name = name,
        muscleGroup = muscleGroup,
        equipment = equipment,
        userId = userId
    )
}

fun Exercise.toEntity(): ExerciseEntity {
    return ExerciseEntity(
        id = id,
        name = name,
        muscleGroup = muscleGroup,
        equipment = equipment
    )
}

// ==========================================
// ROUTINE MAPPERS
// ==========================================
fun RoutineEntity.toDomain(userId: Long = 1L): Routine {
    return Routine(
        id = id,
        name = name,
        description = description,
        userId = userId
    )
}

fun Routine.toEntity(): RoutineEntity {
    return RoutineEntity(
        id = id,
        name = name,
        description = description
    )
}

// ==========================================
// WORKOUT SESSION MAPPERS
// ==========================================
fun WorkoutSessionEntity.toDomain(routineName: String = "Entrenamiento"): WorkoutSession {
    return WorkoutSession(
        id = id,
        userId = userId,
        routineName = routineName,
        startTime = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(startTime),
            ZoneId.systemDefault()
        ),
        endTime = endTime?.let {
            LocalDateTime.ofInstant(
                Instant.ofEpochMilli(it),
                ZoneId.systemDefault()
            )
        },
        isCompleted = isCompleted
    )
}

fun WorkoutSession.toEntity(): WorkoutSessionEntity {
    return WorkoutSessionEntity(
        id = id,
        userId = userId,
        routineName = routineName ?: "Entrenamiento libre",
        startTime = startTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
        endTime = endTime?.atZone(ZoneId.systemDefault())?.toInstant()?.toEpochMilli(),
        isCompleted = isCompleted
    )
}