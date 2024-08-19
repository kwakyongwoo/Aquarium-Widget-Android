plugins {
    alias(libs.plugins.aquariumwidget.jvm.library)
    alias(libs.plugins.aquariumwidget.hilt)
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)
}