package com.dyddyd.aquariumwidget.feature.fishging.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.ui.platform.LocalConfiguration
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
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { -it },
                animationSpec = tween(700)
            )
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { -it },
                animationSpec = tween(700)
            )
        }
    ) {
        FishingRoute(
            onHomeClick = onHomeClick,
            onShowFishDetailDialog = onShowFishDetailDialog,
            showFishDialog = showFishDialog
        )
    }
}