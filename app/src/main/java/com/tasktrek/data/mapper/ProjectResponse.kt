package com.tasktrek.data.mapper

import com.tasktrek.data.model.response.project.ProjectResponse
import com.tasktrek.domain.model.ProjectResult

fun ProjectResponse.toDomain(): ProjectResult {
    return ProjectResult(
        id = id,
        title = title,
        code = code,
        description = description,
        endDate = endDate,
        complete = complete,
        estimatedTime = estimatedTime,
        tasksToComplete = tasksToComplete,
        completedTasks = completedTasks,
        elapsedTime = elapsedTime,
        owner = owner,
        members = members.map { it.toDomain() }
    )
}