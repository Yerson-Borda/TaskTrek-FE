package com.tasktrek.data.mapper

import com.tasktrek.data.model.response.project.JoinProjectResponse
import com.tasktrek.domain.model.JoinProjectResult

fun JoinProjectResponse.toDomain() = JoinProjectResult(
    success = success
)