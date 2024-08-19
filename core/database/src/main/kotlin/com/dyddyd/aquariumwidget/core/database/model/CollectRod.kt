package com.dyddyd.aquariumwidget.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "COLLECT_ROD",
    primaryKeys = ["user_id", "rod_id"],
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["user_id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.NO_ACTION,
            onUpdate = ForeignKey.NO_ACTION
        ),
        ForeignKey(
            entity = RodEntity::class,
            parentColumns = ["rod_id"],
            childColumns = ["rod_id"],
            onDelete = ForeignKey.NO_ACTION,
            onUpdate = ForeignKey.NO_ACTION
        )
    ]
)
data class CollectRod(
    @ColumnInfo(name = "user_id")
    val userId: Int,
    @ColumnInfo(name = "rod_id")
    val rodId: Int
)
