package com.tasktrek.utils

import android.app.TimePickerDialog
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun TimePicker(
    initialTime: LocalTime,
    onTimeChange: (LocalTime) -> Unit
) {
    val context = LocalContext.current
    val hour = initialTime.hour
    val minute = initialTime.minute

    val timePickerDialog = remember {
        TimePickerDialog(context, { _, h, m ->
            onTimeChange(LocalTime.of(h, m))
        }, hour, minute, true)
    }

    val formattedTime = remember(initialTime) {
        DateTimeFormatter.ofPattern("HH:mm").format(initialTime)
    }

    OutlinedButton(onClick = { timePickerDialog.show() }) {
        Text(text = formattedTime)
    }
}