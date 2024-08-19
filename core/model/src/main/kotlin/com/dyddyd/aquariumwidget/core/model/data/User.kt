package com.dyddyd.aquariumwidget.core.model.data

data class User(
    val userId: Int,
    val chance: Int,
    val curHabitat: Int,
    val selectedAquariumThemeId: Int?,
    val lastPlayedDate: Int?,
)