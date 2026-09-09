package com.example.gainstracker.domain.repository

import com.example.gainstracker.domain.model.BodyMeasurement
import com.example.gainstracker.domain.model.Exercise
import com.example.gainstracker.domain.model.Routine
import com.example.gainstracker.domain.model.User
import com.example.gainstracker.domain.model.WorkoutSession
import com.example.gainstracker.domain.model.WorkoutSet
import kotlinx.coroutines.flow.Flow

interface WorkoutRepository {

    // Usuarios
    fun getAllUsers(): Flow<List<User>>
    fun getActiveUser(): Flow<User?>
    suspend fun getActiveUserOnce(): User?
    suspend fun insertUser(user: User): Long
    suspend fun setActiveUser(userId: Long)

    // Ejercicios
    fun getExercisesForUser(userId: Long): Flow<List<Exercise>>
    suspend fun insertExercise(exercise: Exercise): Long

    // Rutinas
    fun getRoutinesForUser(userId: Long): Flow<List<Routine>>
    suspend fun insertRoutine(routine: Routine): Long
    suspend fun addExerciseToRoutine(routineId: Long, exerciseId: Long)

    // Sesiones
    fun getActiveSession(userId: Long): Flow<WorkoutSession?>
    suspend fun getActiveSessionOnce(userId: Long): WorkoutSession?
    fun getCompletedSessions(userId: Long): Flow<List<WorkoutSession>>
    suspend fun insertSession(session: WorkoutSession): Long
    suspend fun completeSession(sessionId: Long, endTime: Long = System.currentTimeMillis())

    // Series
    fun getSetsForSession(sessionId: Long): Flow<List<WorkoutSet>>
    suspend fun insertSet(set: WorkoutSet): Long
    suspend fun updateSetCompletion(setId: Long, isCompleted: Boolean)
    suspend fun deleteSet(setId: Long)

    // Medidas
    fun getBodyMeasurements(userId: Long): Flow<List<BodyMeasurement>>
    suspend fun insertBodyMeasurement(measurement: BodyMeasurement): Long
}