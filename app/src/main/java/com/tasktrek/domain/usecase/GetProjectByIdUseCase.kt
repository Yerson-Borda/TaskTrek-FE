package com.tasktrek.domain.usecase

import com.tasktrek.domain.model.ProjectResult
import com.tasktrek.domain.repository.ProjectRepository

class GetProjectByIdUseCase(
    private val projectRepository: ProjectRepository
) {
    suspend operator fun invoke(projectId: String): ProjectResult {
        return projectRepository.getProjectById(projectId)
    }
}