package com.tasktrek.data.mapper

import com.tasktrek.data.model.request.task.CreateTaskRequest
import com.tasktrek.domain.model.CreateTaskDomainModel

fun CreateTaskDomainModel.toRequest() = CreateTaskRequest(
    title = title,
    description = description,
    startDate = startDate,
    endDate = endDate,
    pomodoros = pomodoros,
    tag = tag,
    repeat = repeat,
    reminder = reminder,
    note = note,
    projectId = projectId
)