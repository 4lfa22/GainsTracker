package com.example.gainstracker.data.local

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.gainstracker.data.local.dao.ExerciseDao
import com.example.gainstracker.data.local.entities.ExerciseEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

class DatabaseCallback @Inject constructor(
    private val exerciseDaoProvider: Provider<ExerciseDao>
) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        CoroutineScope(Dispatchers.IO).launch {
            populateInitialExercises()
        }
    }

    private suspend fun populateInitialExercises() {
        val initialExercises = listOf(
            // Pecho
            ExerciseEntity(name = "Press de Banca con Barra", muscleGroup = "Pecho", equipment = "Barra"),
            ExerciseEntity(name = "Press Inclinado con Mancuernas", muscleGroup = "Pecho", equipment = "Mancuernas"),
            ExerciseEntity(name = "Cruce de Poleas", muscleGroup = "Pecho", equipment = "Polea"),
            // Espalda
            ExerciseEntity(name = "Dominadas", muscleGroup = "Espalda", equipment = "Peso Corporal"),
            ExerciseEntity(name = "Remo con Barra", muscleGroup = "Espalda", equipment = "Barra"),
            ExerciseEntity(name = "Jalón al Pecho", muscleGroup = "Espalda", equipment = "Polea"),
            // Piernas
            ExerciseEntity(name = "Sentadilla Trasera", muscleGroup = "Piernas", equipment = "Barra"),
            ExerciseEntity(name = "Prensa de Piernas", muscleGroup = "Piernas", equipment = "Máquina"),
            ExerciseEntity(name = "Peso Muerto Rumano", muscleGroup = "Piernas", equipment = "Barra"),
            // Hombros
            ExerciseEntity(name = "Press Militar con Barra", muscleGroup = "Hombros", equipment = "Barra"),
            ExerciseEntity(name = "Elevaciones Laterales", muscleGroup = "Hombros", equipment = "Mancuernas"),
            // Brazos
            ExerciseEntity(name = "Curl de Bíceps con Mancuernas", muscleGroup = "Brazos", equipment = "Mancuernas"),
            ExerciseEntity(name = "Extensión de Tríceps en Polea", muscleGroup = "Brazos", equipment = "Polea")
        )

        val dao = exerciseDaoProvider.get()
        initialExercises.forEach { dao.insertExercise(it) }
    }
}