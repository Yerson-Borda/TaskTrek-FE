package com.tasktrek.data.model.response.task

import java.time.LocalDateTime
import java.util.UUID

data class TaskListItemResponse(
    val id: UUID,
    val title: String,
    val endDate: LocalDateTime,
    val complete: Boolean
)