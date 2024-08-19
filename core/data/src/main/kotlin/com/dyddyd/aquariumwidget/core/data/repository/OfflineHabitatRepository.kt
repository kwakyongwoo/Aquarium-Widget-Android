package com.dyddyd.aquariumwidget.core.data.repository

import com.dyddyd.aquariumwidget.core.database.dao.HabitatDao
import com.dyddyd.aquariumwidget.core.database.model.HabitatEntity
import com.dyddyd.aquariumwidget.core.database.model.asExternalModel
import com.dyddyd.aquariumwidget.core.model.data.Habitat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class OfflineHabitatRepository @Inject constructor(
    private val habitatDao: HabitatDao,
): HabitatRepository {
    override fun getAllCollectedHabitats(): Flow<List<Habitat>> =
        habitatDao.getAllCollectedHabitats()
            .map { it.map(HabitatEntity::asExternalModel) }

    override fun getAllHabitats(): Flow<List<Habitat>> =
        habitatDao.getAllHabitats()
            .map { it.map(HabitatEntity::asExternalModel) }

    override fun getHabitatInfo(habitatId: Int): Flow<Habitat> =
        habitatDao.getHabitatInfo(habitatId)
            .map { it.asExternalModel() }
}