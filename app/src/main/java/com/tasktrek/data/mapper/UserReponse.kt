package com.tasktrek.data.mapper

import com.tasktrek.data.model.response.project.UserResponse
import com.tasktrek.domain.model.UserResult
import java.util.UUID

fun UserResponse.toDomain() = UserResult(
    id = id ?: UUID.randomUUID(),
    username = username,
    profileImage = profileImage
)