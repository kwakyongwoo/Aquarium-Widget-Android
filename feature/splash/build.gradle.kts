plugins {
    alias(libs.plugins.aquariumwidget.android.feature)
    alias(libs.plugins.aquariumwidget.android.library.compose)
}

android {
    namespace = "com.dyddyd.aquariumwidget.feature.splash"
}

dependencies {
    implementation(projects.core.designsystem)
}
