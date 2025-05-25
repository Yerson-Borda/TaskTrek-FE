package com.tasktrek.presentation.ui.screens.splash.view

import androidx.compose.animation.core.EaseOutExpo
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tasktrek.presentation.ui.screens.splash.viewModel.SplashViewModel

@Composable
fun SplashScreen(
    onNextScreen: () -> Unit
) {
    val viewModel: SplashViewModel = viewModel()
    val isLoadingDone by viewModel.isLoadingDone.collectAsState()

    LaunchedEffect(isLoadingDone) {
        if (isLoadingDone) onNextScreen()
    }

    val transition = rememberInfiniteTransition(label = "")
    val offsetX by transition.animateFloat(
        initialValue = -300f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseOutExpo),
            repeatMode = RepeatMode.Restart
        ),
        label = "TaskOffset"
    )

    val offsetXTrek by transition.animateFloat(
        initialValue = 300f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseOutExpo),
            repeatMode = RepeatMode.Restart
        ),
        label = "TrekOffset"
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = "TASK",
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier
                    .offset(x = offsetX.dp)
                    .padding(end = 64.dp)
            )
            Text(
                text = "TREK",
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF5F33E1),
                modifier = Modifier
                    .offset(x = offsetXTrek.dp)
                    .padding(start = 64.dp) // 16.dp per character
            )
        }
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    MaterialTheme {
        SplashScreen(
            onNextScreen = {}
        )
    }
}