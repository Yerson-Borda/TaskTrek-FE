package com.tasktrek.data.mapper

import com.tasktrek.data.model.request.project.CreateProjectRequest
import com.tasktrek.domain.model.CreateProjectDomainModel

fun CreateProjectDomainModel.toRequest() = CreateProjectRequest(
    title = title,
    endDate = endDate
)