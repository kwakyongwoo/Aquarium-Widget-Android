package com.dyddyd.aquariumwidget.core.data.repository

import android.util.Log
import com.dyddyd.aquariumwidget.core.database.dao.UserDao
import com.dyddyd.aquariumwidget.core.database.model.asExternalModel
import com.dyddyd.aquariumwidget.core.model.data.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class OfflineUserRepository @Inject constructor(
    private val userDao: UserDao,
): UserRepository {
    override fun getUserId(): Flow<Int> =
        userDao.getUserId()

    override fun getUserInfo(): Flow<User> =
        userDao.getUserInfo()
            .map { it.asExternalModel() }

    override suspend fun decreaseGameChanceCount() {
        userDao.decreaseGameChanceCount()
    }

    override suspend fun resetGameChanceCount() {
        userDao.resetGameChanceCount()
    }

    override suspend fun changeAquariumTheme(aquariumId: Int) {
        userDao.changeAquariumTheme(aquariumId = aquariumId)
    }

//    override fun clearCurrentHabitat(userId: Int, habitatId: Int): Flow<Boolean> =
//        userDao.getMaxHabitat().map { maxHabitatId ->
//            Log.d("OfflineUserRepository", "maxHabitatId: $maxHabitatId $habitatId")
//            if (habitatId < maxHabitatId) {
//                userDao.clearCurrentHabitat(userId, habitatId)
//                return@map true
//            }
//            return@map false
//        }

    override suspend fun clearCurrentHabitat(habitatId: Int) {
        if (habitatId < 5) {
            Log.d("OfflineUserRepository", "clearCurrentHabitat: $habitatId")
            userDao.clearCurrentHabitat(habitatId = habitatId)
        }
    }

    override suspend fun updateLastPlayedDate(curDate: Int) {
        userDao.updateLastPlayedDate(curDate)
    }

    override suspend fun setCurrentHabitat(habitatId: Int) {
        userDao.setCurrentHabitat(habitatId = habitatId)
    }
}