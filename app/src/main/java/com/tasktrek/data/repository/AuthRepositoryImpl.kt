package com.tasktrek.data.repository

import com.tasktrek.data.model.request.auth.LoginRequest
import com.tasktrek.data.model.request.auth.RegisterRequest
import com.tasktrek.domain.model.AuthResult
import com.tasktrek.domain.model.LoginDomainModel
import com.tasktrek.domain.model.RegisterDomainModel
import com.tasktrek.domain.model.RegistrationResult
import com.tasktrek.domain.repository.AuthRepository
import com.tasktrek.network.AuthApiService

class AuthRepositoryImpl(
    private val authApiService: AuthApiService
) : AuthRepository {
    override suspend fun register(register: RegisterDomainModel): RegistrationResult {
        val request = RegisterRequest(register.username, register.email, register.password)
        val response = authApiService.register(request)
        return if (response.isSuccessful) {
            RegistrationResult(true, "Registration successful")
        } else {
            val errorBody = response.errorBody()?.string() ?: "Unknown error"
            RegistrationResult(false, errorBody)
        }
    }

    override suspend fun login(login: LoginDomainModel): AuthResult {
        val request = LoginRequest(login.email, login.password)
        val response = authApiService.login(request)
        if (!response.isSuccessful) {
            val errorBody = response.errorBody()?.string() ?: "Unknown error"
            throw Exception("Login failed: $errorBody")
        }
        return AuthResult(response.body()?.token ?: throw Exception("Empty token"))
    }
}