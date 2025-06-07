package com.tasktrek.presentation.ui.screens.project.view

import com.tasktrek.domain.model.ProjectResult

data class ProjectUiState(
    val isLoading: Boolean = false,
    val project: ProjectResult? = null,
    val errorMessage: String? = null
)