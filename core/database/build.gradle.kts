plugins {
    alias(libs.plugins.aquariumwidget.android.library)
    alias(libs.plugins.aquariumwidget.android.library.jacoco)
    alias(libs.plugins.aquariumwidget.android.room)
    alias(libs.plugins.aquariumwidget.hilt)
}

android {
    namespace = "com.dyddyd.aquariumwidget.core.database"
}

dependencies {
    api(projects.core.model)

    androidTestImplementation(libs.kotlinx.coroutines.test)
    implementation(libs.androidx.test.rules)
}