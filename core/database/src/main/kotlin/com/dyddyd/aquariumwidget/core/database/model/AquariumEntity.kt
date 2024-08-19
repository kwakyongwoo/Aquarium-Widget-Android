package com.dyddyd.aquariumwidget.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dyddyd.aquariumwidget.core.model.data.Aquarium

@Entity(tableName = "AQUARIUM")
data class AquariumEntity(
    @PrimaryKey
    @ColumnInfo(name = "aquarium_id")
    val aquariumId: Int,
    val name: String,
    val description: String,
    val imageUrl: String?,
)

fun AquariumEntity.asExternalModel() = Aquarium(
    aquariumId = aquariumId,
    name = name,
    description = description,
    imageUrl = imageUrl,
)