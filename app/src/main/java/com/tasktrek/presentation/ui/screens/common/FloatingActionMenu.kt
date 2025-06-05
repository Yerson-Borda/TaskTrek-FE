package com.tasktrek.presentation.ui.screens.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FloatingActionMenu(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    onAddProject: () -> Unit,
    onJoinProject: () -> Unit,
    onAddTask: () -> Unit
) {
    if (!isVisible) return

    Box(contentAlignment = Alignment.BottomCenter, modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(bottom = 100.dp)
                .background(Color.White, RoundedCornerShape(12.dp))
                .shadow(4.dp)
                .padding(8.dp)
        ) {
            Text("Add Project", modifier = Modifier
                .clickable {
                    onAddProject()
                }
                .padding(8.dp))
            Text("Join Project", modifier = Modifier
                .clickable {
                    onJoinProject()
                }
                .padding(8.dp))
            Text("Add Task", modifier = Modifier
                .clickable {
                    onAddTask()
                }
                .padding(8.dp))
        }
    }
}