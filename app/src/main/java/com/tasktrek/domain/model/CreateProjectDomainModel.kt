package com.tasktrek.domain.model

import java.time.LocalDateTime

data class CreateProjectDomainModel (
    val title: String,
    val endDate: LocalDateTime
)
