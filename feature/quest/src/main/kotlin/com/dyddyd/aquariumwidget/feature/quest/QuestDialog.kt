package com.dyddyd.aquariumwidget.feature.quest

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dyddyd.aquariumwidget.core.designsystem.component.ImageMaxHeight
import com.dyddyd.aquariumwidget.core.designsystem.component.ImageMaxWidth
import com.dyddyd.aquariumwidget.core.designsystem.component.dpToSp
import com.dyddyd.aquariumwidget.core.model.data.Quest
import com.dyddyd.aquariumwidget.core.ui.getPainterByName

@Composable
fun QuestDialog(
    onDismiss: () -> Unit,
    viewModel: QuestViewModel = hiltViewModel()
) {
    val questUiState by viewModel.questUiState.collectAsStateWithLifecycle()

    QuestDialog(
        onDismiss = onDismiss,
        questUiState = questUiState
    )
}

@Composable
private fun QuestDialog(
    onDismiss: () -> Unit,
    questUiState: QuestUiState
) {
    var animate by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        animate = true
    }

    Dialog(onDismissRequest = onDismiss) {
        AnimatedVisibility(
            visible = animate,
            enter = slideInVertically(
                initialOffsetY = { it },
                animationSpec = tween(700)
            )
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.feature_quest_title),
                    contentDescription = "quest background",
                    modifier = Modifier
                        .height(24.dp)
                        .padding(horizontal = 16.dp),
                    contentScale = ContentScale.FillHeight
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    ImageMaxWidth(
                        painter = painterResource(id = R.drawable.feature_quest_background),
                        contentDescription = "quest background",
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 32.dp, end = 48.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .height(16.dp)
                                .padding(start = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            ImageMaxHeight(
                                painter = painterResource(id = R.drawable.feature_quest_stage_title),
                                contentDescription = "quest stage title",
                            )

                            if (questUiState is QuestUiState.Success) {
                                ImageMaxHeight(
                                    painter = getPainterByName("quest_title_${questUiState.habitatId}"),
                                    contentDescription = "quest stage",
                                )
                            }
                        }

                        if (questUiState is QuestUiState.Success) {
                            val clearedQuests = questUiState.allClearQuestsInHabitat

                            questUiState.allQuestsInHabitat.forEach { quest ->
                                QuestItem(
                                    quest = quest,
                                    isCleared = clearedQuests.contains(quest),
                                    partsName = questUiState.allParts.find { it.partsId == quest.partsId }?.name ?: ""
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun QuestItem(
    modifier: Modifier = Modifier,
    quest: Quest,
    isCleared: Boolean,
    partsName: String
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        ImageMaxWidth(painter = painterResource(id = R.drawable.feature_quest_content_background))

        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp).padding(bottom = 16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                ImageMaxHeight(
                    painter = painterResource(id = if (isCleared) R.drawable.feature_quest_check else R.drawable.feature_quest_uncheck),
                    modifier = Modifier.height(24.dp),
                    contentDescription = "quest content title",
                )

                Text(
                    text = quest.title,
                    fontSize = dpToSp(16.dp),
                    style = MaterialTheme.typography.bodyLarge.copy(lineHeight = dpToSp(24.dp)),
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "• Reward: $partsName",
                    fontSize = dpToSp(14.dp),
                    style = MaterialTheme.typography.bodyLarge.copy(lineHeight = dpToSp(14.dp)),
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                Text(
                    text = "• Progress: ${quest.actualCount} out of ${quest.targetCount}",
                    fontSize = dpToSp(14.dp),
                    style = MaterialTheme.typography.bodyLarge.copy(lineHeight = dpToSp(14.dp)),
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }
    }
}