package com.example.gainstracker.data.mapper

import com.example.gainstracker.data.local.entities.ExerciseEntity
import com.example.gainstracker.data.local.entities.RoutineEntity
import com.example.gainstracker.data.local.entities.WorkoutSessionEntity
import com.example.gainstracker.domain.model.Exercise
import com.example.gainstracker.domain.model.Routine
import com.example.gainstracker.domain.model.WorkoutSession
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test
import java.time.LocalDateTime
import java.time.ZoneId

class WorkoutMapperTest {

    @Test
    fun exerciseEntity_toDomain_mapsFieldsCorrectly() {
        val entity = ExerciseEntity(
            id = 10L,
            name = "Press Banca",
            muscleGroup = "Pecho",
            equipment = "Barra"
        )

        val domain = entity.toDomain(userId = 1L)

        assertEquals(10L, domain.id)
        assertEquals("Press Banca", domain.name)
        assertEquals("Pecho", domain.muscleGroup)
        assertEquals("Barra", domain.equipment)
        assertEquals(1L, domain.userId)
    }

    @Test
    fun exerciseDomain_toEntity_mapsFieldsCorrectly() {
        val domain = Exercise(
            id = 5L,
            name = "Dominadas",
            muscleGroup = "Espalda",
            equipment = "Peso Corporal",
            userId = 1L
        )

        val entity = domain.toEntity()

        assertEquals(5L, entity.id)
        assertEquals("Dominadas", entity.name)
        assertEquals("Espalda", entity.muscleGroup)
        assertEquals("Peso Corporal", entity.equipment)
    }

    @Test
    fun workoutSessionEntity_toDomain_convertsTimestampToLocalDateTimeCorrectly() {
        val nowMillis = System.currentTimeMillis()
        val entity = WorkoutSessionEntity(
            id = 1L,
            userId = 1L,
            routineName = "Torso Fuerza",
            startTime = nowMillis,
            endTime = null,
            isCompleted = false
        )

        val domain = entity.toDomain()

        assertEquals(1L, domain.id)
        assertEquals("Torso Fuerza", domain.routineName)
        assertNotNull(domain.startTime)
        assertNull(domain.endTime)
        assertEquals(false, domain.isCompleted)
    }

    @Test
    fun workoutSessionDomain_toEntity_convertsLocalDateTimeToTimestampCorrectly() {
        val nowDateTime = LocalDateTime.now()
        val domain = WorkoutSession(
            id = 2L,
            userId = 1L,
            routineName = "Pierna",
            startTime = nowDateTime,
            endTime = null,
            isCompleted = true
        )

        val entity = domain.toEntity()

        val expectedMillis = nowDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        assertEquals(2L, entity.id)
        assertEquals("Pierna", entity.routineName)
        assertEquals(expectedMillis, entity.startTime)
        assertNull(entity.endTime)
        assertEquals(true, entity.isCompleted)
    }
}