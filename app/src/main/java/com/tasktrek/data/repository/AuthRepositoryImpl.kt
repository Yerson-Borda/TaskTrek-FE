package com.tasktrek.data.repository

import com.tasktrek.data.model.request.auth.LoginRequest
import com.tasktrek.data.model.request.auth.RegisterRequest
import com.tasktrek.data.model.response.auth.AuthResponse
import com.tasktrek.data.model.response.auth.RegistrationResponse
import com.tasktrek.domain.repository.AuthRepository
import com.tasktrek.network.AuthApiService

class AuthRepositoryImpl(
    private val authApiService: AuthApiService
) : AuthRepository {
    override suspend fun register(registerRequest: RegisterRequest): RegistrationResponse {
        val response = authApiService.register(registerRequest)
        if (!response.isSuccessful) {
            val errorBody = response.errorBody()?.string() ?: "Unknown error"
            return RegistrationResponse(false, "Registration failed: $errorBody")
        }
        return RegistrationResponse(true, "Registration successful")
    }

    override suspend fun login(loginRequest: LoginRequest): AuthResponse {
        val response = authApiService.login(loginRequest)
        if (!response.isSuccessful) {
            val errorBody = response.errorBody()?.string() ?: "Unknown error"
            throw Exception("Login failed: $errorBody")
        }
        return response.body() ?: throw Exception("Empty response body")
    }
}