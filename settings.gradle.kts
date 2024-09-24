pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode = RepositoriesMode.FAIL_ON_PROJECT_REPOS
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "AquariumWidget"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")
include(":core:common")
include(":core:datastore-proto")
include(":core:model")
include(":core:datastore")
include(":core:datastore-test")
include(":core:database")
include(":core:designsystem")
include(":core:ui")
include(":core:data")
include(":feature:splash")
include(":feature:home")
include(":feature:fishing")
include(":feature:collections")
include(":feature:items")
include(":feature:help")
include(":feature:fish")
include(":feature:quest")
