package com.example.gainstracker.ui.screens.home

import com.example.gainstracker.domain.model.WorkoutSession

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data object Empty : HomeUiState
    data class Success(val sessions: List<WorkoutSession>) : HomeUiState
    data class Error(val message: String) : HomeUiState
}