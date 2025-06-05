package com.tasktrek.domain.usecase

import com.tasktrek.domain.model.TaskListItemResult
import com.tasktrek.domain.repository.TaskRepository

class GetTasksUseCase(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(): List<TaskListItemResult> {
        return taskRepository.getTasks()
    }
}