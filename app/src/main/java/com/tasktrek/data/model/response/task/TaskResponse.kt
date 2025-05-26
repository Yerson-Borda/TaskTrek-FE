package com.tasktrek.data.model.response.task

import com.tasktrek.data.model.enums.RepeatInterval
import com.tasktrek.data.model.enums.TaskTag
import java.time.LocalDateTime
import java.util.UUID

data class TaskResponse(
    val id: UUID,
    val title: String,
    val description: String?,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val pomodoros: Int,
    val tag: TaskTag,
    val repeat: RepeatInterval?,
    val reminder: LocalDateTime?,
    val note: String?,
    val complete: Boolean
)