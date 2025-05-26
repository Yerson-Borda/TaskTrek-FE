package com.tasktrek.data.model.response.task

import java.time.LocalDateTime

data class TaskListItemResponse(
    val title: String,
    val endDate: LocalDateTime,
    val complete: Boolean
)