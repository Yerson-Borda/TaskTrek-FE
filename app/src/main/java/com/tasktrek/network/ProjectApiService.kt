package com.tasktrek.network

import com.tasktrek.domain.model.ProjectListItemResult
import retrofit2.http.GET

interface ProjectApiService {
    @GET("/projects")
    suspend fun getProjects(): List<ProjectListItemResult>
}