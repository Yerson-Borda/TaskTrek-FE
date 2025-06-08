package com.tasktrek.presentation.ui.screens.task_creation.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasktrek.data.model.enums.RepeatInterval
import com.tasktrek.data.model.enums.TaskTag
import com.tasktrek.domain.model.CreateTaskDomainModel
import com.tasktrek.domain.usecase.CreateTaskUseCase
import com.tasktrek.presentation.ui.screens.task_creation.view.TaskCreationUiState
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class TaskCreationViewModel(
    private val createTaskUseCase: CreateTaskUseCase
) : ViewModel() {

    var uiState by mutableStateOf(TaskCreationUiState())
        private set

    fun updateTitle(title: String) {
        uiState = uiState.copy(title = title)
    }

    fun updateDescription(desc: String) {
        uiState = uiState.copy(description = desc)
    }

    fun updateStartDate(date: LocalDateTime) {
        uiState = uiState.copy(startDate = date)
    }

    fun updateEndDate(date: LocalDateTime) {
        uiState = uiState.copy(endDate = date)
    }

    fun updatePomodoros(count: Int) {
        uiState = uiState.copy(pomodoros = count)
    }

    fun updateTag(tag: TaskTag) {
        uiState = uiState.copy(tag = tag)
    }

    fun updateRepeat(repeat: RepeatInterval?) {
        uiState = uiState.copy(repeat = repeat)
    }

    fun updateReminder(reminder: LocalDateTime?) {
        uiState = uiState.copy(reminder = reminder)
    }

    fun updateNote(note: String?) {
        uiState = uiState.copy(note = note)
    }

    fun createTask() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            try {
                val result = createTaskUseCase(
                    CreateTaskDomainModel(
                        title = uiState.title,
                        description = uiState.description,
                        startDate = uiState.startDate,
                        endDate = uiState.endDate,
                        pomodoros = uiState.pomodoros,
                        tag = uiState.tag,
                        repeat = uiState.repeat,
                        reminder = uiState.reminder,
                        note = uiState.note
                    )
                )
                uiState = uiState.copy(successTaskId = result.id, isLoading = false)
            } catch (e: Exception) {
                uiState = uiState.copy(errorMessage = e.message, isLoading = false)
            }
        }
    }
}