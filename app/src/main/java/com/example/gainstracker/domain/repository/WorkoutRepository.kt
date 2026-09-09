package com.example.gainstracker.domain.repository

import com.example.gainstracker.domain.model.Exercise
import com.example.gainstracker.domain.model.Routine
import com.example.gainstracker.domain.model.WorkoutSession
import kotlinx.coroutines.flow.Flow

interface WorkoutRepository {

    // Ejercicios
    fun getExercisesForUser(userId: Long): Flow<List<Exercise>>
    suspend fun insertExercise(exercise: Exercise): Long

    // Rutinas
    fun getRoutinesForUser(userId: Long): Flow<List<Routine>>
    suspend fun insertRoutine(routine: Routine): Long
    suspend fun addExerciseToRoutine(
        routineId: Long,
        exerciseId: Long,
        orderIndex: Int,
        targetSets: Int,
        targetReps: Int
    )

    // Sesiones
    fun getActiveSession(userId: Long): Flow<WorkoutSession?>
    suspend fun getActiveSessionOnce(userId: Long): WorkoutSession?
    fun getCompletedSessions(userId: Long): Flow<List<WorkoutSession>>
    suspend fun insertSession(session: WorkoutSession): Long
    suspend fun completeSession(sessionId: Long)
    fun getActiveUser(): Flow<com.example.gainstracker.domain.model.User?>
}