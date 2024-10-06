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
import androidx.glance.layout.Alignment
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

            AquariumWidgetScreen(updateCount, AQUARIUM_WIDGET_TYPE_LARGE)
        }
    }
}