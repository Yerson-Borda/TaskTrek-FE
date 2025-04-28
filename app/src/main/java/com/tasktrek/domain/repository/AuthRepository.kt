package com.tasktrek.domain.repository

import com.tasktrek.data.model.request.auth.LoginRequest
import com.tasktrek.data.model.request.auth.RegisterRequest
import com.tasktrek.data.model.response.auth.AuthResponse
import com.tasktrek.data.model.response.auth.RegistrationResponse

interface AuthRepository {
    suspend fun register(registerRequest: RegisterRequest): RegistrationResponse
    suspend fun login(loginRequest: LoginRequest): AuthResponse
}