package com.tasktrek.data.mapper

import com.tasktrek.data.model.response.auth.RegistrationResponse
import com.tasktrek.domain.model.RegistrationResult

fun RegistrationResponse.toDomain() = RegistrationResult(
    success = success,
    message = message
)