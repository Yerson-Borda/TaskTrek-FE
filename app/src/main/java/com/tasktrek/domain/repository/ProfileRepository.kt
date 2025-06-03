package com.tasktrek.domain.repository

import com.tasktrek.domain.model.UserResult

interface ProfileRepository {
    suspend fun getProfile(): UserResult
}