plugins {
    alias(libs.plugins.aquariumwidget.android.feature)
    alias(libs.plugins.aquariumwidget.android.library.compose)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.dyddyd.aquariumwidget.feature.widget"
}

dependencies {
    implementation(projects.core.data)

    implementation(libs.glance.appwidget)
    implementation(libs.glance.appwidget.preview)
    implementation(libs.glance.material3)
    implementation(libs.glance.preview)

    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.hilt.ext.work)
}
