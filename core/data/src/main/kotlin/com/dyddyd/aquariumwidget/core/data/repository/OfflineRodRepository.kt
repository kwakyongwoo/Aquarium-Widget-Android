package com.dyddyd.aquariumwidget.core.data.repository

import com.dyddyd.aquariumwidget.core.database.dao.RodDao
import com.dyddyd.aquariumwidget.core.database.model.RodEntity
import com.dyddyd.aquariumwidget.core.database.model.asExternalModel
import com.dyddyd.aquariumwidget.core.model.data.Rod
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class OfflineRodRepository @Inject constructor(
    private val rodDao: RodDao,
): RodRepository {
    override suspend fun collectRod(rodId: Int) {
        rodDao.collectRod(rodId = rodId)
    }

    override fun getAllRods(): Flow<List<Rod>> =
        rodDao.getAllRods()
            .map { it.map(RodEntity::asExternalModel) }

    override fun getAllCollectedRods(): Flow<List<Rod>> =
        rodDao.getAllCollectedRods()
            .map { it.map(RodEntity::asExternalModel) }

    override fun getMatchedRod(habitatId: Int): Flow<Rod?> =
        rodDao.getMatchedRod(habitatId)
            .map { it?.asExternalModel() }
}