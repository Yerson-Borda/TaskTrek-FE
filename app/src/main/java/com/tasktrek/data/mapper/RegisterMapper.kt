package com.tasktrek.data.mapper

import com.tasktrek.data.model.request.auth.RegisterRequest
import com.tasktrek.domain.model.RegisterDomainModel

fun RegisterDomainModel.toRequest() = RegisterRequest(
    username = username,
    email = email,
    password = password
)