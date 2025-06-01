package com.tasktrek.data.model.response.project

import java.time.LocalDateTime
import java.util.UUID

data class ProjectListItemResponse(
    val id: UUID,
    val title: String,
    val description: String?,
    val endDate: LocalDateTime,
    val tasksToComplete: Int,
    val completedTasks: Int,
    val members: List<UserResponse>
)