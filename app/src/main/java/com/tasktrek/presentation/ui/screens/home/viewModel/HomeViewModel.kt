package com.tasktrek.presentation.ui.screens.home.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasktrek.domain.model.CreateProjectDomainModel
import com.tasktrek.domain.model.JoinProjectDomainModel
import com.tasktrek.domain.usecase.CreateProjectUseCase
import com.tasktrek.domain.usecase.GetProfileUseCase
import com.tasktrek.domain.usecase.GetProjectsUseCase
import com.tasktrek.domain.usecase.GetTasksUseCase
import com.tasktrek.domain.usecase.JoinProjectUseCase
import com.tasktrek.presentation.ui.screens.home.view.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.UUID

class HomeViewModel(
    private val taskUseCase: GetTasksUseCase,
    private val projectUseCase: GetProjectsUseCase,
    private val getProfileUseCase: GetProfileUseCase,
    private val createProjectUseCase: CreateProjectUseCase,
    private val joinProjectUseCase: JoinProjectUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState(isLoading = true))
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        fetchData()
    }

    fun updateSearchQuery(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
    }

    private fun fetchData() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)
                val profile = getProfileUseCase()
                val projects = projectUseCase()
                val tasks = taskUseCase()

                _uiState.value = HomeUiState(
                    user = profile,
                    projects = projects,
                    tasks = tasks
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Unexpected error"
                )
            }
        }
    }

    fun createProject(title: String, deadline: String, onComplete: (UUID) -> Unit) {
        viewModelScope.launch {
            try {
                val parsedDate = LocalDateTime.parse(deadline)
                val model = CreateProjectDomainModel(title = title, endDate = parsedDate)
                val createdProject = createProjectUseCase(model)
                fetchData()
                onComplete(createdProject.id)
            } catch (_: Exception) {
                throw Exception("Invalid date format")
            }
        }
    }


    fun joinProject(code: String, onComplete: () -> Unit) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
                val model = JoinProjectDomainModel(code = code)
                joinProjectUseCase(model)
                fetchData()
                onComplete()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Failed to join project"
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}
