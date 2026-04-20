import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    id("co.touchlab.skie") version "0.10.11"
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true

            export(project(":core:data"))
            export(project(":core:database"))
            export(project(":core:model"))
            export(project(":core:network"))
            export(project(":core:ui"))
            export(project(":feature:rocketlaunch"))

            export(libs.androidx.lifecycle.viewmodel)
        }
    }
    
    sourceSets {
        commonMain.dependencies {
            api(project(":core:data"))
            api(project(":core:database"))
            api(project(":core:model"))
            api(project(":core:network"))
            api(project(":core:ui"))
            api(project(":feature:rocketlaunch"))

            api(libs.androidx.lifecycle.viewmodel)

            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.koin.core)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        androidMain.dependencies {}
        iosMain.dependencies {}
    }
}

android {
    namespace = "com.jetbrains.spacetutorial.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
