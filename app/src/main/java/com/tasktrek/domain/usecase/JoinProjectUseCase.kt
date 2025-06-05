package com.tasktrek.domain.usecase

import com.tasktrek.domain.model.JoinProjectDomainModel
import com.tasktrek.domain.repository.ProjectRepository

class JoinProjectUseCase(
    private val repository: ProjectRepository
) {
    suspend operator fun invoke(joinProject: JoinProjectDomainModel): Boolean {
        return repository.joinProject(joinProject)
    }
}