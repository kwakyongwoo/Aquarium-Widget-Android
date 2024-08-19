package com.dyddyd.aquariumwidget.core.model.data

interface Quest {
    val questId: Int
    val habitatId: Int
    val partsId: Int
    val title: String
    val description: String
    val targetCount: Int
}

data class QuestTypeTotal(
    override val questId: Int,
    override val habitatId: Int,
    override val partsId: Int,
    override val title: String,
    override val description: String,
    override val targetCount: Int,
) : Quest

data class QuestTypeSingle(
    override val questId: Int,
    override val habitatId: Int,
    override val partsId: Int,
    override var title: String,
    override var description: String,
    override val targetCount: Int,
    val targetFishId: Int,
) : Quest

data class QuestTypeTotalNoDup(
    override val questId: Int,
    override val habitatId: Int,
    override val partsId: Int,
    override val title: String,
    override val description: String,
    override val targetCount: Int
): Quest

data class QuestTypeRarity(
    override val questId: Int,
    override val habitatId: Int,
    override val partsId: Int,
    override var title: String,
    override var description: String,
    override val targetCount: Int,
    val rarity: String,
): Quest {
    init {
        title += "$targetCount $rarity fish."
        description += "$targetCount $rarity fish."
    }
}