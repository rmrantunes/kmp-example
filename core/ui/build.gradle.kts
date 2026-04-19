plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
}

kotlin {
    androidTarget()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        all {}

        commonMain.dependencies {
            implementation(project(":core:model"))

            implementation(libs.kotlinx.coroutines.core)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        androidMain.dependencies {}

        iosMain.dependencies {}
    }
}

android {
    namespace = "com.jetbrains.spacetutorial.core.ui"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
}