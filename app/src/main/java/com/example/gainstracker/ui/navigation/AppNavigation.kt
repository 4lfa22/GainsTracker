package com.example.gainstracker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gainstracker.ui.screens.exercise.ExerciseLibraryScreen

sealed class Screen(val route: String) {
    object ExerciseLibrary : Screen("exercise_library")
    object Workout : Screen("workout")
    object Home : Screen("home")
    object Progress : Screen("progress")
}

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.ExerciseLibrary.route
    ) {
        composable(Screen.ExerciseLibrary.route) {
            ExerciseLibraryScreen(
                onExerciseClick = { exerciseId ->
                    // Navegación futura al detalle o edición
                },
                onAddExerciseClick = {
                    // Navegación futura a la creación de ejercicio
                }
            )
        }
    }
}