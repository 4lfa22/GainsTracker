package com.example.gainstracker.di

import android.content.Context
import androidx.room.Room
import com.example.gainstracker.data.local.AppDatabase
import com.example.gainstracker.data.local.DatabaseCallback
import com.example.gainstracker.data.local.dao.ExerciseDao
import com.example.gainstracker.data.local.dao.WorkoutDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
        databaseCallback: DatabaseCallback
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "gains_tracker_db"
        )
            .addCallback(databaseCallback)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideWorkoutDao(database: AppDatabase): WorkoutDao {
        return database.workoutDao()
    }

    @Provides
    fun provideExerciseDao(database: AppDatabase): ExerciseDao {
        return database.exerciseDao()
    }
}