package com.example.gainstracker.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.gainstracker.data.local.entities.BodyMeasurementEntity
import com.example.gainstracker.data.local.entities.ExerciseEntity
import com.example.gainstracker.data.local.entities.RoutineEntity
import com.example.gainstracker.data.local.entities.RoutineExerciseCrossRef
import com.example.gainstracker.data.local.entities.UserEntity
import com.example.gainstracker.data.local.entities.WorkoutSessionEntity
import com.example.gainstracker.data.local.entities.WorkoutSetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {

    // ==========================================
    // 1. USUARIOS (PerfilUsuario)
    // ==========================================
    @Query("SELECT * FROM users ORDER BY createdAt ASC")
    fun getAllUsers(): Flow<List<UserEntity>>

    @Query("SELECT * FROM users WHERE isCurrentActive = 1 LIMIT 1")
    fun getActiveUser(): Flow<UserEntity?>

    @Query("SELECT * FROM users WHERE isCurrentActive = 1 LIMIT 1")
    suspend fun getActiveUserOnce(): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity): Long

    @Transaction
    suspend fun setActiveUser(userId: Long) {
        clearActiveUsers()
        markUserActive(userId)
    }

    @Query("UPDATE users SET isCurrentActive = 0")
    suspend fun clearActiveUsers()

    @Query("UPDATE users SET isCurrentActive = 1 WHERE id = :userId")
    suspend fun markUserActive(userId: Long)


    // ==========================================
    // 2. EJERCICIOS (BibliotecaEjercicios)
    // ==========================================
    // NOTA: Se elimina 'userId' ya que ExerciseEntity es global en este modelo
    @Query("SELECT * FROM exercises ORDER BY name ASC")
    fun getExercisesForUser(): Flow<List<ExerciseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercise(exercise: ExerciseEntity): Long


    // ==========================================
    // 3. RUTINAS (PlantillaRutina)
    // ==========================================
    // NOTA: Se elimina 'userId' ya que RoutineEntity no tiene esa columna
    @Query("SELECT * FROM routines ORDER BY id DESC")
    fun getRoutinesForUser(): Flow<List<RoutineEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoutine(routine: RoutineEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoutineExerciseCrossRef(crossRef: RoutineExerciseCrossRef)

    // CORREGIDO: Se cambia 'orderInRoutine' por 'orderIndex'
    @Query("SELECT * FROM routine_exercise_cross_ref WHERE routineId = :routineId ORDER BY orderIndex ASC")
    suspend fun getRoutineExercises(routineId: Long): List<RoutineExerciseCrossRef>


    // ==========================================
    // 4. SESIONES EN VIVO (DiarioDeEntrenamientos)
    // ==========================================
    @Query("SELECT * FROM workout_sessions WHERE userId = :userId AND isCompleted = 0 LIMIT 1")
    fun getActiveSession(userId: Long): Flow<WorkoutSessionEntity?>

    @Query("SELECT * FROM workout_sessions WHERE userId = :userId AND isCompleted = 0 LIMIT 1")
    suspend fun getActiveSessionOnce(userId: Long): WorkoutSessionEntity?

    @Query("SELECT * FROM workout_sessions WHERE userId = :userId AND isCompleted = 1 ORDER BY startTime DESC")
    fun getCompletedSessions(userId: Long): Flow<List<WorkoutSessionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: WorkoutSessionEntity): Long

    @Query("UPDATE workout_sessions SET isCompleted = 1, endTime = :endTime WHERE id = :sessionId")
    suspend fun completeSession(sessionId: Long, endTime: Long = System.currentTimeMillis())


    // ==========================================
    // 5. SERIES REALIZADAS (SeriesDelDia)
    // ==========================================
    @Query("SELECT * FROM workout_sets WHERE sessionId = :sessionId ORDER BY id ASC")
    fun getSetsForSession(sessionId: Long): Flow<List<WorkoutSetEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSet(set: WorkoutSetEntity): Long

    @Query("UPDATE workout_sets SET isCompleted = :isCompleted WHERE id = :setId")
    suspend fun updateSetCompletion(setId: Long, isCompleted: Boolean)

    @Query("DELETE FROM workout_sets WHERE id = :setId")
    suspend fun deleteSet(setId: Long)


    // ==========================================
    // 6. MEDIDAS CORPORALES (RegistroBasculaYMedidas)
    // ==========================================
    @Query("SELECT * FROM body_measurements WHERE userId = :userId ORDER BY date DESC")
    fun getBodyMeasurements(userId: Long): Flow<List<BodyMeasurementEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBodyMeasurement(measurement: BodyMeasurementEntity): Long
}