package com.dyddyd.aquariumwidget.core.model.data

data class Fish(
    val fishId: Int,
    val name: String,
    val description: String,
    val rarity: String,
    val imageUrl: String?,
    val habitatId: Int,
)