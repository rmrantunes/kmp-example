import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KmpFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("org.jetbrains.kotlin.multiplatform")
            pluginManager.apply("com.android.library")
            pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            extensions.configure<KotlinMultiplatformExtension> {
                androidTarget()
                iosArm64()
                iosSimulatorArm64()

                sourceSets.commonMain.dependencies {
                    implementation(project(":core:model"))
                    implementation(project(":core:data"))
                    implementation(project(":core:ui"))
                    implementation(libs.findLibrary("kotlinx-coroutines-core").get())
                    api(libs.findLibrary("androidx-lifecycle-viewmodel").get())
                    implementation(libs.findLibrary("compose-runtime").get())
                    implementation(libs.findLibrary("compose-foundation").get())
                    implementation(libs.findLibrary("compose-ui").get())
                    implementation(libs.findLibrary("compose-components-resources").get())
                    implementation(libs.findLibrary("compose-uiToolingPreview").get())
                }

                sourceSets.commonTest.dependencies {
                    implementation(libs.findLibrary("kotlin-test").get())
                }

                sourceSets.androidMain.dependencies {
                    implementation(libs.findLibrary("ktor-client-android").get())
                    implementation(libs.findLibrary("androidx-activity-compose").get())
                    implementation(libs.findLibrary("androidx-compose-material3").get())
                    implementation(libs.findLibrary("koin-androidx-compose").get())
                }

                sourceSets.iosMain.dependencies {
                    implementation(libs.findLibrary("ktor-client-darwin").get())
                }
            }
        }
    }
}
