package com.tasktrek.network

import com.tasktrek.data.model.request.project.CreateProjectRequest
import com.tasktrek.data.model.request.project.JoinProjectRequest
import com.tasktrek.data.model.response.project.ProjectListItemResponse
import com.tasktrek.data.model.response.project.ProjectResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ProjectApiService {
    @GET("/projects")
    suspend fun getProjects(): List<ProjectListItemResponse>

    @POST("/projects")
    suspend fun createProject(@Body createProjectRequest: CreateProjectRequest): ProjectResponse

    @POST("/projects/join")
    suspend fun joinProject(@Body joinProjectRequest: JoinProjectRequest): Boolean
}