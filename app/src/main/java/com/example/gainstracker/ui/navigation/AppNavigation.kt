package com.example.gainstracker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gainstracker.ui.screens.exercise.ExerciseLibraryScreen
import com.example.gainstracker.ui.screens.welcome.WelcomeScreen

sealed class Screen(val route: String) {
    object Welcome: Screen("Welcome")
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
        startDestination = Screen.Welcome.route
    ) {
        composable(Screen.Welcome.route) {
            WelcomeScreen(
                onNavigateToLogin = { /* TODO: Navegar a LoginScreen */ },
                onNavigateToRegister = { /* TODO: Navegar a RegisterScreen */ },
                onContinueAsGuest = {
                    navController.navigate(Screen.ExerciseLibrary.route) {
                        // Limpiar el backstack para que no vuelva a la bienvenida al pulsar atrás
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.ExerciseLibrary.route) {
            ExerciseLibraryScreen()
        }
    }
}