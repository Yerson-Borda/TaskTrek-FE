package com.tasktrek.presentation.ui.screens.common

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.LaunchedEffect
import androidx.core.view.WindowCompat
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun TopOvalBackground(
    modifier: Modifier = Modifier,
    color: Color = Color(0xFFDBC0E8)
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = Color.Transparent,
        darkIcons = true
    )

    val view = LocalView.current
    LaunchedEffect(view) {
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val controller = v.context.findActivity()?.window
            WindowCompat.setDecorFitsSystemWindows(controller!!, false)
            insets
        }
    }

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(280.dp)
    ) {
        val width = size.width
        val height = size.height

        drawArc(
            color = color,
            startAngle = 0f,
            sweepAngle = 180f,
            useCenter = true,
            topLeft = Offset(-width / 2, -height * 1.3f), // Push oval way up to show curve
            size = size.copy(width = width * 2, height = height * 2)
        )
    }
}

@Preview
@Composable
fun SemiCircleComposablePreview() {
    TopOvalBackground()
}