package com.tasktrek.data.mapper

import com.tasktrek.data.model.response.project.ProjectListItemResponse
import com.tasktrek.domain.model.ProjectListItemResult

fun ProjectListItemResponse.toDomain() = ProjectListItemResult(
    id = id,
    title = title,
    description = description,
    endDate = endDate,
    tasksToComplete = tasksToComplete,
    completedTasks = completedTasks,
    members = members.map { it.toDomain() }
)