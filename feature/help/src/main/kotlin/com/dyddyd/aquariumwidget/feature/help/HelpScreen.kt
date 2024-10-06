package com.dyddyd.aquariumwidget.feature.help

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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dyddyd.aquariumwidget.core.designsystem.component.ImageMaxWidth
import com.dyddyd.aquariumwidget.core.designsystem.component.noRippleClickable
import com.dyddyd.aquariumwidget.core.ui.FishItem
import com.dyddyd.aquariumwidget.core.ui.getPainterByName

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
                    painter = painterResource(id = R.drawable.feature_help_title),
                    contentDescription = "Help Title",
                    modifier = Modifier.height(20.dp),
                    contentScale = ContentScale.FillHeight
                )

                Image(
                    painter = getPainterByName("close"),
                    contentDescription = "Help Close",
                    modifier = Modifier
                        .size(28.dp)
                        .noRippleClickable {
                            onCancelClick()
                        },
                    contentScale = ContentScale.FillHeight
                )
            }

            HelpContent()
        }
    }
}

@Composable
private fun HelpContent(
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(pageCount = {
        7
    })

    val helpTextList = listOf(
        "1. Long press on the screen. Tab the \"Widgets\" option to access the widget gallery",
        "2. Browse or search for the app's name \"Aquarium Widget\". Our widgets come in two sizes, so choose the one that best fits your preference.",
        "3. Long press on the widget you want to add to your home screen. Drag and drop the widget to the desired location on your home screen.",
        "4. The widget will be added to your home screen. You can now customize the widget by tap and drag it. But hold on… your Aquarium still seems empty… \uD83D\uDE14",
        "5. This is where the fun begins! Tap the \"Go Fishing\" button to begin your adverture of collecting adorable, pixel-designed fish \uD83D\uDC1F\uD83D\uDE09",
        "6. After successfully catching a fish, simply tap 'Add to Aquarium' to showcase your prize!",
        "7. Now enjoy watching your collected fish come to life, playfully swimming in your vibrant aquarium widget!"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(90 / 133f)
    ) {
        ImageMaxWidth(painter = painterResource(id = R.drawable.feature_help_background))

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp)
                .padding(start = 16.dp)
                .padding(top = 48.dp, bottom = 80.dp),
        ) { page ->
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(text = "Help ${helpTextList[page]}")

                Image(
                    painter = getPainterByName("help_${page + 1}"),
                    contentDescription = "Help Image"
                )
            }
        }
    }
}