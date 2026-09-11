package com.example.gainstracker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gainstracker.ui.screens.auth.LoginScreen
import com.example.gainstracker.ui.screens.auth.RegisterScreen
import com.example.gainstracker.ui.screens.exercise.ExerciseLibraryScreen
import com.example.gainstracker.ui.screens.welcome.WelcomeScreen

sealed class Screen(val route: String) {
    object Welcome: Screen("Welcome")
    object Login : Screen("login")
    object Register : Screen("register")
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
                onNavigateToLogin = { navController.navigate(Screen.Login.route) },
                onNavigateToRegister = { navController.navigate(Screen.Register.route) },
                onContinueAsGuest = {
                    navController.navigate(Screen.ExerciseLibrary.route) {
                        // Limpiar el backstack para que no vuelva a la bienvenida al pulsar atrás
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.ExerciseLibrary.route) {
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(Screen.ExerciseLibrary.route) {
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.ExerciseLibrary.route) {
            ExerciseLibraryScreen()
        }
    }
}