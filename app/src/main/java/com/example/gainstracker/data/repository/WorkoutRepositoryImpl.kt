package com.example.gainstracker.data.repository

import com.example.gainstracker.data.local.dao.WorkoutDao
import com.example.gainstracker.data.local.entities.BodyMeasurementEntity
import com.example.gainstracker.data.local.entities.ExerciseEntity
import com.example.gainstracker.data.local.entities.RoutineEntity
import com.example.gainstracker.data.local.entities.RoutineExerciseCrossRef
import com.example.gainstracker.data.local.entities.UserEntity
import com.example.gainstracker.data.local.entities.WorkoutSessionEntity
import com.example.gainstracker.data.local.entities.WorkoutSetEntity
import com.example.gainstracker.domain.repository.WorkoutRepository
import kotlinx.coroutines.flow.Flow

class WorkoutRepositoryImpl(
    private val workoutDao: WorkoutDao
) : WorkoutRepository {

    // Usuarios
    override fun getAllUsers(): Flow<List<UserEntity>> =
        workoutDao.getAllUsers()

    override fun getActiveUser(): Flow<UserEntity?> =
        workoutDao.getActiveUser()

    override suspend fun getActiveUserOnce(): UserEntity? =
        workoutDao.getActiveUserOnce()

    override suspend fun insertUser(user: UserEntity): Long =
        workoutDao.insertUser(user)

    override suspend fun setActiveUser(userId: Long) =
        workoutDao.setActiveUser(userId)

    // Ejercicios
    override fun getExercisesForUser(userId: Long): Flow<List<ExerciseEntity>> =
        workoutDao.getExercisesForUser(userId)

    override suspend fun insertExercise(exercise: ExerciseEntity): Long =
        workoutDao.insertExercise(exercise)

    // Rutinas
    override fun getRoutinesForUser(userId: Long): Flow<List<RoutineEntity>> =
        workoutDao.getRoutinesForUser(userId)

    override suspend fun insertRoutine(routine: RoutineEntity): Long =
        workoutDao.insertRoutine(routine)

    override suspend fun insertRoutineExerciseCrossRef(crossRef: RoutineExerciseCrossRef) =
        workoutDao.insertRoutineExerciseCrossRef(crossRef)

    override suspend fun getRoutineExercises(routineId: Long): List<RoutineExerciseCrossRef> =
        workoutDao.getRoutineExercises(routineId)

    // Sesiones
    override fun getActiveSession(userId: Long): Flow<WorkoutSessionEntity?> =
        workoutDao.getActiveSession(userId)

    override suspend fun getActiveSessionOnce(userId: Long): WorkoutSessionEntity? =
        workoutDao.getActiveSessionOnce(userId)

    override fun getCompletedSessions(userId: Long): Flow<List<WorkoutSessionEntity>> =
        workoutDao.getCompletedSessions(userId)

    override suspend fun insertSession(session: WorkoutSessionEntity): Long =
        workoutDao.insertSession(session)

    override suspend fun completeSession(sessionId: Long, endTime: Long) =
        workoutDao.completeSession(sessionId, endTime)

    // Series
    override fun getSetsForSession(sessionId: Long): Flow<List<WorkoutSetEntity>> =
        workoutDao.getSetsForSession(sessionId)

    override suspend fun insertSet(set: WorkoutSetEntity): Long =
        workoutDao.insertSet(set)

    override suspend fun updateSetCompletion(setId: Long, isCompleted: Boolean) =
        workoutDao.updateSetCompletion(setId, isCompleted)

    override suspend fun deleteSet(setId: Long) =
        workoutDao.deleteSet(setId)

    // Medidas
    override fun getBodyMeasurements(userId: Long): Flow<List<BodyMeasurementEntity>> =
        workoutDao.getBodyMeasurements(userId)

    override suspend fun insertBodyMeasurement(measurement: BodyMeasurementEntity): Long =
        workoutDao.insertBodyMeasurement(measurement)
}