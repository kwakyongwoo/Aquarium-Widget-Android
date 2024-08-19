package com.dyddyd.aquariumwidget.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.dyddyd.aquariumwidget.core.database.model.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT USER.user_id FROM USER LIMIT 1")
    fun getUserId(): Flow<Int>

    @Query("SELECT * FROM USER WHERE user_id = :userId")
    fun getUserInfo(userId: Int = 1): Flow<UserEntity>

    @Query("UPDATE USER SET chance = chance - 1 WHERE user_id = :userId AND chance > 0;")
    suspend fun decreaseGameChanceCount(userId: Int = 1)

    @Query("UPDATE USER SET chance = 5 WHERE user_id = :userId")
    suspend fun resetGameChanceCount(userId: Int = 1)

    @Query("UPDATE USER SET selected_aquarium_theme_id = :aquariumId WHERE user_id = :userId")
    suspend fun changeAquariumTheme(userId: Int = 1, aquariumId: Int)

    @Query("SELECT MAX(habitat_id) FROM HABITAT")
    fun getMaxHabitat(): Flow<Int>

    @Query("UPDATE USER SET cur_habitat = cur_habitat + 1 WHERE user_id = :userId AND cur_habitat = :habitatId")
    suspend fun clearCurrentHabitat(userId: Int = 1, habitatId: Int)

    @Query("UPDATE USER SET last_played_date = :curDate WHERE user_id = :userId")
    suspend fun updateLastPlayedDate(curDate: Int, userId: Int = 1)
}