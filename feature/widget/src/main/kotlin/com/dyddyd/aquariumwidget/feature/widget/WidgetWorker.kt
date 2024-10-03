package com.dyddyd.aquariumwidget.feature.widget

import android.content.Context
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import java.util.concurrent.TimeUnit

class WidgetWorker(
    private val context: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        var isStop = true

        GlanceAppWidgetManager(context).getGlanceIds(AquariumSmallWidget::class.java).forEach { id ->
            isStop = false
            updateAppWidgetState(context, id) {
                it[longPreferencesKey("update_count")] = System.currentTimeMillis()
            }
            AquariumSmallWidget().update(context, id)
        }

        GlanceAppWidgetManager(context).getGlanceIds(AquariumLargeWidget::class.java).forEach { id ->
            isStop = false
            updateAppWidgetState(context, id) {
                it[longPreferencesKey("update_count")] = System.currentTimeMillis()
            }
            AquariumLargeWidget().update(context, id)
        }

        scheduleWidgetUpdate(context)

        if (isStop) {
            WorkManager.getInstance(context).cancelUniqueWork("AquariumWidget")
        }

        return Result.success()
    }

}

fun scheduleWidgetUpdate(context: Context) {
    val request = OneTimeWorkRequestBuilder<WidgetWorker>()
        .setInitialDelay(5, TimeUnit.SECONDS)
        .build()

    WorkManager.getInstance(context).enqueueUniqueWork(
        "AquariumWidget",
        ExistingWorkPolicy.REPLACE,
        request
    )
}
