package com.dyddyd.aquariumwidget.feature.items.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.dyddyd.aquariumwidget.feature.items.ItemsRoute

const val ITEMS_ROUTE = "items_route"

fun NavController.navigateToItems() = navigate(ITEMS_ROUTE, navOptions {
    launchSingleTop = true
})

fun NavGraphBuilder.itemsScreen(
    onCancelClick: () -> Unit
) {
    composable(
        route = ITEMS_ROUTE,
    ) {
        ItemsRoute(onCancelClick = onCancelClick)
    }
}