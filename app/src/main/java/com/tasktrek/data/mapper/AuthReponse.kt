package com.tasktrek.data.mapper

import com.tasktrek.data.model.response.auth.AuthResponse
import com.tasktrek.domain.model.AuthResult

fun AuthResponse.toDomain() = AuthResult(
    token = token
)