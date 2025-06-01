package com.tasktrek.presentation.ui.screens.auth.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.tasktrek.presentation.ui.screens.auth.view.ErrorField
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import com.tasktrek.R

@Composable
fun Password(
    password: String,
    error: String?,
    onPasswordChanged: (String) -> Unit,
    onImeAction: () -> Unit
){
    val showPassword = remember { mutableStateOf(false) }
    Column {
        FixedLabel(stringResource(R.string.password))

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
            value = password,
            onValueChange = { onPasswordChanged(it) },
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            visualTransformation = if (showPassword.value) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = { onImeAction() }),
            label = null,
            placeholder = {
                Text(
                    stringResource(R.string.password_details),
                    style = MaterialTheme.typography.labelMedium,
                    color = Color(0xFF8C8C8C)
                )
             },
            trailingIcon = {
                if (showPassword.value) {
                    IconButton(onClick = { showPassword.value = false }) {
                        Icon(
                            imageVector = Icons.Filled.Visibility,
                            tint = LocalContentColor.current.copy(alpha = 0.6f),
                            contentDescription = stringResource(R.string.hide_password)
                        )
                    }
                } else {
                    IconButton(onClick = { showPassword.value = true }) {
                        Icon(
                            imageVector = Icons.Filled.VisibilityOff,
                            tint = LocalContentColor.current.copy(alpha = 0.6f),
                            contentDescription = stringResource(R.string.show_password)
                        )
                    }
                }
            },
            isError = error != null
        )
    }

    error?.let { ErrorField(it) }
}