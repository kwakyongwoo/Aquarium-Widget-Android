plugins {
    alias(libs.plugins.aquariumwidget.android.library)
    alias(libs.plugins.aquariumwidget.android.library.compose)
    alias(libs.plugins.roborazzi)
}

android {
    namespace = "com.dyddyd.aquariumwidget.core.designsystem"
}

dependencies {
    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.foundation.layout)
    api(libs.androidx.compose.material.iconsExtended)
    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.material3.adaptive)
    api(libs.androidx.compose.material3.navigationSuite)
    api(libs.androidx.compose.runtime)
    api(libs.androidx.compose.ui.util)

    implementation(libs.coil.kt.compose)

    implementation(libs.androidx.activity.compose)
}