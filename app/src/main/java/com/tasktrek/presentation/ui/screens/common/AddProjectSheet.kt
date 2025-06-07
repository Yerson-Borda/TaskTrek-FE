package com.tasktrek.presentation.ui.screens.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.tasktrek.presentation.ui.screens.home.viewModel.HomeViewModel
import com.tasktrek.utils.DateTimePickerDialog
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

@Composable
fun AddProjectSheet(
    onDone: (UUID) -> Unit,
    viewModel: HomeViewModel = koinViewModel()
) {
    var title by remember { mutableStateOf("") }
    var deadline by remember { mutableStateOf<LocalDateTime?>(null) }
    var showPicker by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "New Project",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = null,
            singleLine = true,
            shape = RoundedCornerShape(15.dp),
            placeholder = { Text("Project Name", color = Color(0xFF8C8C8C)) },
            modifier = Modifier
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent,
                unfocusedContainerColor = Color(0xFFF0F0F0),
                focusedContainerColor = Color(0xFFF0F0F0),
                focusedTextColor = Color.Black,
            )
        )

        Spacer(Modifier.height(8.dp))

        OutlinedButton(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFF0F0F0),
                contentColor = Color.Black
            ),
            onClick = { showPicker = true },
            border = null,
            modifier = Modifier
                .height(48.dp)
                .fillMaxWidth()
        ) {
            Text(deadline?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) ?: "Set Deadline")
        }

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                deadline?.let {
                    viewModel.createProject(title, it.toString(), onDone)
                }
            },
            enabled = title.isNotBlank() && deadline != null,
            modifier = Modifier
                .height(48.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF5F33E1),
                disabledContainerColor = Color(0xFF5F33E1),
                contentColor = Color.White
            )
        ) {
            Text("Create Project")
        }

        if (showPicker) {
            DateTimePickerDialog(
                initialDateTime = deadline ?: LocalDateTime.now(),
                onDismiss = { showPicker = false },
                onConfirm = {
                    deadline = it
                    showPicker = false
                }
            )
        }
    }
}