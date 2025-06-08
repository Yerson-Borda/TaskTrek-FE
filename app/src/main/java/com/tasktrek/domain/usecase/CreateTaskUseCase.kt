package com.tasktrek.domain.usecase

import com.tasktrek.domain.model.CreateTaskDomainModel
import com.tasktrek.domain.model.TaskResult
import com.tasktrek.domain.repository.TaskRepository

class CreateTaskUseCase(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(createTask: CreateTaskDomainModel): TaskResult {
        return taskRepository.createTask(createTask)
    }
}