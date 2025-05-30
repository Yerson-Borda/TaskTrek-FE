package com.tasktrek.data.mapper

import com.tasktrek.data.model.response.task.TaskListItemResponse
import com.tasktrek.domain.model.TaskListItemResult

fun TaskListItemResponse.toDomain() = TaskListItemResult(
    title = title,
    endDate = endDate,
    complete = complete
)