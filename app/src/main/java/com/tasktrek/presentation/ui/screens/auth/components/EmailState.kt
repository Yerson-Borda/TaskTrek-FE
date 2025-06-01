package com.tasktrek.presentation.ui.screens.auth.components

import android.util.Patterns
import com.tasktrek.presentation.ui.screens.common.TextFieldState

class EmailState: TextFieldState(
    validator = ::isEmailValid,
    errorMessage = ::emailErrorMessage
)

private fun isEmailValid(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

private fun emailErrorMessage (email: String) = "Email \"$email\" is invalid"