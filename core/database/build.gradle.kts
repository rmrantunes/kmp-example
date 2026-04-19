plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.sqldelight)
}

kotlin {
    androidTarget()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        all {}

        commonMain.dependencies {
            implementation(project(":core:model"))
            implementation(libs.runtime)
            implementation("app.cash.sqldelight:coroutines-extensions:2.3.2")
        }

        commonTest.dependencies {}

        androidMain.dependencies {
            implementation(libs.android.driver)
        }

        iosMain.dependencies {
            implementation(libs.native.driver)

        }
    }
}

android {
    namespace = "com.jetbrains.spacetutorial.core.database"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
}

sqldelight {
    databases {
        create("AppDatabase") {
            packageName.set("com.jetbrains.spacetutorial.core.database")
        }
    }
}