# Installation

KotStep is published via [JitPack](https://jitpack.io/#binayshaw7777/KotStep).

---

## 1. Configure Repository

Add the JitPack Maven repository to your `settings.gradle.kts` (or root `build.gradle.kts`):

=== "settings.gradle.kts"
    ```kotlin
    dependencyResolutionManagement {
        repositories {
            google()
            mavenCentral()
            maven("https://jitpack.io")
        }
    }
    ```

=== "root build.gradle.kts"
    ```kotlin
    allprojects {
        repositories {
            google()
            mavenCentral()
            maven("https://jitpack.io")
        }
    }
    ```

---

## 2. Add Dependencies

You can include the core stepper library `:kotstep`, the Server-Driven UI module `:kotstep-sdui`, or both.

### Compose Multiplatform (`commonMain`)

In your shared module's `build.gradle.kts`:

```kotlin
kotlin {
    sourceSets {
        commonMain.dependencies {
            // Core Stepper Library
            implementation("com.github.binayshaw7777.KotStep:kotstep:3.2.0")

            // Optional: Server-Driven UI Module
            implementation("com.github.binayshaw7777.KotStep:kotstep-sdui:3.2.0")
        }
    }
}
```

### Android (Single-Platform App)

In your `app/build.gradle.kts`:

```kotlin
dependencies {
    implementation("com.github.binayshaw7777.KotStep:kotstep:3.2.0")
    implementation("com.github.binayshaw7777.KotStep:kotstep-sdui:3.2.0")
}
```

---

## 3. Platform Targets & Requirements

| Platform | Minimum Supported | Notes |
|---|---|---|
| **Android** | API 24 (Android 7.0+) | Uses Compose compiler and Jetpack Compose runtime |
| **Desktop (JVM)** | JDK 17+ | Uses Skiko rendering engine |
| **iOS** | iOS 16+ | Native static framework linking |
| **Web (Wasm)** | Chrome 119+ / Firefox 120+ | Requires WebAssembly Garbage Collection (Wasm-GC) |

---

## 4. iOS Framework Setup

When compiling for iOS, JitPack resolves the Kotlin Multiplatform metadata and klib artifacts. If linking from source into Xcode:

```bash
./gradlew :kotstep:linkReleaseFrameworkIosSimulatorArm64 \
          :kotstep:linkReleaseFrameworkIosArm64 \
          :kotstep:linkReleaseFrameworkIosX64
```

Combine the framework bundles into an `.xcframework` using `xcodebuild`:

```bash
xcodebuild -create-xcframework \
    -framework kotstep/build/bin/iosArm64/releaseFramework/KotStep.framework \
    -framework kotstep/build/bin/iosSimulatorArm64/releaseFramework/KotStep.framework \
    -output build/KotStep.xcframework
```
