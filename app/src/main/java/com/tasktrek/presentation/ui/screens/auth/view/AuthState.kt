package com.tasktrek.presentation.ui.screens.auth.view

import com.tasktrek.domain.model.AuthResult

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object RegistrationSuccess : AuthState()
    data class Success(val authResult: AuthResult) : AuthState()
    data class Error(val message: String) : AuthState()
}