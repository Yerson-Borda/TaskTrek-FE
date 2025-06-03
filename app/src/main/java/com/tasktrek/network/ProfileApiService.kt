package com.tasktrek.network

import com.tasktrek.data.model.response.project.UserResponse
import retrofit2.http.GET

interface ProfileApiService {
    @GET("profile")
    suspend fun getProfile(): UserResponse
}