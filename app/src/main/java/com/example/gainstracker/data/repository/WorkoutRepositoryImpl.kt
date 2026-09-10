package com.example.gainstracker.data.repository

import com.example.gainstracker.data.local.dao.WorkoutDao
import com.example.gainstracker.data.local.entities.RoutineExerciseCrossRef
import com.example.gainstracker.data.mapper.*
import com.example.gainstracker.domain.model.Exercise
import com.example.gainstracker.domain.model.Routine
import com.example.gainstracker.domain.model.User
import com.example.gainstracker.domain.model.WorkoutSession
import com.example.gainstracker.domain.repository.WorkoutRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WorkoutRepositoryImpl @Inject constructor(
    private val workoutDao: WorkoutDao
) : WorkoutRepository {

    // ==========================================
    // USUARIOS
    // ==========================================
    override fun getActiveUser(): Flow<User?> {
        return workoutDao.getActiveUser().map { entity ->
            entity?.let {
                User(
                    id = it.id,
                    name = it.name
                )
            }
        }
    }

    // ==========================================
    // EJERCICIOS
    // ==========================================
    override fun getExercisesForUser(userId: Long): Flow<List<Exercise>> {
        return workoutDao.getExercisesForUser().map { entities ->
            entities.map { it.toDomain(userId) }
        }
    }

    override suspend fun insertExercise(exercise: Exercise): Long {
        return workoutDao.insertExercise(exercise.toEntity())
    }

    // ==========================================
    // RUTINAS
    // ==========================================
    override fun getRoutinesForUser(userId: Long): Flow<List<Routine>> {
        return workoutDao.getRoutinesForUser().map { entities ->
            entities.map { it.toDomain(userId) }
        }
    }

    override suspend fun insertRoutine(routine: Routine): Long {
        return workoutDao.insertRoutine(routine.toEntity())
    }

    override suspend fun addExerciseToRoutine(
        routineId: Long,
        exerciseId: Long,
        orderIndex: Int,
        targetSets: Int,
        targetReps: Int
    ) {
        val crossRef = RoutineExerciseCrossRef(
            routineId = routineId,
            exerciseId = exerciseId,
            orderIndex = orderIndex,
            targetSets = targetSets,
            targetReps = targetReps
        )
        workoutDao.insertRoutineExerciseCrossRef(crossRef)
    }

    // ==========================================
    // SESIONES EN VIVO
    // ==========================================
    override fun getActiveSession(userId: Long): Flow<WorkoutSession?> {
        return workoutDao.getActiveSession(userId).map { entity ->
            entity?.toDomain()
        }
    }

    override suspend fun getActiveSessionOnce(userId: Long): WorkoutSession? {
        return workoutDao.getActiveSessionOnce(userId)?.toDomain()
    }

    override fun getCompletedSessions(userId: Long): Flow<List<WorkoutSession>> {
        return workoutDao.getCompletedSessions(userId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun insertSession(session: WorkoutSession): Long {
        return workoutDao.insertSession(session.toEntity())
    }

    override suspend fun completeSession(sessionId: Long) {
        workoutDao.completeSession(sessionId)
    }
}