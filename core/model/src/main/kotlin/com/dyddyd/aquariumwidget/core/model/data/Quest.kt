package com.dyddyd.aquariumwidget.core.model.data

interface Quest {
    val questId: Int
    val habitatId: Int
    val partsId: Int
    val title: String
    val description: String
    val targetCount: Int
    var actualCount: Int

    fun updateActualCount(fishList: List<Fish>)
}

data class QuestTypeTotal(
    override val questId: Int,
    override val habitatId: Int,
    override val partsId: Int,
    override val title: String,
    override val description: String,
    override val targetCount: Int,
    override var actualCount: Int = 0
) : Quest {
    override fun updateActualCount(fishList: List<Fish>) {
        actualCount = fishList.count { it.habitatId == habitatId }
    }
}

data class QuestTypeSingle(
    override val questId: Int,
    override val habitatId: Int,
    override val partsId: Int,
    override var title: String,
    override var description: String,
    override val targetCount: Int,
    override var actualCount: Int = 0,
    val targetFishId: Int,
) : Quest {
    override fun updateActualCount(fishList: List<Fish>) {
        actualCount = fishList.count { it.fishId == targetFishId }
    }
}

data class QuestTypeTotalNoDup(
    override val questId: Int,
    override val habitatId: Int,
    override val partsId: Int,
    override val title: String,
    override val description: String,
    override val targetCount: Int,
    override var actualCount: Int = 0
): Quest {
    override fun updateActualCount(fishList: List<Fish>) {
        actualCount = fishList.toSet().count { it.habitatId == habitatId }
    }
}

data class QuestTypeRarity(
    override val questId: Int,
    override val habitatId: Int,
    override val partsId: Int,
    override var title: String,
    override var description: String,
    override val targetCount: Int,
    override var actualCount: Int = 0,
    val rarity: String,
): Quest {
    init {
        title += "$targetCount $rarity fish."
        description += "$targetCount $rarity fish."
    }

    override fun updateActualCount(fishList: List<Fish>) {
        actualCount = fishList.count { it.habitatId == habitatId && it.rarity == rarity }
    }
}