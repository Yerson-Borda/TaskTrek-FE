package com.tasktrek.network

import com.tasktrek.data.model.request.task.CreateTaskRequest
import com.tasktrek.data.model.response.task.TaskListItemResponse
import com.tasktrek.data.model.response.task.TaskResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TaskApiService {
    @GET("/tasks")
    suspend fun getTasks(): List<TaskListItemResponse>

    @GET("/tasks/{id}")
    suspend fun getTaskById(@Path("id") taskId: String): TaskResponse

    @POST("/tasks")
    suspend fun createTask(@Body createTaskRequest: CreateTaskRequest): TaskResponse
}