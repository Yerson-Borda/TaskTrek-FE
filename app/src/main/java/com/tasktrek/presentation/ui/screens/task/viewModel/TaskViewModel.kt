package com.tasktrek.presentation.ui.screens.task.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasktrek.domain.usecase.GetTaskByIdUseCase
import com.tasktrek.presentation.ui.screens.task.view.TaskUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class TaskViewModel(
    private val getTaskByIdUseCase: GetTaskByIdUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TaskUiState())
    val uiState: StateFlow<TaskUiState> = _uiState

    fun loadTask(taskId: UUID) {
        viewModelScope.launch {
            _uiState.value = TaskUiState(isLoading = true)
            try {
                val task = getTaskByIdUseCase(taskId.toString())
                _uiState.value = TaskUiState(task = task)
            } catch (e: Exception) {
                _uiState.value = TaskUiState(errorMessage = e.message ?: "Failed to load task")
            }
        }
    }
}