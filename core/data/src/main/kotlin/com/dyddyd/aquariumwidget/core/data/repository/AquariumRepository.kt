package com.dyddyd.aquariumwidget.core.data.repository

import com.dyddyd.aquariumwidget.core.model.data.Aquarium
import kotlinx.coroutines.flow.Flow

interface AquariumRepository {

    fun getAllCollectedAquariums(): Flow<List<Aquarium>>

    fun getMatchedAquarium(habitatId: Int): Flow<Aquarium?>
}