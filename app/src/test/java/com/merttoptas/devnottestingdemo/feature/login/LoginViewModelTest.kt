package com.merttoptas.devnottestingdemo.feature.login

import com.merttoptas.devnottestingdemo.core.data.model.LoginResponse
import com.merttoptas.devnottestingdemo.core.data.repository.TestLoginRepository
import com.merttoptas.devnottestingdemo.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by mertcantoptas on 15.04.2023
 */
@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val loginRepository = TestLoginRepository()

    private lateinit var viewModel: LoginViewModel


    @Before
    fun setUp() {
        viewModel = LoginViewModel(
            loginRepository = loginRepository
        )
    }

    @Test
    fun `onUserNameChanged should update the state with new username`() {
        val newUserName = "john"
        viewModel.onUserNameChanged(newUserName)

        assertEquals(newUserName, viewModel.currentState.userName)
    }

    @Test
    fun `onPasswordChanged should update the state with new password`() {
        val newPassword = "password"
        viewModel.onPasswordChanged(newPassword)

        assertEquals(newPassword, viewModel.currentState.password)
    }

    @Test
    fun `onLoginClicked should set isLoading to true when username and password are not empty and password is valid`() =
        runTest {
            val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.uiState.collect() }
            // Given
            viewModel.onUserNameChanged("john")
            viewModel.onPasswordChanged("123456")

            // When
            viewModel.onLoginClicked()

            // Then
            delay(300)
            assertEquals(true, viewModel.currentState.isLoading)
            loginRepository.sendLogin(sampleResponse)
            collectJob.cancel()
        }

    @Test
    fun `onLoginClicked should set isErrorUserName to true when username is empty`() {
        val invalidUserName = ""
        viewModel.setState { viewModel.currentState.copy(userName = invalidUserName) }
        viewModel.onLoginClicked()
        assertTrue(viewModel.currentState.isErrorUserName)
    }

    @Test
    fun `onLoginClicked should set isErrorPassword to true when password is empty`() {
        val invalidPassword = ""
        viewModel.setState { viewModel.currentState.copy(password = invalidPassword) }

        viewModel.onLoginClicked()

        assertTrue(viewModel.currentState.isErrorPassword)
    }

    @Test
    fun `onLoginClicked should set isErrorPassword to true when password length is invalid`() {
        val invalidPassword = "123"
        viewModel.setState { viewModel.currentState.copy(password = invalidPassword) }

        viewModel.onLoginClicked()

        assertTrue(viewModel.currentState.isErrorPassword)
    }

    @Test
    fun `createInitialState should return an instance of LoginUiState with default values`() {
        val expectedState = LoginUiState()

        assertEquals(expectedState, viewModel.createInitialState())
    }

    @Test
    fun `login with invalid password should set error flag`() = runTest {
        // Given
        viewModel.onUserNameChanged("testuser")
        viewModel.onPasswordChanged("123")

        // When
        viewModel.onLoginClicked()

        // Then
        val currentState = viewModel.uiState.value
        assertEquals(
            LoginUiState(
                userName = "testuser",
                password = "123",
                isLoading = false,
                isErrorUserName = false,
                isErrorPassword = true,
                navigateToHome = false
            ),
            currentState
        )
    }

    @Test
    fun `login with valid credentials should navigate to home`() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.uiState.collect() }
        // Given
        viewModel.onUserNameChanged("testuser")
        viewModel.onPasswordChanged("testpassword")

        // When
        viewModel.onLoginClicked()
        loginRepository.sendLogin(sampleResponse)

        // Then
        assertEquals(viewModel.uiState.value.copy(navigateToHome = true), viewModel.uiState.value)

        collectJob.cancel()

        /*
        uiState akışını dinleyen bir collectJob başlatılır.
        Test kullanıcısı adı ve şifre girer.
    onLoginClicked yöntemi çağrılır ve bu da loginRepository'nin sendLogin yöntemine sampleResponse parametresiyle tetikleme gönderir.
    Sonuç olarak, ViewModel, uiState akışını gözlemleyen bir yöntem tarafından tüketilir ve uiState akışındaki son değer, beklendiği gibi navigateToHome özelliğinin doğru ayarlanmasıyla kontrol edilir.
    En sonunda collectJob iptal edilir.
         */
    }
}

private val sampleResponse = LoginResponse("token")
