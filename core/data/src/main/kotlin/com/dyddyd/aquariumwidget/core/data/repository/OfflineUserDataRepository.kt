package com.dyddyd.aquariumwidget.core.data.repository

import com.dyddyd.aquariumwidget.core.datastore.AquariumPreferencesDataSource
import com.dyddyd.aquariumwidget.core.model.data.DarkThemeConfig
import com.dyddyd.aquariumwidget.core.model.data.ThemeBrand
import com.dyddyd.aquariumwidget.core.model.data.UserData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class OfflineUserDataRepository @Inject constructor(
    private val aquariumPreferencesDataSource: AquariumPreferencesDataSource
): UserDataRepository {

    override val userData: Flow<UserData> = aquariumPreferencesDataSource.userData

    override suspend fun setThemeBrand(themeBrand: ThemeBrand) {
        aquariumPreferencesDataSource.setThemeBrand(themeBrand)
    }

    override suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        aquariumPreferencesDataSource.setDarkThemeConfig(darkThemeConfig)
    }
}