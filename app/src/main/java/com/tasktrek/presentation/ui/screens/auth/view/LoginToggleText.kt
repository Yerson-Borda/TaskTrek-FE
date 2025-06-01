package com.tasktrek.presentation.ui.screens.auth.view

import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import com.tasktrek.R

@Composable
fun LoginToggleText(
    isLoginMode: Boolean,
    onToggle: () -> Unit
) {
    val annotatedText = buildAnnotatedString {
        val actionText = if (isLoginMode) stringResource(R.string.sign_up) else stringResource(R.string.log_in)
        val prefixText = if (isLoginMode) stringResource(R.string.don_t_have_an_account) else stringResource(
            R.string.have_an_account_already
        )

        append(prefixText)

        val start = length
        append(actionText)
        val end = length

        addStyle(
            style = SpanStyle(
                color = Color(0xFF6200EE),
                textDecoration = TextDecoration.None
            ),
            start = start,
            end = end
        )
        addStringAnnotation(
            tag = "TOGGLE",
            annotation = "toggle_login_mode",
            start = start,
            end = end
        )
    }

    ClickableText(
        text = annotatedText,
        onClick = { offset ->
            annotatedText.getStringAnnotations("TOGGLE", offset, offset)
                .firstOrNull()?.let {
                    onToggle()
                }
        }
    )
}