plugins {
    alias(libs.plugins.aquariumwidget.android.library)
    alias(libs.plugins.aquariumwidget.hilt)
}

android {
    namespace = "com.dyddyd.aquariumwidget.core.datastore.test"
}

dependencies {
    implementation(libs.hilt.android.testing)
    implementation(projects.core.common)
    implementation(projects.core.datastore)
}