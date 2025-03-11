package com.dyddyd.aquariumwidget.ui

import android.util.Log
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
import com.dyddyd.aquariumwidget.core.data.repository.UserRepository
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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun rememberAquariumAppState(
    userRepository: UserRepository,
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
): AquariumAppState {
    return remember(
        navController,
        coroutineScope,
        userRepository,
    ) {
        AquariumAppState(
            navController = navController,
            coroutineScope = coroutineScope,
            userRepository = userRepository,
        )
    }
}

@Stable
class AquariumAppState(
    val navController: NavHostController,
    private val coroutineScope: CoroutineScope,
    private val userRepository: UserRepository,
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

    val lastPlayedDate: StateFlow<Int?> = userRepository.getUserInfo().map {
        it.lastPlayedDate
    }
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null
        )

    fun updateLastPlayedDate() {
        coroutineScope.launch {
            val date = SimpleDateFormat("yyyyMMdd").format(Date()).toInt()
            Log.d("AquariumAppState", "updateLastPlayedDate: $date")

            lastPlayedDate.value?.let {
                Log.d("AquariumAppState", "updateLastPlayedDate(): $it")
                if (it < date) {
                    userRepository.resetGameChanceCount()
                }
            }

            userRepository.updateLastPlayedDate(date)
        }
    }

    fun navigateToTopLevelDestination(destination: TopLevelDestination) {
        val topLevelNavOptions = navOptions {
            popUpTo(navController.graph.id) {
                saveState = true
                inclusive = true
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

}