package com.dyddyd.aquariumwidget.core.ui

import android.util.Log
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.dyddyd.aquariumwidget.core.designsystem.component.ImageMaxWidth
import com.dyddyd.aquariumwidget.core.model.data.Fish
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun AnimateFishImage(
    aquariumId: Int,
    fishList: List<Int>,
    modifier: Modifier = Modifier
) {
    var aquariumSize by remember { mutableStateOf(IntSize.Zero) }
    var showFish by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(100)
        showFish = true
    }

    Box(
        modifier = modifier
    ) {
        ImageMaxWidth(
            painter = getPainterByName("aquarium_$aquariumId"),
            contentDescription = "fish",
            modifier = Modifier.onGloballyPositioned { coordinates ->
                aquariumSize = coordinates.size
            }
        )

        if (showFish) {
            fishList.forEach { fishId ->
                AnimateFish(fishId, aquariumSize)
            }
        }
    }
}

@Composable
private fun AnimateFish(
    fishId: Int,
    aquariumSize: IntSize,
) {
    val padding = aquariumSize.width / 20
    val animationDuration = Random.nextInt(2500, 3500)
    val scale = 1f

    val density = LocalDensity.current.density
    val fishPainter = getPainterByName("fish_$fishId")
    val fishSize = fishPainter.intrinsicSize

    var fishXPosition by remember {
        mutableStateOf(
            Random.nextInt(
                padding,
                ((aquariumSize.width - fishSize.width - padding) / density).toInt()
            )
        )
    }
    var fishYPosition by remember {
        mutableStateOf(
            Random.nextInt(
                padding,
                ((aquariumSize.height - fishSize.height - padding * 3) / density).toInt()
            )
        )
    }

    var fishFlip by remember { mutableStateOf(false) }

    val animatedXPosition by animateDpAsState(
        fishXPosition.dp,
        animationSpec = tween(
            durationMillis = animationDuration,
            easing = CubicBezierEasing(0.42f, 0f, 0.58f, 1f)
        )
    )
    val animatedYPosition by animateDpAsState(
        fishYPosition.dp,
        animationSpec = tween(
            durationMillis = animationDuration,
            easing = CubicBezierEasing(0.42f, 0f, 0.58f, 1f)
        )
    )

    LaunchedEffect(Unit) {
        while (true) {
            val nextXPosition = Random.nextInt(
                padding,
                ((aquariumSize.width - fishSize.width - padding) / density).toInt()
            )

            fishFlip = fishXPosition < nextXPosition

            fishXPosition = nextXPosition
            fishYPosition = Random.nextInt(
                padding,
                ((aquariumSize.height - fishSize.height - padding * 2) / density).toInt()
            )

            delay(animationDuration.toLong() + 500)
        }
    }

    Image(
        painter = fishPainter,
        contentDescription = "fish",
        modifier = Modifier
            .offset(x = animatedXPosition, y = animatedYPosition)
            .scale(scaleX = if (fishFlip) -scale else scale, scaleY = scale)
    )
}

//@Preview(showBackground = true)
//@Composable
//fun AnimateFishImagePreview() {
//    AnimateFishImage(aquariumId = 1, fishId = 1)
//}