package com.dyddyd.aquariumwidget.core.data.repository

import com.dyddyd.aquariumwidget.core.model.data.Fish
import kotlinx.coroutines.flow.Flow

interface FishRepository {

    suspend fun catchFish(fishId: Int, rodId: Int)

    fun getAllFish(): Flow<List<Fish>>

    fun getAllCollectFish(): Flow<List<Fish>>

    suspend fun addFishToAquarium(aquariumId: Int, fishId: Int)

    suspend fun removeFishFromAquarium(aquariumId: Int, fishId: Int)

    fun getAllFishInAquarium(aquariumId: Int): Flow<List<Fish>>

    fun getCollectedFish(fishId: Int): Flow<List<Fish>>

    fun getAllCollectedFishByRarity(rarity: String): Flow<List<Fish>>

    fun getAllCollectedFishWithoutDuplication(): Flow<List<Fish>>
}