import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType
import kotlin.collections.set

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinCocoapods)
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
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        iosTarget.binaries {
            all {
                freeCompilerArgs += listOf("-linker-options", "-ios_deployment_target 26.0")
            }
            framework {
                baseName = "Koslm"
                isStatic = true
                transitiveExport = true
            }
        }
    }

    cocoapods {
        summary = "Framework of on-device ML for Kotlin Multiplatform"
        homepage = "https://github.com/qiaoyuang/MyMovie"
        name = "Koslm"
        version = "1.0"
        ios.deploymentTarget = "26.0"
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        framework {
            baseName = "Koslm"
            isStatic = true
            transitiveExport = true
        }
        xcodeConfigurationToNativeBuildType["CUSTOM_DEBUG"] = NativeBuildType.DEBUG
        xcodeConfigurationToNativeBuildType["CUSTOM_RELEASE"] = NativeBuildType.RELEASE
    }

    sourceSets {
        commonMain.dependencies {
            // Add common dependencies here if needed
        }
        androidMain.dependencies {
            implementation(libs.aiedge.aicore)
            implementation(libs.mlkit.genai.summarization)
            implementation(libs.mlkit.genai.proofeding)
            implementation(libs.mlkit.genai.rewritting)
            implementation(libs.mlkit.genai.image.description)
            implementation(libs.mlkit.genai.prompt)
        }
        iosMain.dependencies {
            // Add iOS-specific dependencies here if needed
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "com.yuangqiao.koslm"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
