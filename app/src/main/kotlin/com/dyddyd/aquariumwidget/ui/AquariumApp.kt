package com.dyddyd.aquariumwidget.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
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

    Box(modifier = modifier) {

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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .consumeWindowInsets(padding)
        ) {
            val destination = appState.currentTopLevelDestination

            if (destination != TopLevelDestination.SPLASH) {
                Column(modifier = Modifier.zIndex(1f)) {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(90 / 16f) // Home Top Background aspect ratio
                    )

                    AquariumTopAppBar(
                        modifier = Modifier,
                        onHomeClick = {  },
                        onCollectionClick = {  },
                        onItemClick = {  },
                        onHelpClick = {  },
                    )
                }
            }

//            AquariumNavHost(
//                appState = appState,
//                modifier = Modifier.fillMaxSize(),
//            )
        }
    }
}