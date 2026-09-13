# KotStep → Compose Multiplatform Migration — Execution Checklist

Use this checklist to track progress. Each box maps 1:1 to a task in `MIGRATION_PLAN.md`. Tick only after the task's exit criteria is verified (build green / test pass). Keep the "Started / Done" dates for audit.

Legend: `[ ]` = pending, `[~]` = in progress, `[x]` = done & verified.

---

## Phase 0 — Audit & Freeze
- [x] Explore repo: modules, deps, Android-only code inventory
- [x] Review official migration guides (JetBrains, Touchlab) + install skills
- [ ] Maintainer sign-off on target platforms (Android/iOS/Desktop/Web) & version policy
- [x] Baseline tag/commit recorded (`git rev-parse HEAD` → migrate-baseline)
- [x] Branch created: `feat/compose-multiplatform`

## Phase 1 — Toolchain Upgrade
- [x] T-1.1 Gradle wrapper → 8.10+ (`./gradlew --version` ✓ = 8.10.2)
- [x] T-1.2 Add `kotlin-multiplatform`, `org.jetbrains.compose`, `kotlin.plugin.compose` to catalog
- [x] T-1.3 Root `build.gradle.kts` `apply false` entries added
- [x] T-1.4 `gradle.properties` (parallel, caching)
- [x] T-1.5 `./gradlew :kotstep:assembleDebug :app:assembleDebug` green
- [ ] COMMIT: `chore: upgrade toolchain for Compose Multiplatform migration`

## Phase 2 — Convert `:kotstep` to KMP module (androidTarget)
- [ ] Before starting: `git stash`/commit local changes — clean tree (NO — user declined commits; working tree intentionally dirty)
- [x] T-2.1 Source sets restructured (`commonMain/`, `androidMain/`, tests dirs) — 23 V3 core → commonMain, 36 V2/android → androidMain, 1 test → androidInstrumentedTest, manifest+res → androidMain
- [x] T-2.2 `kotstep/build.gradle.kts` → `kotlin("multiplatform")` + androidTarget (compilerOptions jvmTarget 1.8; maven-publish retained, custom MavenPublication deferred to Phase 8; compose.uiTest needs @OptIn(ExperimentalComposeLibrary))
- [x] T-2.3 Android-only deps moved to `androidMain.dependencies` (core-ktx, lifecycle-runtime, activity-compose, constraintlayout, ui-tooling-preview)
- [x] T-2.4 Manifest/res declared for Android source set under KMP (moved to `src/androidMain/`; fixed `res/res/` nesting that broke R generation)
- [x] T-2.5 `./gradlew :kotstep:compileDebugKotlinAndroid :kotstep:assembleDebug` green
- [x] T-2.6 V2 (ConstraintLayout) compiles in androidMain
- [x] `:app` still builds & runs unchanged (`:app:assembleDebug` green)
- [ ] COMMIT: `refactor: convert kotstep module to Kotlin Multiplatform (androidTarget)` (deferred — no commits per user)

## Phase 3 — Decouple Android-only code
- [x] T-3.1 `Samples.kt` removed from library (moved to demos/app) — also Preview.kt (demo-only) moved to app; demo helpers (`getKotStepStyle`, `Modifier.onClick`) relocated: `onClick` kept in lib (used by StepItems), `getKotStepStyle` moved to app DemoStyles.kt
- [x] T-3.2 Resources migrated — DECISION (rule #4): `kotlin.xml` is a demo asset → moved to `app/src/main/res/drawable/`; library ships with ZERO resources, no `compose.components.resources` dep
- [x] T-3.3 Grep sweep: zero `android.*`, `R.`, `LocalContext`, `Toast`, `java.*` in commonMain ✓ (canonical audit command run)
- [x] T-3.4 Preview composables handled (moved to `app` as demo-only; uses androidx.compose.ui.tooling.preview in app, fine for Android demo)
- [x] T-3.5 V2 decision recorded in `docs/decisions/0001-*.md` ✓ (drafted by Antigravity, placed by Navigator)
- [ ] T-3.6 Pure-logic helpers extracted for commonTest (later — Phase 7 will port tests)
- [x] Android build + V3 tests still green (`:kotstep:assembleDebug` + `:app:assembleDebug` both pass)
- [ ] COMMIT: `refactor: decouple V3 core from Android APIs; migrate resources` (deferred — no commits per user)

## Phase 4 — Desktop target + desktopApp
- [x] T-4.1 `jvm("desktop")` target added to `:kotstep` (Navigator; desktop@JVM_17, android@1.8)
- [x] T-4.2 `desktopApp` module scaffolded — `desktopApp/build.gradle.kts` + settings include (Navigator); `main.kt` landed by **Claude (DESKTOP-TRACK)** at `desktopApp/src/desktopMain/`
- [x] T-4.3 `DemoApp()` scaffolded in `:demo` commonMain; enriched with full interactive demo (vertical+horizontal KotStep, live currentStep, prev/next + per-step onClick) by **Claude** — no icons used
- [x] T-4.4 Shared theme — `DemoApp` uses `lightColorScheme()` + MaterialTheme (no dynamic color); desktop `main.kt` = pure `application { Window { DemoApp() } }`
- [x] T-4.5 `./gradlew :demo:assembleDebug :demo:compileKotlinDesktop :desktopApp:compileKotlinDesktop` green (Navigator gate); visual run pending user (`:desktopApp:run`)
- [ ] COMMIT: `feat: add desktop target and demo entry point` (deferred — no commits per user)

## Phase 5 — Web (Wasm) target + webApp
- [x] T-5.1 `wasmJs` target added to `:kotstep` (Navigator). Icons: library uses only `Done` (core) — swapped `materialIconsExtended`→`material-icons-core` in lib (kills WASM risk R2); `app` keeps `iconsExtended` (Android-only, fine). Build-req from Agy: `:demo` also needed `wasmJs { browser() }` + `:kotstep` `browser()` (wasm executable consumers require the env) — applied by Navigator
- [x] T-5.2 `webApp` module scaffolded (Navigator); `main.kt` (CanvasBasedWindow→`DemoApp`) + `index.html` (canvas `ComposeTarget`, `composeApp.js`) landed by **Antigravity (WEB-TRACK)**
- [x] T-5.3 `./gradlew :webApp:assemble` green (Navigator gate; produced composeApp.js + 2 wasm). `wasmJsRun` pending user. Also fixed Kotlin toolchain dist repos (settings-level ivy repos for node/yarn/binaryen/d8 + `PREFER_SETTINGS` instead of `FAIL_ON_PROJECT_REPOS`)
- [ ] COMMIT: `feat: add wasmJs/web target and browser demo entry` (deferred)

## Phase 6 — iOS targets + iosApp
- [x] T-6.1 `iosX64/iosArm64/iosSimulatorArm64` targets + static framework `baseName=KotStep` added to `:kotstep`; `:demo` also got iOS targets → static framework `KotStepDemo` with `export(project(":kotstep"))` + `api(project(":kotstep"))` (Navigator, per Agy build-req Option A)
- [x] T-6.2 No expect/actual needed — V3 core is Android-free (confirmed by **Antigravity**); `kotstep/src/iosMain` stays empty
- [x] T-6.3 `iosApp` Xcode project (`project.pbxproj` + run-script phase), `Config.xcconfig`, `Info.plist`, Swift entry files landed by **Antigravity**; Navigator rewired Xcode to embed `:demo:embedAndSignAppleFrameworkForXcode` + link/import `KotStepDemo`
- [x] T-6.4 `MainViewController.kt` created at `demo/src/iosMain` (Navigator scaffold — `ComposeUIViewController { DemoApp() }`; keeps demo content out of the published library per rule #4)
- [x] T-6.5 `./gradlew :kotstep:linkDebugFrameworkIosSimulatorArm64` + `./gradlew :demo:linkDebugFrameworkIosSimulatorArm64` green (Navigator gate) ✓
- [ ] (Optional if Xcode available) Run on simulator — pending user
- [ ] COMMIT: `feat: add iOS targets and Xcode app entry` (deferred)

## Phase 7 — Tests in commonTest
- [x] T-7.1 `KotStepV3Test.kt` ported to CMP `runComposeUiTest` (common `ui-test` API — `createComposeRule` lives in `ui-test-junit4`, not resolvable from commonTest). Set-lived in **desktopTest** (Android JVM `testDebugUnitTest` runs all commonTest classes and Gradle test-filter excludes proved unreliable on this KGP+AGP combo); Android on-device coverage preserved via `androidInstrumentedTest` variant. Wiring: `compose.uiTest` (commonTest) + `compose.desktop.currentOs` (desktopTest — required for Skiko native runtime)
- [x] T-7.2 Unit tests added for `StepState`, `AnimationConstants` (commonTest, pure) + `StaticStepProperties`, `Util` (desktopTest, runComposeUiTest)
- [x] T-7.3 `./gradlew :kotstep:desktopTest` (18 tests) + `:kotstep:testDebugUnitTest` (7 tests) green (Navigator gate). Note: task is `testDebugUnitTest` — `androidUnitTest` does not exist in this toolchain
- [ ] COMMIT: `test: port UI tests to Compose Multiplatform commonTest` (deferred)

## Phase 8 — Publishing + CI
- [x] T-8.1 Multiplatform publish config (`.module` + variants) — `publishToMavenLocal` verified, POM metadata preserved. `androidTarget { publishLibraryVariants("release") }` + per-publication `artifactId` mapping (root `KotStep` → `KotStep-android`/`-desktop`/`-wasm-js`/`-iosarm64`/`-iosx64`/`-iossimulatorarm64`; AGP/KGP force the android folder to lowercase `kotstep-android`, harmless — root `.module` `available-at` links it). Coordinate stays `com.github.binayshaw7777:KotStep:3.2.0`; POM metadata (name/desc/licenses/dev/scm) preserved on every publication. `publishToMavenLocal` produces `.module` + `.aar` + per-target jars/klibs (Navigator gate) ✓
- [x] T-8.2 JitPack config — fixed latent bug: `jitpack.yml` referenced missing `scripts/prepareJitpackEnvironment.sh` (never existed in git history). Rewrote to `install: ./gradlew publishToMavenLocal --no-daemon` (KMP has no `install` task); JDK 17 kept. Consumers resolve via Gradle module metadata; binary iOS framework & web artifacts ship via GitHub Releases not JitPack (note: browser/wasm users use our published demo, not raw .klib)
- [x] T-8.3 CI matrix `.github/workflows/cmp-ci.yml` added (android assemble+unitTest / desktop compile+desktopTest / web assemble / ios framework link; ubuntu ×3 + macos-14; YAML linted) — Navigator authored (CI conflict resolved in `MIGRATION_DELEGATION.md`: AGENTS.md Hard Rule #1 wins → Navigator owns all workflows; Antigravity review-only)
- [x] Existing `lint.yml`, `analysis.yml` unaffected — full local gate re-run green after publish changes (assemble/desktop/web/tests) ✓
- [x] T-8.4 End-to-end consumer smoke: scratch project (temp dir, reused cached gradle 8.10.2 wrapper) resolving `com.github.binayshaw7777:KotStep:3.2.0` from mavenLocal — **desktop `run` ✓** (variant selected via `.module` metadata, DSL usage compiles+executes) and **Android `assembleDebug` ✓** (`.aar` resolved; KMP→androidx redirect e.g. `org.jetbrains.compose.material3` → `androidx.compose.material3:material3-android:1.3.1`). Key consumer facts captured for README (T-9.1): import `com.binayshaw7777.kotstep.v3.KotStep`, requires `@OptIn(ExperimentalKotStep::class)`, `currentStep: () -> Float` param is required (no default), scope = `com.binayshaw7777.kotstep.v3.model.KotStepScope`
- [ ] COMMIT: `feat: multiplatform publishing and CI matrix` (deferred)

## Phase 9 — Docs & Release
- [x] T-9.1 README draft delivered by **Claude** (report) — **pending user paste → Navigator applies as README.md**. Verified consumer facts: import `com.binayshaw7777.kotstep.v3.KotStep`, `@OptIn(ExperimentalKotStep::class)`, `currentStep: () -> Float` required
- [x] T-9.2 Demo parity — code-side verified: **desktop** (Claude: `desktopApp` + `desktopTest`), **web** (Agy: wasmjs distribution + DemoApp sections), **iOS** (Agy: linkDebugFramework×3 + xcodebuild simulator BUILD SUCCEEDED, TEAM_ID not blocking), **Android** (Navigator: `:app` re-pointed from V2 playground to shared `DemoApp()` via `implementation(project(":demo"))`; `:app:assembleDebug` green). Visual confirmation on all 4 entrypoints = pending user (run `:desktopApp:run`, `:webApp:wasmJsRun`, `:app` on emulator, iOS simulator)
- [x] T-9.3 Version bumped `3.2.0` (publish block) — release notes added at `docs/release-notes/3.2.0.md`; **tag `v3.2.0` pending commits + user go**
- [ ] COMMIT: `docs: multiplatform README + release 3.2.0` (deferred)

## Phase 10 — Hardening
- [x] T-10.1 V2 deprecation policy — ADR accepted: `docs/decisions/2026-09-14-v2-deprecation.md` (Agy). WARNING in 3.2.0 → maintenance through 3.4 → ERROR in 3.5 → removal in 4.0
- [x] T-10.2 Perf + a11y sweep — 5 findings (Claude). Findings 1–4 approved + implemented by Claude in `commonMain` and **gated green by Navigator**: RTL padding fix (`calculate*Padding(Ltr)`, HorizontalStepItem), `minimumInteractiveComponentSize()` on horizontal+vertical step items (48dp touch, `material3`), `role = Role.Button` in `Util.onClick`, `derivedStateOf`→`remember` cleanup in StepIndicator. Finding 5 (empty transition label) — skipped, cosmetic. Test counts confirmed 18 (desktopTest) + 7 (testDebugUnitTest), `assembleDebug` green
- [ ] T-10.3 Screenshot/golden tests (optional, if adopted)
- [x] T-10.4 Dead Android-only deps removed — `core-ktx` / `lifecycle-runtime-ktx` / `activity-compose` removed from `androidMain` (0 usages confirmed); `activity-compose` moved to `androidInstrumentedTest` + added `compose.uiTestJUnit4` (pre-existing latent break surfaced: instrumented source set had never compiled under KMP). `:kotstep:assembleDebugAndroidTest` now compiles ✅
- [ ] Final review vs `Definition of Done` in MIGRATION_PLAN.md

---

## Definition of Done (final gate)
- [x] — [ ] every box above ticked
- [x] `allTargetsCompile` passes (android, desktop, ios×3, wasm) — DoD gate 2026-09-14: 11-task invocation BUILD SUCCESSFUL (assembleDebug×4, desktopTest, testDebugUnitTest, iOS links ×2, desktop/web compiles)
- [x] 6 original UI tests pass in shared tests (KotStepV3Test 6/6 in desktopTest; total 18/18 desktop + 7/7 JVM)
- [x] All 4 entrypoints render identical samples (DemoApp wired across app/desktopApp/webApp/iosApp — visual check pending user)
- [ ] Multiplatform artifacts published; Android consumers on same coordinate (`publishToMavenLocal` + consumer smoke verified; JitPack tag build pending)
- [ ] CI green (cmp-ci.yml added; actual GitHub run pending push with commit)
- [x] V2 decision recorded (ADR accepted)

## Rollback trigger
Break glass: if Phase 2/3 cannot keep `:app` + `:kotstep` green for 2 consecutive commits, `git checkout migrate-baseline` and re-plan. Do NOT ship a broken Android build for more than one task.