package com.tasktrek.data.repository

import com.tasktrek.data.mapper.toDomain
import com.tasktrek.domain.model.ProjectListItemResult
import com.tasktrek.domain.repository.ProjectRepository
import com.tasktrek.network.ProjectApiService

class ProjectRepositoryImpl(
    private val projectApiService: ProjectApiService
): ProjectRepository {
    override suspend fun getProjects(): List<ProjectListItemResult> {
        return projectApiService.getProjects().map {
            it.toDomain()
        }
    }
}