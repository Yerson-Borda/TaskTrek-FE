package com.tasktrek.domain.repository

import com.tasktrek.domain.model.ProjectListItemResult

interface ProjectRepository {
    suspend fun getProjects(): List<ProjectListItemResult>
}