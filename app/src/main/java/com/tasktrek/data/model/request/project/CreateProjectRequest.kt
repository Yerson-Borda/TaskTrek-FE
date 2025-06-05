package com.tasktrek.data.model.request.project

import java.time.LocalDateTime

data class CreateProjectRequest (
    val title: String,
    val endDate: LocalDateTime
)