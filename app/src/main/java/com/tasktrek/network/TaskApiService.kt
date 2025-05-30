package com.tasktrek.network

import com.tasktrek.data.model.response.task.TaskListItemResponse
import retrofit2.http.GET

interface TaskApiService {
    @GET("/tasks")
    suspend fun getTasks(): List<TaskListItemResponse>
}