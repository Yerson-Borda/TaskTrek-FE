package com.tasktrek.domain.usecase

import com.tasktrek.domain.model.RegisterDomainModel
import com.tasktrek.domain.model.RegistrationResult
import com.tasktrek.domain.repository.AuthRepository

class RegisterUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(register: RegisterDomainModel): RegistrationResult {
        return authRepository.register(register)
    }
}