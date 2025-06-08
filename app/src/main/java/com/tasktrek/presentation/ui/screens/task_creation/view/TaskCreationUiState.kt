package com.tasktrek.presentation.ui.screens.task_creation.view

import android.net.Uri
import com.tasktrek.data.model.enums.RepeatInterval
import com.tasktrek.data.model.enums.TaskTag
import java.time.LocalDateTime
import java.util.UUID

data class TaskCreationUiState(
    val title: String = "",
    val description: String = "",
    val startDate: LocalDateTime = LocalDateTime.now(),
    val endDate: LocalDateTime = LocalDateTime.now(),
    val pomodoros: Int = 1,
    val tag: TaskTag = TaskTag.UNIVERSITY,
    val repeat: RepeatInterval? = null,
    val reminder: LocalDateTime? = null,
    val note: String? = null,
    val attachments: List<Uri> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successTaskId: UUID? = null
)