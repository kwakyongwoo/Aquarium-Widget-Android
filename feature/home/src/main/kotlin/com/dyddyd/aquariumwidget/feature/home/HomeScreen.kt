package com.dyddyd.aquariumwidget.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dyddyd.aquariumwidget.core.designsystem.component.ImageMaxWidth
import com.dyddyd.aquariumwidget.core.designsystem.theme.HomeBackgroundColor
import com.dyddyd.aquariumwidget.core.ui.getPainterByName

@Composable
internal fun HomeRoute(
    onGoFishingClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val homeUiState by viewModel.homeUiState.collectAsStateWithLifecycle()

    HomeScreen(
        modifier = modifier,
        onGoFishingClick = onGoFishingClick,
        homeUiState = homeUiState
    )
}

@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    onGoFishingClick: () -> Unit,
    homeUiState: HomeUiState,
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            ImageMaxWidth(
                painter = painterResource(id = R.drawable.feature_home_top_background),
                contentDescription = "Home Top Background"
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(HomeBackgroundColor),
                verticalArrangement = Arrangement.Bottom,
            ) {
                ImageMaxWidth(
                    painter = painterResource(id = R.drawable.feature_home_game_button),
                    contentDescription = "Go Fishing Button",
                    modifier = Modifier
                        .padding(horizontal = 48.dp)
                        .clickable { onGoFishingClick() }
                )

                Spacer(modifier = Modifier.fillMaxHeight(0.1f))

                HomeBottom(
                    homeUiState = homeUiState,
                )
            }
        }
    }
}

@Composable
private fun HomeBottom(
    modifier: Modifier = Modifier,
    homeUiState: HomeUiState,
) {
    Box {
        ImageMaxWidth(
            painter = painterResource(id = R.drawable.feature_home_bottom_background),
            contentDescription = "Home Bottom Background",
            modifier = Modifier.align(Alignment.BottomCenter)
        )

        Column(
            modifier = Modifier.align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.Bottom
        ) {
            val aquariumPainter = getPainterByName(
                name = when (homeUiState) {
                    HomeUiState.Loading -> "aquarium_1"
                    is HomeUiState.Success -> "aquarium_${homeUiState.aquariumThemeId}"
                }
            )

            ImageMaxWidth(
                painter = aquariumPainter,
                contentDescription = "Home Aquarium",
                modifier = Modifier.padding(horizontal = 32.dp)
            )

            Box(modifier = Modifier.aspectRatio(5f))

            Box(modifier = Modifier.aspectRatio(3f)) {
                ImageMaxWidth(
                    painter = painterResource(id = R.drawable.feature_home_bottom_bar),
                    contentDescription = "Home Bottom Bar"
                )
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(onGoFishingClick = {}, homeUiState = HomeUiState.Loading)
}