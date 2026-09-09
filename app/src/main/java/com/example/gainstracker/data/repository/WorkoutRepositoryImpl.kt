package com.example.gainstracker.data.repository

import com.example.gainstracker.data.local.dao.WorkoutDao
import com.example.gainstracker.data.local.entities.BodyMeasurementEntity
import com.example.gainstracker.data.local.entities.ExerciseEntity
import com.example.gainstracker.data.local.entities.RoutineEntity
import com.example.gainstracker.data.local.entities.RoutineExerciseCrossRef
import com.example.gainstracker.data.local.entities.UserEntity
import com.example.gainstracker.data.local.entities.WorkoutSessionEntity
import com.example.gainstracker.data.local.entities.WorkoutSetEntity
import com.example.gainstracker.domain.model.BodyMeasurement
import com.example.gainstracker.domain.model.Exercise
import com.example.gainstracker.domain.model.Routine
import com.example.gainstracker.domain.model.User
import com.example.gainstracker.domain.model.WorkoutSession
import com.example.gainstracker.domain.model.WorkoutSet
import com.example.gainstracker.domain.repository.WorkoutRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.Instant
import java.time.ZoneId
import javax.inject.Inject

class WorkoutRepositoryImpl @Inject constructor(
    private val workoutDao: WorkoutDao
) : WorkoutRepository {

    // Usuarios
    override fun getAllUsers(): Flow<List<User>> =
        workoutDao.getAllUsers().map { list -> list.map { it.toDomain() } }

    override fun getActiveUser(): Flow<User?> =
        workoutDao.getActiveUser().map { it?.toDomain() }

    override suspend fun getActiveUserOnce(): User? =
        workoutDao.getActiveUserOnce()?.toDomain()

    override suspend fun insertUser(user: User): Long =
        workoutDao.insertUser(user.toEntity())

    override suspend fun setActiveUser(userId: Long) =
        workoutDao.setActiveUser(userId)

    // Ejercicios
    override fun getExercisesForUser(userId: Long): Flow<List<Exercise>> =
        workoutDao.getExercisesForUser(userId).map { list -> list.map { it.toDomain() } }

    override suspend fun insertExercise(exercise: Exercise): Long =
        workoutDao.insertExercise(exercise.toEntity())

    // Rutinas
    override fun getRoutinesForUser(userId: Long): Flow<List<Routine>> =
        workoutDao.getRoutinesForUser(userId).map { list -> list.map { it.toDomain() } }

    override suspend fun insertRoutine(routine: Routine): Long =
        workoutDao.insertRoutine(routine.toEntity())

    override suspend fun addExerciseToRoutine(routineId: Long, exerciseId: Long) {
        val crossRef = RoutineExerciseCrossRef(
            routineId = routineId,
            exerciseId = exerciseId,
            orderInRoutine = 0,
            targetSets = 3,
            targetRepRange = "8-12",
            targetRestSeconds = 90
        )
        workoutDao.insertRoutineExerciseCrossRef(crossRef)
    }

    // Sesiones
    override fun getActiveSession(userId: Long): Flow<WorkoutSession?> =
        workoutDao.getActiveSession(userId).map { it?.toDomain() }

    override suspend fun getActiveSessionOnce(userId: Long): WorkoutSession? =
        workoutDao.getActiveSessionOnce(userId)?.toDomain()

    override fun getCompletedSessions(userId: Long): Flow<List<WorkoutSession>> =
        workoutDao.getCompletedSessions(userId).map { list -> list.map { it.toDomain() } }

    override suspend fun insertSession(session: WorkoutSession): Long =
        workoutDao.insertSession(session.toEntity())

    override suspend fun completeSession(sessionId: Long, endTime: Long) =
        workoutDao.completeSession(sessionId, endTime)

    // Series
    override fun getSetsForSession(sessionId: Long): Flow<List<WorkoutSet>> =
        workoutDao.getSetsForSession(sessionId).map { list -> list.map { it.toDomain() } }

    override suspend fun insertSet(set: WorkoutSet): Long =
        workoutDao.insertSet(set.toEntity())

    override suspend fun updateSetCompletion(setId: Long, isCompleted: Boolean) =
        workoutDao.updateSetCompletion(setId, isCompleted)

    override suspend fun deleteSet(setId: Long) =
        workoutDao.deleteSet(setId)

    // Medidas
    override fun getBodyMeasurements(userId: Long): Flow<List<BodyMeasurement>> =
        workoutDao.getBodyMeasurements(userId).map { list -> list.map { it.toDomain() } }

    override suspend fun insertBodyMeasurement(measurement: BodyMeasurement): Long =
        workoutDao.insertBodyMeasurement(measurement.toEntity())
}

// ============================================================================
// MAPPERS EXACTOS CON MANEJO DE NULOS
// ============================================================================

private fun UserEntity.toDomain() = User(
    id = id,
    name = name
)

private fun User.toEntity() = UserEntity(
    id = id,
    name = name
)

private fun ExerciseEntity.toDomain() = Exercise(
    id = id,
    userId = userId ?: 0L,
    name = name,
    muscleGroup = muscleGroup,
    equipment = equipment ?: "Ninguno", // Soluciona String? vs String
    notes = notes
)

private fun Exercise.toEntity() = ExerciseEntity(
    id = id,
    userId = userId,
    name = name,
    muscleGroup = muscleGroup,
    equipment = equipment,
    notes = notes
)

private fun RoutineEntity.toDomain() = Routine(
    id = id,
    userId = userId,
    name = name,
    description = description
)

private fun Routine.toEntity() = RoutineEntity(
    id = id,
    userId = userId,
    name = name,
    description = description
)

private fun WorkoutSessionEntity.toDomain() = WorkoutSession(
    id = id,
    userId = userId,
    routineId = routineId,
    routineName = routineName,
    startTime = Instant.ofEpochMilli(startTime).atZone(ZoneId.systemDefault()).toLocalDateTime(),
    endTime = endTime?.let { Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDateTime() },
    isCompleted = isCompleted
)

private fun WorkoutSession.toEntity() = WorkoutSessionEntity(
    id = id,
    userId = userId,
    routineId = routineId ?: 0L, // Soluciona Long? vs Long en la entidad
    routineName = routineName ?: "",
    startTime = startTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
    endTime = endTime?.atZone(ZoneId.systemDefault())?.toInstant()?.toEpochMilli(),
    isCompleted = isCompleted
)

private fun WorkoutSetEntity.toDomain() = WorkoutSet(
    id = id,
    sessionId = sessionId,
    exerciseId = exerciseId,
    setNumber = setNumber,
    reps = reps,
    weightKg = weightKg ?: 0.0, // Soluciona Double? vs Double
    isCompleted = isCompleted
)

private fun WorkoutSet.toEntity() = WorkoutSetEntity(
    id = id,
    sessionId = sessionId,
    exerciseId = exerciseId,
    setNumber = setNumber,
    reps = reps,
    weightKg = weightKg,
    isCompleted = isCompleted
)

private fun BodyMeasurementEntity.toDomain() = BodyMeasurement(
    id = id,
    userId = userId,
    weightKg = weightKg ?: 0.0,
    bodyFatPercentage = null, // Si BodyMeasurementEntity no tiene grasa corporal
    date = Instant.ofEpochMilli(date).atZone(ZoneId.systemDefault()).toLocalDateTime()
)

private fun BodyMeasurement.toEntity() = BodyMeasurementEntity(
    id = id,
    userId = userId,
    weightKg = weightKg,
    date = date.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
)