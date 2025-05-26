package com.tasktrek.presentation.ui.screens.home.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasktrek.domain.usecase.ProjectUseCase
import com.tasktrek.domain.usecase.TaskUseCase
import com.tasktrek.presentation.ui.screens.home.view.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val taskUseCase: TaskUseCase,
    private val projectUseCase: ProjectUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState(isLoading = true))
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)
                val projects = projectUseCase()
                val tasks = taskUseCase()
                _uiState.value = HomeUiState(projects = projects, tasks = tasks)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Unexpected error"
                )
            }
        }
    }
}
