package com.tasktrek.network

import com.tasktrek.domain.model.TaskListItemResult
import retrofit2.http.GET

interface TaskApiService {
    @GET("/tasks")
    suspend fun getTasks(): List<TaskListItemResult>
}