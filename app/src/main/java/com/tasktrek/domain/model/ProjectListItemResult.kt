package com.tasktrek.domain.model

import com.tasktrek.data.model.response.project.UserResponse
import java.time.LocalDateTime
import java.util.UUID

data class ProjectListItemResult (
    val id: UUID,
    val title: String,
    val description: String?,
    val endDate: LocalDateTime,
    val tasksToComplete: Int,
    val completedTasks: Int,
    val members: List<UserResponse>
)