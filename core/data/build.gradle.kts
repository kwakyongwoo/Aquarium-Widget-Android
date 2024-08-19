plugins {
    alias(libs.plugins.aquariumwidget.android.library)
    alias(libs.plugins.aquariumwidget.android.library.jacoco)
    alias(libs.plugins.aquariumwidget.hilt)
    id("kotlinx-serialization")
}

android {
    namespace = "com.dyddyd.aquariumwidget.core.data"
}

dependencies {
    api(projects.core.common)
    api(projects.core.database)
    api(projects.core.datastore)

    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.kotlinx.serialization.json)
    testImplementation(projects.core.datastoreTest)
}