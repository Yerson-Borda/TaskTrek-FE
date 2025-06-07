package com.tasktrek.domain.repository

import com.tasktrek.domain.model.CreateProjectDomainModel
import com.tasktrek.domain.model.JoinProjectDomainModel
import com.tasktrek.domain.model.JoinProjectResult
import com.tasktrek.domain.model.ProjectListItemResult
import com.tasktrek.domain.model.ProjectResult

interface ProjectRepository {
    suspend fun getProjects(): List<ProjectListItemResult>

    suspend fun getProjectById(projectId: String): ProjectResult

    suspend fun createProject(createProject: CreateProjectDomainModel): ProjectResult

    suspend fun joinProject(joinProject: JoinProjectDomainModel): JoinProjectResult
}