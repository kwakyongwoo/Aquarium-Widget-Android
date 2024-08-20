package com.dyddyd.aquariumwidget.feature.fishging.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.dyddyd.aquariumwidget.feature.fishging.FishingRoute

const val FISHING_ROUTE = "fishing_route"

fun NavController.navigateToFishing() = navigate(FISHING_ROUTE, navOptions {
    launchSingleTop = true
})

fun NavGraphBuilder.fishingScreen(
    onHomeClick: () -> Unit
) {
    composable(
        route = FISHING_ROUTE,
    ) {
        FishingRoute(onHomeClick = onHomeClick)
    }
}