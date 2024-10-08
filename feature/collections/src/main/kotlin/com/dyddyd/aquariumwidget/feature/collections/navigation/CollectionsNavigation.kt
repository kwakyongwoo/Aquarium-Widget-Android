package com.dyddyd.aquariumwidget.feature.collections.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.dyddyd.aquariumwidget.core.model.data.Fish
import com.dyddyd.aquariumwidget.feature.collections.CollectionsRoute

const val COLLECTIONS_ROUTE = "collections_route"

fun NavController.navigateToCollections(navOptions: NavOptions? = null) = navigate(COLLECTIONS_ROUTE, navOptions)

fun NavGraphBuilder.collectionsScreen(
    onCancelClick: () -> Unit,
    onFishItemClick: (Fish) -> Unit,
) {
    composable(
        route = COLLECTIONS_ROUTE,
    ) {
        CollectionsRoute(
            onCancelClick = onCancelClick,
            onFishItemClick = onFishItemClick
        )
    }
}