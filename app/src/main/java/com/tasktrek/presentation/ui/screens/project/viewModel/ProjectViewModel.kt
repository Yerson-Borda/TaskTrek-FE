package com.tasktrek.presentation.ui.screens.project.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasktrek.domain.usecase.GetProjectByIdUseCase
import com.tasktrek.presentation.ui.screens.project.view.ProjectUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class ProjectViewModel(
    private val getProjectByIdUseCase: GetProjectByIdUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProjectUiState())
    val uiState: StateFlow<ProjectUiState> = _uiState

    fun loadProject(projectId: UUID) {
        viewModelScope.launch {
            _uiState.value = ProjectUiState(isLoading = true)
            try {
                val project = getProjectByIdUseCase(projectId.toString())
                _uiState.value = ProjectUiState(project = project)
            } catch (e: Exception) {
                _uiState.value = ProjectUiState(errorMessage = e.message ?: "Failed to load project")
            }
        }
    }
}