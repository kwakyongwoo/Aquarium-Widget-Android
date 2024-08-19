package com.dyddyd.aquariumwidget.core.data.repository

import com.dyddyd.aquariumwidget.core.database.dao.PartsDao
import com.dyddyd.aquariumwidget.core.database.model.PartsEntity
import com.dyddyd.aquariumwidget.core.database.model.asExternalModel
import com.dyddyd.aquariumwidget.core.model.data.Parts
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class OfflinePartsRepository @Inject constructor(
    private val partsDao: PartsDao,
): PartsRepository {
    override suspend fun collectPart(partId: Int) {
        partsDao.collectParts(partsId = partId)
    }

    override fun getAllParts(): Flow<List<Parts>> =
        partsDao.getAllParts()
            .map { it.map(PartsEntity::asExternalModel) }

    override fun getAllCollectedParts(): Flow<List<Parts>> =
        partsDao.getAllCollectedParts()
            .map { it.map(PartsEntity::asExternalModel) }
}