package com.dyddyd.aquariumwidget.core.data.repository

import com.dyddyd.aquariumwidget.core.model.data.Rod
import kotlinx.coroutines.flow.Flow

interface RodRepository {

    suspend fun collectRod(rodId: Int)

    fun getAllRods(): Flow<List<Rod>>

    fun getAllCollectedRods(): Flow<List<Rod>>

    fun getMatchedRod(habitatId: Int): Flow<Rod?>
}