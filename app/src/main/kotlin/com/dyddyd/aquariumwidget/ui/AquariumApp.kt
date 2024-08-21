package com.dyddyd.aquariumwidget.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.dyddyd.aquariumwidget.core.designsystem.component.AquariumTopAppBar
import com.dyddyd.aquariumwidget.navigation.AquariumNavHost
import com.dyddyd.aquariumwidget.navigation.TopLevelDestination

@Composable
fun AquariumApp(
    appState: AquariumAppState,
    modifier: Modifier = Modifier,
) {
    var showFishDialog by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {

        AquariumApp(
            appState = appState,
            showFishDialog = showFishDialog,
        )
    }
}

@Composable
fun AquariumApp(
    appState: AquariumAppState,
    showFishDialog: Boolean,
    modifier: Modifier = Modifier,
) {

    if (showFishDialog) {
        // TODO: Implement FishDialog
    }

    Scaffold { padding ->
        padding

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val destination = appState.currentTopLevelDestination

            if (destination != TopLevelDestination.SPLASH) {
                Column(modifier = Modifier.zIndex(1f)) {
                    Spacer(modifier = Modifier.height(24.dp))

                    AquariumTopAppBar(
                        modifier = Modifier,
                        onHomeClick = { appState.navigateToHome() },
                        onCollectionClick = { appState.navigateToCollections() },
                        onItemClick = { appState.navigateToItems() },
                        onHelpClick = { appState.navigateToHelp() },
                        isHomeSelected = destination == TopLevelDestination.HOME,
                    )
                }
            }

            AquariumNavHost(
                appState = appState,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}