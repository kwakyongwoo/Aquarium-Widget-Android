package com.dyddyd.aquariumwidget.core.data.repository

import com.dyddyd.aquariumwidget.core.model.data.Parts
import com.dyddyd.aquariumwidget.core.model.data.Quest
import kotlinx.coroutines.flow.Flow

interface QuestRepository {

    suspend fun clearQuest(habitatId: Int, questId: Int)

    fun getAllQuestsInHabitat(habitatId: Int): Flow<List<Quest>>

    fun getAllClearQuestsInHabitat(habitatId: Int): Flow<List<Quest>>


    /**
     * Check all quest cleared in the habitat
     * use getTotalQuestNum and getClearQuestNum
     */
    fun checkAllQuestClearedInTheHabitat(habitatId: Int): Flow<Boolean>

    fun checkQuestCleared(questId: Int): Flow<Boolean>

    fun getQuestReward(questId: Int): Flow<Parts?>
}