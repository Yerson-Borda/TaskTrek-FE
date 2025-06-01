package com.tasktrek.presentation.ui.screens.auth.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasktrek.domain.model.LoginDomainModel
import com.tasktrek.domain.model.RegisterDomainModel
import com.tasktrek.domain.usecase.LoginUseCase
import com.tasktrek.domain.usecase.RegisterUseCase
import com.tasktrek.presentation.ui.screens.auth.components.EmailState
import com.tasktrek.presentation.ui.screens.auth.components.PasswordState
import com.tasktrek.presentation.ui.screens.auth.components.UsernameState
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

    private var _isLoginMode = mutableStateOf(true)
    val isLoginMode: Boolean get() = _isLoginMode.value
    fun toggleLoginMode() {
        _isLoginMode.value = !_isLoginMode.value
    }

    val usernameState = UsernameState()
    val emailState = EmailState()
    val passwordState = PasswordState()

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    fun register() {
        usernameState.validate()
        emailState.validate()
        passwordState.validate()

        if (!usernameState.isValid() || !emailState.isValid() || !passwordState.isValid()) return

        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val result = registerUseCase(
                    RegisterDomainModel(usernameState.text, emailState.text, passwordState.text)
                )
                _authState.value = if (result.success) AuthState.RegistrationSuccess
                else AuthState.Error(result.message ?: "Registration failed")
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Registration failed")
            }
        }
    }

    fun login() {
        emailState.validate()
        passwordState.validate()

        if (!emailState.isValid() || !passwordState.isValid()) return

        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val result = loginUseCase(LoginDomainModel(emailState.text, passwordState.text))
                tokenManager.saveToken(result.token)
                _authState.value = AuthState.Success(result)
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Login failed")
            }
        }
    }

    fun resetInputs() {
        usernameState.text = ""
        emailState.text = ""
        passwordState.text = ""
    }
}