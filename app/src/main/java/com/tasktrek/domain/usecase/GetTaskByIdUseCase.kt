package com.tasktrek.domain.usecase

import com.tasktrek.domain.model.TaskResult
import com.tasktrek.domain.repository.TaskRepository

class GetTaskByIdUseCase(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(taskId: String): TaskResult {
        return taskRepository.getTaskById(taskId)
    }
}