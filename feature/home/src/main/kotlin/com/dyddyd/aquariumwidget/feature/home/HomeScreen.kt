package com.dyddyd.aquariumwidget.feature.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
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
import com.dyddyd.aquariumwidget.core.designsystem.component.noRippleClickable
import com.dyddyd.aquariumwidget.core.designsystem.theme.HomeBackgroundColor
import com.dyddyd.aquariumwidget.core.model.data.Fish
import com.dyddyd.aquariumwidget.core.ui.FishItem
import com.dyddyd.aquariumwidget.core.ui.getPainterByName

@Composable
internal fun HomeRoute(
    onGoFishingClick: () -> Unit,
    onFishItemClick: (Fish) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val homeUiState by viewModel.homeUiState.collectAsStateWithLifecycle()

    HomeScreen(
        modifier = modifier,
        onGoFishingClick = onGoFishingClick,
        onFishItemClick = onFishItemClick,
        homeUiState = homeUiState,
        onClickFishItem = viewModel::setFishId
    )
}

@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    onGoFishingClick: () -> Unit,
    onFishItemClick: (Fish) -> Unit,
    homeUiState: HomeUiState,
    onClickFishItem: (Int) -> Unit,
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
                    onFishItemClick = onFishItemClick,
                    homeUiState = homeUiState,
                    onClickFishItem = onClickFishItem
                )
            }
        }
    }
}

@Composable
private fun HomeBottom(
    onFishItemClick: (Fish) -> Unit,
    modifier: Modifier = Modifier,
    homeUiState: HomeUiState,
    onClickFishItem: (Int) -> Unit
) {
    Box(modifier = modifier) {
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

                if (homeUiState is HomeUiState.Success) {
                    LazyRow(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxWidth()
                            .padding(24.dp)
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        homeUiState.fishList.forEach { fish ->
                            item {
                                FishItem(
                                    fishId = fish.fishId,
                                    modifier = Modifier.noRippleClickable {
                                        onClickFishItem(fish.fishId)
                                        onFishItemClick(fish)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

//@Preview
//@Composable
//fun HomeScreenPreview() {
//    HomeScreen(
//        onGoFishingClick = {},
//        onFishItemClick = {},
//        homeUiState = HomeUiState.Success(
//            aquariumThemeId = 1,
//            fishList = listOf(
//                Fish(
//                    fishId = 1,
//                    name = "Fish",
//                    habitatId = 1,
//                    description = "Fish",
//                    rarity = "",
//                    imageUrl = "1"
//                ),
//                Fish(
//                    fishId = 2,
//                    name = "Fish",
//                    habitatId = 1,
//                    description = "Fish",
//                    rarity = "",
//                    imageUrl = "2"
//                ),
//                Fish(
//                    fishId = 3,
//                    name = "Fish",
//                    habitatId = 1,
//                    description = "Fish",
//                    rarity = "",
//                    imageUrl = "3"
//                ),
//                Fish(
//                    fishId = 4,
//                    name = "Fish",
//                    habitatId = 1,
//                    description = "Fish",
//                    rarity = "",
//                    imageUrl = "4"
//                ),
//                Fish(
//                    fishId = 5,
//                    name = "Fish",
//                    habitatId = 1,
//                    description = "Fish",
//                    rarity = "",
//                    imageUrl = "5"
//                )
//            )
//        )
//    )
//}