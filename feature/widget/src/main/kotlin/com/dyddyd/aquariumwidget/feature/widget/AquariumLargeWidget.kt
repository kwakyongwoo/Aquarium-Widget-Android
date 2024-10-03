package com.dyddyd.aquariumwidget.feature.widget

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.LocalSize
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.provideContent
import androidx.glance.currentState
import androidx.glance.layout.Box
import androidx.glance.layout.absolutePadding
import androidx.glance.layout.size
import androidx.glance.text.Text
import com.dyddyd.aquariumwidget.core.ui.getResIdByName
import kotlin.random.Random

class AquariumLargeWidget : GlanceAppWidget() {

    override val sizeMode: SizeMode = SizeMode.Exact

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        scheduleWidgetUpdate(context)

        provideContent {
            val prefs = currentState<Preferences>()
            val updateCount = prefs[longPreferencesKey("update_count")] ?: 0

            AquariumScreen(updateCount)
        }
    }

    private fun generateRandomPosition(size: DpSize, width: Dp, height: Dp): Pair<Int, Int> {
        return Pair(
            Random.nextInt((size.width - width).value.toInt()),
            Random.nextInt((size.height - height).value.toInt())
        )
    }

    @Composable
    private fun AquariumScreen(updateCount: Long) {
        val size = LocalSize.current
        val context = LocalContext.current

        val prefs = context.getSharedPreferences("aquarium", Context.MODE_PRIVATE)
        val aquariumId= prefs.getString("aquarium_id", "")?.toInt() ?: 1
        val fishList = prefs.getString("fish_list", "")?.let {
            if (it != "") {
                it.split(",").map { it.toInt() }
            }
            else {
                emptyList()
            }
        } ?: emptyList()

        Box {
            Text(text = "$updateCount")

            Image(
                provider = ImageProvider(resId = getResIdByName(context, "widget_aquarium_large_$aquariumId")),
                contentDescription = "Aquarium",
            )

            fishList.forEach { i ->
                val resId = getResIdByName(context, "fish_$i")
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
        val position = generateRandomPosition(size, fishImage.width.dp * scale, fishImage.height.dp * scale)

        Box(
            modifier = GlanceModifier.absolutePadding(left = position.first.dp, top = position.second.dp)
        ) {
            Image(
                provider = ImageProvider(bitmap = fishImage),
                contentDescription = "Fish",
                modifier = GlanceModifier.size(width = fishImage.width.dp * scale, height = fishImage.height.dp * scale)
            )
        }
    }
}