plugins {
    alias(libs.plugins.aquariumwidget.android.feature)
    alias(libs.plugins.aquariumwidget.android.library.compose)
}

android {
    namespace = "com.dyddyd.aquariumwidget.feature.quest"
}

dependencies {
    implementation(projects.core.data)
}
