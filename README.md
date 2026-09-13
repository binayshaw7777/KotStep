<h1 align="center">KotStep</h1>

<p align="center">
  <img alt="Android" src="https://img.shields.io/badge/Android-API%2024+-3DDC84?style=for-the-badge&logo=android&logoColor=white"/>
  <img alt="iOS" src="https://img.shields.io/badge/iOS-16+-000000?style=for-the-badge&logo=apple&logoColor=white"/>
  <img alt="Desktop JVM" src="https://img.shields.io/badge/Desktop-JVM%2017-orange?style=for-the-badge&logo=openjdk&logoColor=white"/>
  <img alt="Web Wasm" src="https://img.shields.io/badge/Web-Wasm-654FF0?style=for-the-badge&logo=webassembly&logoColor=white"/>
  <img alt="Kotlin" src="https://img.shields.io/badge/Kotlin-2.1.10-a503fc?style=for-the-badge&logo=kotlin&logoColor=white"/>
  <img alt="Compose Multiplatform" src="https://img.shields.io/badge/Compose%20Multiplatform-1.7.3-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white"/>
  <a href="https://jitpack.io/#binayshaw7777/KotStep"><img src="https://img.shields.io/jitpack/v/github/binayshaw7777/KotStep?style=for-the-badge&color=purple"/></a>
  <a href="https://hits.sh/github.com/binayshaw7777/KotStep/"><img alt="Hits" src="https://hits.sh/github.com/binayshaw7777/KotStep.svg?style=for-the-badge&label=Views&color=41b316"/></a>
</p>

<p align="center">
KotStep is a <strong>Compose Multiplatform</strong> stepper UI library — vertical and horizontal
multi-step flows with animated progress, custom styles, collapsible steps, and leading/trailing
labels. Works on Android, iOS, Desktop (JVM), and Web (Wasm) from a single shared codebase.
</p>

<p align="center">
<img src="https://github.com/binayshaw7777/KotStep/assets/62587060/2cf2c41a-6812-484a-bcdc-d5f72cad94f0"/>
</p>

---

## Installation

KotStep is published via [JitPack](https://jitpack.io/#binayshaw7777/KotStep).

### Step 1 — Add the JitPack repository

**`settings.gradle.kts`:**
```kotlin
dependencyResolutionManagement {
    repositories {
        maven("https://jitpack.io")
    }
}
```

### Step 2 — Add the dependency

#### Android (single-platform project)
```kotlin
// app/build.gradle.kts
dependencies {
    implementation("com.github.binayshaw7777:KotStep:3.2.0")
}
```

#### Kotlin Multiplatform (Android + Desktop)
```kotlin
// shared/build.gradle.kts
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation("com.github.binayshaw7777:KotStep:3.2.0")
        }
    }
}
```

#### iOS

> ⚠️ JitPack publishes Kotlin metadata and klibs but **not** pre-built iOS frameworks.
> Link the framework from source per architecture and combine the outputs:
>
> ```bash
> ./gradlew :kotstep:linkReleaseFrameworkIosSimulatorArm64 \
>           :kotstep:linkReleaseFrameworkIosArm64 \
>           :kotstep:linkReleaseFrameworkIosX64
> ```
>
> The three `.framework` bundles land under `kotstep/build/bin/<target>/releaseFramework/`
> (base name `KotStep`). Combine them into a distributable `.xcframework` with
> `xcodebuild -create-xcframework …`.

#### Web (Wasm)

> Web consumers resolve the published Wasm klib directly via the JitPack coordinate above.
> To run the demo web app locally:
>
> ```bash
> ./gradlew :webApp:wasmJsBrowserDevelopmentRun
> ```

---

## Platform requirements

| Platform | Minimum |
|---|---|
| Android | API 24 |
| Desktop (JVM) | JVM 17 |
| iOS | iOS 16 |
| Web | Chrome 119+ / Firefox 120+ (Wasm-GC) |

---

## Quick Start — V3 API

> ⚠️ **V3 is the current API and is multiplatform.** V2 is Android-only — see the [migration note](#v2--v3-migration) below.

```kotlin
import com.binayshaw7777.kotstep.v3.KotStep
import com.binayshaw7777.kotstep.v3.model.step.StepLayoutStyle
import com.binayshaw7777.kotstep.v3.model.style.KotStepStyle
import com.binayshaw7777.kotstep.v3.util.ExperimentalKotStep

@OptIn(ExperimentalKotStep::class)       // required
@Composable
fun CheckoutFlow() {
    var currentStep by remember { mutableStateOf(0f) }

    KotStep(
        currentStep = { currentStep },   // () -> Float — required, no default
        style = KotStepStyle(stepLayoutStyle = StepLayoutStyle.Horizontal)
    ) {
        step(title = "Cart",    onClick = { currentStep = 0f })
        step(title = "Address", onClick = { currentStep = 1f })
        step(title = "Payment", onClick = { currentStep = 2f })
        step(title = "Done",    onClick = { currentStep = 3f })
    }
}
```

### API quick-reference

| | |
|---|---|
| Entry composable | `com.binayshaw7777.kotstep.v3.KotStep` |
| Required opt-in | `@OptIn(ExperimentalKotStep::class)` |
| `currentStep` type | `() -> Float` — **required, no default** |
| DSL scope type | `com.binayshaw7777.kotstep.v3.model.KotStepScope` |
| Layout variants | `StepLayoutStyle.Vertical` (default) · `StepLayoutStyle.Horizontal` |

### `currentStep` semantics

`currentStep` is a `Float`. Whole numbers are fully-completed steps; fractions animate the connecting progress line.

```kotlin
-1f    // all steps in Todo state
 0f    // index 0 is Current
 0.5f  // 50 % progress on the line between index 0 and 1
 1f    // index 1 is Current; index 0 is Done
 3f    // index 3 is Current; indices 0-2 are Done
```

### Step DSL variants

```kotlin
// Text / numbered indicator
step(title = "Shipping")

// Icon indicator (ImageVector)
step(icon = Icons.Default.Done)

// Fully custom composable indicator
step(content = { MyComposable() })

// Collapsible step — tapping toggles content visibility
step(title = "Details", isCollapsible = true)

// Leading and trailing labels
step(
    title = "Payment",
    leadingLabel  = { Text("Step 3") },
    trailingLabel = { Text("~5 min") }
)

// With click callback
step(title = "Review", onClick = { currentStep = 3f })
```

### Styling

```kotlin
@OptIn(ExperimentalKotStep::class)
KotStep(
    currentStep = { currentStep },
    style = KotStepStyle(
        stepLayoutStyle     = StepLayoutStyle.Vertical,
        showCheckMarkOnDone = true,
        ignoreCurrentState  = false,
        stepStyle = StepStyles.default().copy(
            onCurrent = StepStyle.defaultTodo().copy(
                stepSize  = 48.dp,
                stepColor = MaterialTheme.colorScheme.primary
            ),
            onDone = StepStyle.defaultTodo().copy(
                stepColor = MaterialTheme.colorScheme.secondary
            )
        ),
        lineStyle = LineStyles.default().copy(
            onCurrent = LineStyle.defaultCurrent().copy(
                lineThickness = 4.dp,
                lineType      = LineType.Dashed()
            )
        )
    )
) {
    step(title = "Start")
    step(title = "Middle")
    step(title = "End")
}
```

---

## V2 → V3 Migration

> ⚠️ **V2 is Android-only.** The V2 sealed-class API (`HorizontalStepper`, `VerticalStepper`,
> `tabHorizontal(…)`, `iconVertical(…)`, etc.) is not available on Desktop, iOS, or Web and
> will not receive new features. Migrate to V3 for multiplatform support.

### Before — V2 (Android only)

```kotlin
HorizontalStepper(
    style = tabHorizontal(totalSteps = 3, currentStep = 1)
) {
    // step content
}
```

### After — V3 (all platforms)

```kotlin
@OptIn(ExperimentalKotStep::class)
@Composable
fun MyFlow() {
    var step by remember { mutableStateOf(1f) }

    KotStep(
        currentStep = { step },
        style = KotStepStyle(stepLayoutStyle = StepLayoutStyle.Horizontal)
    ) {
        step(title = "Step 1")
        step(title = "Step 2")
        step(title = "Step 3")
    }
}
```

**Key changes:**

| V2 | V3 |
|---|---|
| `HorizontalStepper { }` / `VerticalStepper { }` | Single `KotStep { }` composable |
| `currentStep: Int` inside style factory | `currentStep: () -> Float` on `KotStep` |
| `tabHorizontal(…)` / `iconVertical(…)` factories | `KotStepStyle(stepLayoutStyle = …)` |
| Separate step composables per variant | Unified `step(title/icon/content)` DSL |
| Android-only | Android · iOS · Desktop · Web |

---

## Run the demo app

The demo entrypoints all render the same shared `DemoApp`:

| Platform | Command |
|---|---|
| Android | Open `app/` in Android Studio and run the `app` configuration |
| Desktop | `./gradlew :desktopApp:run` |
| Web | `./gradlew :webApp:wasmJsBrowserDevelopmentRun` (open the printed URL in Chrome 119+ / Firefox 120+) |
| iOS | Open `iosApp/iosApp.xcodeproj` in Xcode and run on a simulator |

---

## Contributing

See [`AGENTS.md`](AGENTS.md) for the multi-agent migration protocol and owned-path rules.

Bug reports and feature requests → [GitHub Issues](https://github.com/binayshaw7777/KotStep/issues).

## License

```
Copyright 2024 Binay Shaw

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
