package com.dyddyd.aquariumwidget.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dyddyd.aquariumwidget.core.database.dao.AquariumDao
import com.dyddyd.aquariumwidget.core.database.dao.FishDao
import com.dyddyd.aquariumwidget.core.database.dao.HabitatDao
import com.dyddyd.aquariumwidget.core.database.dao.PartsDao
import com.dyddyd.aquariumwidget.core.database.dao.QuestDao
import com.dyddyd.aquariumwidget.core.database.dao.RodDao
import com.dyddyd.aquariumwidget.core.database.dao.UserDao
import com.dyddyd.aquariumwidget.core.database.model.AquariumEntity
import com.dyddyd.aquariumwidget.core.database.model.Catch
import com.dyddyd.aquariumwidget.core.database.model.Clear
import com.dyddyd.aquariumwidget.core.database.model.CollectParts
import com.dyddyd.aquariumwidget.core.database.model.CollectRod
import com.dyddyd.aquariumwidget.core.database.model.Contain
import com.dyddyd.aquariumwidget.core.database.model.FishEntity
import com.dyddyd.aquariumwidget.core.database.model.HabitatEntity
import com.dyddyd.aquariumwidget.core.database.model.Match
import com.dyddyd.aquariumwidget.core.database.model.PartsEntity
import com.dyddyd.aquariumwidget.core.database.model.QuestEntity
import com.dyddyd.aquariumwidget.core.database.model.RodEntity
import com.dyddyd.aquariumwidget.core.database.model.UserEntity

@Database(
    entities = [
        AquariumEntity::class,
        Catch::class,
        Clear::class,
        CollectParts::class,
        CollectRod::class,
        Contain::class,
        FishEntity::class,
        HabitatEntity::class,
        Match::class,
        PartsEntity::class,
        QuestEntity::class,
        RodEntity::class,
        UserEntity::class,
    ],
    version = 1
)
internal abstract class AquariumDatabase : RoomDatabase() {
    abstract fun aquariumDao(): AquariumDao
    abstract fun fishDao(): FishDao
    abstract fun habitatDao(): HabitatDao
    abstract fun partsDao(): PartsDao
    abstract fun questDao(): QuestDao
    abstract fun rodDao(): RodDao
    abstract fun userDao(): UserDao
}