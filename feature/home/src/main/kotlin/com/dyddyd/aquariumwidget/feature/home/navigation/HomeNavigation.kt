package com.dyddyd.aquariumwidget.feature.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.dyddyd.aquariumwidget.core.model.data.Fish
import com.dyddyd.aquariumwidget.feature.home.HomeRoute
import com.dyddyd.aquariumwidget.feature.splash.navigation.SPLASH_ROUTE

const val HOME_ROUTE = "home_route"

fun NavController.navigateToHome() = navigate(HOME_ROUTE, navOptions {
    popUpTo(SPLASH_ROUTE) {
        inclusive = true
    }
    launchSingleTop = true
})

fun NavGraphBuilder.homeScreen(
    onGoFishingClick: () -> Unit,
    onFishItemClick: (Fish) -> Unit
) {
    composable(
        route = HOME_ROUTE,
    ) {
        HomeRoute(
            onGoFishingClick = onGoFishingClick,
            onFishItemClick = onFishItemClick
        )
    }
}