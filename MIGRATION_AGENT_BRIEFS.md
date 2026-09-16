# KotStep → Compose Multiplatform — Agent Orchestration Briefs

This file contains paste-ready briefs an AI orchestrator (main agent) can hand to sub-agents per phase. The model is: **Navigator (main thread) → Builder + Reviewer (sub-agents) → QA gate (re-run build/tests) → commit**.

## Global Rules Passed To Every Agent

1. Invoke skills first: *"Using the `kmp-compose-multiplatform`, `compose-skill`, and `compose-multiplatform-patterns` skills…"*.
2. Never import `android.*`, `R.`, `LocalContext`, `Toast`, `java.time`, or platform types in `commonMain`.
3. One task = one green commit. Do not mix phases.
4. Never commit unless explicitly asked by the main thread.
5. If verification can't run locally (iOS/Xcode), verify by compiling the framework and say so.

---

## Phase 1 Brief — Toolchain (BUILD, no code)

**Builder prompt:**
> Using the `kmp-compose-multiplatform` skill's `build-system` guidance, upgrade the Android-only KotStep build to a CMP-ready toolchain **without changing any source code**:
> 1. Bump Gradle wrapper to 8.10+.
> 2. In `libs.versions.toml` add `kotlin-multiplatform`, `org.jetbrains.compose`, and `org.jetbrains.kotlin.plugin.compose` plugins (pin a Kotlin 2.1.x ↔ Compose MP version pair verified on Maven Central / the CMP release notes).
> 3. Root `build.gradle.kts`: add the three plugins with `apply false`.
> 4. `gradle.properties`: enable `org.gradle.parallel`, `org.gradle.caching`.
> 5. Run `./gradlew :kotstep:assembleDebug :app:assembleDebug` and confirm green. Report the resolved plugin versions and any warnings.

**Reviewer prompt:**
> Review the diff for T-1.*. Confirm: wrapper version line correct, catalog aliases consistent, no source files touched, android build passes. Report file:line refs for anything off.

---

## Phase 2 Brief — Module → KMP (ANDROID ONLY, build stays green)

**Builder prompt:**
> Using the `kmp-compose-multiplatform` skill's "Source Set Layout" and the JetBrains migration guide, convert `:kotstep` to a Kotlin Multiplatform module with only `androidTarget`:
> 1. Move `src/main/java/com/binayshaw7777/kotstep/v3/**` to `commonMain/kotlin/...`.
> 2. Move the V2 legacy code (`model/`, `ui/`, `components/`, `util/`) to `androidMain/kotlin/...`.
> 3. Move `androidTest/**` to `androidInstrumentedTest/`.
> 4. Rewrite `kotstep/build.gradle.kts` with `kotlin("multiplatform")` + `compose` plugin; keep Android manifest/res wired for the Android source set.
> 5. Keep Android-only deps (`core-ktx`, `lifecycle`, `activity-compose`, `constraintlayout-compose`) in `androidMain.dependencies`, NOT commonMain.
> 6. Replace `R.`-based access with `Res.` only where trivially possible; otherwise defer to Phase 3 and note it.
> Verify: `./gradlew :kotstep:compileDebugKotlinAndroid :kotstep:assembleDebug :app:assembleDebug`. Fix the classic `jvmTarget 1.8 vs 17` mismatch via per-target `compilerOptions`. Report a file-move map (old path → new path).

**Reviewer prompt:**
> Verify the diff: no V3 file lost, `androidMain` contains only Android-coupled files, commonMain has no `android.*`/`R.` imports yet (report any that slipped through — builder will gate them in Phase 3), build passes. Confirm `settings.gradle.kts` unchanged except nothing.

---

## Phase 3 Brief — Decouple commonMain (CODE CHURN, biggest risk)

**Builder prompt:**
> Using the `compose-skill` references `resources.md` and `cross-platform.md`, remove every Android dependency from `commonMain` in `:kotstep`:
> 1. **Samples.kt**: relocate `v3/samples/Samples.kt` out of the published library into the demo app (`:app`); refactor `Toast`/`LocalContext`/`painterResource`/`vectorResource(R.drawable.kotlin)` out — replace with icon-based samples or CMP `Res.drawable.kotlin`.
> 2. **Resources**: create `commonMain/composeResources/drawable/kotlin.xml`; delete the `res/drawable` copy in the Android source set; run `./gradlew :kotstep:generateComposeResClass`; update imports to the generated `Res.*`.
> 3. **Preview.kt**: keep `@Preview` in an `androidMain`-reachable file (or CMP preview if stable) — report which route you took.
> 4. **V2 strategy**: confirm V2 lives only in `androidMain` (ConstraintLayout) — write `docs/decisions/0001-v2-legacy-strategy.md` recording the decision.
> 5. **Pure logic**: extract any composable-free helpers (`StepState`, `StaticStepProperties.calculateStaticStepProperties`, `Util`) so they're unit-testable.
> Run the audit: `grep -rn "android\\." commonMain` → must be empty; `grep -rn "R\\." commonMain` → must be empty. Then build Android + run the 6 V3 androidInstrumentedTest if an emulator is available. Report the grep output.

**Reviewer prompt:**
> Grep-audit `commonMain` yourself (android./R./Toast/LocalContext/java.). If any hit, reject with file:line. Confirm Samples no longer ships in the library artifact and V2 is fully under androidMain. Report violations only.

---

## Phase 4 Brief — First New Platform: Desktop

**Builder prompt:**
> Using the `compose-skill` reference `ci-cd-distribution.md` (desktop app module setup) and `cross-platform.md`, add a JVM desktop target:
> 1. `:kotstep`: add `jvm("desktop")` with `compilerOptions` matching a sane JVM target; keep android at 1.8/17 parity where compatible.
> 2. New `desktopApp` module: KMP + compose plugin, `compose.desktop.currentOs`, `application { mainClass }`, windowed `main.kt`.
> 3. Extract `DemoApp()` (+ shared M3 theme WITHOUT `WindowCompat`/`dynamicColor`/`LocalView`) into a shared demo location (`:demo` module or `demoCommon` source set) consumed by `:app` and `desktopApp`; wire `:app` back to it to prove shared UI.
> 4. `./gradlew :desktopApp:run` must open a window showing KotStep samples. Screenshot it if possible.
> Report: demo extraction structure, any dep gaps desktop-only.

**Reviewer prompt:**
> Diff-review `:kotstep` and `desktopApp`. Confirm: `DemoApp` is truly shared (no duplicated screens), the shared theme has no Android APIs, `:app` still compiles. Flag anything desktop-only that duplicated common code.

---

## Phase 5 Brief — Web (Wasm)

**Builder prompt:**
> Using `compose-skill` ref `gradle-build.md` + CMP Wasm docs, add the web target:
> 1. `:kotstep`: add `wasmJs { binaries.executable() }`.
> 2. Verify `material-icons-extended` resolves for wasm; if not, swap commonMain icon usage to `material-icons-core` (or conditional source sets) and flag the change.
> 3. New `webApp` module: wasmJs target, `CanvasBasedWindow` main, `compose.html`/wasm deps.
> 4. `./gradlew :webApp:wasmJsRun` renders samples in the browser.
> Report: any wasm-incompatible deps found → mitigation.

**Reviewer prompt:**
> Verify webApp compiles (`./gradlew :webApp:wasmJsBrowserProductionRun` compile), report any `materialIconsExtended` swap side-effects (icon usages that vanished), confirm no android imports leaked.

---

## Phase 6 Brief — iOS

**Builder prompt:**
> Using `kmp-compose-multiplatform` skill refs `ios-interop.md` + Jetcaster guide, add iOS:
> 1. `:kotstep`: add `iosX64()`, `iosArm64()`, `iosSimulatorArm64()` with static framework `baseName = "KotStep"`.
> 2. Only add `iosMain` expect/actual if the build requires it (V3 core should not).
> 3. Scaffold `iosApp` Xcode project (Swift `UIViewControllerRepresentable` wrapping `MainViewControllerKt.MainViewController()`, `Config.xcconfig`, Gradle embed script for `embedAndSignAppleFrameworkForXcode`).
> 4. `./gradlew :kotstep:linkDebugFrameworkIosSimulatorArm64` must succeed.
> 5. If Xcode is available: `xcodebuild -project iosApp/iosApp.xcodeproj` + run on simulator.
> Report: link success, framework path, any Swift interop surprises.

**Reviewer prompt:**
> Confirm the framework links; check `iosApp` files for correct Swift↔Kotlin wiring (nullability, function name), ensure Android-only deps not leaking into iosMain compile.

---

## Phase 7 Brief — Tests

**Builder prompt:**
> Using `compose-skill` ref `testing.md`, port tests:
> 1. Move `KotStepV3Test.kt` to `commonTest` with `createComposeRule()` (CMP test API); keep an Android-specific variant (`createAndroidComposeRule`) for `androidInstrumentedTest`.
> 2. Adapt assertions that used `getUnclippedBoundsInRoot`/`waitForIdle` to cross-platform equivalents; if not portable, gate them to Android.
> 3. Add `commonTest` unit tests for `Util`, `StepState`, `StaticStepProperties`, `AnimationConstants` (kotlin.test).
> 4. `./gradlew :kotstep:desktopTest :kotstep:androidUnitTest` (and emulator tests if available) must pass.
> Report which assertions had to be Android-gated and why.

**Reviewer prompt:**
> Check the test diff: shared assertions are platform-neutral, Android-specific ones are isolated, no flaky timing assumptions (`waitForIdle` misuse). Run desktopTest.

---

## Phase 8 Brief — Publish + CI

**Builder prompt:**
> 1. Replace Android-only `publishing {}` in `:kotstep` with KMP publishing (`publishLibraryVariants`, per-platform artifacts, preserve POM: groupId `com.github.binayshaw7777`, name `KotStep`, licenses/developers/scm). `./gradlew :kotstep:publishToMavenLocal` and list the generated artifacts + `.module`.
> 2. Update `jitpack.yml` if needed; document binary-target limits (iOS/web need GH Releases/XCFramework).
> 3. Create `.github/workflows/cmp-ci.yml` with android/desktop/wasm/ios jobs (macOS runner for iOS).
> 4. Ensure existing `lint.yml`/`analysis.yml` still green.
> Report: publish artifact list, CI yaml summary, JitPack caveats.

**Reviewer prompt:**
> Verify artifacts published to mavenLocal match expected variant names (KotStep-android, -desktop, -iosarm64, -wasmjs, -kmp metadata), POM fields preserved, CI matrix jobs won't fail fast on missing hardware (iOS job gated). Report discrepancies.

---

## Phase 9 Brief — Docs & Release

**Builder prompt:**
> 1. README: platform badges, per-platform dependency snippets, migration note for existing users.
> 2. Demo parity pass across `:app`/`desktopApp`/`webApp`/`iosApp` — fix rendering quirks so samples match.
> 3. Bump to `3.2.0`, write release notes, tag.
> Report: screenshot + release checklist state.

---

## Phase 10 Brief — Hardening

**Builder prompt:**
> 1. Write V2 deprecation policy into `docs/decisions/0001`.
> 2. Run `compose-performance-audit` + `android-accessibility` skills sweep on commonMain.
> 3. (Optional) Add screenshot/golden tests.
> 4. Remove dead Android-only deps from `:kotstep` if build proves safe.

---

## Orchestrator Cheat-Sheet

- **Verify command per phase:** P1 `assembleDebug`; P2 `compileDebugKotlinAndroid`; P3 grep+C.I. tests; P4 `desktopApp:run`; P5 `webApp:wasmJsRun`; P6 `linkDebugFrameworkIosSimulatorArm64`; P7 `desktopTest`; P8 `publishToMavenLocal`; P9 release; P10 sweep.
- **Always re-run before commit:** `./gradlew :kotstep:allTargetsCompile` (once targets exist) + targeted phase verify.
- **Skills to load per agent type:** builder → `compose-skill`/`kmp-compose-multiplatform`; reviewer → `caveman-review` or plain diff review; safety audit → `kmp-compose-multiplatform` common-pitfalls list.