package com.dyddyd.aquariumwidget.core.ui

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource

@Composable
fun getPainterByName(name: String): Painter {
    val context = LocalContext.current
    val resourceId = context.resources.getIdentifier(
        "core_ui_$name",
        "drawable",
        context.packageName
    )

    return painterResource(id = resourceId)
}

@Composable
fun getPainterByName(context: Context, name: String): Painter {
    val resourceId = context.resources.getIdentifier(
        "core_ui_$name",
        "drawable",
        context.packageName
    )

    return painterResource(id = resourceId)
}

@Composable
fun getResIdByName(context: Context,  name: String): Int {
    val resourceId = context.resources.getIdentifier(
        "core_ui_$name",
        "drawable",
        context.packageName
    )

    return resourceId
}
