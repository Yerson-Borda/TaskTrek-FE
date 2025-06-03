package com.tasktrek.presentation.ui.screens.home.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tasktrek.R

@Composable
fun NetworkImage(
    imageUrl: String?,
    modifier: Modifier = Modifier
) {

    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(resolvedUrl)
            .crossfade(true)
            .build(),
        contentDescription = null,
        placeholder = painterResource(R.drawable.loading_img),
        error = painterResource(R.drawable.ic_broken_image),
        fallback = painterResource(R.drawable.ic_connection_error),
        modifier = modifier.clip(CircleShape),
        contentScale = ContentScale.Crop
    )
}

