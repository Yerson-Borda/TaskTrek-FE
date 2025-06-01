package com.tasktrek.data.mapper

import com.tasktrek.data.model.request.auth.LoginRequest
import com.tasktrek.domain.model.LoginDomainModel

fun LoginDomainModel.toRequest() = LoginRequest(
    email = email,
    password = password
)