package com.dyddyd.aquariumwidget.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

/*
CREATE TABLE "COLLECT_PARTS" (
	"user_id"	INTEGER NOT NULL,
	"parts_id"	INTEGER NOT NULL,
	PRIMARY KEY("user_id","parts_id"),
	FOREIGN KEY("user_id") REFERENCES "USER"("user_id"),
	FOREIGN KEY("parts_id") REFERENCES "PARTS"("parts_id")
);
 */
@Entity(
    tableName = "COLLECT_PARTS",
    primaryKeys = ["user_id", "parts_id"],
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["user_id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.NO_ACTION,
            onUpdate = ForeignKey.NO_ACTION
        ),
        ForeignKey(
            entity = PartsEntity::class,
            parentColumns = ["parts_id"],
            childColumns = ["parts_id"],
            onDelete = ForeignKey.NO_ACTION,
            onUpdate = ForeignKey.NO_ACTION
        )
    ]
)
data class CollectParts(
    @ColumnInfo(name = "user_id")
    val userId: Int,
    @ColumnInfo(name = "parts_id")
    val partsId: Int
)