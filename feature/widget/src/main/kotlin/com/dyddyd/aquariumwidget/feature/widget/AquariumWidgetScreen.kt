package com.dyddyd.aquariumwidget.feature.widget

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.LocalSize
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.ContentScale
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.text.Text
import com.dyddyd.aquariumwidget.core.ui.getResIdByName
import kotlin.random.Random

@Composable
fun AquariumWidgetScreen(updateCount: Long, type: Int) {
    val size = LocalSize.current
    val context = LocalContext.current

    val prefs = context.getSharedPreferences("aquarium", Context.MODE_PRIVATE)
    val aquariumId = prefs.getString("aquarium_id", "")?.toInt() ?: 1
    val fishList = prefs.getString("fish_list", "")?.let {
        if (it != "") {
            it.split(",").map { it.toInt() }
        } else {
            emptyList()
        }
    } ?: emptyList()

    Box {
        Text(text = "$updateCount")

        Image(
            provider = ImageProvider(
                resId = getResIdByName(
                    context,
                    if (type == AQUARIUM_WIDGET_TYPE_SMALL) "widget_aquarium_$aquariumId" else "widget_aquarium_large_$aquariumId"
                )
            ),
            contentDescription = "Aquarium Background",
            contentScale = ContentScale.FillBounds
        )

        fishList.forEach { fishId ->
            val resId = getResIdByName(context, "fish_$fishId")
            val bitmap = BitmapFactory.decodeResource(context.resources, resId)
            val matrix = Matrix()
            matrix.preScale(if (Random.nextBoolean()) -1f else 1f, 1f)
            val fishImage = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true)

            FishImage(size, fishImage)
        }
    }
}

@Composable
private fun FishImage(
    size: DpSize,
    fishImage: Bitmap,
    scale: Float = 0.1f
) {
    val boxSize = generateRandomPosition(size, fishImage.width.dp * scale, fishImage.height.dp * scale)

    Box(
        modifier = GlanceModifier.size(boxSize.first.dp, boxSize.second.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        Image(
            provider = ImageProvider(bitmap = fishImage),
            contentDescription = "Fish",
            modifier = GlanceModifier.size(width = fishImage.width.dp * scale, height = fishImage.height.dp * scale)
        )
    }
}

private fun generateRandomPosition(size: DpSize, width: Dp, height: Dp): Pair<Int, Int> {
    return Pair(
        Random.nextInt(width.value.toInt() + 1, size.width.value.toInt()),
        Random.nextInt(height.value.toInt() + 1, size.height.value.toInt())
    )
}

const val AQUARIUM_WIDGET_TYPE_SMALL = 0
const val AQUARIUM_WIDGET_TYPE_LARGE = 1