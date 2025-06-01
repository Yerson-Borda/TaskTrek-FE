package com.tasktrek.presentation.ui.screens.auth.components

import com.tasktrek.presentation.ui.screens.common.TextFieldState

class UsernameState: TextFieldState(
    validator = ::isUsernameValid,
    errorMessage = { usernameErrorMessage() }
)

fun isUsernameValid(username: String): Boolean {
    return username.isNotEmpty()
}

fun usernameErrorMessage() = "Username cannot be empty"