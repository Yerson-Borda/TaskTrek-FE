package com.tasktrek.domain.usecase

import com.tasktrek.data.model.request.auth.LoginRequest
import com.tasktrek.data.model.response.auth.AuthResponse
import com.tasktrek.domain.repository.AuthRepository

class LoginUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(loginRequest: LoginRequest): AuthResponse {
        return authRepository.login(loginRequest)
    }
}