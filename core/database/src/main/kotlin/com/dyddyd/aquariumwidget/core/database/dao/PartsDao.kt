package com.dyddyd.aquariumwidget.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.dyddyd.aquariumwidget.core.database.model.PartsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PartsDao {

    @Query("INSERT OR IGNORE INTO COLLECT_PARTS (user_id, parts_id) VALUES (:userId, :partsId)")
    suspend fun collectParts(userId: Int = 1, partsId: Int)

    @Query("SELECT * FROM PARTS")
    fun getAllParts(): Flow<List<PartsEntity>>

    @Query("SELECT * FROM PARTS INNER JOIN COLLECT_PARTS ON PARTS.parts_id = COLLECT_PARTS.parts_id WHERE COLLECT_PARTS.user_id = :userId")
    fun getAllCollectedParts(userId: Int = 1): Flow<List<PartsEntity>>
}