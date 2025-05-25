package com.tasktrek.domain.repository

import com.tasktrek.domain.model.AuthResult
import com.tasktrek.domain.model.LoginDomainModel
import com.tasktrek.domain.model.RegisterDomainModel
import com.tasktrek.domain.model.RegistrationResult

interface AuthRepository {
    suspend fun register(register: RegisterDomainModel): RegistrationResult
    suspend fun login(login: LoginDomainModel): AuthResult
}