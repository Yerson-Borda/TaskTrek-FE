package com.tasktrek.presentation.ui.screens.task.view

import com.tasktrek.domain.model.TaskResult

data class TaskUiState (
    val isLoading: Boolean = false,
    val task: TaskResult? = null,
    val errorMessage: String? = null
)