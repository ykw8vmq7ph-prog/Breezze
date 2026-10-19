/*
 * The desktop's own Compose UI — the player, lyrics, queue and the Home /
 * Explore / Library / Search pages.
 *
 * It began as a copy of :sharedUi, which the phone, tablet and TV keep drawing
 * from. The two now move separately: a desktop redesign lands here and never
 * reaches the phone, and the phone's player never moves the desktop's. Packages
 * and the resource class are unchanged from :sharedUi, so the desktop's imports
 * did not move; that is safe only because no module depends on both.
 */
val cmp = "1.10.3"

/**
 * What the desktop graph resolves, and what this module is compiled against.
 *
 * Skia's Matrix33 became an inline float[9] between skiko 0.9 and 0.15
 * (`makeTranslate` went from returning a Matrix33 to a name-mangled float[]
 * overload), so bytecode compiled against 0.9 linked its object call and
 * crashed with NoSuchMethodError on a desktop running 0.15 the first time a
 * lyric line grew. Compiling against the version the desktop actually resolves
 * keeps the compile-time link and the runtime link the same.
 */
val CMP_DESKTOP =
    // Haze 1.7.x, which the Windows build uses (see desktopApp/build.gradle.kts), was compiled
    // against a ShaderBrush.createShader that returns skia's Shader; 1.12 changed that return
    // type, so on 1.12 every blur dies with NoSuchMethodError. Windows stays on the plugin's
    // own 1.10.3 / skiko 0.9; only Linux moves to 1.12.
    if (System.getProperty("os.name").contains("Windows", ignoreCase = true)) "1.10.3" else "1.12.0"

plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
    id("org.jetbrains.kotlin.plugin.serialization")
}

kotlin {
    jvm {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        }
    }

    // iOS targets - only configured when on macOS with Xcode
    val xcodeDir = System.getenv("XCODE_DEVELOPER_DIR") ?: "/Applications/Xcode.app/Contents/Developer"
    val isMacOS = System.getProperty("os.name").contains("Mac", ignoreCase = true) && file(xcodeDir).isDirectory
    
    if (isMacOS) {
        listOf("iosSimulatorArm64", "iosArm64").forEach { targetName ->
            val target = iosTarget(targetName) {
                binaries.framework {
                    baseName = "desktopUi"
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

    applyDefaultHierarchyTemplate()

    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }

    sourceSets {
        // Kept from :sharedUi so the two trees line up file for file; with one
        // target it is simply the body of jvmMain.
        val jvmSharedMain by creating {
            dependsOn(commonMain.get())
        }
        jvmMain.get().dependsOn(jvmSharedMain)

        commonMain.dependencies {
            api(project(":shared"))
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
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
        }
        // See CMP_DESKTOP above.
        jvmMain.dependencies {
            api("org.jetbrains.compose.ui:ui-desktop:$CMP_DESKTOP")
        }
        jvmTest.dependencies {
            implementation(kotlin("test"))
            implementation("junit:junit:4.13.2")
        }
        
        // iOS-specific source sets for desktop UI - only on macOS
        if (isMacOS) {
            val iosTargets = listOf(
                kotlin.targets.getByName<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget>("iosSimulatorArm64"),
                kotlin.targets.getByName<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget>("iosArm64")
            )
            iosTargets.forEach { target ->
                val iosMain = target.compilations["main"].defaultSourceSet
                val iosTest = target.compilations["test"].defaultSourceSet
                iosMain.dependsOn(commonMain)
                iosMain.dependencies {
                    // Compose iOS dependencies - same as sharedUi
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
                }
                iosTest.dependencies {
                    implementation(kotlin("test"))
                }
            }
        }
    }
}

compose.resources {
    publicResClass = true
    // The same class :sharedUi generates, so the desktop's `Res` imports did not move.
    packageOfResClass = "com.music.breezze.sharedui.resources"
    generateResClass = always
}
