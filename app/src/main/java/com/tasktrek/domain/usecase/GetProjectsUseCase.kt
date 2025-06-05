package com.tasktrek.domain.usecase

import com.tasktrek.domain.model.ProjectListItemResult
import com.tasktrek.domain.repository.ProjectRepository

class GetProjectsUseCase (
    private val projectRepository: ProjectRepository
) {
    suspend operator fun invoke(): List<ProjectListItemResult> {
        return projectRepository.getProjects()
    }
}