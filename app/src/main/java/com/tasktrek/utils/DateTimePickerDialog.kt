package com.tasktrek.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import java.time.LocalDateTime

@Composable
fun DateTimePickerDialog(
    initialDateTime: LocalDateTime = LocalDateTime.now(),
    onDismiss: () -> Unit,
    onConfirm: (LocalDateTime) -> Unit
) {
    var pickedDate by remember { mutableStateOf(initialDateTime.toLocalDate()) }
    var pickedTime by remember { mutableStateOf(initialDateTime.toLocalTime()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                val combined = LocalDateTime.of(pickedDate, pickedTime)
                onConfirm(combined)
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        title = { Text("Pick Deadline") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                DatePicker(
                    initialDate = pickedDate,
                    onDateChange = { pickedDate = it }
                )
                TimePicker(
                    initialTime = pickedTime,
                    onTimeChange = { pickedTime = it }
                )
            }
        }
    )
}