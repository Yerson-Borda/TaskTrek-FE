package com.tasktrek.data.model.request.auth

data class RegisterRequest (
    val username: String,
    val email: String,
    val password: String
)