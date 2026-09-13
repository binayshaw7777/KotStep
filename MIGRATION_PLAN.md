# KotStep — Jetpack Compose → Compose Multiplatform (CMP) Migration Plan

**Status:** In Progress (Phase 0)
**Last updated:** 2026-09-13
**Version:** 0.1 (planning draft)

---

## 0. Executive Summary

KotStep is currently an Android-only library (`:kotstep`) + demo app (`:app`), published to JitPack as `com.github.binayshaw7777:KotStep:3.1.0`. The goal of this migration is to make it a **Compose Multiplatform** library that runs on **Android, iOS, Desktop (JVM), and Web (Wasm/JS)**, while keeping the existing V3 DSL API surface **100% backward compatible** for Android consumers.

This is deliberately **not** a rewrite. The V3 core is already ~97% Compose-portable (verified: 15 of 16 V3 source files use only multiplatform-safe `androidx.compose.*` imports). The strategy is **gradual, screen-by-screen / module-by-module** migration where each commit leaves the build green, exactly as recommended by the JetBrains/Google migration guide.

### Why this will work smoothly

| KotStep reality | Migration implication |
|---|---|
| V3 core uses only `androidx.compose.*` (runtime/foundation/ui/animation/material3) | Fully supported by CMP — move to `commonMain` almost verbatim |
| No networking, no DB, no DI, no Java | No dependency substitutions needed (no Hilt→Koin, no Retrofit→Ktor) |
| Compose ConstraintLayout used only in V2 legacy code | Isolated risk — V2 can be Android-only during migration |
| Single Android-coupling file in V3 (`Samples.kt`) | Small, contained refactor |
| 6 instrumentation tests | Must be ported to CMP UI test API (skiko-based) |
| Library published via Maven/JitPack | Requires cross-platform publication strategy (multiple variants) |

---

## 1. Goals & Non-Goals

### Goals
1. Compile KotStep V3 for Android, iOS, Desktop (JVM), and Web targets.
2. Ship a `commonMain` source set containing the entire V3 DSL, models, styles, layout components, and progress bars.
3. Preserve the existing public API (`KotStep()`, `KotStepScope`, `step()`, styles) exactly for Android consumers — no breaking changes.
4. Publish multiplatform artifacts (`.module` metadata + platform variants) so Android consumers can keep using it, and iOS/Desktop/Web consumers can adopt it.
5. Keep the demo `:app` working on Android and add Desktop + Web + iOS entry points.
6. Port the 6 UI tests to the CMP test framework.

### Non-Goals (explicitly out of scope)
- Migrating the V2 legacy API (`StepperStyle` sealed classes, ConstraintLayout renderers) to multiplatform. Deprecation or Android-only retention only.
- Adding networking, persistence, or DI layers (the library has none).
- Rewriting the DSL architecture.
- Supporting Native/JS targets beyond the four listed (no watchOS/tvOS/linux).

---

## 2. Current State Audit (from repo exploration)

### Module layout today
```
KotStep/
├── build.gradle.kts          # root — android plugins apply false
├── settings.gradle.kts       # includes :app, :kotstep
├── gradle/libs.versions.toml # version catalog (Kotlin 2.1.10, AGP 8.3.2, Compose BOM 2025.06.01)
├── gradle/wrapper/           # Gradle 8.4
├── gradle.properties         # AndroidX flags, Xmx2048m
├── app/                      # Android demo app (application plugin)
└── kotstep/                  # Android library (library plugin), JitPack maven-publish
```

### `:kotstep` dependencies (Android-specific flagged)
| Dependency | V3 uses? | V2 uses? | Multiplatform? |
|---|---|---|---|
| `androidx.compose.ui:ui` + BOM | yes | yes | ✓ (CMP) |
| `androidx.compose.material3:material3` | yes | yes | ✓ |
| `androidx.compose.material:material-icons-extended` | yes | — | ✓ (CMP) |
| `androidx.compose.animation:animation` | yes | — | ✓ |
| `androidx.compose.ui:ui-graphics` | yes | — | ✓ |
| `androidx.compose.ui:ui-tooling-preview` | dev-only | — | Android-only (dev) |
| `androidx.constraintlayout:constraintlayout-compose` | **no** | yes | **Android-only** ⚠ |
| `androidx.core:core-ktx` | no | no | **Android-only** ⚠ |
| `androidx.lifecycle:lifecycle-runtime-ktx` | no | no | **Android-only** ⚠ |
| `androidx.activity:activity-compose` | no | no | **Android-only** ⚠ |
| `kotlinx-collections-immutable` | yes | — | ✓ |
| `com.slack.lint.compose:compose-lint-checks` | build-time | build-time | build-only |

### Source-set classification (from exploration)
- **`kotstep/src/main/java/com/binayshaw7777/kotstep/v3/`** — 16 files. **15 are multiplatform-portable** (pure `androidx.compose.*`). The 1 exception is `v3/samples/Samples.kt` which uses `Toast`, `LocalContext`, `painterResource`, `R.drawable.kotlin`.
- **`kotstep/src/main/java/com/binayshaw7777/kotstep/v2`** (`model/`, `ui/`, `components/`, `util/`) — ~22 files. Legacy, Android-coupled (`ConstraintLayout`, `LocalContext`).
- **`app/`** — 11 files, `MainActivity`, `Theme.kt` with `WindowCompat`/`dynamicColorScheme`/`LocalView`, `Utils.kt` with `Toast`.
- **Tests** — `KotStepV3Test.kt` (6 tests, `createAndroidComposeRule<ComponentActivity>`).

---

## 3. Reference Material

### Official docs
- JetBrains KMP migration guide (Jetcaster walkthrough): https://kotlinlang.org/docs/multiplatform/migrate-from-android.html
- Touchlab "Jetpack Compose → Compose Multiplatform" (2-step conversion + resources): https://touchlab.co/compose-multiplatform-transition-guide
- CMP resources docs: https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-multiplatform-resources-usage.html

### Agent skills installed for this project (all agents)
| Skill | Location | Use for |
|---|---|---|
| `kmp-compose-multiplatform` | `~/.agents/skills/kmp-compose-multiplatform` (linked from `~/.claude/skills`, `~/.codex/skills`, `~/.cursor/skills`, `./.opencode/skills`, `./.claude/skills`) | Architecture, expect/actual, Koin, source sets, version catalog, publishing |
| `compose-skill` | `~/.agents/skills/compose-skill` (linked everywhere) | Compose/CMP patterns, resources, testing, navigation, performance |
| `compose-multiplatform-patterns` | `~/.agents/skills/compose-multiplatform-patterns` (linked everywhere) | State mgmt, navigation, theming, KMP platform UI |

Every task in this plan should begin with: *"Using the `kmp-compose-multiplatform` and `compose-skill` skills..."*

---

## 4. Target Architecture

After migration:
```
KotStep/
├── build.gradle.kts
├── settings.gradle.kts          # includes :kotstep, :app, :desktopApp, iosApp
├── gradle/libs.versions.toml    # + kotlin-multiplatform & compose plugins, CMP artifacts
├── kotstep/                     # KMP library module
│   ├── build.gradle.kts
│   └── src/
│       ├── commonMain/kotlin/com/binayshaw7777/kotstep/   # V3 (moved), V2-dropped or androidMain
│       ├── commonMain/composeResources/                   # drawable/kotlin.xml etc.
│       ├── androidMain/kotlin/
│       ├── iosMain/kotlin/
│       ├── jvmMain/kotlin/
│       ├── wasmJsMain/kotlin/
│       └── commonTest/kotlin/  + androidUnitTest/ + iosTest/? etc.
├── app/                          # Android demo (kept, depends on kotstep)
├── desktopApp/                   # NEW desktop entry (main.kt)
├── iosApp/                       # NEW iOS Xcode project (ComposeUIViewController)
└── webApp/                       # NEW wasmJs entry (Html could be in desktopApp? separate module)
```

**Key decision (recommended):** Keep `:app` as a pure Android application module consuming `:kotstep` (same as today's consumers do), and create **separate entry-point modules** for Desktop/Web/iOS. This mirrors the Jetcaster guide (`mobile` stays Android, shared UI lives in common).

---

## 5. Version & Tooling Baseline (verify at task start)

Recommended target versions (verify latest on Maven Central before pinning):
- Gradle: 8.10+ (KMP + AGP 8.7 compat); Kotlin: 2.1.x (≥2.1.10, keep in lockstep with compose compiler)
- AGP: 8.7.x (KMP androidTarget compatible)
- Compose Multiplatform plugin: 1.7.x/1.8.x (must match Kotlin 2.1.10)
- `org.jetbrains.kotlin.plugin.compose` (org.jetbrains.compose compiler plugin) — same version as Kotlin
- JDK: 17 for toolchain (JitPack already uses OpenJDK 17)

> The Compose Multiplatform Gradle plugin version and Kotlin version must be compatible. Check the [CMP releases](https://github.com/JetBrains/compose-multiplatform/releases) for the pairing table. This is the #1 source of migration build failures.

---

## 6. High-Level Phase Map

```
PHASE 0  Audit & freeze        (no code change; done — this doc)
PHASE 1  Toolchain upgrade     (Gradle/Kotlin/AGP/CMP plugin, still Android-only)   ✅ gates everything
PHASE 2  Android-only "-->" module becomes KMP           (kotlin("multiplatform"), androidTarget + commonMain split)
PHASE 3  Decouple Android-only code                          (Samples.kt, resources, V2 handling)
PHASE 4  Add Desktop target + desktopApp entry          (first new platform — fastest feedback)
PHASE 5  Add Web (Wasm/JS) target + webApp entry
PHASE 6  Add iOS targets + iosApp Xcode entry
PHASE 7  Port tests to CMP commonTest
PHASE 8  Multiplatform publishing (Maven/JitPack) + CI matrix
PHASE 9  Docs, README, samples, demo parity, release
PHASE 10 Post-migration hardening                       (V2 deprecation, perf, a11y parity)
```

Each phase is further divided into **tasks (T-XXX)**. Each task is sized so an AI agent can complete it and the build stays green.

---
---

# PHASE 1 — Toolchain Upgrade (Android-only, still compiles)

**Gate:** Everything after this depends on the toolchain. Do this first, verify Android build still works, commit.

### T-1.1 — Upgrade Gradle Wrapper
**Agent:** `general` (build system)
`gradle/wrapper/gradle-wrapper.properties` → `gradle-8.10-bin.zip` (or latest 8.x that supports AGP 8.7 + Kotlin 2.1).
- Run `./gradlew wrapper --gradle-version 8.10`
- Verify: `./gradlew --version`

### T-1.2 — Version catalog: add CMP + KMP plugins
**Agent:** `general`
Update `gradle/libs.versions.toml`:
- Add `[versions]`: `kotlin = "2.1.10"` (keep), `compose-multiplatform = "<latest compatible>"`, `agp = "8.7.x"`.
- Add `[plugins]`: `kotlin-multiplatform = { id = "org.jetbrains.kotlin.multiplatform", version.ref = "kotlin" }`, `compose-multiplatform = { id = "org.jetbrains.compose", version.ref = "compose-multiplatform" }`, `kotlin-compose-compiler = { id = "org.jetbrains.kotlin.plugin.compose", version.ref = "kotlin" }`.
- Keep existing Compose BOM for the `:app` module (still Android for now).

### T-1.3 — Root `build.gradle.kts`: declare new plugins `apply false`
**Agent:** `general`
Add `alias(libs.plugins.kotlin.multiplatform) apply false`, `alias(libs.plugins.compose.multiplatform) apply false`, `alias(libs.plugins.kotlin.compose.compiler) apply false`.

### T-1.4 — `gradle.properties` tuning
**Agent:** `general`
- Add `org.gradle.parallel=true`, `org.gradle.caching=true`, `kotlin.mpp.enableCInteropCommonization=true` (if needed later), `org.jetbrains.compose.experimental.uikit.enabled=true` only when iOS is added.

### T-1.5 — Verify green build
**Agent:** `reviewer`
`./gradlew :kotstep:assembleDebug :app:assembleDebug` — must pass unchanged.

**Exit criteria Phase 1:** Android builds with new toolchain; no code changes other than build files.
**Commit:** `chore: upgrade toolchain for Compose Multiplatform migration`

---
---

# PHASE 2 — Convert `:kotstep` to a KMP module (Android-only KMP)

**Gate:** The library module becomes `kotlin("multiplatform")` with an `androidTarget()`. Nothing else changes behavior. This is Step 1 of the Touchlab recipe.

### T-2.1 — Move Android source sets to KMP layout
**Agent:** `builder`
- Create `kotstep/src/commonMain/`, `kotstep/src/androidMain/`, `kotstep/src/androidUnitTest/`, `kotstep/src/androidInstrumentedTest/`.
- Move physical files: currently `src/main/java/...` and `src/androidTest/...`.
  - `v3/**` (all 16 files) → `commonMain/kotlin/` (temp home; they're "portable", confirm in Phase 3).
  - `model/`, `ui/`, `components/`, `util/` (V2) → `androidMain/kotlin/` (Android-only for now).
  - `androidTest/.../KotStepV3Test.kt` → `androidInstrumentedTest/`.
- Keep `src/main/AndroidManifest.xml` and `res/` paths in `androidMain/` won't work — see T-3.2. For Phase 2, keep manifest/res in Android source set configuration.

### T-2.2 — Rewrite `kotstep/build.gradle.kts` as KMP library
**Agent:** `builder`
```kotlin
plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose.compiler)
    id("maven-publish")
}

kotlin {
    androidTarget {
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions { jvmTarget.set(JvmTarget.JVM_1_8) }
            }
        }
    }
    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.ui)
            implementation(compose.animation)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)   // verify name in plugin version
            implementation(libs.kotlinx.collections.immutable)
        }
        androidMain.dependencies {
            // keep core-ktx, lifecycle, activity only if V2 actually needs them
        }
        androidInstrumentedTest.dependencies {
            implementation(compose.uiTest)
            implementation(libs.androidx.test.ext.junit)
            implementation(libs.androidx.test.espresso.core)
        }
    }
}

android {
    namespace = "com.binayshaw7777.kotstep"
    compileSdk = 35
    defaultConfig { minSdk = 24; testInstrumentationRunner = ... }
    publishLibraryVariants("release", "debug")   // KMP publishing
}
```
**CAUTION:** With KMP, Android resources/manifest move under `src/androidMain/res` + a manifest must be merged properly. Verify with the skill's `build-system` reference.

### T-2.3 — Move the Android dependencies that break
**Agent:** `builder`
Remove from commonMain any of `core-ktx`, `lifecycle-runtime-ktx`, `activity-compose`, `constraintlayout-compose` that V2 needs — these move to `androidMain` dependencies (NOT commonMain).

### T-2.4 — Fix `R`/`Res` resource references
**Agent:** `builder`
- Move `kotstep/src/main/res/drawable/kotlin.xml` → `kotstep/src/commonMain/composeResources/drawable/kotlin.xml`.
- For now, in `Samples.kt` and V2, either keep Android `R` working via androidMain or do the resource sweep now (do it now — cheaper). This is where `de.undercouch`? No — CMP uses `composeResources` + generated `Res` class (compose components resources plugin incl. in `compose.components.resources`).

### T-2.5 — Compile Android
**Agent:** `builder` + `reviewer`
`./gradlew :kotstep:compileDebugKotlinAndroid :kotstep:assembleDebug`
Fix any `jvmTarget` mismatch errors (Touchlab gotcha). Commit.

### T-2.6 — Keep V2 compiling in androidMain
**Agent:** `builder`
If V2 (ConstraintLayout code) fails, ensure `androidx.constraintlayout:constraintlayout-compose` is declared in `androidMain.dependencies` only.

**Exit criteria Phase 2:** `:kotstep` is a KMP module with `androidTarget`, compiles for Android, `:app` still works unsigned of changes (V2/V3 both exported).

**Commit:** `refactor: convert kotstep module to Kotlin Multiplatform (androidTarget)`

---
---

# PHASE 3 — Decouple Android-only code from the shared core

**Gate:** `commonMain` must contain ZERO Android imports except the CMP-equivalent of what's shared. This is the biggest code-churn phase.

### T-3.1 — Purge `Samples.kt` from the library
**Agent:** `builder` (small, surgical)
- The library currently ships `v3/samples/Samples.kt` with `Toast`, `LocalContext`, `painterResource`, `R.drawable.kotlin`.
- **Move samples out of `:kotstep`** into `:app` (and later the demo entrypoints). A published library should not ship demo samples.
- Replace the 3 Android usages:
  - `Toast.makeText(...)` → replace with a public `onStepClick`/content slot test or no-op in samples moved to app. In library, delete.
  - `LocalContext.current` → delete (only used for Toast).
  - `painterResource(R.drawable.kotlin)` / `vectorResource(R.drawable.kotlin)` → use Compose `ImageVector` from `Icons` for the preview/samples, or make samples non-library.
- Alternative: keep samples as `internal` in `commonMain` using only `ImageVector.vectorResource`? No — `vectorResource` on Android needs `LocalContext`. Use `Res.drawable.kotlin` via CMP resources.

### T-3.2 — Resource migration (R → Res)
**Agent:** `builder`
Per Touchlab bonus + CMP resources docs:
- Directory: `kotstep/src/commonMain/composeResources/`
- Move `drawable/kotlin.xml` → `composeResources/drawable/kotlin.xml`.
- Regenerate Res class: `./gradlew :kotstep:generateComposeResClass`.
- Update imports `com.binayshaw7777.kotstep.R` → `kotstep.composeapp.generated.resources.Res` (generated package; verify) and `painterResource(Res.drawable.kotlin)`.
- Note: CMP resources support = strings, drawable/vector, fonts, files. Unsupported = colors, ints, dims, plurals.

### T-3.3 — V3 verification sweep (every V3 file in commonMain)
**Agent:** `reviewer`
Grep commonMain for forbidden imports:
```
android\.  (anything)
R\.       (resource access)
LocalContext
painterResource / vectorResource (except via Res)
Toast
java\.    (java.time etc.)
```
Use the skill's common pitfalls list. Fix any stragglers.

### T-3.4 — Preview composables
**Agent:** `builder`
`v3/component/Preview.kt` uses `androidx.compose.ui.tooling.preview.Preview` (Android-only). Options: keep in `androidMain` (an `@Preview` wrapper) OR use `@Preview` from CMP `org.jetbrains.compose.ui.tooling.preview`? Currently CMP preview support exists for commonMain via the compose plugin. Verify; if unstable, keep previews in an `androidMain` file so they compile Android-only. Only affects IDE experience, not runtime.

### T-3.5 — V2 legacy decision
**Agent:** `general` (decision record)
PROPOSED: keep V2 code in `androidMain` so it stays exported to Android consumers unchanged, but NOT part of commonMain (saves ConstraintLayout porting). Confirm with maintainer in Phase 10 whether V2 is deprecated or eventually dropped. Write a `docs/decisions/0001-v2-legacy-strategy.md`.

### T-3.6 — Unit-test-ability refactor for common models
**Agent:** `builder`
The `StepState` derivation + `StaticStepProperties.calculateStaticStepProperties()` + `Util` helpers are pure logic — confirm they have **no composable dependency** so they can be unit-tested in `commonTest` (Phase 7). Extract any composable-free helpers now.

**Exit criteria Phase 3:** `commonMain` has zero Android imports; V3 core is verbatim-portable; V2 locked in androidMain; `:app` + tests still green on Android.

**Commit:** `refactor: decouple V3 core from Android APIs; migrate resources to composeResources`

---
---

# PHASE 4 — Desktop (JVM) target + desktopApp

**Gate:** First new platform. JVM Desktop is the cheapest to stand up and the best dev loop. All V3 core should "just work".

### T-4.1 — Add `jvm("desktop")` target
**Agent:** `builder`
In `kotlin { }` block:
```kotlin
listOf(jvm("desktop")).forEach { target ->
    target.compilations.all { compileTaskProvider.configure { compilerOptions { jvmTarget.set(JvmTarget.JVM_17) } } }
}
```
Keep androidTarget at JVM 1.8 for compatibility; desktop at 17 (or keep both 1.8 to be safe; verify).

### T-4.2 — New module `desktopApp`
**Agent:** `builder`
- `desktopApp/build.gradle.kts` with `kotlin("multiplatform")` + `compose` plugin, jvm desktop target, `application { mainClass = "..." }`, `dependencies { implementation(compose.desktop.currentOs); implementation(project(":kotstep")) }`.
- `desktopApp/src/jvmMain/kotlin/.../main.kt`:
```kotlin
fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "KotStep Demo") {
        App()   // shared demo content (Phase 4.3)
    }
}
```
- Add `include(":desktopApp")` to `settings.gradle.kts`.

### T-4.3 — Extract shared demo UI into commonMain
**Agent:** `builder`
Move the KotStep demo screens (from `:app`'s `ui/theme/presentation/v2/steppers/Groww.kt`, `KotStepPreview.kt`) into a shared demo module `commonMain` (either inside `desktopApp`/`webApp`/`app` each, or a `demo-shared` module → simpler: put in `:app`'s commonMain? No — create `demoCommon` source set or a `:demo` KMP module). PROPOSED: create small `:demo` KMP module with `commonMain` containing `DemoApp()` + theme, consumed by `app`, `desktopApp`, `webApp`, `iosApp`. This is the "shared UI module" from Jetcaster.

### T-4.4 — Desktop theme (M3)
**Agent:** `builder`
`darkColorScheme()`/`lightColorScheme()` work cross-platform. Replace `dynamicColor`/`WindowCompat`/`LocalView` blocks in the shared theme with a guarded version:
```kotlin
@Composable fun AppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) { ... }
```
Dynamic color is Android-only → keep those calls in `:app`'s theme (androidMain), use plain M3 in shared/demo theme.

### T-4.5 — Verify
**Agent:** `builder` + `reviewer`
`./gradlew :desktopApp:run` (or `:desktopApp:jvmRun`) — window opens with KotStep samples.
**Commit:** `feat: add desktop target and demo entry point`

---
---

# PHASE 5 — Web (Wasm/JS) target + webApp

### T-5.1 — Add wasmJs target
**Agent:** `builder`
```kotlin
wasmJs {
    browser { commonWebpackConfig { ... } }  // or wasmNodeJs for testing
    binaries.executable()
}
```
Add `implementation(libs.kotlinx.coroutines.core)` already there; CMP 1.7+ supports WASM. Note: `materialIconsExtended` has WASM support? verify — if not, swap to `material-icons-core` for web.

### T-5.2 — New webApp module
**Agent:** `builder`
`webApp/build.gradle.kts` with wasmJs target + `compose.html` or `compose.wasm` plugin deps; `main.kt`:
```kotlin
@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    CanvasBasedWindow("KotStep Demo") { DemoApp() }
}
```
`include(":webApp")`. `wasm-dce` gradle plugin for tree-shaking (proguard equivalent).

### T-5.3 — Verify
**Agent:** `builder`
`./gradlew :webApp:wasmJsRun` — opens browser page with stepper. Fix any wasm-unsupported deps (`material-icons-extended` is the likely casualty; consider conditional source set dep or switch library to core icons).

**Commit:** `feat: add wasmJs/web target and browser demo entry`

---
---

# PHASE 6 — iOS targets + iosApp

### T-6.1 — Add iOS targets to `:kotstep`
**Agent:** `builder`
```kotlin
listOf(iosX64(), iosArm64(), iosSimulatorArm64()).forEach { iosTarget ->
    iosTarget.binaries.framework { baseName = "KotStep"; isStatic = true }
}
// Android-only deps must not leak into iosMain — verify commonMain is clean (Phase 3 gate)
```

### T-6.2 — `iosMain` expect/actual (if any)
**Agent:** `builder`
V3 core shouldn't need any. If resources `Res.drawable` is fine. Create empty `iosMain` placeholders only if the build requires.

### T-6.3 — Create `iosApp` Xcode project
**Agent:** `general` (can scaffold, but Xcode project generation is best done by the Kotlin Multiplatform wizard — alternatively use `kdoctor` + manual Xcode setup)
- `iosApp/iosApp/iOSApp.swift`, `ContentView.swift`, `Info.plist`.
- `ContentView.swift`: `UIViewControllerRepresentable` wrapping `MainViewControllerKt.MainViewController()`.
- `iosApp/Configuration/Config.xcconfig` with `TEAM_ID`, `BUNDLE_ID`, and a build phase script that runs `./gradlew :kotstep:embedAndSignAppleFrameworkForXcode`.

### T-6.4 — iOS main controller
**Agent:** `builder`
`kotstep` or a thin `iosApp` layer: `iosMain/.../MainViewController.kt`:
```kotlin
fun MainViewController(): UIViewController = ComposeUIViewController { DemoApp() }
```

### T-6.5 — Verify
**Agent:** `builder` (requires Mac + Xcode)
`./gradlew :kotstep:linkDebugFrameworkIosSimulatorArm64` then run from Xcode. If no Xcode available, at least link the framework.

**Commit:** `feat: add iOS targets and Xcode app entry`

---
---

# PHASE 7 — Port tests to commonTest (CMP test framework)

**Gate:** All 6 existing instrumentation tests must keep passing on Android AND be runnable on at least desktop via the CMP UI test API.

### T-7.1 — Convert test infra
**Agent:** `builder`
- Move `KotStepV3Test.kt` → `commonTest/kotlin/`.
- Replace `createAndroidComposeRule<ComponentActivity>()` (Android-only) with CMP API:
  ```kotlin
  @get:Rule val rule = createComposeRule()   // from org.jetbrains.compose.ui:ui-test-junit4 (desktop/jvm)
  ```
  On Android device tests, keep the android variant with `createAndroidComposeRule` gated to `androidInstrumentedTest`. Use `expect/actual` test rule or duplicate test classes per platform (Jetcaster approach: shared `commonTest` + per-platform).
- `getUnclippedBoundsInRoot()` — verify parity in CMP; may need `onNodeWithTag(...).getBoundsInRoot()`.

### T-7.2 — Add unit tests for pure logic
**Agent:** `builder`
`commonTest` for `Util`, `StepState` derivation, `StaticStepProperties.calculateStaticStepProperties()`, `AnimationConstants`. Use `kotlin.test`.

### T-7.3 — Multi-platform test run script
**Agent:** `general`
Document + wire: `./gradlew :kotstep:desktopTest :kotstep:androidUnitTest :kotstep:allTests` etc. Add to CI in Phase 8.

**Commit:** `test: port UI tests to Compose Multiplatform commonTest`

---
---

# PHASE 8 — Multiplatform publishing + CI matrix

### T-8.1 — Publish variants for Kotlin Multiplatform
**Agent:** `builder`
- Remove the Android-only `publishing { publications { MavenPublication } }` block.
- Use:
  ```kotlin
  android { publishLibraryVariants("release") }
  kotlin { explicitApi() /* optional */ }
  ```
  plus `withXml` POM customization (preserve existing groupId `com.github.binayshaw7777`, artifactId `KotStep`, version `3.2.0`, name/description/licenses/developers/scm from current pom) so the Android JitPack consumers keep working.
- `./gradlew :kotstep:publishToMavenLocal` → verify `.module` metadata + `KotStep-android`, `-desktop`, `-iosarm64`, `-wasmjs`, `-sources` artifacts.

### T-8.2 — JitPack config
**Agent:** `general`
`jitpack.yml` needs JDK 17 (already) + possibly `./gradlew publishToMavenLocal`. Test by tagging a local commit. Note: JitPack supports MPP but wasm/iOS artifacts need `github` releases for binary targets (XCFramework) — document as known limitation; Android/JVM/Metadata still work via JitPack.

### T-8.3 — CI matrix (GitHub Actions)
**Agent:** `general`
`.github/workflows/cmp-ci.yml`:
- job: android (`gradle build`, `connectedAndroidTest` via emulator or Robolectric alternative)
- job: desktop (`./gradlew :desktopApp:jvmTest` + screenshot smoke)
- job: wasm (`:webApp:wasmJsBrowserTest`)
- job: ios (needs macOS runner: `./gradlew :kotstep:linkDebugFrameworkIosSimulatorArm64` + `xcodebuild` for iosApp, `kdoctor` check)
- Keep `lint.yml`, `analysis.yml`.

**Commit:** `feat: multiplatform publishing and CI matrix`

---
---

# PHASE 9 — Docs, sample parity, release

### T-9.1 — Update README
**Agent:** `general`
- "Platforms supported" badges (Android/iOS/Desktop/Web). How to add dependency per platform (Maven coordinates, SPM/XCFramework for iOS, wasm for web).
- Migration notes for existing Android users (artifact coordinate unchanged).

### T-9.2 — Demo parity between :app, desktopApp, webApp, iosApp
**Agent:** `builder`
Ensure every sample in `KotStepVerticalExample` / `KotStepHorizontalExample` renders identically on all 4. Fix platform quirks (icons, fonts, text scaling).

### T-9.3 — Version bump + release notes
**Agent:** `general`
`3.2.0` (minor: new platforms, no breaking API change). Tag. Update `.github/skills/kotstep-v3-plan/`, `LEADING_TRAILING_LABEL_ROADMAP.md` if relevant.

**Commit:** `docs: multiplatform README + release 3.2.0`

---
---

# PHASE 10 — Post-migration hardening (follow-ups)

### T-10.1 — V2 deprecation policy
**Agent:** `general` (decision)
Decide & document (deprecate annotations, keep in androidMain, or drop in v4). Reads/updates `docs/decisions/0001`.

### T-10.2 — Perf & a11y parity sweep
**Agent:** `reviewer`
Run `compose-performance-audit` + `android-accessibility` skills cross-platform. Check: 48dp touch targets, contentDescription on indicators (already good per tests), RTL paddings (start/end not left/right).

### T-10.3 — Golden/screenshot tests (optional)
**Agent:** `builder`
Per skill: Paparazzi (Android) or Compose desktop screenshot tests for indicators across platforms.

### T-10.4 — Remove dead Android-only deps
**Agent:** `builder`
Confirm `core-ktx`, `lifecycle-runtime-ktx`, `activity-compose` are removable from `:kotstep` entirely (test the hypothesis).

---

## 7. Risk Register

| # | Risk | Impact | Mitigation | Phase |
|---|---|---|---|---|
| R1 | Compose Multiplatform ↔ Kotlin version incompatibility | Build fails everywhere | Pin known-good pair (T-1.2); consult CMP release notes | 1 |
| R2 | `material-icons-extended` lacks WASM/iOS artifacts | Web build fails | Swap to `material-icons-core` or subset icons in commonMain | 5 |
| R3 | `materialIconsExtended` accessor name differs across plugin versions | Compile error | Verify accessor names in resolved plugin (skill's build-system ref) | 2 |
| R4 | ConstraintLayout (V2) not portable | V2 breaks | V2 stays in `androidMain`; not part of commonMain | 3 |
| R5 | `Samples.kt` in library leaks Android types into public API/artifacts | Consumers on iOS/Web can't compile | Remove samples from library; move to demos | 3 |
| R6 | Resource `R` → `Res` migration surprises (generated package name) | Import errors | Follow CMP resources docs exactly; regen `generateComposeResClass` | 3 |
| R7 | iOS build can't run locally (no Xcode) | iOS blocked | Do framework-link only; CI on macOS runner | 6 |
| R8 | JitPack can't host binary iOS/web artifacts | Publishing incomplete | .module/JVM/Android via JitPack; XCFramework via GH Releases/SPM | 8 |
| R9 | Test API parity (`getUnclippedBoundsInRoot`, `waitForIdle`) | Tests flaky/don't compile | Gate Android-specific asserts to android tests; simpler shared asserts | 7 |
| R10 | JVM target mismatch android(1.8) vs desktop(17) | Compile error | Set per-target compilerOptions; keep parity where possible | 4 |
| R11 | `@Preview` tooling is Android-only | IDE preview loses samples | Keep androidMain preview wrappers; document | 3 |
| R12 | Demo app modules drift from shared `DemoApp` | 4 entrypoints diverge | Extract `DemoApp()` into a shared demo module (T-4.3) | 4–6 |

---

## 8. Agent Orchestration Model

This plan is **agent-executable end-to-end**. Recommended role mapping:

| Role | Agent type | Responsibility |
|---|---|---|
| Navigator | main thread (you) | Run phases in order; gate by exit criteria; keep plan doc updated |
| Builder | `general`/`explore` subagents | Implement each T-XXX (write code, move files, edit build files) |
| Reviewer | subagent or `caveman-review`/`caveman` skills | Post-task diff review against gate criteria + lint |
| Auditor | single run per phase | Grep for Android imports in commonMain; verify no `android.*` |

**Per-task prompt template for agents:**
```
Using the `kmp-compose-multiplatform`, `compose-skill`, and `compose-multiplatform-patterns`
skills, complete task T-<N>.<M> from MIGRATION_PLAN.md. Do NOT touch anything outside
the task scope. After implementation: (1) run <verification command>, (2) confirm exit
criteria, (3) report diff summary with file:line references. Never commit unless asked.
```

**Non-negotiable rules for every agent working on this migration:**
1. One task = one working-state commit. Never batch phases.
2. `commonMain` must never import from `android.*`, `java.*` (unless multiplatform-verified), or use `R.`.
3. Android consumers must remain on the same artifact coordinate until Phase 8 lands.
4. If a task can't be verified running (no emulator/Xcode), verify via compile + unit tests and note it.

---

## 9. Definition of Done (project-level)

- [ ] `./gradlew :kotstep:allTargetsCompile` succeeds (android+desktop+iosX3+wasm)
- [ ] `:app` (Android) runs the same Samples as before migration — pixel-consistent
- [ ] `desktopApp`, `webApp`, `iosApp` each render identical stepper samples
- [ ] All 6 original UI tests pass in `commonTest` (desktop + Android)
- [ ] Library publishes `.module` + per-platform artifacts; Android consumers can upgrade without code changes
- [ ] README documents per-platform installation
- [ ] CI green across android/desktop/wasm/ios jobs
- [ ] V2 legacy: decision recorded, deprecation path in docs

---

*Companion docs: `MIGRATION_CHECKLIST.md` (executable checklist), `MIGRATION_AGENT_BRIEFS.md` (per-phase agent briefs), risk register above.*