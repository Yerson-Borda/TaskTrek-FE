package com.tasktrek.presentation.ui.screens.task_creation.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.tasktrek.presentation.ui.screens.task_creation.viewModel.TaskCreationViewModel
import com.tasktrek.utils.DateTimePickerDialog
import org.koin.androidx.compose.koinViewModel
import java.util.UUID

@Composable
fun TaskCreationScreen(
    onTaskCreated: (UUID) -> Unit
) {
    val viewModel: TaskCreationViewModel = koinViewModel()

    val uiState = viewModel.uiState

    if (uiState.successTaskId != null) {
        onTaskCreated(uiState.successTaskId)
        return
    }

    if (uiState.isLoading) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = uiState.title,
            onValueChange = { viewModel.updateTitle(it) },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = uiState.description,
            onValueChange = { viewModel.updateDescription(it) },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        Text("Start Date")
        DateTimePickerDialog(
            initialDateTime = uiState.startDate,
            onDismiss = {},
            onConfirm = { viewModel.updateStartDate(it) }
        )

        Spacer(Modifier.height(8.dp))

        Text("End Date")
        DateTimePickerDialog(
            initialDateTime = uiState.endDate,
            onDismiss = {},
            onConfirm = { viewModel.updateEndDate(it) }
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = uiState.pomodoros.toString(),
            onValueChange = { viewModel.updatePomodoros(it.toIntOrNull() ?: 0) },
            label = { Text("Pomodoros") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        // Add more pickers or dropdowns for tag, repeat, etc.
        // For now, just a simple dropdown placeholder
        Text("Tag: ${uiState.tag}")
        // Example: DropdownMenu or a ChipsRow

        Spacer(Modifier.height(8.dp))

        TextField(
            value = uiState.note ?: "",
            onValueChange = { viewModel.updateNote(it) },
            label = { Text("Notes") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = { viewModel.createTask() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Task")
        }

        uiState.errorMessage?.let {
            Text(text = it, color = Color.Red, modifier = Modifier.padding(top = 8.dp))
        }
    }
}