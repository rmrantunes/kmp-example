plugins {
    `kotlin-dsl`
}

group = "com.jetbrains.spacetutorial.buildlogic"

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("kmpFeature") {
            id = "com.jetbrains.spacetutorial.kmp.feature"
            implementationClass = "KmpFeatureConventionPlugin"
        }
    }
}
