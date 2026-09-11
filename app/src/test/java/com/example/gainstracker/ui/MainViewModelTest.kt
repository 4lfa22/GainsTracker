package com.example.gainstracker.ui

import com.example.gainstracker.data.repository.UserPreferencesRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    private val userPreferencesRepository: UserPreferencesRepository = mockk()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `cuando el usuario no esta autenticado authState emite Unauthenticated`() = runTest {
        every { userPreferencesRepository.isLoggedIn } returns flowOf(false)
        every { userPreferencesRepository.isGuest } returns flowOf(false)

        val viewModel = MainViewModel(userPreferencesRepository)

        // Activamos la suscripcion del StateFlow en backgroundScope para que SharingStarted.WhileSubscribed reaccione
        val collectJob = backgroundScope.launch { viewModel.authState.collect() }
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(AuthState.Unauthenticated, viewModel.authState.value)
    }

    @Test
    fun `cuando el usuario ha iniciado sesion authState emite Authenticated`() = runTest {
        every { userPreferencesRepository.isLoggedIn } returns flowOf(true)
        every { userPreferencesRepository.isGuest } returns flowOf(false)

        val viewModel = MainViewModel(userPreferencesRepository)

        // Activamos la suscripcion del StateFlow en backgroundScope
        val collectJob = backgroundScope.launch { viewModel.authState.collect() }
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(AuthState.Authenticated, viewModel.authState.value)
    }
}