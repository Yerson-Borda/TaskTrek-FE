package com.tasktrek.domain.repository

import com.tasktrek.domain.model.TaskListItemResult

interface TaskRepository {
    suspend fun getTasks(): List<TaskListItemResult>
}