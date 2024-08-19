package com.dyddyd.aquariumwidget.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.dyddyd.aquariumwidget.core.database.model.AquariumEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AquariumDao {

    @Query(
        value = """
            SELECT * FROM AQUARIUM 
            WHERE AQUARIUM.aquarium_id IN (
                SELECT AQUARIUM.aquarium_id FROM AQUARIUM, COLLECT_ROD, `MATCH` 
                WHERE 
                    AQUARIUM.aquarium_id = `MATCH`.aquarium_id 
                    AND `MATCH`.rod_id = COLLECT_ROD.rod_id
                    AND COLLECT_ROD.user_id = :userId)
        """
    )
    fun getAllCollectedAquariums(userId: Int = 1): Flow<List<AquariumEntity>>

    @Query(
        value = """
            SELECT * FROM AQUARIUM
            WHERE AQUARIUM.aquarium_id IN (
                SELECT AQUARIUM.aquarium_id FROM AQUARIUM, `MATCH`, HABITAT 
                WHERE 
                    AQUARIUM.aquarium_id = `MATCH`.aquarium_id 
                    AND `MATCH`.habitat_id = HABITAT.habitat_id 
                    AND HABITAT.habitat_id = :habitatId)
        """
    )
    fun getMatchedAquarium(habitatId: Int): Flow<AquariumEntity?>
}