package com.tasktrek.domain.model

import java.time.LocalDateTime

data class TaskListItemResult (
    val title: String,
    val endDate: LocalDateTime,
    val complete: Boolean
)