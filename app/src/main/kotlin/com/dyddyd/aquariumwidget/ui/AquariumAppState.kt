package com.dyddyd.aquariumwidget.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.dyddyd.aquariumwidget.feature.collections.navigation.COLLECTIONS_ROUTE
import com.dyddyd.aquariumwidget.feature.collections.navigation.navigateToCollections
import com.dyddyd.aquariumwidget.feature.fishging.navigation.FISHING_ROUTE
import com.dyddyd.aquariumwidget.feature.fishging.navigation.navigateToFishing
import com.dyddyd.aquariumwidget.feature.help.navigation.HELP_ROUTE
import com.dyddyd.aquariumwidget.feature.help.navigation.navigateToHelp
import com.dyddyd.aquariumwidget.feature.home.navigation.HOME_ROUTE
import com.dyddyd.aquariumwidget.feature.home.navigation.navigateToHome
import com.dyddyd.aquariumwidget.feature.items.navigation.ITEMS_ROUTE
import com.dyddyd.aquariumwidget.feature.items.navigation.navigateToItems
import com.dyddyd.aquariumwidget.feature.splash.navigation.SPLASH_ROUTE
import com.dyddyd.aquariumwidget.navigation.TopLevelDestination
import kotlinx.coroutines.CoroutineScope
import com.dyddyd.aquariumwidget.navigation.TopLevelDestination.SPLASH
import com.dyddyd.aquariumwidget.navigation.TopLevelDestination.HOME
import com.dyddyd.aquariumwidget.navigation.TopLevelDestination.FISHING
import com.dyddyd.aquariumwidget.navigation.TopLevelDestination.COLLECTIONS
import com.dyddyd.aquariumwidget.navigation.TopLevelDestination.ITEMS
import com.dyddyd.aquariumwidget.navigation.TopLevelDestination.HELP

@Composable
fun rememberAquariumAppState(
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
): AquariumAppState {
    return remember(
        navController,
        coroutineScope,
    ) {
        AquariumAppState(
            navController = navController,
            coroutineScope = coroutineScope,
        )
    }
}

@Stable
class AquariumAppState(
    val navController: NavHostController,
    coroutineScope: CoroutineScope,
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = when (currentDestination?.route) {
            SPLASH_ROUTE -> SPLASH
            HOME_ROUTE -> HOME
            FISHING_ROUTE -> FISHING
            COLLECTIONS_ROUTE -> COLLECTIONS
            ITEMS_ROUTE -> ITEMS
            HELP_ROUTE -> HELP
            else -> null
        }

    fun navigateToTopLevelDestination(destination: TopLevelDestination) {
        val topLevelNavOptions = navOptions {
            popUpTo(navController.graph.id) {
                saveState = true
            }

            launchSingleTop = true
            restoreState = true
        }

        when (destination) {
            HOME -> navController.navigateToHome(topLevelNavOptions)
            FISHING -> navController.navigateToFishing(topLevelNavOptions)
            COLLECTIONS -> navController.navigateToCollections(topLevelNavOptions)
            ITEMS -> navController.navigateToItems(topLevelNavOptions)
            HELP -> navController.navigateToHelp(topLevelNavOptions)
            else -> { }
        }
    }

    fun navigateToHome() = navController.navigateToHome()
    fun navigateToCollections() = navController.navigateToCollections()
    fun navigateToItems() = navController.navigateToItems()
    fun navigateToHelp() = navController.navigateToHelp()

}