package com.dyddyd.aquariumwidget.feature.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dyddyd.aquariumwidget.core.designsystem.component.ImageMaxHeight
import com.dyddyd.aquariumwidget.core.designsystem.component.ImageMaxWidth
import com.dyddyd.aquariumwidget.core.designsystem.component.dpToSp
import com.dyddyd.aquariumwidget.core.designsystem.component.noRippleClickable
import com.dyddyd.aquariumwidget.core.model.data.Parts
import com.dyddyd.aquariumwidget.core.model.data.Rod
import com.dyddyd.aquariumwidget.core.ui.getPainterByName

@Composable
internal fun ItemsRoute(
    onCancelClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ItemsViewModel = hiltViewModel()
) {
    val rodsUiState by viewModel.rodsUiState.collectAsStateWithLifecycle()
    val partsUiState by viewModel.partsUiState.collectAsStateWithLifecycle()

    ItemsScreen(
        modifier = modifier,
        onCancelClick = onCancelClick,
        selectedTab = viewModel.selectedTab,
        onTabSelected = viewModel::onTabSelected,
        rodsUiState = rodsUiState,
        partsUiState = partsUiState
    )
}

@Composable
internal fun ItemsScreen(
    modifier: Modifier = Modifier,
    onCancelClick: () -> Unit,
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    rodsUiState: RodsUiState,
    partsUiState: PartsUiState
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
                    painter = painterResource(id = R.drawable.feature_items_title),
                    contentDescription = "Items Title",
                    modifier = Modifier.height(20.dp),
                    contentScale = ContentScale.FillHeight
                )

                Image(
                    painter = getPainterByName("close"),
                    contentDescription = "Items Close",
                    modifier = Modifier
                        .size(28.dp)
                        .noRippleClickable {
                            onCancelClick()
                        },
                    contentScale = ContentScale.FillHeight
                )
            }

            if (selectedTab == RODS_TAB) {
                RodsContent(
                    onTabSelected = onTabSelected,
                    rodsUiState = rodsUiState
                )
            } else {
                PartsContent(
                    onTabSelected = onTabSelected,
                    partsUiState = partsUiState,
                    rodsUiState = rodsUiState
                )
            }
        }
    }
}

@Composable
private fun RodsContent(
    onTabSelected: (Int) -> Unit,
    rodsUiState: RodsUiState
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(90 / 137f)
    ) {
        ImageMaxWidth(painter = painterResource(id = R.drawable.feature_items_background_rod))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(11 / 137f)
        ) {
            Box(modifier = Modifier.weight(7f))

            Box(
                modifier = Modifier
                    .weight(37f),
                contentAlignment = Alignment.Center
            ) {
                ImageMaxHeight(
                    painter = painterResource(id = R.drawable.feature_items_rods_title),
                    modifier = Modifier.padding(top = 12.dp, bottom = 10.dp)
                )
            }

            Box(modifier = Modifier.weight(2f))

            Box(
                modifier = Modifier
                    .weight(37f)
                    .noRippleClickable { onTabSelected(PARTS_TAB) },
                contentAlignment = Alignment.Center
            ) {
                ImageMaxHeight(
                    painter = painterResource(id = R.drawable.feature_items_parts_title_disable),
                    modifier = Modifier.padding(top = 12.dp, bottom = 10.dp)
                )
            }

            Box(modifier = Modifier.weight(7f))
        }

        if (rodsUiState is RodsUiState.Success) {
            Column(modifier = Modifier.fillMaxSize()) {
                Box(modifier = Modifier.weight(16f))

                Row(
                    modifier = Modifier
                        .weight(100f)
                        .fillMaxWidth()
                ) {
                    Box(modifier = Modifier.weight(11f))

                    Box(modifier = Modifier.weight(70f)) {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            rodsUiState.allRods.forEach { rod ->
                                item {
                                    if (rodsUiState.collectedRods.contains(rod)) {
                                        RodItem(rod = rod)
                                    } else {
                                        ImageMaxWidth(painter = painterResource(R.drawable.feature_items_rods_item_unknown_background))
                                    }
                                }
                            }
                        }
                    }

                    Box(modifier = Modifier.weight(9f))
                }

                Box(modifier = Modifier.weight(21f))
            }
        }
    }
}

@Composable
private fun RodItem(
    rod: Rod
) {
    val habitatName = arrayOf("Pond", "Lake", "River", "Sea", "Sea")

    Box(contentAlignment = Alignment.Center) {
        ImageMaxWidth(painter = painterResource(R.drawable.feature_items_rods_item_background))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.weight(4f))

            Box(
                modifier = Modifier.weight(14f),
                contentAlignment = Alignment.Center
            ) {
                ImageMaxWidth(painter = painterResource(R.drawable.feature_items_box))

                ImageMaxWidth(
                    painter = getPainterByName("rod_${rod.rodId}"),
                    modifier = Modifier.padding(12.dp)
                )
            }

            Box(modifier = Modifier.weight(7f))

            Column(
                modifier = Modifier.weight(42f),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = rod.name.replace("Fishing ", ""),
                    fontSize = dpToSp(18.dp)
                )

                Text(
                    text = "Habitat : ${habitatName[rod.rodId - 1]}",
                    fontSize = dpToSp(14.dp)
                )
            }

            Box(modifier = Modifier.weight(3f))
        }
    }
}

@Composable
private fun PartsContent(
    onTabSelected: (Int) -> Unit,
    partsUiState: PartsUiState,
    rodsUiState: RodsUiState
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(90 / 137f)
    ) {
        ImageMaxWidth(painter = painterResource(id = R.drawable.feature_items_background_part))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(11 / 137f)
        ) {
            Box(modifier = Modifier.weight(7f))

            Box(
                modifier = Modifier
                    .weight(37f)
                    .noRippleClickable { onTabSelected(RODS_TAB) },
                contentAlignment = Alignment.Center
            ) {
                ImageMaxHeight(
                    painter = painterResource(id = R.drawable.feature_items_rods_title_disable),
                    modifier = Modifier.padding(top = 12.dp, bottom = 10.dp)
                )
            }

            Box(modifier = Modifier.weight(2f))

            Box(
                modifier = Modifier
                    .weight(37f),
                contentAlignment = Alignment.Center
            ) {
                ImageMaxHeight(
                    painter = painterResource(id = R.drawable.feature_items_parts_title),
                    modifier = Modifier.padding(top = 12.dp, bottom = 10.dp)
                )
            }

            Box(modifier = Modifier.weight(7f))
        }

        if (partsUiState is PartsUiState.Success && rodsUiState is RodsUiState.Success) {
            Column(modifier = Modifier.fillMaxSize()) {
                Box(modifier = Modifier.weight(16f))

                Row(
                    modifier = Modifier
                        .weight(100f)
                        .fillMaxWidth()
                ) {
                    Box(modifier = Modifier.weight(11f))

                    Box(modifier = Modifier.weight(70f)) {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            rodsUiState.allRods.forEach { rod ->
                                item {
                                    PartItem(
                                        allParts = partsUiState.allParts.filter { it.rodId == rod.rodId },
                                        collectedParts = partsUiState.collectedParts.filter { it.rodId == rod.rodId }
                                    )
                                }
                            }
                        }
                    }

                    Box(modifier = Modifier.weight(9f))
                }

                Box(modifier = Modifier.weight(21f))
            }
        }
    }
}

@Composable
private fun PartItem(
    allParts: List<Parts>,
    collectedParts: List<Parts>
) {
    val partName = arrayOf("Reel", "Rod", "Line")

    Box(contentAlignment = Alignment.Center) {
        ImageMaxWidth(painter = painterResource(R.drawable.feature_items_parts_item_background))

        Row(modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier.weight(2f))

            allParts.forEachIndexed { index, part ->
                Column(
                    modifier = Modifier.weight(14f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = partName[index],
                        fontSize = dpToSp(14.dp),
                        style = MaterialTheme.typography.bodyLarge.copy(lineHeight = dpToSp(14.dp))
                    )

                    if (collectedParts.contains(part)) {
                        Box(contentAlignment = Alignment.Center) {
                            ImageMaxWidth(painter = painterResource(R.drawable.feature_items_box))

                            ImageMaxWidth(
                                painter = getPainterByName("part_${part.partsId}"),
                                modifier = Modifier.padding(12.dp)
                            )
                        }
                    } else {
                        ImageMaxWidth(painter = painterResource(R.drawable.feature_items_box_unknown))
                    }
                }

                Box(modifier = Modifier.weight(2f))
            }

            Column(
                modifier = Modifier.weight(5f).align(Alignment.CenterVertically),
            ) {
                Text(
                    text = "",
                    style = MaterialTheme.typography.bodyLarge.copy(lineHeight = dpToSp(14.dp))
                )

                ImageMaxWidth(painter = painterResource(R.drawable.feature_items_arrow))
            }

            Box(modifier = Modifier.weight(2f))

            Column(
                modifier = Modifier.weight(14f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "",
                    style = MaterialTheme.typography.bodyLarge.copy(lineHeight = dpToSp(14.dp))
                )

                if (collectedParts.size == 3) {
                    Box(contentAlignment = Alignment.Center) {
                        ImageMaxWidth(painter = painterResource(R.drawable.feature_items_box))

                        ImageMaxWidth(
                            painter = getPainterByName("rod_${collectedParts[0].rodId}"),
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }
                else {
                    ImageMaxWidth(painter = painterResource(R.drawable.feature_items_box_unknown))
                }
            }

            Box(modifier = Modifier.weight(2f))
        }
    }
}