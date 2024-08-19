package com.dyddyd.aquariumwidget.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dyddyd.aquariumwidget.core.model.data.Habitat

@Entity(tableName = "HABITAT")
data class HabitatEntity(
    @PrimaryKey
    @ColumnInfo(name = "habitat_id")
    val habitatId: Int,
    val name: String,
    val description: String,
    val imageUrl: String?,
)

fun HabitatEntity.asExternalModel() = Habitat(
    habitatId = habitatId,
    name = name,
    description = description,
    imageUrl = imageUrl,
)