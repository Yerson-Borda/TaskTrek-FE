package com.tasktrek.presentation.ui.screens.project.view

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.tasktrek.presentation.ui.screens.home.components.NetworkImage
import com.tasktrek.presentation.ui.screens.project.viewModel.ProjectViewModel
import org.koin.androidx.compose.koinViewModel
import java.util.UUID
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@Composable
fun ProjectScreen(projectId: UUID) {
    val viewModel: ProjectViewModel = koinViewModel()

    LaunchedEffect(projectId) {
        viewModel.loadProject(projectId)
    }

    val uiState by viewModel.uiState.collectAsState()

    val context = LocalContext.current
    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    when {
        uiState.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        uiState.project != null -> {
            uiState.project?.let { project ->
                Box(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White)
                    ) {

                        // Header with safe inset padding and background
                        val backgroundColor = Color(0xFFE0F7FA) // Can randomize if needed
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(backgroundColor)
                                .padding(WindowInsets.statusBars.asPaddingValues()) // prevent overlap
                                .padding(horizontal = 16.dp)
                        ) {
                            Column {
                                // Top row: Back + search and 3-dots
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    IconButton(onClick = {
                                        backDispatcher?.onBackPressed()
                                    }) {
                                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                                    }
                                    Row {
                                        IconButton(onClick = { /* TODO: Search */ }) {
                                            Icon(Icons.Default.Search, contentDescription = "Search")
                                        }
                                        IconButton(onClick = { /* TODO: Options */ }) {
                                            Icon(Icons.Default.MoreVert, contentDescription = "Options")
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(48.dp))

                                // Second Row: Edit and members
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    IconButton(onClick = { /* TODO: Edit */ }) {
                                        Icon(Icons.Default.Edit, contentDescription = "Edit Project")
                                    }

                                    Row(horizontalArrangement = Arrangement.spacedBy((-8).dp)) {
                                        project.members.forEach { member ->
                                            NetworkImage(
                                                imageUrl = member.profileImage,
                                                modifier = Modifier
                                                    .size(32.dp)
                                                    .clip(CircleShape)
                                                    .border(2.dp, Color.White, CircleShape)
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        // Project info
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = project.title,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = project.description ?: "Add Description",
                                color = Color.Gray
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            val progress = if (project.tasksToComplete == 0) 0f
                            else project.completedTasks.toFloat() / project.tasksToComplete

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "${project.completedTasks}/${project.tasksToComplete}",
                                    color = Color(0xFFB39DDB),
                                    style = MaterialTheme.typography.labelSmall,
                                    modifier = Modifier
                                        .background(Color(0xFFF3E5F5), RoundedCornerShape(4.dp))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                )

                                Text(
                                    text = "${ChronoUnit.DAYS.between(LocalDate.now(), project.endDate.toLocalDate())} Days Left, ${project.endDate.toLocalDate()}",
                                    color = Color.Gray,
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            LinearProgressIndicator(
                                progress = progress,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(6.dp),
                                color = Color(0xFFB39DDB),
                                trackColor = Color.LightGray
                            )
                        }
                    }

                    // FloatingActionButton
                    FloatingActionButton(
                        onClick = { /* TODO: Add task */ },
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(16.dp),
                        containerColor = Color(0xFF3D5AFE)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.White)
                    }
                }
            }
        }

        uiState.errorMessage != null -> {
            Text(
                text = "Error: ${uiState.errorMessage}",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}