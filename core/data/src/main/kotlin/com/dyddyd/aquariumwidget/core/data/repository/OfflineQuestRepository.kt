package com.dyddyd.aquariumwidget.core.data.repository

import com.dyddyd.aquariumwidget.core.database.dao.QuestDao
import com.dyddyd.aquariumwidget.core.database.model.QuestEntity
import com.dyddyd.aquariumwidget.core.database.model.asExternalModel
import com.dyddyd.aquariumwidget.core.model.data.Parts
import com.dyddyd.aquariumwidget.core.model.data.Quest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class OfflineQuestRepository @Inject constructor(
    private val questDao: QuestDao,
) : QuestRepository {
    override suspend fun clearQuest(habitatId: Int, questId: Int) {
        questDao.clearQuest(habitatId = habitatId, questId = questId)
    }

    override fun getAllQuestsInHabitat(habitatId: Int): Flow<List<Quest>> =
        questDao.getAllQuestsInTheHabitat(habitatId)
            .map { it.map(QuestEntity::asExternalModel) }

    override fun getAllClearQuestsInHabitat(habitatId: Int): Flow<List<Quest>> =
        questDao.getAllClearedQuestsInTheHabitat(habitatId = habitatId)
            .map { it.map(QuestEntity::asExternalModel) }

    override fun checkAllQuestClearedInTheHabitat(habitatId: Int): Flow<Boolean> =
        combine(
            questDao.getTotalQuestNum(habitatId),
            questDao.getClearQuestNum(habitatId = habitatId)
        ) { total, clear ->
            total == clear
        }

    override fun checkQuestCleared(questId: Int): Flow<Boolean> =
        questDao.checkQuestCleared(questId = questId)

    override fun getQuestReward(questId: Int): Flow<Parts?> =
        questDao.getQuestReward(questId)
            .map { it?.asExternalModel() }

}