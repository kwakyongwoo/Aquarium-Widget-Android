package com.dyddyd.aquariumwidget.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.dyddyd.aquariumwidget.core.model.data.Fish
import com.dyddyd.aquariumwidget.feature.collections.navigation.collectionsScreen
import com.dyddyd.aquariumwidget.feature.fishging.navigation.fishingScreen
import com.dyddyd.aquariumwidget.feature.fishging.navigation.navigateToFishing
import com.dyddyd.aquariumwidget.feature.help.navigation.helpScreen
import com.dyddyd.aquariumwidget.feature.home.navigation.homeScreen
import com.dyddyd.aquariumwidget.feature.home.navigation.navigateToHome
import com.dyddyd.aquariumwidget.feature.items.navigation.itemsScreen
import com.dyddyd.aquariumwidget.feature.splash.navigation.SPLASH_ROUTE
import com.dyddyd.aquariumwidget.feature.splash.navigation.splashScreen
import com.dyddyd.aquariumwidget.ui.AquariumAppState
import com.dyddyd.aquariumwidget.navigation.TopLevelDestination.HOME

@Composable
fun AquariumNavHost(
    appState: AquariumAppState,
    modifier: Modifier = Modifier,
    startDestination: String = SPLASH_ROUTE,
    onShowFishDetailDialog: (Fish) -> Unit,
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
        splashScreen(navigateToHome = { appState.navigateToTopLevelDestination(HOME) })
        homeScreen(
            onGoFishingClick = navController::navigateToFishing,
            onFishItemClick = onShowFishDetailDialog
        )
        fishingScreen(onHomeClick = { appState.navigateToTopLevelDestination(HOME) })
        collectionsScreen(
            onCancelClick = { appState.navigateToTopLevelDestination(HOME) },
            onFishItemClick = onShowFishDetailDialog
        )
        itemsScreen(onCancelClick = { appState.navigateToTopLevelDestination(HOME) })
        helpScreen(onCancelClick = { appState.navigateToTopLevelDestination(HOME) })
    }
}