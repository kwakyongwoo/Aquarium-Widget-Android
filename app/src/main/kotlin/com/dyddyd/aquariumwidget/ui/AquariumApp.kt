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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dyddyd.aquariumwidget.core.designsystem.component.AquariumTopAppBar
import com.dyddyd.aquariumwidget.core.model.data.Fish
import com.dyddyd.aquariumwidget.feature.fish.FishDialog
import com.dyddyd.aquariumwidget.feature.fish.FishDialogUiState
import com.dyddyd.aquariumwidget.feature.fish.FishDialogViewModel
import com.dyddyd.aquariumwidget.navigation.AquariumNavHost
import com.dyddyd.aquariumwidget.navigation.TopLevelDestination

@Composable
fun AquariumApp(
    appState: AquariumAppState,
    modifier: Modifier = Modifier,
    dialogViewModel: FishDialogViewModel = hiltViewModel()
) {
    var showFishDialog by remember { mutableStateOf(false) }
    val fishDialogUiState by dialogViewModel.fishDialogUiState.collectAsStateWithLifecycle()

    Box(modifier = modifier.fillMaxSize()) {
        AquariumApp(
            appState = appState,
            showFishDialog = showFishDialog,
            onFishDialogDismiss = { showFishDialog = false },
            onShowFishDetailDialog = { fish ->
                dialogViewModel.setFishId(fish.fishId)
                showFishDialog = true
            },
            fishDialogUiState = fishDialogUiState,
            addRemoveFish = dialogViewModel::addRemoveFishInAquarium
        )
    }
}

@Composable
fun AquariumApp(
    appState: AquariumAppState,
    modifier: Modifier = Modifier,
    showFishDialog: Boolean,
    onFishDialogDismiss: () -> Unit,
    onShowFishDetailDialog: (Fish) -> Unit,
    fishDialogUiState: FishDialogUiState,
    addRemoveFish: (Boolean) -> Unit
) {

    if (showFishDialog) {
        FishDialog(
            uiState = fishDialogUiState,
            onDismiss = { onFishDialogDismiss() },
            addRemoveFish = addRemoveFish
        )
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
                onShowFishDetailDialog = onShowFishDetailDialog
            )
        }
    }
}