package com.dyddyd.aquariumwidget.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.dyddyd.aquariumwidget.core.model.data.Fish
import com.dyddyd.aquariumwidget.core.model.data.Quest
import com.dyddyd.aquariumwidget.core.model.data.QuestTypeRarity
import com.dyddyd.aquariumwidget.core.model.data.QuestTypeSingle
import com.dyddyd.aquariumwidget.core.model.data.QuestTypeTotal
import com.dyddyd.aquariumwidget.core.model.data.QuestTypeTotalNoDup

@Entity(
    tableName = "QUEST",
    foreignKeys = [
        ForeignKey(
            entity = HabitatEntity::class,
            parentColumns = ["habitat_id"],
            childColumns = ["habitat_id"],
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
data class QuestEntity(
    @PrimaryKey
    @ColumnInfo(name = "quest_id")
    val questId: Int,
    @ColumnInfo(name = "habitat_id")
    val habitatId: Int,
    @ColumnInfo(name = "parts_id")
    val partsId: Int,
)

fun QuestEntity.asExternalModel(fishList: List<Fish>): Quest {
    return when (questId % 3) {
        1 -> {
            QuestTypeTotal(
                questId = questId,
                habitatId = habitatId,
                partsId = partsId,
                title = "Catch more than ${habitatId * 5} fish.",
                description = "Catch more than ${habitatId * 5} fish.",
                targetCount = habitatId * 5
            )
        }

        2 -> {
            if (habitatId <= 2) {
                QuestTypeSingle(
                    questId = questId,
                    habitatId = habitatId,
                    partsId = partsId,
                    title = "Catch $habitatId ${if (habitatId == 1) "Emerald" else "Angel"} fish.",
                    description = "Catch $habitatId ${if (habitatId == 1) "Emerald" else "Angel"} fish.",
                    targetCount = habitatId,
                    targetFishId = (habitatId - 1) * 10 + 7
                )
            } else {
                QuestTypeTotalNoDup(
                    questId = questId,
                    habitatId = habitatId,
                    partsId = partsId,
                    title = "Catch ${(habitatId + 1) * 2} different fishes.",
                    description = "Catch ${(habitatId + 1) * 2} different fishes.",
                    targetCount = (habitatId + 1) * 2
                )
            }
        }

        0 -> {
            if (habitatId <= 3) {
                QuestTypeRarity(
                    questId = questId,
                    habitatId = habitatId,
                    partsId = partsId,
                    title = "Catch ",
                    description = "Catch ",
                    targetCount = if (habitatId <= 2) 3 else 5,
                    rarity = if (habitatId == 1) "Uncommon" else if (habitatId == 2) "Rare" else "Epic"
                )
            } else {
                QuestTypeSingle(
                    questId = questId,
                    habitatId = habitatId,
                    partsId = partsId,
                    title = "Catch Leviathan.",
                    description = "Catch Leviathan.",
                    targetCount = 1,
                    targetFishId = 44
                )
            }
        }

        else -> {
            QuestTypeTotal(
                questId = questId,
                habitatId = habitatId,
                partsId = partsId,
                title = "Catch more than ${habitatId * 5} fish.",
                description = "Catch more than ${habitatId * 5} fish.",
                targetCount = habitatId * 5
            )
        }
    }.apply {
        updateActualCount(fishList)
    }
}