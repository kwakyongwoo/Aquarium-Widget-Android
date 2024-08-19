package com.dyddyd.aquariumwidget.core.data.di

import com.dyddyd.aquariumwidget.core.data.repository.AquariumRepository
import com.dyddyd.aquariumwidget.core.data.repository.FishRepository
import com.dyddyd.aquariumwidget.core.data.repository.HabitatRepository
import com.dyddyd.aquariumwidget.core.data.repository.OfflineAquariumRepository
import com.dyddyd.aquariumwidget.core.data.repository.OfflineFishRepository
import com.dyddyd.aquariumwidget.core.data.repository.OfflineHabitatRepository
import com.dyddyd.aquariumwidget.core.data.repository.OfflinePartsRepository
import com.dyddyd.aquariumwidget.core.data.repository.OfflineQuestRepository
import com.dyddyd.aquariumwidget.core.data.repository.OfflineRodRepository
import com.dyddyd.aquariumwidget.core.data.repository.OfflineUserDataRepository
import com.dyddyd.aquariumwidget.core.data.repository.OfflineUserRepository
import com.dyddyd.aquariumwidget.core.data.repository.PartsRepository
import com.dyddyd.aquariumwidget.core.data.repository.QuestRepository
import com.dyddyd.aquariumwidget.core.data.repository.RodRepository
import com.dyddyd.aquariumwidget.core.data.repository.UserDataRepository
import com.dyddyd.aquariumwidget.core.data.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    internal abstract fun bindsAquariumRepository(
        aquariumRepository: OfflineAquariumRepository,
    ): AquariumRepository

    @Binds
    internal abstract fun bindsFishRepository(
        fishRepository: OfflineFishRepository,
    ): FishRepository

    @Binds
    internal abstract fun bindsHabitatRepository(
        habitatRepository: OfflineHabitatRepository,
    ): HabitatRepository

    @Binds
    internal abstract fun bindsPartsRepository(
        partsRepository: OfflinePartsRepository,
    ): PartsRepository

    @Binds
    internal abstract fun bindsQuestRepository(
        questRepository: OfflineQuestRepository,
    ): QuestRepository

    @Binds
    internal abstract fun bindsRodRepository(
        rodRepository: OfflineRodRepository,
    ): RodRepository

    @Binds
    internal abstract fun bindsUserRepository(
        userRepository: OfflineUserRepository,
    ): UserRepository

    @Binds
    internal abstract fun bindsUserDataRepository(
        userDataRepository: OfflineUserDataRepository,
    ): UserDataRepository
}