plugins {
    alias(libs.plugins.aquariumwidget.android.feature)
    alias(libs.plugins.aquariumwidget.android.library.compose)
}

android {
    namespace = "com.dyddyd.aquariumwidget.feature.collections"
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.feature.fish)

}
