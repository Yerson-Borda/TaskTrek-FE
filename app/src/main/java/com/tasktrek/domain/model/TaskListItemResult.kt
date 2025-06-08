package com.tasktrek.domain.model

import java.time.LocalDateTime
import java.util.UUID

data class TaskListItemResult (
    val id: UUID,
    val title: String,
    val endDate: LocalDateTime,
    val complete: Boolean
)