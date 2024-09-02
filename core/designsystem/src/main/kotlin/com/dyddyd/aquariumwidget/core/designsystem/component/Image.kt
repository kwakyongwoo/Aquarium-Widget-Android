package com.dyddyd.aquariumwidget.core.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale

@Composable
fun ImageMaxSize(
    painter: Painter,
    contentDescription: String? = null,
    modifier: Modifier = Modifier,
    fraction: Float = 1f
) {
    Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier.fillMaxSize(fraction),
        contentScale = ContentScale.FillBounds
    )
}

@Composable
fun ImageMaxHeight(
    painter: Painter,
    contentDescription: String? = null,
    modifier: Modifier = Modifier,
    fraction: Float = 1f
) {
    Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier.fillMaxHeight(fraction),
        contentScale = ContentScale.FillHeight
    )
}

@Composable
fun ImageMaxWidth(
    painter: Painter,
    contentDescription: String? = null,
    modifier: Modifier = Modifier,
    fraction: Float = 1f
) {
    Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier.fillMaxWidth(fraction),
        contentScale = ContentScale.FillWidth
    )
}