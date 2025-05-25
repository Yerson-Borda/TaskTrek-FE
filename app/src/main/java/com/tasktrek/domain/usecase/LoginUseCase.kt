package com.tasktrek.domain.usecase

import com.tasktrek.domain.model.AuthResult
import com.tasktrek.domain.model.LoginDomainModel
import com.tasktrek.domain.repository.AuthRepository

class LoginUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(login: LoginDomainModel): AuthResult {
        return authRepository.login(login)
    }
}