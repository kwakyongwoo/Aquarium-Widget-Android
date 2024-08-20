package com.dyddyd.aquariumwidget.feature.splash.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dyddyd.aquariumwidget.feature.splash.SplashRoute

const val SPLASH_ROUTE = "splash_route"

fun NavGraphBuilder.splashScreen(
    navigateToHome: () -> Unit
) {
    composable(
        route = SPLASH_ROUTE,
    ) {
        SplashRoute(navigateToHome = navigateToHome)
    }
}