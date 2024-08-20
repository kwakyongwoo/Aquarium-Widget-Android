package com.dyddyd.aquariumwidget.feature.fishging

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
internal fun FishingRoute(
    onHomeClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FishingViewModel = hiltViewModel()
) {
    FishingScreen(
        modifier = modifier,
        onHomeClick = onHomeClick
    )
}

@Composable
internal fun FishingScreen(
    modifier: Modifier = Modifier,
    onHomeClick: () -> Unit
) {
    Box(contentAlignment = Alignment.Center) {
        Text(text = "Fishing")
    }
}