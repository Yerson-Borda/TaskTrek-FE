package com.tasktrek.presentation.ui.screens.auth.components

import com.tasktrek.presentation.ui.screens.common.TextFieldState

class PasswordState: TextFieldState(
    validator = ::isPasswordValid,
    errorMessage = { passwordErrorMessage() }
)

fun isPasswordValid(password: String): Boolean {
    return password.length >= 6
            && password.any { it.isDigit() }
            && password.any { it.isUpperCase() }
            && password.any { it.isLowerCase() }
            && password.any { "!@#$%^&*()-_=+[{]}|;:'\",<.>/?".contains(it) }
}

fun passwordErrorMessage() = "Use uppercase, numbers & symbols)"