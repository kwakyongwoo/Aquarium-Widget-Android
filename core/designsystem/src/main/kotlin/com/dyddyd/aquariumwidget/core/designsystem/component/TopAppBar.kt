package com.dyddyd.aquariumwidget.core.designsystem.component

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.dyddyd.aquariumwidget.core.designsystem.R

val APP_BAR_HEIGHT = 56.dp

@Composable
fun AquariumTopAppBar(
    modifier: Modifier = Modifier,
    onHomeClick: () -> Unit,
    onCollectionClick: () -> Unit,
    onItemClick: () -> Unit,
    onHelpClick: () -> Unit,
    isHomeSelected: Boolean,
) {
    var isSideBarOpen by remember { mutableStateOf(false) }

    BackHandler(enabled = isSideBarOpen) {
        isSideBarOpen = false
    }

    Box(modifier = modifier.fillMaxSize()) {
        AnimatedVisibility(
            modifier = modifier
                .zIndex(1f),
            visible = isSideBarOpen,
            enter = slideInHorizontally(
                initialOffsetX = { it },
                animationSpec = tween(300)
            ),
            exit = slideOutHorizontally(
                targetOffsetX = { it },
                animationSpec = tween(300)
            )
        ) {
            Row {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(0.2f)
                        .noRippleClickable {
                            isSideBarOpen = false
                        }
                )

                AquariumSideBar(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(0.8f)
                        .fillMaxWidth(0.8f),
                    onHomeClick = if (isHomeSelected) {
                        { isSideBarOpen = false }
                    } else onHomeClick,
                    onCollectionClick = onCollectionClick,
                    onItemClick = onItemClick,
                    onHelpClick = onHelpClick,
                    closeSideBar = { isSideBarOpen = false }
                )
            }
        }

        Column {
            // Home Top Background aspect ratio
            Box(modifier = Modifier.aspectRatio(90 / 12f))

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .height(APP_BAR_HEIGHT)
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ImageMaxHeight(
                    painter = painterResource(id = R.drawable.core_designsystem_topappbar_icon),
                    contentDescription = "Top App Bar Icon",
                )

                Row(
                    modifier = Modifier.padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    ImageMaxHeight(
                        painter = painterResource(id = R.drawable.core_designsystem_topappbar_quest),
                        contentDescription = "Top App Bar Menu",
                    )

                    ImageMaxHeight(
                        painter = painterResource(id = R.drawable.core_designsystem_topappbar_menu),
                        contentDescription = "Top App Bar Menu",
                        modifier = Modifier
                            .clickable {
                                isSideBarOpen = !isSideBarOpen
                            },
                    )
                }
            }
        }
    }
}

@Composable
private fun AquariumSideBar(
    modifier: Modifier,
    onHomeClick: () -> Unit,
    onCollectionClick: () -> Unit,
    onItemClick: () -> Unit,
    onHelpClick: () -> Unit,
    closeSideBar: () -> Unit
) {
    val options = arrayOf(
        R.drawable.core_designsystem_sidebar_home to onHomeClick,
        R.drawable.core_designsystem_sidebar_collections to onCollectionClick,
        R.drawable.core_designsystem_sidebar_items to onItemClick,
        R.drawable.core_designsystem_sidebar_help to onHelpClick
    )

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.core_designsystem_sidebar_background),
            contentDescription = "Side Bar Background",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(id = R.drawable.core_designsystem_sidebar_menu),
                    contentDescription = "Side Bar Menu",
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(vertical = 4.dp),
                    contentScale = ContentScale.FillHeight
                )

                Image(
                    painter = painterResource(id = R.drawable.core_designsystem_sidebar_arrow),
                    contentDescription = "Side Bar Arrow",
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(vertical = 4.dp)
                        .noRippleClickable { closeSideBar() },
                    contentScale = ContentScale.FillHeight
                )
            }

            options.forEach { (drawable, onClick) ->
                SideBarOption(
                    painter = painterResource(id = drawable),
                    onClick = onClick,
                    closeSideBar = closeSideBar
                )
            }
        }
    }
}

@Composable
fun SideBarOption(
    painter: Painter,
    onClick: () -> Unit,
    closeSideBar: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(60 / 19f)
            .noRippleClickable {
                onClick()
                closeSideBar()
            },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.core_designsystem_sidebar_option_background),
            contentDescription = "Side Bar Option",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillWidth
        )

        Image(
            painter = painter,
            contentDescription = "Side Bar Item",
            modifier = Modifier.fillMaxHeight(0.25f),
            contentScale = ContentScale.FillHeight
        )
    }
}

@Composable
fun TopAppBarVerticalDivider(
    modifier: Modifier = Modifier
) {
    Column {
        Box(modifier = Modifier.height(24.dp))

        Box(modifier = Modifier.aspectRatio(90 / 12f))

        Box(modifier = modifier.height(APP_BAR_HEIGHT))
    }
}

@Preview
@Composable
fun AquariumTopAppBarPreview() {
    Box(
        modifier = Modifier.background(Color.White)
    ) {
        AquariumTopAppBar(
            onHomeClick = {},
            onCollectionClick = {},
            onItemClick = {},
            onHelpClick = {},
            isHomeSelected = true
        )

        Column {
            TopAppBarVerticalDivider()

            Text(text = "Content")
        }
    }
}