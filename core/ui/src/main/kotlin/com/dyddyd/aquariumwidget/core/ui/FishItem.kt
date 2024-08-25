package com.dyddyd.aquariumwidget.core.ui

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dyddyd.aquariumwidget.core.designsystem.component.ImageMaxHeight
import com.dyddyd.aquariumwidget.core.designsystem.component.ImageMaxSize
import com.dyddyd.aquariumwidget.core.designsystem.component.ImageMaxWidth

@Composable
fun FishItem(
    modifier: Modifier = Modifier,
    fishId: Int,
) {
    val fishPainter = getPainterByName(name = "fish_$fishId")

    Box(
        modifier = modifier
            .fillMaxSize()
            .aspectRatio(1f),
        contentAlignment = Alignment.Center
    ) {
        ImageMaxSize(
            painter = painterResource(id = R.drawable.core_ui_fish_background_default),
            contentDescription = "Fish Background"
        )

        if (fishPainter.intrinsicSize.width > fishPainter.intrinsicSize.height) {
            ImageMaxWidth(
                painter = fishPainter,
                contentDescription = "Fish Image",
                modifier = Modifier.padding(horizontal = if (fishPainter.intrinsicSize.width <= 200) 16.dp else 12.dp)
            )
        } else {
            ImageMaxHeight(
                painter = fishPainter,
                contentDescription = "Fish Image",
                modifier = Modifier.padding(vertical = 12.dp)
            )
        }
    }
}

@Preview
@Composable
fun FishItemPreviewWidth() {
    FishItem(fishId = 1, modifier = Modifier.size(100.dp))
}

@Preview
@Composable
fun FishItemPreviewHeight() {
    FishItem(fishId = 10, modifier = Modifier.size(100.dp))
}