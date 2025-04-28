package com.tasktrek.domain.usecase

import com.tasktrek.data.model.request.auth.RegisterRequest
import com.tasktrek.data.model.response.auth.RegistrationResponse
import com.tasktrek.domain.repository.AuthRepository

class RegisterUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(registerRequest: RegisterRequest): RegistrationResponse {
        return authRepository.register(registerRequest)
    }
}