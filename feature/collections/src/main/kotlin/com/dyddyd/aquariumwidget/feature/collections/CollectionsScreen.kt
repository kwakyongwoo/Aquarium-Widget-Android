package com.dyddyd.aquariumwidget.feature.collections

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
internal fun CollectionsRoute(
    onCancelClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CollectionsViewModel = hiltViewModel()
) {
    CollectionsScreen(
        modifier = modifier,
        onCancelClick = onCancelClick
    )
}

@Composable
internal fun CollectionsScreen(
    modifier: Modifier = Modifier,
    onCancelClick: () -> Unit
) {
    Box(contentAlignment = Alignment.Center) {
        Text(text = "Collections")
    }
}
