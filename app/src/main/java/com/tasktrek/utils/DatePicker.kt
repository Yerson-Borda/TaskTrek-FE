package com.tasktrek.utils

import android.app.DatePickerDialog
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun DatePicker(
    initialDate: LocalDate,
    onDateChange: (LocalDate) -> Unit
) {
    val context = LocalContext.current
    val year = initialDate.year
    val month = initialDate.monthValue - 1
    val day = initialDate.dayOfMonth

    val datePickerDialog = remember {
        DatePickerDialog(context, { _, y, m, d ->
            onDateChange(LocalDate.of(y, m + 1, d))
        }, year, month, day)
    }

    val formattedDate = remember(initialDate) {
        DateTimeFormatter.ofPattern("yyyy-MM-dd").format(initialDate)
    }

    OutlinedButton(onClick = { datePickerDialog.show() }) {
        Text(text = formattedDate)
    }
}
