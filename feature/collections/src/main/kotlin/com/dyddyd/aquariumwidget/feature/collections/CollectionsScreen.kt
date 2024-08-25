package com.dyddyd.aquariumwidget.feature.collections

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dyddyd.aquariumwidget.core.designsystem.component.ImageMaxWidth
import com.dyddyd.aquariumwidget.core.designsystem.component.noRippleClickable
import com.dyddyd.aquariumwidget.core.model.data.Fish
import com.dyddyd.aquariumwidget.core.ui.FishItem

@Composable
internal fun CollectionsRoute(
    onCancelClick: () -> Unit,
    onFishItemClick: (Fish) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CollectionsViewModel = hiltViewModel()
) {
    val collectionsUiState by viewModel.collectionsUiState.collectAsStateWithLifecycle()

    CollectionsScreen(
        modifier = modifier,
        onCancelClick = onCancelClick,
        uiState = collectionsUiState,
        onFishItemClick = onFishItemClick
    )
}

@Composable
internal fun CollectionsScreen(
    modifier: Modifier = Modifier,
    onCancelClick: () -> Unit,
    uiState: CollectionsUiState,
    onFishItemClick: (Fish) -> Unit
) {
    Box(
        modifier = modifier.background(Color(30, 20, 3)),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.feature_collections_title),
                    contentDescription = "Collections Title",
                    modifier = Modifier.height(20.dp),
                    contentScale = ContentScale.FillHeight
                )

                Image(
                    painter = painterResource(id = R.drawable.feature_collections_close),
                    contentDescription = "Collections Close",
                    modifier = Modifier
                        .size(28.dp)
                        .noRippleClickable {
                            onCancelClick()
                        },
                    contentScale = ContentScale.FillHeight
                )
            }

            CollectionsContent(uiState = uiState, onFishItemClick = onFishItemClick)
        }
    }
}

@Composable
private fun CollectionsContent(
    modifier: Modifier = Modifier,
    uiState: CollectionsUiState,
    onFishItemClick: (Fish) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(90 / 133f)
    ) {
        ImageMaxWidth(painter = painterResource(id = R.drawable.feature_collections_background))

        if (uiState is CollectionsUiState.Success) {
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp)
                    .padding(start = 16.dp)
                    .padding(top = 48.dp, bottom = 80.dp),
                columns = GridCells.Adaptive(56.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                uiState.allFishList.forEach { fish ->
                    item {
                        if (uiState.collectedFishList.any { it.fishId == fish.fishId }) {
                            FishItem(
                                fishId = fish.fishId,
                                modifier = Modifier.noRippleClickable {
                                    onFishItemClick(fish)
                                })
                        } else {
                            Image(
                                painter = painterResource(id = R.drawable.feature_collections_fish_background_unknown),
                                contentDescription = "Fish",
                                contentScale = ContentScale.FillWidth
                            )
                        }
                    }
                }
            }
        }
    }
}