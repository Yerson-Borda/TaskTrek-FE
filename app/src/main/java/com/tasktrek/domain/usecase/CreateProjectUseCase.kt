package com.tasktrek.domain.usecase

import com.tasktrek.domain.model.CreateProjectDomainModel
import com.tasktrek.domain.model.ProjectResult
import com.tasktrek.domain.repository.ProjectRepository

class CreateProjectUseCase (
    private val projectRepository: ProjectRepository
) {
    suspend operator fun invoke(createProject: CreateProjectDomainModel): ProjectResult {
        return projectRepository.createProject(createProject)
    }
}