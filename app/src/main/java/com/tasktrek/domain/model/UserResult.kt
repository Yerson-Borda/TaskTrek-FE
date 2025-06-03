package com.tasktrek.domain.model

import java.util.UUID

data class UserResult (
    val id: UUID?,
    val username: String,
    val profileImage: String?
)