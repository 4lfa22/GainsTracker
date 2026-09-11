package com.example.gainstracker.ui.screens.auth

import com.example.gainstracker.data.repository.UserPreferencesRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    private val userPreferencesRepository: UserPreferencesRepository = mockk(relaxed = true)
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = LoginViewModel(userPreferencesRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `login con campos vacios actualiza errorMessage en uiState`() {
        viewModel.onEmailChanged("")
        viewModel.onPasswordChanged("")

        var onSuccessCalled = false
        viewModel.login { onSuccessCalled = true }

        assertNotNull(viewModel.uiState.value.errorMessage)
        assertEquals(false, onSuccessCalled)
    }

    @Test
    fun `login con credenciales validas guarda sesion en UserPreferencesRepository`() = runTest {
        val testEmail = "usuario@example.com"
        viewModel.onEmailChanged(testEmail)
        viewModel.onPasswordChanged("123456")

        var onSuccessCalled = false
        viewModel.login { onSuccessCalled = true }
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify(exactly = 1) { userPreferencesRepository.setLoggedIn(testEmail) }
        assertTrue(onSuccessCalled)
    }
}