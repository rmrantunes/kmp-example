plugins {
    alias(libs.plugins.kmpFeature)
}

android {
    namespace = "com.jetbrains.spacetutorial.feature.rocketlaunch"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
}
