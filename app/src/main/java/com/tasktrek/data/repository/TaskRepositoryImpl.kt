package com.tasktrek.data.repository

import com.tasktrek.data.mapper.toDomain
import com.tasktrek.data.mapper.toRequest
import com.tasktrek.domain.model.CreateTaskDomainModel
import com.tasktrek.domain.model.TaskListItemResult
import com.tasktrek.domain.model.TaskResult
import com.tasktrek.domain.repository.TaskRepository
import com.tasktrek.network.TaskApiService

class TaskRepositoryImpl(
    private val taskApiService: TaskApiService
): TaskRepository {
    override suspend fun getTasks(): List<TaskListItemResult> {
        return taskApiService.getTasks().map { it.toDomain() }
    }

    override suspend fun getTaskById(taskId: String): TaskResult {
        return taskApiService.getTaskById(taskId).toDomain()
    }

    override suspend fun createTask(createTask: CreateTaskDomainModel): TaskResult {
        return taskApiService.createTask(createTask.toRequest()).toDomain()
    }
}