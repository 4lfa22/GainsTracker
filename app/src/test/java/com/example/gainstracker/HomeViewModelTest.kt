package com.example.gainstracker

import com.example.gainstracker.domain.model.User
import com.example.gainstracker.domain.model.WorkoutSession
import com.example.gainstracker.domain.repository.WorkoutRepository
import com.example.gainstracker.ui.screens.home.HomeUiState
import com.example.gainstracker.ui.screens.home.HomeViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val workoutRepository: WorkoutRepository = mockk()
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun uiState_emits_Success_when_user_has_completed_sessions() = runTest {
        // Given
        val mockUser = User(id = 1L, name = "Alex")
        val mockSessions = listOf(
            WorkoutSession(id = 101L, userId = 1L, startTime = LocalDateTime.now(), isCompleted = true)
        )

        coEvery { workoutRepository.getActiveUser() } returns flowOf(mockUser)
        coEvery { workoutRepository.getCompletedSessions(1L) } returns flowOf(mockSessions)

        // When
        viewModel = HomeViewModel(workoutRepository)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = viewModel.uiState.first { it !is HomeUiState.Loading }
        assertEquals(HomeUiState.Success(mockSessions), state)
    }

    @Test
    fun uiState_emits_Empty_when_user_has_no_completed_sessions() = runTest {
        // Given
        val mockUser = User(id = 1L, name = "Alex")

        coEvery { workoutRepository.getActiveUser() } returns flowOf(mockUser)
        coEvery { workoutRepository.getCompletedSessions(1L) } returns flowOf(emptyList())

        // When
        viewModel = HomeViewModel(workoutRepository)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = viewModel.uiState.first { it !is HomeUiState.Loading }
        assertEquals(HomeUiState.Empty, state)
    }
}