package com.dyddyd.aquariumwidget.core.database.di

import com.dyddyd.aquariumwidget.core.database.AquariumDatabase
import com.dyddyd.aquariumwidget.core.database.dao.AquariumDao
import com.dyddyd.aquariumwidget.core.database.dao.FishDao
import com.dyddyd.aquariumwidget.core.database.dao.HabitatDao
import com.dyddyd.aquariumwidget.core.database.dao.PartsDao
import com.dyddyd.aquariumwidget.core.database.dao.QuestDao
import com.dyddyd.aquariumwidget.core.database.dao.RodDao
import com.dyddyd.aquariumwidget.core.database.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DaosModule {

    @Provides
    fun provideAquariumDao(
        database: AquariumDatabase
    ): AquariumDao = database.aquariumDao()

    @Provides
    fun provideFishDao(
        database: AquariumDatabase
    ): FishDao = database.fishDao()

    @Provides
    fun provideHabitatDao(
        database: AquariumDatabase
    ): HabitatDao = database.habitatDao()

    @Provides
    fun providePartsDao(
        database: AquariumDatabase
    ): PartsDao = database.partsDao()

    @Provides
    fun provideQuestDao(
        database: AquariumDatabase
    ): QuestDao = database.questDao()

    @Provides
    fun provideRodDao(
        database: AquariumDatabase
    ): RodDao = database.rodDao()

    @Provides
    fun provideUserDao(
        database: AquariumDatabase
    ): UserDao = database.userDao()
}