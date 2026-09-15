# Migration Guide: KotStep V2 to V3 (Compose Multiplatform)

This document provides a comprehensive migration guide for migrating from **KotStep V2 (Android-only)** to **KotStep V3 (Compose Multiplatform)**.

---

## 🌟 Executive Summary of Changes

| Feature / Aspect | KotStep V2 (Legacy) | KotStep V3 (Current) |
|---|---|---|
| **Platform Support** | Android only | Android, iOS, Desktop (JVM), Web (Wasm) |
| **Underlying Layout** | `constraintlayout-compose` | Pure Compose Multiplatform custom Layouts |
| **Entry Composable** | `HorizontalStepper`, `VerticalStepper` | Single unified `KotStep` composable |
| **Step Declaration** | Sealed classes / predefined item lists | Declarative DSL scope: `KotStep { step(...) }` |
| **State Tracking** | `currentStep: Int` passed into style factory | `currentStep: () -> Float` lambda passed to `KotStep` |
| **Progress Animation** | Integer-bound discrete jumps | Continuous `Float` interpolation for connecting lines |
| **Label Slots** | Rigid titles/subtitles | Composable `leadingLabel` and `trailingLabel` slots |
| **Interactive Content**| Not supported | `isCollapsible = true` for accordion-style cards |
| **Package Structure** | `com.binayshaw7777.kotstep` | `com.binayshaw7777.kotstep.v3.*` |

---

## 🔄 API Comparison & Before / After Diffs

### 1. Basic Horizontal Stepper

#### Before (V2 - Android Only)
```kotlin
// V2 used separate composables and style factories with fixed step counts
HorizontalStepper(
    style = tabHorizontal(
        totalSteps = 3,
        currentStep = 1,
        stepColor = Color.Blue
    )
) {
    // Step content placed inside
}
```

#### After (V3 - Multiplatform)
```kotlin
// V3 uses unified KotStep with declarative steps and reactive state lambda
@OptIn(ExperimentalKotStep::class)
@Composable
fun OrderStepper() {
    var currentStep by remember { mutableFloatStateOf(1f) }

    KotStep(
        currentStep = { currentStep },
        style = KotStepStyle(stepLayoutStyle = StepLayoutStyle.Horizontal)
    ) {
        step(title = "Cart", onClick = { currentStep = 0f })
        step(title = "Address", onClick = { currentStep = 1f })
        step(title = "Payment", onClick = { currentStep = 2f })
    }
}
```

---

### 2. Numbered Stepper

#### Before (V2)
```kotlin
HorizontalStepper(
    style = numberedHorizontal(
        totalSteps = 4,
        currentStep = 2,
        stepSize = 36.dp
    )
)
```

#### After (V3)
```kotlin
@OptIn(ExperimentalKotStep::class)
KotStep(
    currentStep = { 2f },
    style = KotStepStyle(
        stepLayoutStyle = StepLayoutStyle.Horizontal,
        stepStyle = StepStyles.default(
            onCurrent = StepStyle(stepSize = 36.dp, stepColor = MaterialTheme.colorScheme.primary)
        )
    )
) {
    step(title = "1")
    step(title = "2")
    step(title = "3")
    step(title = "4")
}
```

---

### 3. Icon Stepper

#### Before (V2)
```kotlin
VerticalStepper(
    style = iconVertical(
        totalSteps = 3,
        currentStep = 1,
        icons = listOf(Icons.Default.Home, Icons.Default.Person, Icons.Default.Done)
    )
)
```

#### After (V3)
```kotlin
@OptIn(ExperimentalKotStep::class)
KotStep(
    currentStep = { 1f },
    style = KotStepStyle(stepLayoutStyle = StepLayoutStyle.Vertical)
) {
    step(icon = Icons.Default.Home, trailingLabel = { Text("Home") })
    step(icon = Icons.Default.Person, trailingLabel = { Text("Profile") })
    step(icon = Icons.Default.Done, trailingLabel = { Text("Complete") })
}
```

---

### 4. Custom Composable Indicators

#### Before (V2)
Not directly supported or required modifying internal sealed classes.

#### After (V3)
```kotlin
@OptIn(ExperimentalKotStep::class)
KotStep(
    currentStep = { 1f },
    style = KotStepStyle(stepLayoutStyle = StepLayoutStyle.Horizontal)
) {
    step(content = { CircularProgressIndicator(modifier = Modifier.size(24.dp)) })
    step(title = "Review")
    step(icon = Icons.Default.Check)
}
```

---

### 5. Timelines with Leading and Trailing Labels

#### Before (V2)
V2 had no concept of leading label slots (e.g. timestamps) or custom trailing layouts.

#### After (V3)
```kotlin
@OptIn(ExperimentalKotStep::class)
KotStep(
    currentStep = { 1f },
    style = KotStepStyle(stepLayoutStyle = StepLayoutStyle.Vertical)
) {
    step(
        title = "1",
        leadingLabel = { Text("10:00 AM", color = Color.Gray, fontSize = 12.sp) },
        trailingLabel = {
            Column {
                Text("Order Placed", fontWeight = FontWeight.Bold)
                Text("Received by restaurant", fontSize = 12.sp)
            }
        }
    )
    step(
        title = "2",
        leadingLabel = { Text("10:30 AM", color = Color.Gray, fontSize = 12.sp) },
        trailingLabel = {
            Column {
                Text("In Kitchen", fontWeight = FontWeight.Bold)
                Text("Preparing your meal", fontSize = 12.sp)
            }
        }
    )
}
```

---

## 🗺️ Symbol & Class Mapping Table

| V2 API (Deprecated / Android-Only) | V3 Multiplatform Replacement | Notes |
|---|---|---|
| `com.binayshaw7777.kotstep.HorizontalStepper` | `com.binayshaw7777.kotstep.v3.KotStep` | Pass `StepLayoutStyle.Horizontal` in style |
| `com.binayshaw7777.kotstep.VerticalStepper` | `com.binayshaw7777.kotstep.v3.KotStep` | Default layout is `StepLayoutStyle.Vertical` |
| `tabHorizontal(...)` | `KotStepStyle(stepLayoutStyle = StepLayoutStyle.Horizontal)` | Configured via `KotStepStyle` |
| `numberedHorizontal(...)` | `KotStepStyle(...)` + `step(title = "1")` | Use step titles for numbers |
| `iconHorizontal(...)` | `KotStepStyle(...)` + `step(icon = Icons.Default.*)` | Pass `ImageVector` directly to `step()` |
| `currentStep: Int` | `currentStep: () -> Float` | Allows animated continuous line progress |
| `totalSteps: Int` | Inferred automatically | KotStep counts steps declared in `content` scope |
| `stepColor` / `strokeColor` | `StepStyle(stepColor, borderStyle)` | Configured per state (`onTodo`, `onCurrent`, `onDone`) |
| `lineThickness` / `lineColor` | `LineStyle(lineThickness, lineColor, progressColor)` | Supports `LineType.Solid`, `Dashed`, and `Dotted` |

---

## ⚠️ Breaking Changes & Migration Checklist

1. **Add Opt-in Annotation**:
   V3 requires `@OptIn(ExperimentalKotStep::class)` at the callsite or file level:
   ```kotlin
   import com.binayshaw7777.kotstep.v3.util.ExperimentalKotStep
   ```

2. **Migrate `currentStep` to Lambda**:
   Instead of passing an `Int` value directly, pass a lambda returning `Float`:
   ```kotlin
   // V2
   currentStep = 2

   // V3
   currentStep = { 2f }
   ```

3. **Declare Steps in DSL Block**:
   Move from factory parameter lists (`icons = ...`, `titles = ...`) to the DSL block:
   ```kotlin
   KotStep(currentStep = { step }) {
       step(title = "Step 1")
       step(title = "Step 2")
   }
   ```

4. **Multiplatform Imports**:
   Ensure your imports reference `com.binayshaw7777.kotstep.v3.*`. Remove any references to legacy package `com.binayshaw7777.kotstep.model.*` (which required Android `ConstraintLayout`).
