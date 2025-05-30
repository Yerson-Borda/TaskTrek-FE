package com.tasktrek.data.mapper

import com.tasktrek.data.model.response.project.UserResponse
import com.tasktrek.domain.model.UserResult

fun UserResponse.toDomain() = UserResult(
    id = id,
    username = username,
    profileImage = profileImage
)