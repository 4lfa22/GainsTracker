package com.example.gainstracker.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gainstracker.domain.repository.WorkoutRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val workoutRepository: WorkoutRepository
) : ViewModel() {

    val uiState: StateFlow<HomeUiState> = workoutRepository.getActiveUser()
        .flatMapLatest { activeUser ->
            if (activeUser == null) {
                flowOf(HomeUiState.Empty)
            } else {
                workoutRepository.getCompletedSessions(activeUser.id).map { sessions ->
                    if (sessions.isEmpty()) {
                        HomeUiState.Empty
                    } else {
                        HomeUiState.Success(sessions)
                    }
                }
            }
        }
        .catch { throwable ->
            emit(HomeUiState.Error(throwable.message ?: "Error desconocido al cargar sesiones"))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiState.Loading
        )
}