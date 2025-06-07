package com.tasktrek.presentation.ui.screens.home.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tasktrek.presentation.ui.screens.home.viewModel.HomeViewModel
import org.koin.androidx.compose.koinViewModel
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.tasktrek.presentation.ui.screens.home.components.NetworkImage
import com.tasktrek.presentation.ui.screens.home.components.ProjectCard
import com.tasktrek.presentation.ui.screens.home.components.TaskCard
import com.tasktrek.R
import com.tasktrek.data.model.enums.SheetType
import com.tasktrek.presentation.ui.screens.common.AddProjectSheet
import com.tasktrek.presentation.ui.screens.common.FloatingActionMenu
import com.tasktrek.presentation.ui.screens.common.JoinProjectSheet
import kotlinx.coroutines.launch
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    showFloatingMenu: Boolean,
    onDismissFloatingMenu: () -> Unit,
    onCreateTask: () -> Unit,
    onProjectClick: (UUID) -> Unit,
    onProjectCreated: (UUID) -> Unit
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Hidden,
            skipHiddenState = false
        )
    )

    var currentSheet by remember { mutableStateOf<SheetType?>(null) }

    BottomSheetScaffold(
        scaffoldState = sheetState,
        sheetContent = {
            when (currentSheet) {
                SheetType.AddProject -> AddProjectSheet(onDone = {
                    scope.launch { sheetState.bottomSheetState.hide() }
                    onProjectCreated(it)
                })

                SheetType.JoinProject -> JoinProjectSheet(onDone = {
                    scope.launch { sheetState.bottomSheetState.hide() }
                })

                else -> Spacer(modifier = Modifier.height(1.dp))
            }
        },
        sheetPeekHeight = 0.dp
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {

            val viewModel: HomeViewModel = koinViewModel()
            val uiState by viewModel.uiState.collectAsState()

            if (uiState.isLoading) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
                return@BottomSheetScaffold
            }

            if (uiState.errorMessage != null) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Error: ${uiState.errorMessage}", color = MaterialTheme.colorScheme.error)
                }
                return@BottomSheetScaffold
            }

            val searchQuery = uiState.searchQuery
            val filteredProjects = uiState.projects.filter {
                it.title.contains(searchQuery, ignoreCase = true)
            }
            val filteredTasks = uiState.tasks.filter {
                it.title.contains(searchQuery, ignoreCase = true)
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, end = 16.dp, top = 58.dp)
            ) {
                // Top Bar
                uiState.user?.let { user ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            NetworkImage(
                                imageUrl = user.profileImage,
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                            )
                            Spacer(Modifier.width(4.dp))
                            Text(
                                "Hello,\n${user.username}",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                        Row(
//                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(onClick = { /* future click */ }) {
                                Icon(
                                    painter = painterResource(R.drawable.calendar_icon),
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            IconButton(onClick = { /* future click */ }) {
                                Icon(
                                    painter = painterResource(R.drawable.achievements_icon),
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            IconButton(onClick = { /* future click */ }) {
                                Icon(
                                    painter = painterResource(R.drawable.stats_icon),
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            IconButton(onClick = { /* future click */ }) {
                                Icon(
                                    painter = painterResource(R.drawable.notification_icon),
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }

                // Search Bar
                var active by remember { mutableStateOf(false) }
                SearchBar(
                    query = searchQuery,
                    onQueryChange = { viewModel.updateSearchQuery(it) },
                    onSearch = { active = false },
                    active = active,
                    onActiveChange = { active = it },
                    placeholder = { Text("Search") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(40.dp),
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = null)
                    }
                ) {}
                Spacer(Modifier.height(20.dp))

                // Projects Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Recent Projects", style = MaterialTheme.typography.headlineSmall)
                    TextButton(onClick = { }) {
                        Text("See All", color = Color(0xFF5F33E1), fontWeight = FontWeight.Bold)
                    }
                }

                LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(filteredProjects) { project ->
                        ProjectCard(project = project, onClick = { onProjectClick(project.id)})
                    }
                }

                Spacer(Modifier.height(25.dp))

                // Tasks Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Tasks", style = MaterialTheme.typography.headlineSmall)
                    TextButton(onClick = { }) {
                        Text("See All", color = Color(0xFF5F33E1), fontWeight = FontWeight.Bold)
                    }
                }

                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(filteredTasks) { task ->
                        TaskCard(task)
                    }
                }
            }
        }
    }

    FloatingActionMenu(
        isVisible = showFloatingMenu,
        onDismiss = onDismissFloatingMenu,
        onAddProject = {
            onDismissFloatingMenu()
            currentSheet = SheetType.AddProject
            scope.launch { sheetState.bottomSheetState.expand() }
        },
        onJoinProject = {
            onDismissFloatingMenu()
            currentSheet = SheetType.JoinProject
            scope.launch { sheetState.bottomSheetState.expand() }
        },
        onAddTask = {
            onDismissFloatingMenu()
            onCreateTask()
        }
    )
}