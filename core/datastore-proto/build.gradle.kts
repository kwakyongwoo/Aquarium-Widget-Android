plugins {
    alias(libs.plugins.aquariumwidget.android.library)
    alias(libs.plugins.protobuf)
}

android {
    namespace = "com.dyddyd.aquariumwidget.core.datastore.proto"
}

protobuf {
    protoc {
        artifact = libs.protobuf.protoc.get().toString()
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                register("java") {
                    option("lite")
                }
                register("kotlin") {
                    option("lite")
                }
            }
        }
    }
}

androidComponents.beforeVariants { variant ->
    val variantName = variant.name
    val buildDir = layout.buildDirectory.get().asFile
    val javaSrcDir = buildDir.resolve("generated/source/proto/${variantName}/java")
    val kotlinSrcDir = buildDir.resolve("generated/source/proto/${variantName}/kotlin")

    android.sourceSets.getByName(variantName).run {
        java.srcDir(javaSrcDir)
        kotlin.srcDir(kotlinSrcDir)
    }
}

dependencies {
    api(libs.protobuf.kotlin.lite)
}