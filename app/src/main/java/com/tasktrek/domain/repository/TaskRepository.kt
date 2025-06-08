package com.tasktrek.domain.repository

import com.tasktrek.domain.model.CreateTaskDomainModel
import com.tasktrek.domain.model.TaskListItemResult
import com.tasktrek.domain.model.TaskResult

interface TaskRepository {
    suspend fun getTasks(): List<TaskListItemResult>

    suspend fun getTaskById(taskId: String): TaskResult

    suspend fun createTask(createTask: CreateTaskDomainModel): TaskResult
}