package com.dyddyd.aquariumwidget.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.dyddyd.aquariumwidget.ui.AquariumAppState

@Composable
fun AquariumNavHost(
    appState: AquariumAppState,
    modifier: Modifier = Modifier,
    startDestination: String = "SPLASH_ROUTE",
) {
    val navController = appState.navController

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
    ) {

    }
}