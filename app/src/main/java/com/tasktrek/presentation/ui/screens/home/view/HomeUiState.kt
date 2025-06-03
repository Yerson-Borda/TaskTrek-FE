package com.tasktrek.presentation.ui.screens.home.view

import com.tasktrek.domain.model.ProjectListItemResult
import com.tasktrek.domain.model.TaskListItemResult
import com.tasktrek.domain.model.UserResult

data class HomeUiState(
    val projects: List<ProjectListItemResult> = emptyList(),
    val tasks: List<TaskListItemResult> = emptyList(),
    val searchQuery: String = "",
    val user: UserResult? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

