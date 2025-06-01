package com.tasktrek.data.repository

import com.tasktrek.data.mapper.toDomain
import com.tasktrek.data.mapper.toRequest
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
        val request = register.toRequest()
        val response = authApiService.register(request)

        if (!response.isSuccessful) {
            val errorBody = response.errorBody()?.string() ?: "Unknown error"
            return RegistrationResult(success = false, message = errorBody)
        }

        return response.body()?.toDomain() ?: RegistrationResult(false, "Empty response")
    }

    override suspend fun login(login: LoginDomainModel): AuthResult {
        val request = login.toRequest()
        val response = authApiService.login(request)
        if (!response.isSuccessful) {
            val errorBody = response.errorBody()?.string() ?: "Unknown error"
            throw Exception("Login failed: $errorBody")
        }
        return response.body()?.toDomain() ?: throw Exception("Empty token")    }
}