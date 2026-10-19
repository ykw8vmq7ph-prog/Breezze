import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("org.jetbrains.compose")
}

kotlin {
    // iOS targets
    listOf("iosSimulatorArm64", "iosArm64").forEach { targetName ->
        val target = iosTarget(targetName) {
            binaries.framework {
                baseName = "iosApp"
                isStatic = false
            }
        }
        // Configure iOS deployment target
        target.compilations.all {
            compilerOptions {
                freeCompilerArgs.add("-Xios-version-min=15.0")
            }
        }
    }

    // For local development/testing on macOS
    val isMacOS = System.getProperty("os.name").contains("Mac", ignoreCase = true)
    if (isMacOS) {
        macosArm64("macos")
    }

    sourceSets {
        val cmp = "1.10.3"
        
        commonMain.dependencies {
            api(project(":shared"))
            api(project(":sharedUi"))
            api(project(":desktopUi"))
            
            // Compose Multiplatform
            api("org.jetbrains.compose.runtime:runtime:$cmp")
            api("org.jetbrains.compose.foundation:foundation:$cmp")
            api("org.jetbrains.compose.animation:animation:$cmp")
            api("org.jetbrains.compose.ui:ui:$cmp")
            api("org.jetbrains.compose.material3:material3:1.7.3")
            api("org.jetbrains.compose.material:material-icons-extended:1.7.3")
            api("org.jetbrains.compose.components:components-resources:$cmp")
            api("org.jetbrains.androidx.lifecycle:lifecycle-runtime-compose:2.8.4")
            api("io.coil-kt.coil3:coil-compose:3.0.4")
            api("dev.chrisbanes.haze:haze:1.3.1")
            api("dev.chrisbanes.haze:haze-materials:1.3.1")
            
            // Serialization
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
        }
        
        // iOS-specific dependencies
        val iosTargets = listOf(
            kotlin.targets.getByName<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget>("iosSimulatorArm64"),
            kotlin.targets.getByName<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget>("iosArm64")
        )
        iosTargets.forEach { target ->
            val iosMain = target.compilations["main"].defaultSourceSet
            iosMain.dependsOn(commonMain)
            
            // iOS-specific dependencies (Foundation, UIKit, etc.)
            iosMain.dependencies {
                // Platform-specific dependencies if needed
            }
        }
        
        // macOS target (for local testing)
        val macosMain = kotlin.targets.getByName<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget>("macos", "").compilations["main"].defaultSourceSet
        macosMain.dependsOn(commonMain)
        macosMain.dependencies {
            // macOS-specific dependencies if needed
        }
    }
}

// Compose Multiplatform iOS plugin
compose.desktop {
    // This is needed for the iOS build to work properly
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = JvmTarget.JVM_17.toString()
    }
}