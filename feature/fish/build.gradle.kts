plugins {
    alias(libs.plugins.aquariumwidget.android.feature)
    alias(libs.plugins.aquariumwidget.android.library.compose)
}

android {
    namespace = "com.dyddyd.aquariumwidget.feature.fish"
}

dependencies {
    implementation(projects.feature.splash)

    implementation(projects.core.data)
}
