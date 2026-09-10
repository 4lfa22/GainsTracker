package com.example.gainstracker.ui.screens.exercise

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.gainstracker.domain.model.Exercise

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseLibraryScreen(
    viewModel: ExerciseLibraryViewModel = hiltViewModel(),
    onExerciseClick: (Long) -> Unit = {},
    onAddExerciseClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    val muscleGroups = listOf("Pecho", "Espalda", "Pierna", "Hombros", "Brazos", "Core")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Biblioteca de Ejercicios") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddExerciseClick) {
                Icon(Icons.Default.Add, contentDescription = "Añadir Ejercicio")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            // Buscador
            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = viewModel::onSearchQueryChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                placeholder = { Text("Buscar ejercicio...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                singleLine = true
            )

            // Filtros de Grupo Muscular (Chips)
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                items(muscleGroups) { group ->
                    FilterChip(
                        selected = uiState.selectedMuscleGroup == group,
                        onClick = { viewModel.onMuscleGroupSelected(group) },
                        label = { Text(group) }
                    )
                }
            }

            // Lista de Ejercicios
            if (uiState.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (uiState.exercises.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No se encontraron ejercicios", style = MaterialTheme.typography.bodyLarge)
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(uiState.exercises, key = { it.id }) { exercise ->
                        ExerciseItem(
                            exercise = exercise,
                            onClick = { onExerciseClick(exercise.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ExerciseItem(
    exercise: Exercise,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = exercise.name,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SuggestionChip(
                    onClick = { },
                    label = { Text(exercise.muscleGroup) }
                )
                if (exercise.equipment.isNotBlank()) {
                    SuggestionChip(
                        onClick = { },
                        label = { Text(exercise.equipment) }
                    )
                }
            }
        }
    }
}