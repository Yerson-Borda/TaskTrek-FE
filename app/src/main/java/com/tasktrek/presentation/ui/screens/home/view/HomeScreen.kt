package com.tasktrek.presentation.ui.screens.home.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tasktrek.presentation.ui.screens.home.viewModel.HomeViewModel
import org.koin.androidx.compose.koinViewModel
import androidx.compose.runtime.*

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {

    val viewModel: HomeViewModel = koinViewModel()

    val uiState by viewModel.uiState.collectAsState()

    when {
        uiState.isLoading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        uiState.errorMessage != null -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error: ${uiState.errorMessage}", color = MaterialTheme.colorScheme.error)
            }
        }
        else -> {
            Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                Text("Projects", style = MaterialTheme.typography.headlineSmall)

                LazyRow(
                    contentPadding = PaddingValues(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.projects) { project ->
                        Card(
                            modifier = Modifier
                                .width(200.dp)
                                .height(120.dp),
                            elevation = CardDefaults.cardElevation()
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(project.title, style = MaterialTheme.typography.titleMedium)
                                Spacer(Modifier.height(4.dp))
                                Text("Ends: ${project.endDate}", style = MaterialTheme.typography.bodySmall)
                                Text("${project.completedTasks}/${project.tasksToComplete} tasks done")
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text("Tasks", style = MaterialTheme.typography.headlineSmall)

                LazyColumn(
                    contentPadding = PaddingValues(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.tasks) { task ->
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text(task.title, style = MaterialTheme.typography.titleMedium)
                                    Text("Due: ${task.endDate}", style = MaterialTheme.typography.bodySmall)
                                }
                                Checkbox(checked = task.complete, onCheckedChange = null)
                            }
                        }
                    }
                }
            }
        }
    }
}