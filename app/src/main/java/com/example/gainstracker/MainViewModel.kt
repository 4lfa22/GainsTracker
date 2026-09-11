package com.example.gainstracker.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gainstracker.data.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface AuthState {
    object Loading : AuthState
    object Unauthenticated : AuthState
    object Authenticated : AuthState
}

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    val authState: StateFlow<AuthState> = combine(
        userPreferencesRepository.isLoggedIn,
        userPreferencesRepository.isGuest
    ) { isLoggedIn, isGuest ->
        if (isLoggedIn || isGuest) {
            AuthState.Authenticated
        } else {
            AuthState.Unauthenticated
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = AuthState.Loading
    )

    fun continueAsGuest() {
        viewModelScope.launch {
            userPreferencesRepository.setGuestMode()
        }
    }
}