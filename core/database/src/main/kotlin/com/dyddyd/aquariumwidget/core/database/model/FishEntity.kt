package com.dyddyd.aquariumwidget.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.dyddyd.aquariumwidget.core.model.data.Fish

@Entity(
    tableName = "FISH",
    foreignKeys = [
        ForeignKey(
            entity = HabitatEntity::class,
            parentColumns = ["habitat_id"],
            childColumns = ["habitat_id"],
            onDelete = ForeignKey.NO_ACTION,
            onUpdate = ForeignKey.NO_ACTION
        )
    ]
)
data class FishEntity(
    @PrimaryKey
    @ColumnInfo(name = "fish_id")
    val fishId: Int,
    val name: String,
    val description: String,
    val rarity: String,
    val imageUrl: String?,
    @ColumnInfo(name = "habitat_id")
    val habitatId: Int,
)

fun FishEntity.asExternalModel() = Fish(
    fishId = fishId,
    name = name,
    description = description,
    rarity = rarity,
    imageUrl = imageUrl,
    habitatId = habitatId,
)