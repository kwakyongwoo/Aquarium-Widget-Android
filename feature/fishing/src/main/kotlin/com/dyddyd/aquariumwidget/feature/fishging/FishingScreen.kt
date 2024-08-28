package com.dyddyd.aquariumwidget.feature.fishging

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideOut
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dyddyd.aquariumwidget.core.designsystem.component.APP_BAR_HEIGHT
import com.dyddyd.aquariumwidget.core.designsystem.component.ImageMaxHeight
import com.dyddyd.aquariumwidget.core.designsystem.component.ImageMaxSize
import com.dyddyd.aquariumwidget.core.designsystem.component.ImageMaxWidth
import com.dyddyd.aquariumwidget.core.designsystem.component.TopAppBarVerticalDivider
import com.dyddyd.aquariumwidget.core.designsystem.component.dpToSp
import com.dyddyd.aquariumwidget.core.designsystem.component.noRippleClickable
import com.dyddyd.aquariumwidget.core.model.data.Fish
import com.dyddyd.aquariumwidget.core.model.data.Quest
import com.dyddyd.aquariumwidget.core.ui.getPainterByName
import com.dyddyd.aquariumwidget.feature.fishing.R
import kotlinx.coroutines.delay

@Composable
internal fun FishingRoute(
    onHomeClick: () -> Unit,
    onShowFishDetailDialog: (Fish) -> Unit,
    showFishDialog: Boolean,
    modifier: Modifier = Modifier,
    viewModel: FishingViewModel = hiltViewModel()
) {
    val fishingUiState by viewModel.fishingUiState.collectAsStateWithLifecycle()

    LaunchedEffect(showFishDialog) {
        viewModel.checkAndResetHearts()
        if (!showFishDialog) {
            viewModel.fishingState = FishingState.NotFishing
            viewModel.checkQuest()
            delay(2000)
            viewModel.clearedQuestList = null
        }
    }

    LaunchedEffect(viewModel.fishingState) {
        if (viewModel.fishingState is FishingState.Caught) {
            onShowFishDetailDialog((viewModel.fishingState as FishingState.Caught).fish)
        }
    }

    FishingScreen(
        modifier = modifier,
        onHomeClick = onHomeClick,
        fishingUiState = fishingUiState,
        fishingState = viewModel.fishingState,
        onFishingClick = viewModel::fishing,
        clearedQuests = viewModel.clearedQuestList
    )
}

@Composable
internal fun FishingScreen(
    modifier: Modifier = Modifier,
    onHomeClick: () -> Unit,
    fishingUiState: FishingUiState,
    fishingState: FishingState,
    onFishingClick: () -> Unit,
    clearedQuests: List<Quest>?
) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val screenHeightDp = configuration.screenHeightDp.dp

    val expandToWidth = screenWidthDp.value / screenHeightDp.value > 90 / 195f

    if (fishingUiState is FishingUiState.Error) {
        onHomeClick()
    }

    val habitatName = if (fishingUiState is FishingUiState.Success) {
        fishingUiState.habitat.name.lowercase()
    } else {
        "pond"
    }

    Box(modifier = modifier.fillMaxSize()) {
        if (fishingUiState is FishingUiState.Success) {
            ImageMaxSize(painter = getPainterByName(name = "fishing_background_${fishingUiState.habitat.name.lowercase()}"))

            FishingTopBar(
                onHomeClick = onHomeClick,
                chance = fishingUiState.chance,
                modifier = Modifier.align(Alignment.TopCenter),
                habitatName = fishingUiState.habitat.name
            )

            FishingCat(
                modifier = Modifier
                    .fillMaxWidth(82 / 90f)
                    .fillMaxHeight(143 / 195f)
                    .align(Alignment.TopEnd),
                expandToWidth = expandToWidth
            )

            if (fishingState !is FishingState.NotFishing) {
                FishingRod(
                    modifier = Modifier
                        .fillMaxWidth(71 / 90f)
                        .fillMaxHeight(159 / 195f)
                        .align(Alignment.TopEnd),
                    expandToWidth = expandToWidth,
                )
            }

            FishingButton(
                modifier = Modifier
                    .fillMaxWidth(37 / 90f)
                    .fillMaxHeight(151 / 195f)
                    .align(Alignment.TopEnd),
                expandToWidth = expandToWidth,
                enabled = fishingUiState.chance > 0 && fishingState is FishingState.NotFishing,
                onFishingClick = onFishingClick,
            )

            FishingStateImage(
                fishingState = fishingState,
                modifier = Modifier.align(Alignment.Center)
            )

            AnimatedVisibility(
                visible = clearedQuests != null && fishingState is FishingState.NotFishing,
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier.align(Alignment.Center)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    clearedQuests?.forEach { quest ->
                        Box(
                            modifier = Modifier.fillMaxWidth(0.9f),
                            contentAlignment = Alignment.Center
                        ) {
                            ImageMaxWidth(painter = painterResource(id = R.drawable.feature_fishing_quest_clear_background))

                            Text(
                                text = quest.title,
                                fontSize = dpToSp(dp = 20.dp),
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun FishingTopBar(
    modifier: Modifier = Modifier,
    onHomeClick: () -> Unit,
    chance: Int,
    habitatName: String
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
    ) {
        TopAppBarVerticalDivider()

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(APP_BAR_HEIGHT),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            LazyRow(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                repeat(chance) {
                    item {
                        Image(
                            painter = painterResource(id = R.drawable.feature_fishing_heart_fill),
                            contentDescription = "Heart Fill",
                            modifier = Modifier.size(28.dp),
                            contentScale = ContentScale.FillWidth
                        )
                    }
                }

                repeat(5 - chance) {
                    item {
                        Image(
                            painter = painterResource(id = R.drawable.feature_fishing_heart_empty),
                            contentDescription = "Heart Empty",
                            modifier = Modifier.size(28.dp),
                            contentScale = ContentScale.FillWidth
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth(0.4f)
                ) {
                    ImageMaxSize(painter = painterResource(id = R.drawable.feature_fishing_stage))

                    Text(
                        text = "Stage: $habitatName",
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center)
                            .padding(start = 24.dp),
                        fontSize = dpToSp(dp = 16.dp),
                        maxLines = 1,
                        color = Color.White
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.feature_fishing_stage_change),
                    contentDescription = "Change Stage",
                    modifier = Modifier
                        .size(40.dp)
                        .noRippleClickable { Log.d("FishingScreen", "Change Stage") },
                )
            }

            Image(
                painter = painterResource(id = R.drawable.feature_fishing_back_home),
                contentDescription = "Back Home",
                modifier = Modifier
                    .size(40.dp)
                    .noRippleClickable { onHomeClick() },
                contentScale = ContentScale.FillBounds
            )
        }
    }
}

@Composable
private fun FishingCat(
    modifier: Modifier = Modifier,
    expandToWidth: Boolean
) {
    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.feature_fishing_cat),
            contentDescription = "Fishing Cat",
            modifier = Modifier
                .fillMaxHeight(21 / 143f)
                .fillMaxWidth(16 / 82f)
                .align(Alignment.BottomStart),
            contentScale = if (expandToWidth) ContentScale.FillHeight else ContentScale.FillWidth
        )
    }
}

@Composable
private fun FishingRod(
    modifier: Modifier = Modifier,
    expandToWidth: Boolean
) {
    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.feature_fishing_rod),
            contentDescription = "Fishing Rod",
            modifier = Modifier
                .fillMaxHeight(45 / 159f)
                .fillMaxWidth(33 / 71f)
                .align(Alignment.BottomStart),
            contentScale = if (expandToWidth) ContentScale.FillHeight else ContentScale.FillWidth
        )
    }
}

@Composable
private fun FishingButton(
    modifier: Modifier = Modifier,
    expandToWidth: Boolean,
    enabled: Boolean,
    onFishingClick: () -> Unit,
) {
    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(
                id =
                if (enabled) R.drawable.feature_fishing_button_enable
                else R.drawable.feature_fishing_button_disable
            ),
            contentDescription = "Fishing Button",
            modifier = Modifier
                .fillMaxHeight(33 / 151f)
                .fillMaxWidth(34 / 37f)
                .align(Alignment.BottomStart)
                .noRippleClickable {
                    Log.d("FishingScreen", "Fishing Button Clicked")
                    Log.d("FishingScreen", "enabled: $enabled")
                    if (enabled) {
                        onFishingClick()
                    }
                },
            contentScale = if (expandToWidth) ContentScale.FillHeight else ContentScale.FillWidth
        )
    }
}

@Composable
private fun FishingStateImage(
    fishingState: FishingState,
    modifier: Modifier = Modifier
) {
    when (fishingState) {
        is FishingState.Waiting -> {
            Image(
                painter = painterResource(id = fishingState.image),
                contentDescription = "Waiting",
                modifier = modifier.fillMaxWidth(0.7f),
                contentScale = ContentScale.FillWidth
            )
        }

        is FishingState.Bite -> {
            Image(
                painter = painterResource(id = fishingState.image),
                contentDescription = "Bite",
                modifier = modifier.fillMaxWidth(0.1f),
                contentScale = ContentScale.FillWidth
            )
        }

        is FishingState.Gotcha -> {
            Image(
                painter = painterResource(id = fishingState.image),
                contentDescription = "Gotcha",
                modifier = modifier.fillMaxWidth(0.7f),
                contentScale = ContentScale.FillWidth
            )
        }

        else -> {
        }
    }
}

//@Preview
//@Composable
//private fun FishingScreenPreview1() {
//    Box(modifier = Modifier.fillMaxSize()) {
//        FishingStateImage(
//            fishingState = FishingState.Waiting(image = R.drawable.feature_fishing_state1),
//            modifier = Modifier.align(Alignment.Center)
//        )
//    }
//}
//
//@Preview
//@Composable
//private fun FishingScreenPreview2() {
//    Box(modifier = Modifier.fillMaxSize()) {
//        FishingStateImage(
//            fishingState = FishingState.Bite(image = R.drawable.feature_fishing_state2),
//            modifier = Modifier.align(Alignment.Center)
//        )
//    }
//}
//
//@Preview
//@Composable
//private fun FishingScreenPreview3() {
//    Box(modifier = Modifier.fillMaxSize()) {
//        FishingStateImage(
//            fishingState = FishingState.Gotcha(image = R.drawable.feature_fishing_state3),
//            modifier = Modifier.align(Alignment.Center)
//        )
//    }
//}