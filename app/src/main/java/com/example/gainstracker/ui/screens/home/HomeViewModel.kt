package com.example.gainstracker.ui.screens.home

import androidx.lifecycle.ViewModel
import com.example.gainstracker.domain.repository.WorkoutRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val workoutRepository: WorkoutRepository
) : ViewModel() {

    // A partir de aquí utilizaremos workoutRepository para exponer el estado a la UI
}