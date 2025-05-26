package com.tasktrek.presentation.ui.screens.auth.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasktrek.domain.model.LoginDomainModel
import com.tasktrek.domain.model.RegisterDomainModel
import com.tasktrek.domain.usecase.LoginUseCase
import com.tasktrek.domain.usecase.RegisterUseCase
import com.tasktrek.presentation.ui.screens.auth.view.AuthState
import com.tasktrek.utils.TokenManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val registerUseCase: RegisterUseCase,
    private val loginUseCase: LoginUseCase,
    private val tokenManager: TokenManager
) : ViewModel() {

    var username by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var isLoginMode by mutableStateOf(true)

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    fun register() {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val result = registerUseCase(
                    RegisterDomainModel(username, email, password)
                )
                _authState.value =
                    if (result.success) AuthState.RegistrationSuccess
                    else AuthState.Error(result.message ?: "Registration failed")
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Registration failed")
            }
        }
    }

    fun login() {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val result = loginUseCase(LoginDomainModel(email, password))
                tokenManager.saveToken(result.token)
                _authState.value = AuthState.Success(result)
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Login failed")
            }
        }
    }

    fun resetInputs() {
        username = ""
        email = ""
        password = ""
    }
}