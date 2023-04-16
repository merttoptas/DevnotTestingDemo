package com.merttoptas.devnottestingdemo.feature.login

import androidx.lifecycle.viewModelScope
import com.merttoptas.devnottestingdemo.arch.BaseViewModel
import com.merttoptas.devnottestingdemo.arch.IViewState
import com.merttoptas.devnottestingdemo.core.data.repository.LoginRepository
import com.merttoptas.devnottestingdemo.core.result.Result
import com.merttoptas.devnottestingdemo.core.result.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by mertcantoptas on 14.04.2023
 */
@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository) :
    BaseViewModel<LoginUiState>() {
    override fun createInitialState() = LoginUiState()

    fun onUserNameChanged(userName: String) {
        setState { currentState.copy(userName = userName) }

    }

    fun onPasswordChanged(password: String) {
        setState { currentState.copy(password = password) }
    }

    fun onLoginClicked() {
        viewModelScope.launch {
            if (validate().not()) {
                onLogin()
            }
        }
    }

    private fun validate(): Boolean {
        val userNameEmpty = currentState.userName.isEmpty()
        val passwordEmpty = currentState.password.isEmpty()
        val passwordLengthInvalid = currentState.password.length < 6
        val isValidate = userNameEmpty || passwordEmpty || passwordLengthInvalid

        setState {
            currentState.copy(
                isErrorUserName = userNameEmpty,
                isErrorPassword = passwordEmpty || passwordLengthInvalid,
                isLoading = !userNameEmpty && !passwordEmpty && !passwordLengthInvalid
            )
        }
        return isValidate
    }

    private suspend fun onLogin() {
        loginRepository.login(currentState.userName, currentState.password).asResult()
            .collect {
                when (it) {
                    is Result.Success -> {
                        setState {
                            currentState.copy(
                                isLoading = false,
                                navigateToHome = true
                            )
                        }
                    }

                    Result.Loading -> {
                        setState {
                            currentState.copy(
                                isLoading = true
                            )
                        }
                    }

                    is Result.Error -> {
                        setState {
                            currentState.copy(
                                isLoading = false
                            )
                        }
                    }
                }
            }
    }
}

data class LoginUiState(
    val userName: String = "",
    val password: String = "",
    val isErrorUserName: Boolean = false,
    val isErrorPassword: Boolean = false,
    val isLoading: Boolean = false,
    val navigateToHome: Boolean = false
) : IViewState