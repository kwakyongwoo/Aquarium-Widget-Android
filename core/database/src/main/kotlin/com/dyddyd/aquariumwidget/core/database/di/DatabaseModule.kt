package com.dyddyd.aquariumwidget.core.database.di

import android.content.Context
import androidx.room.Room
import com.dyddyd.aquariumwidget.core.database.AquariumDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {
    @Provides
    @Singleton
    fun providesAquariumDatabase(
        @ApplicationContext context: Context,
    ): AquariumDatabase = Room.databaseBuilder(
        context,
        AquariumDatabase::class.java,
        "aquarium-database",
    ).createFromAsset("DB.sqlite3").build()
}