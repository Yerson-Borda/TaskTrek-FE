package com.tasktrek.domain.usecase

import com.tasktrek.domain.model.TaskListItemResult
import com.tasktrek.domain.repository.TaskRepository

class TaskUseCase(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(): List<TaskListItemResult> {
        return taskRepository.getTasks()
    }
}