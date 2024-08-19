package com.dyddyd.aquariumwidget.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.dyddyd.aquariumwidget.core.database.model.RodEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RodDao {

    @Query("INSERT OR IGNORE INTO COLLECT_ROD (user_id, rod_id) VALUES (:userId, :rodId);")
    suspend fun collectRod(userId: Int = 1, rodId: Int)

    @Query("SELECT * FROM ROD")
    fun getAllRods(): Flow<List<RodEntity>>

    @Query("SELECT * FROM ROD INNER JOIN COLLECT_ROD ON ROD.rod_id = COLLECT_ROD.rod_id WHERE COLLECT_ROD.user_id = :userId")
    fun getAllCollectedRods(userId: Int = 1): Flow<List<RodEntity>>

    @Query(
        value = """
            SELECT * FROM ROD
            WHERE ROD.rod_id IN (
                SELECT ROD.rod_id FROM ROD, `MATCH`, HABITAT
                WHERE 
                    ROD.rod_id = `MATCH`.rod_id 
                    AND `MATCH`.habitat_id = HABITAT.habitat_id 
                    AND HABITAT.habitat_id = :habitatId
            )
        """
    )
    fun getMatchedRod(habitatId: Int): Flow<RodEntity?>
}