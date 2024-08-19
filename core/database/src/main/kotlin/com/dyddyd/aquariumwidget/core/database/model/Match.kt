package com.dyddyd.aquariumwidget.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "MATCH",
    primaryKeys = ["aquarium_id", "rod_id", "habitat_id"],
    foreignKeys = [
        ForeignKey(
            entity = AquariumEntity::class,
            parentColumns = ["aquarium_id"],
            childColumns = ["aquarium_id"],
            onDelete = ForeignKey.NO_ACTION,
            onUpdate = ForeignKey.NO_ACTION
        ),
        ForeignKey(
            entity = RodEntity::class,
            parentColumns = ["rod_id"],
            childColumns = ["rod_id"],
            onDelete = ForeignKey.NO_ACTION,
            onUpdate = ForeignKey.NO_ACTION
        ),
        ForeignKey(
            entity = HabitatEntity::class,
            parentColumns = ["habitat_id"],
            childColumns = ["habitat_id"],
            onDelete = ForeignKey.NO_ACTION,
            onUpdate = ForeignKey.NO_ACTION
        )

    ]
)
data class Match(
    @ColumnInfo(name = "aquarium_id")
    val aquariumId: Int,
    @ColumnInfo(name = "rod_id")
    val rodId: Int,
    @ColumnInfo(name = "habitat_id")
    val habitatId: Int,
)
