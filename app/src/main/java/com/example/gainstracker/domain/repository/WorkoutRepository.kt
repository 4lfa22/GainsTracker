package com.example.gainstracker.domain.repository

import com.example.gainstracker.data.local.entities.BodyMeasurementEntity
import com.example.gainstracker.data.local.entities.ExerciseEntity
import com.example.gainstracker.data.local.entities.RoutineEntity
import com.example.gainstracker.data.local.entities.RoutineExerciseCrossRef
import com.example.gainstracker.data.local.entities.UserEntity
import com.example.gainstracker.data.local.entities.WorkoutSessionEntity
import com.example.gainstracker.data.local.entities.WorkoutSetEntity
import kotlinx.coroutines.flow.Flow

interface WorkoutRepository {

    // Usuarios
    fun getAllUsers(): Flow<List<UserEntity>>
    fun getActiveUser(): Flow<UserEntity?>
    suspend fun getActiveUserOnce(): UserEntity?
    suspend fun insertUser(user: UserEntity): Long
    suspend fun setActiveUser(userId: Long)

    // Ejercicios
    fun getExercisesForUser(userId: Long): Flow<List<ExerciseEntity>>
    suspend fun insertExercise(exercise: ExerciseEntity): Long

    // Rutinas
    fun getRoutinesForUser(userId: Long): Flow<List<RoutineEntity>>
    suspend fun insertRoutine(routine: RoutineEntity): Long
    suspend fun insertRoutineExerciseCrossRef(crossRef: RoutineExerciseCrossRef)
    suspend fun getRoutineExercises(routineId: Long): List<RoutineExerciseCrossRef>

    // Sesiones
    fun getActiveSession(userId: Long): Flow<WorkoutSessionEntity?>
    suspend fun getActiveSessionOnce(userId: Long): WorkoutSessionEntity?
    fun getCompletedSessions(userId: Long): Flow<List<WorkoutSessionEntity>>
    suspend fun insertSession(session: WorkoutSessionEntity): Long
    suspend fun completeSession(sessionId: Long, endTime: Long = System.currentTimeMillis())

    // Series
    fun getSetsForSession(sessionId: Long): Flow<List<WorkoutSetEntity>>
    suspend fun insertSet(set: WorkoutSetEntity): Long
    suspend fun updateSetCompletion(setId: Long, isCompleted: Boolean)
    suspend fun deleteSet(setId: Long)

    // Medidas
    fun getBodyMeasurements(userId: Long): Flow<List<BodyMeasurementEntity>>
    suspend fun insertBodyMeasurement(measurement: BodyMeasurementEntity): Long
}