package com.tasktrek.domain.usecase

import com.tasktrek.domain.model.UserResult
import com.tasktrek.domain.repository.ProfileRepository

class GetProfileUseCase(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(): UserResult {
        return profileRepository.getProfile()
    }
}