package com.tasktrek.presentation.ui.screens.auth.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp

@Composable
fun RequiredFieldLabel(
    label: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = buildAnnotatedString {
            append(label)
            append(" ")
            withStyle(style = SpanStyle(color = Color(0xFFF81919),)) {
                append("*")
            }
        },
        style = MaterialTheme.typography.labelSmall,
        color = Color.Black,
        modifier = modifier.padding(bottom = 6.dp)
    )
}

@Composable
fun FixedLabel(label: String) {
    Text(
        text = label,
        style = MaterialTheme.typography.labelSmall,
        color = Color.Black,
        modifier = Modifier.padding(bottom = 6.dp)
    )
}
