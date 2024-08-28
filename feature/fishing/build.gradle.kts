plugins {
    alias(libs.plugins.aquariumwidget.android.feature)
    alias(libs.plugins.aquariumwidget.android.library.compose)
}

android {
    namespace = "com.dyddyd.aquariumwidget.feature.fishing"
}

dependencies {
    implementation(projects.core.data)

    implementation(libs.androidx.navigation.compose)
}
