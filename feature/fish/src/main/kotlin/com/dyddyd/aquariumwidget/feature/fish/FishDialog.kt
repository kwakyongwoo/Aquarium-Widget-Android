package com.dyddyd.aquariumwidget.feature.fish

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dyddyd.aquariumwidget.core.designsystem.component.ImageMaxHeight
import com.dyddyd.aquariumwidget.core.designsystem.component.ImageMaxSize
import com.dyddyd.aquariumwidget.core.designsystem.component.ImageMaxWidth
import com.dyddyd.aquariumwidget.core.designsystem.component.TextWithBackground
import com.dyddyd.aquariumwidget.core.designsystem.component.noRippleClickable
import com.dyddyd.aquariumwidget.core.designsystem.theme.AquariumWidgetTheme
import com.dyddyd.aquariumwidget.core.model.data.Fish
import com.dyddyd.aquariumwidget.core.ui.getPainterByName
import java.util.Locale

@Composable
fun FishDialog(
    onDismiss: () -> Unit,
    uiState: FishDialogUiState,
    addRemoveFish: (Boolean) -> Unit
) {

    val fishInfo: Triple<Fish?, Boolean, String> = when (uiState) {
        is FishDialogUiState.Success -> Triple(
            uiState.fish,
            uiState.isInAquarium,
            uiState.habitatName
        )

        else -> Triple(null, false, "")
    }

    val rarity = (fishInfo.first?.rarity ?: "Common").lowercase(Locale.ROOT)

    Dialog(onDismissRequest = { onDismiss() }) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Box(contentAlignment = Alignment.Center) {
                ImageMaxWidth(painter = getPainterByName(name = "fish_detail_background_$rarity"))

                Column(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    FishDialogTop(
                        fishName = fishInfo.first?.name ?: "",
                        rarity = rarity
                    )

                    FishDialogMiddle(
                        fishId = fishInfo.first?.fishId ?: 1,
                        rarity = rarity
                    )

                    FishDialogBottom(
                        fishDescription = fishInfo.first?.description ?: "",
                        habitatName = fishInfo.third,
                        rarity = rarity
                    )
                }
            }

            addRemoveButton(isInAquarium = fishInfo.second, addRemoveFish = addRemoveFish)
        }
    }
}

@Composable
private fun FishDialogTop(
    fishName: String,
    rarity: String
) {
    Box(contentAlignment = Alignment.Center) {
        ImageMaxWidth(painter = getPainterByName(name = "fish_detail_top_$rarity"))

        Text(
            text = fishName,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .align(Alignment.Center),
            fontSize = 6.em
        )
    }
}

@Composable
private fun FishDialogMiddle(
    fishId: Int,
    rarity: String
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(47 / 29f)
    ) {
        ImageMaxWidth(painter = getPainterByName(name = "fish_detail_middle_$rarity"))

        if (fishId != 0) {
            val fishPainter = getPainterByName("fish_$fishId")

            if (fishPainter.intrinsicSize.width > fishPainter.intrinsicSize.height) {
                ImageMaxWidth(
                    painter = fishPainter,
                    contentDescription = "Fish Image",
                    modifier = Modifier.padding(horizontal = 80.dp)
                )
            } else {
                ImageMaxHeight(
                    painter = fishPainter,
                    contentDescription = "Fish Image",
                    modifier = Modifier.padding(vertical = 32.dp)
                )
            }
        }
    }
}

@Composable
private fun FishDialogBottom(
    fishDescription: String,
    habitatName: String,
    rarity: String
) {
    Box(contentAlignment = Alignment.Center) {
        ImageMaxWidth(painter = getPainterByName(name = "fish_detail_bottom_$rarity"))

        Text(
            text = "Habitat: $habitatName",
            modifier = Modifier
                .fillMaxWidth(23 / 47f)
                .padding(top = 8.dp)
                .align(Alignment.TopEnd),
            fontSize = dpToSp(14.dp),
            textAlign = TextAlign.Center
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                .aspectRatio(46 / 16f)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = fishDescription,
                modifier = Modifier
                    .align(Alignment.TopCenter),
                fontSize = dpToSp(16.dp),
                lineHeight = dpToSp(18.dp),
            )
        }
    }
}

@Composable
private fun addRemoveButton(
    modifier: Modifier = Modifier,
    isInAquarium: Boolean,
    addRemoveFish: (Boolean) -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        ImageMaxWidth(
            painter = painterResource(id = if (isInAquarium) R.drawable.feature_fish_detail_del else R.drawable.feature_fish_detail_add),
            contentDescription = "Fish Add/Remove Background",
            modifier = Modifier.noRippleClickable {
                addRemoveFish(isInAquarium)
            }
        )

        ImageMaxWidth(
            painter = painterResource(id = if (isInAquarium) R.drawable.feature_fish_detail_del_text else R.drawable.feature_fish_detail_add_text),
            contentDescription = "Fish Add/Remove Text",
            modifier = if (isInAquarium) Modifier.padding(horizontal = 32.dp) else Modifier.padding(horizontal = 64.dp)
        )
    }
}

@Composable
private fun dpToSp(dp: Dp) = with(LocalDensity.current) { dp.toSp() }


@Preview
@Composable
private fun FishDialogPreviewWidth() {
    AquariumWidgetTheme {
        FishDialog(
            onDismiss = {},
            uiState = FishDialogUiState.Success(
                fish = Fish(
                    fishId = 10,
                    name = "moon fish",
                    description = "Moon fish is known for its large bait that resembles the moon. It comes up to the surface of the water, especially when the full moon rises.",
                    rarity = "Common",
                    imageUrl = "https://www.google.com",
                    habitatId = 1
                ),
                habitatName = "Pond",
                isInAquarium = true
            ),
            addRemoveFish = {}
        )
    }
}

@Preview
@Composable
private fun FishDialogPreviewHeight() {
    AquariumWidgetTheme {
        FishDialog(
            onDismiss = {},
            uiState = FishDialogUiState.Success(
                fish = Fish(
                    fishId = 21,
                    name = "Rainbow fish",
                    description = "Rainbow fish, with their iridescent scales, bring a spectrum of colors to the depths of lakes, making them a delightful sight for nature enthusiasts.",
                    rarity = "Common",
                    imageUrl = "https://www.google.com",
                    habitatId = 2
                ),
                habitatName = "Lake",
                isInAquarium = false
            ),
            addRemoveFish = {}
        )
    }
}