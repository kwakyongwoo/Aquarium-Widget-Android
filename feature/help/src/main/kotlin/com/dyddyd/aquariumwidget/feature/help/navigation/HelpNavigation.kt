package com.dyddyd.aquariumwidget.feature.help.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.dyddyd.aquariumwidget.feature.help.HelpRoute

const val HELP_ROUTE = "help_route"

fun NavController.navigateToHelp(navOptions: NavOptions? = null) = navigate(HELP_ROUTE, navOptions)

fun NavGraphBuilder.helpScreen(
    onCancelClick: () -> Unit
) {
    composable(
        route = HELP_ROUTE,
    ) {
        HelpRoute(onCancelClick = onCancelClick)
    }
}