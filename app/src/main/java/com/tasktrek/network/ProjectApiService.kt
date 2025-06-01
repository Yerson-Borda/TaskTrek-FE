package com.tasktrek.network

import com.tasktrek.data.model.response.project.ProjectListItemResponse
import retrofit2.http.GET

interface ProjectApiService {
    @GET("/projects")
    suspend fun getProjects(): List<ProjectListItemResponse>
}