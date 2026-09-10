package com.example.gainstracker.ui.screens.exercise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gainstracker.domain.model.Exercise
import com.example.gainstracker.domain.repository.WorkoutRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class ExerciseLibraryUiState(
    val exercises: List<Exercise> = emptyList(),
    val searchQuery: String = "",
    val selectedMuscleGroup: String? = null,
    val isLoading: Boolean = true
)

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ExerciseLibraryViewModel @Inject constructor(
    private val repository: WorkoutRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    private val _selectedMuscleGroup = MutableStateFlow<String?>(null)

    // Obtenemos los ejercicios del usuario activo (por defecto id = 1L)
    private val _exercises = repository.getActiveUser().flatMapLatest { user ->
        val userId = user?.id ?: 1L
        repository.getExercisesForUser(userId)
    }

    val uiState: StateFlow<ExerciseLibraryUiState> = combine(
        _exercises,
        _searchQuery,
        _selectedMuscleGroup
    ) { exercises: List<Exercise>, query: String, muscleGroup: String? ->
        val filtered = exercises.filter { exercise ->
            val matchesQuery = exercise.name.contains(query, ignoreCase = true) ||
                    exercise.muscleGroup.contains(query, ignoreCase = true)
            val matchesMuscle = muscleGroup == null || exercise.muscleGroup.equals(muscleGroup, ignoreCase = true)
            matchesQuery && matchesMuscle
        }

        ExerciseLibraryUiState(
            exercises = filtered,
            searchQuery = query,
            selectedMuscleGroup = muscleGroup,
            isLoading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ExerciseLibraryUiState()
    )

    fun onSearchQueryChange(newQuery: String) {
        _searchQuery.value = newQuery
    }

    fun onMuscleGroupSelected(muscleGroup: String?) {
        _selectedMuscleGroup.value = if (_selectedMuscleGroup.value == muscleGroup) null else muscleGroup
    }
}