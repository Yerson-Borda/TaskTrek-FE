package com.tasktrek.network

import com.tasktrek.data.model.request.auth.LoginRequest
import com.tasktrek.data.model.request.auth.RegisterRequest
import com.tasktrek.data.model.response.auth.AuthResponse
import com.tasktrek.data.model.response.auth.RegistrationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("auth/register")
    suspend fun register(@Body registerRequest: RegisterRequest): Response<RegistrationResponse>

    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<AuthResponse>
}