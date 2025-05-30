package com.tasktrek.data.repository

import com.tasktrek.data.mapper.toDomain
import com.tasktrek.domain.model.TaskListItemResult
import com.tasktrek.domain.repository.TaskRepository
import com.tasktrek.network.TaskApiService

class TaskRepositoryImpl(
    private val taskApiService: TaskApiService
): TaskRepository {
    override suspend fun getTasks(): List<TaskListItemResult> {
        return taskApiService.getTasks().map {
            it.toDomain()
        }
    }
}