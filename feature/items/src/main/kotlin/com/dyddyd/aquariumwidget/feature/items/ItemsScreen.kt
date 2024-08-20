package com.dyddyd.aquariumwidget.feature.items

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
internal fun ItemsRoute(
    onCancelClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ItemsViewModel = hiltViewModel()
) {
    ItemsScreen(
        modifier = modifier,
        onCancelClick = onCancelClick
    )
}

@Composable
internal fun ItemsScreen(
    modifier: Modifier = Modifier,
    onCancelClick: () -> Unit
) {
    Box(contentAlignment = Alignment.Center) {
        Text(text = "Items")
    }
}