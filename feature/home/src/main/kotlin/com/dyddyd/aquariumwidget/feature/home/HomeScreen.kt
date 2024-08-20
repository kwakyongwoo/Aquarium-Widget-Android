package com.dyddyd.aquariumwidget.feature.home

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
internal fun HomeRoute(
    onGoFishingClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    HomeScreen(
        modifier = modifier,
        onGoFishingClick = onGoFishingClick
    )
}

@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    onGoFishingClick: () -> Unit,
) {
    Box(contentAlignment = Alignment.Center) {
        Text(text = "Home")
    }
}