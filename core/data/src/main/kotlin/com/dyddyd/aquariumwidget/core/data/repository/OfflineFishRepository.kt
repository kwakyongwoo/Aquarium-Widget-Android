package com.dyddyd.aquariumwidget.core.data.repository

import android.content.Context
import com.dyddyd.aquariumwidget.core.database.dao.FishDao
import com.dyddyd.aquariumwidget.core.database.model.FishEntity
import com.dyddyd.aquariumwidget.core.database.model.asExternalModel
import com.dyddyd.aquariumwidget.core.model.data.Fish
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class OfflineFishRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val fishDao: FishDao,
): FishRepository {
    override suspend fun catchFish(fishId: Int, rodId: Int) {
        fishDao.catchFish(fishId = fishId, rodId = rodId)
    }

    override fun getAllFish(): Flow<List<Fish>> =
        fishDao.getAllFish()
            .map { it.map(FishEntity::asExternalModel) }

    override fun getAllCollectFish(): Flow<List<Fish>> =
        fishDao.getAllCollectFish()
            .map { it.map(FishEntity::asExternalModel) }

    override suspend fun addFishToAquarium(aquariumId: Int, fishId: Int) {
        fishDao.addFishToAquarium(aquariumId = aquariumId, fishId = fishId)
    }

    override suspend fun removeFishFromAquarium(aquariumId: Int, fishId: Int) {
        fishDao.removeFishFromAquarium(aquariumId = aquariumId, fishId = fishId)
    }

    override fun getAllFishInAquarium(aquariumId: Int): Flow<List<Fish>> =
        fishDao.getAllFishInAquarium(aquariumId = aquariumId)
            .map {
                val pref = context.getSharedPreferences("aquarium", Context.MODE_PRIVATE)
                pref.edit().putString("aquarium_id", aquariumId.toString()).apply()
                pref.edit().putString("fish_list", it.joinToString(",") { f -> f.fishId.toString() }).apply()

                it.map(FishEntity::asExternalModel)
            }

    override fun getCollectedFish(fishId: Int): Flow<List<Fish>> =
        fishDao.getCollectedFish(fishId = fishId)
            .map { it.map(FishEntity::asExternalModel) }

    override fun getFishById(fishId: Int): Flow<Fish> =
        fishDao.getFishById(fishId)
            .map { it.asExternalModel() }

    override fun getAllCollectedFishByRarity(rarity: String): Flow<List<Fish>> =
        fishDao.getAllCollectedFishByRarity(rarity = rarity)
            .map { it.map(FishEntity::asExternalModel) }

    override fun getAllCollectedFishWithoutDuplication(): Flow<List<Fish>> =
        fishDao.getAllCollectedFishWithoutDuplication()
            .map { it.map(FishEntity::asExternalModel) }

}