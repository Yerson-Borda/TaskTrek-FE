package com.tasktrek.presentation.ui.screens.auth.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.tasktrek.R
import com.tasktrek.presentation.ui.screens.auth.view.ErrorField

@Composable
fun Email(
    email: String,
    error: String?,
    onEmailChanged: (String) -> Unit,
    onImeAction: () -> Unit
){
    Column {
        FixedLabel(stringResource(R.string.e_mail))

        OutlinedTextField(
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFF5F33E1),
                unfocusedLabelColor = LocalContentColor.current.copy(alpha = 0.6f),
                focusedBorderColor = Color(0xFF5F33E1),
                focusedLabelColor = Color(0xFF5F33E1),
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
            ),
            textStyle = MaterialTheme.typography.labelMedium,
            modifier = Modifier
                .fillMaxWidth(),
            value = email,
            onValueChange = { onEmailChanged(it) },
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            label = null,
            placeholder = {
                Text(
                    text = stringResource(R.string.email_example),
                    style = MaterialTheme.typography.labelMedium,
                    color = Color(0xFF8C8C8C)
                )
              },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    onImeAction()
                }
            ),
            isError = error != null
        )

        error?.let { ErrorField(it) }
    }
}