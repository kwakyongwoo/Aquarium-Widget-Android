package com.dyddyd.aquariumwidget.core.data.repository

import com.dyddyd.aquariumwidget.core.model.data.User
import kotlinx.coroutines.flow.Flow

// Room User Table Repository, not datastore
interface UserRepository {

    fun getUserId(): Flow<Int>

    fun getUserInfo(): Flow<User>

    suspend fun decreaseGameChanceCount()

    suspend fun resetGameChanceCount()

    suspend fun changeAquariumTheme(aquariumId: Int)

    /**
     * check getMaxHabitat()
     * clearCurrentHabitat()
     */
    suspend fun clearCurrentHabitat(habitatId: Int)

    suspend fun updateLastPlayedDate(curDate: Int)
}