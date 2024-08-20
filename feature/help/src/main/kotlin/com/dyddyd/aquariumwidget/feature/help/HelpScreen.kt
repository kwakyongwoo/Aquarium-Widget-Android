package com.dyddyd.aquariumwidget.feature.help

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
internal fun HelpRoute(
    onCancelClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HelpViewModel = hiltViewModel()
) {
    HelpScreen(
        modifier = modifier,
        onCancelClick = onCancelClick
    )
}

@Composable
internal fun HelpScreen(
    modifier: Modifier = Modifier,
    onCancelClick: () -> Unit
) {
    Box(contentAlignment = Alignment.Center) {
        Text(text = "Help")
    }
}