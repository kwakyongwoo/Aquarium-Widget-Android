package com.dyddyd.aquariumwidget.core.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import coil.compose.AsyncImagePainter.State.Empty.painter

@Composable
fun BoxWithBackground(
    painter: Painter,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        ImageMaxWidth(painter = painter)

        content()
    }
}

@Composable
fun TextWithBackground(
    modifier: Modifier = Modifier,
    background: Painter,
    text: String,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        ImageMaxWidth(painter = background)

        Text(text = text)
    }
}

@Composable
fun ImageWithBackground(
    background: Painter,
    modifier: Modifier = Modifier,
    painter: Painter?,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        ImageMaxWidth(painter = background)

        painter?.let {
            ImageMaxWidth(painter = it)
        }
    }
}