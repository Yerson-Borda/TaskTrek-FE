package com.tasktrek.data.mapper

import com.tasktrek.data.model.request.project.JoinProjectRequest
import com.tasktrek.domain.model.JoinProjectDomainModel

fun JoinProjectDomainModel.toRequest() = JoinProjectRequest(
    code = code
)