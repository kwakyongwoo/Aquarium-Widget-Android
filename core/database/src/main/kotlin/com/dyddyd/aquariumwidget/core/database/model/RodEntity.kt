package com.dyddyd.aquariumwidget.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dyddyd.aquariumwidget.core.model.data.Rod

@Entity(tableName = "ROD")
data class RodEntity(
    @PrimaryKey
    @ColumnInfo(name = "rod_id")
    val rodId: Int,
    val name: String,
    val description: String,
    val imageUrl: String?,
)

fun RodEntity.asExternalModel() = Rod(
    rodId = rodId,
    name = name,
    description = description,
    imageUrl = imageUrl,
)