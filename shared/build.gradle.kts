import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("com.android.library")
    id("org.jetbrains.kotlin.plugin.serialization")
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    // iOS targets - only configured when on macOS with Xcode
    val xcodeDir = System.getenv("XCODE_DEVELOPER_DIR") ?: "/Applications/Xcode.app/Contents/Developer"
    val isMacOS = System.getProperty("os.name").contains("Mac", ignoreCase = true) && file(xcodeDir).isDirectory
    
    if (isMacOS) {
        listOf("iosSimulatorArm64", "iosArm64").forEach { targetName ->
            val target = iosTarget(targetName) {
                val xcodeBaseDir = file(xcodeDir)
                binaries.framework {
                    baseName = "shared"
                    isStatic = true
                }
            }
            // Configure iOS deployment target
            target.compilations.all {
                compilerOptions {
                    freeCompilerArgs.add("-Xios-version-min=15.0")
                }
            }
        }
    }

    jvm {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    // Android and desktop are both JVM, but the default hierarchy gives them no source set in
    // common.
    applyDefaultHierarchyTemplate()

    sourceSets {
        val jvmSharedMain by creating {
            dependsOn(commonMain.get())
            dependencies {
                // Compile-only: the two applications supply the runtime artifact themselves.
                compileOnly("com.microsoft.onnxruntime:onnxruntime:1.20.0")
                // The network stack the lyrics providers and the YouTube Music
                // client run on, at the phone's versions — both applications
                // now call the same code, so they resolve the same libraries.
                api("io.ktor:ktor-client-core:3.5.2")
                api("io.ktor:ktor-client-okhttp:3.5.2")
                api("io.ktor:ktor-client-websockets:3.5.2")
                api("io.ktor:ktor-client-content-negotiation:3.5.2")
                api("io.ktor:ktor-client-websockets:3.5.2")
                api("io.ktor:ktor-client-content-negotiation:3.5.2")
                api("io.ktor:ktor-serialization-kotlinx-json:3.5.2")
                api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.11.0")
                api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
                api("org.jsoup:jsoup:1.22.2")
                // YouTube stream resolution, as the phone does it: InnerTubeX's
                // client catalog and cipher tiers, NewPipe as the fallback. NewPipe
                // is compile-only because the phone ships a trimmed jar of it.
                api("com.github.MetrolistGroup.innertubex:innertubex:v0.7.0")
                compileOnly("com.github.TeamNewPipe:NewPipeExtractor:v0.26.3")
            }
        }
        androidMain.get().dependsOn(jvmSharedMain)
        jvmMain.get().dependsOn(jvmSharedMain)

        commonMain.dependencies {
            // Models in this module intentionally have no Android or Compose dependency.
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
        jvmTest.dependencies {
            implementation("com.squareup.okhttp3:mockwebserver:5.3.2")
        }
        
        // iOS-specific source sets - only configure on macOS
        if (isMacOS) {
            val iosTargets = listOf(
                kotlin.targets.getByName<KotlinNativeTarget>("iosSimulatorArm64"),
                kotlin.targets.getByName<KotlinNativeTarget>("iosArm64")
            )
            iosTargets.forEach { target ->
                val iosMain = target.compilations["main"].defaultSourceSet
                val iosTest = target.compilations["test"].defaultSourceSet
                iosMain.dependsOn(commonMain)
                iosMain.dependencies {
                    // iOS-specific dependencies (Foundation, etc.)
                }
                iosTest.dependencies {
                    implementation(kotlin("test"))
                }
            }
        }
    }
}

android {
    namespace = "com.music.breezze.shared"
    compileSdk = 36
}
