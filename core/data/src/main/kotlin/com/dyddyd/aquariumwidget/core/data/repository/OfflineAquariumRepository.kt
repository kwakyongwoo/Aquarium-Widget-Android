package com.dyddyd.aquariumwidget.core.data.repository

import com.dyddyd.aquariumwidget.core.database.dao.AquariumDao
import com.dyddyd.aquariumwidget.core.database.model.AquariumEntity
import com.dyddyd.aquariumwidget.core.database.model.asExternalModel
import com.dyddyd.aquariumwidget.core.model.data.Aquarium
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class OfflineAquariumRepository @Inject constructor(
    private val aquariumDao: AquariumDao,
): AquariumRepository {
    override fun getAllCollectedAquariums(): Flow<List<Aquarium>> =
        aquariumDao.getAllCollectedAquariums()
            .map { it.map(AquariumEntity::asExternalModel) }

    override fun getMatchedAquarium(habitatId: Int): Flow<Aquarium?> =
        aquariumDao.getMatchedAquarium(habitatId)
            .map { it?.asExternalModel() }

}