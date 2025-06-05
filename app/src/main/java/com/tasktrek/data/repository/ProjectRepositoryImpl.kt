package com.tasktrek.data.repository

import com.tasktrek.data.mapper.toDomain
import com.tasktrek.data.mapper.toRequest
import com.tasktrek.domain.model.CreateProjectDomainModel
import com.tasktrek.domain.model.JoinProjectDomainModel
import com.tasktrek.domain.model.ProjectListItemResult
import com.tasktrek.domain.model.ProjectResult
import com.tasktrek.domain.repository.ProjectRepository
import com.tasktrek.network.ProjectApiService

class ProjectRepositoryImpl(
    private val projectApiService: ProjectApiService
) : ProjectRepository {

    override suspend fun getProjects(): List<ProjectListItemResult> {
        return projectApiService.getProjects().map { it.toDomain() }
    }

    override suspend fun createProject(createProject: CreateProjectDomainModel): ProjectResult {
        val response = projectApiService.createProject(createProject.toRequest())
        return response.toDomain()
    }

    override suspend fun joinProject(joinProject: JoinProjectDomainModel): Boolean {
        return projectApiService.joinProject(joinProject.toRequest())
    }
}