package com.tasktrek.data.mapper

import com.tasktrek.data.model.response.task.TaskResponse
import com.tasktrek.domain.model.TaskResult

fun TaskResponse.toDomain() = TaskResult(
    id = id,
    title = title,
    description = description,
    startDate = startDate,
    endDate = endDate,
    pomodoros = pomodoros,
    tag = tag,
    repeat = repeat,
    reminder = reminder,
    note = note,
    complete = complete
)