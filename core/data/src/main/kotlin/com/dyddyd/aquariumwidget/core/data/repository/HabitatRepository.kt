package com.dyddyd.aquariumwidget.core.data.repository

import com.dyddyd.aquariumwidget.core.model.data.Habitat
import kotlinx.coroutines.flow.Flow

interface HabitatRepository {

    fun getAllCollectedHabitats(): Flow<List<Habitat>>

    fun getAllHabitats(): Flow<List<Habitat>>

    fun getHabitatInfo(habitatId: Int): Flow<Habitat>
}