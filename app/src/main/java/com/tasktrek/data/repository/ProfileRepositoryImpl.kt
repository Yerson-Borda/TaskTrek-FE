package com.tasktrek.data.repository

import com.tasktrek.data.mapper.toDomain
import com.tasktrek.domain.model.UserResult
import com.tasktrek.domain.repository.ProfileRepository
import com.tasktrek.network.ProfileApiService

class ProfileRepositoryImpl(
    private val profileApiService: ProfileApiService
): ProfileRepository {
    override suspend fun getProfile(): UserResult {
        return profileApiService.getProfile().toDomain()
    }
}