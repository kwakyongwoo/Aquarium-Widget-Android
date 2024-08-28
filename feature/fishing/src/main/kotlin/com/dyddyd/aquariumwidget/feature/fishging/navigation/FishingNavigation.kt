package com.dyddyd.aquariumwidget.feature.fishging.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.dyddyd.aquariumwidget.core.model.data.Fish
import com.dyddyd.aquariumwidget.feature.fishging.FishingRoute

const val FISHING_ROUTE = "fishing_route"

fun NavController.navigateToFishing(navOptions: NavOptions? = null) = navigate(FISHING_ROUTE, navOptions)

fun NavGraphBuilder.fishingScreen(
    onHomeClick: () -> Unit,
    onShowFishDetailDialog: (Fish) -> Unit,
    showFishDialog: Boolean,
) {
    composable(
        route = FISHING_ROUTE,
    ) {
        FishingRoute(
            onHomeClick = onHomeClick,
            onShowFishDetailDialog = onShowFishDetailDialog,
            showFishDialog = showFishDialog
        )
    }
}