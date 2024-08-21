plugins {
    alias(libs.plugins.aquariumwidget.android.feature)
    alias(libs.plugins.aquariumwidget.android.library.compose)
}

android {
    namespace = "com.dyddyd.aquariumwidget.feature.home"
}

dependencies {
    implementation(projects.feature.splash)

    implementation(projects.core.data)
}
