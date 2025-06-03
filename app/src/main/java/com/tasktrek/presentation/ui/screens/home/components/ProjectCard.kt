package com.tasktrek.presentation.ui.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tasktrek.domain.model.ProjectListItemResult
import com.tasktrek.presentation.ui.theme.interFontFamily

@Composable
fun ProjectCard(project: ProjectListItemResult) {
    Card(
        modifier = Modifier
            .width(260.dp)
            .height(160.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F6FE)) // Set the background color
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    project.members.firstOrNull()?.profileImage?.let {
                        NetworkImage(
                            imageUrl = it,
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                        )
                    }
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = project.title,
                        style = MaterialTheme.typography.labelMedium
                    )
                }

                Spacer(Modifier.height(4.dp))
                Text(project.description ?: "", maxLines = 1, style = MaterialTheme.typography.bodySmall)

                Spacer(Modifier.height(4.dp))
                Text(
                    text = "Ends: ${project.endDate.toLocalDate()}",
                    style = MaterialTheme.typography.labelSmall,
                    fontFamily = interFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    color = Color(0xFF9E9E9E)
                )

                Spacer(Modifier.height(8.dp))

                // Members
                Row(modifier = Modifier.height(32.dp)) {
                    project.members.take(5).forEach { user ->
                        NetworkImage(
                            imageUrl = user.profileImage,
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .border(1.dp, Color.White, CircleShape)
                        )
                        Spacer(Modifier.width(4.dp))
                    }
                }

                Spacer(Modifier.height(4.dp))

                // Progress Indicator
                val progress = if (project.tasksToComplete > 0)
                    project.completedTasks.toFloat() / project.tasksToComplete
                else 0f

                LinearProgressIndicator(
                    progress = progress,
                    color = Color(0xFF5F33E1),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
            }
        }
    }
}