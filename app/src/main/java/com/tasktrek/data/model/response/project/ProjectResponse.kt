package com.tasktrek.data.model.response.project

import java.time.LocalDateTime
import java.util.UUID

data class ProjectResponse(
    val id: UUID,
    val title: String,
    val code: String,
    val description: String?,
    val endDate: LocalDateTime,
    val complete: Boolean,
    val estimatedTime: Int,
    val tasksToComplete: Int,
    val completedTasks: Int,
    val elapsedTime: Int,
    val owner: UserResponse,
    val members: List<UserResponse>
)