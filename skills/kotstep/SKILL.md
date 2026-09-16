---
name: kotstep
description: Guidance for developing, styling, testing, and consuming the KotStep stepper library (Compose Multiplatform v3 DSL, custom indicators, lines, animations, layout slots, constraint safety, and legacy v2 maintenance).
---

# KotStep Library Skill (Compose Multiplatform)

KotStep is a modern, high-performance Stepper UI library built natively for **Compose Multiplatform (CMP)** targeting **Android, iOS, Desktop (JVM), and Web (Wasm)**.

---

## 1. Architecture & Module Structure

```
KotStep/
├── kotstep/             # Core multiplatform library module (v3 DSL + v2 legacy)
│   └── src/
│       ├── commonMain/  # Multiplatform v3 DSL (Android, iOS, Desktop, Web)
│       ├── androidMain/ # Legacy v2 steppers (ConstraintLayout Compose)
│       ├── desktopTest/ # Desktop JVM Compose UI tests & off-screen image rendering
│       └── commonTest/  # Shared logic & unit tests
├── kotstep-sdui/        # Server-Driven UI extension (dynamic JSON stepper definitions)
├── demo/                # Shared Compose Multiplatform demo UI (DemoApp)
├── app/                 # Android demo application runner
├── desktopApp/          # Desktop JVM application runner
├── webApp/              # Wasm browser application runner
├── iosApp/              # iOS SwiftUI / CMP application runner
└── docs/                # Documentation & rendered component previews
    └── images/          # Off-screen exported PNG previews
```

---

## 2. Core V3 DSL API Specification

### A. Minimal Horizontal Stepper
```kotlin
import androidx.compose.runtime.*
import com.binayshaw7777.kotstep.v3.KotStep
import com.binayshaw7777.kotstep.v3.model.step.StepLayoutStyle
import com.binayshaw7777.kotstep.v3.model.style.KotStepStyle
import com.binayshaw7777.kotstep.v3.util.ExperimentalKotStep

@OptIn(ExperimentalKotStep::class)
@Composable
fun OrderFlow() {
    var step by remember { mutableFloatStateOf(1f) }

    KotStep(
        currentStep = { step },
        style = KotStepStyle(stepLayoutStyle = StepLayoutStyle.Horizontal)
    ) {
        step(title = "Cart", onClick = { step = 0f })
        step(title = "Shipping", onClick = { step = 1f })
        step(title = "Payment", onClick = { step = 2f })
        step(title = "Review", onClick = { step = 3f })
    }
}
```

### B. Vertical Stepper with Leading & Trailing Slots
```kotlin
KotStep(
    currentStep = { 2f },
    style = KotStepStyle(
        stepLayoutStyle = StepLayoutStyle.Vertical,
        stepStyle = StepStyles.default().copy(
            onCurrent = StepStyle(
                stepColor = Color(0xFF6366F1),
                stepSize = 32.dp,
                borderStyle = BorderStyle(width = 3.dp, color = Color(0xFFC7D2FE))
            ),
            onDone = StepStyle(stepColor = Color(0xFF10B981), stepSize = 28.dp)
        ),
        lineStyle = LineStyles.default().copy(
            onDone = LineStyle(progressColor = Color(0xFF10B981), lineThickness = 3.dp, lineLength = 32.dp)
        )
    )
) {
    step(
        title = "1",
        leadingLabel = {
            Text("09:30 AM", fontSize = 12.sp, color = Color.Gray)
        },
        trailingLabel = {
            Column {
                Text("Order Placed", fontWeight = FontWeight.Bold)
                Text("Your order #89201 has been confirmed", fontSize = 12.sp, color = Color.Gray)
            }
        }
    )
    step(
        title = "2",
        leadingLabel = {
            Text("11:15 AM", fontSize = 12.sp, color = Color.Gray)
        },
        trailingLabel = {
            Column {
                Text("Package Packed", fontWeight = FontWeight.Bold)
                Text("Fulfilled by seller hub warehouse", fontSize = 12.sp, color = Color.Gray)
            }
        }
    )
    step(
        title = "3",
        leadingLabel = {
            Text("02:45 PM", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF6366F1))
        },
        trailingLabel = {
            Column {
                Text("Out for Delivery", fontWeight = FontWeight.Bold, color = Color(0xFF6366F1))
                Text("Courier agent on the way to your address", fontSize = 12.sp, color = Color.Gray)
            }
        }
    )
}
```

### C. Icon-Based Steps
```kotlin
KotStep(
    currentStep = { 1.5f },
    style = KotStepStyle(stepLayoutStyle = StepLayoutStyle.Horizontal)
) {
    step(
        icon = Icons.Default.Home,
        trailingLabel = { Text("Start") }
    )
    step(
        icon = Icons.Default.Person,
        trailingLabel = { Text("Profile") }
    )
    step(
        icon = Icons.Default.Star,
        trailingLabel = { Text("Perks") }
    )
    step(
        icon = Icons.Default.Check,
        trailingLabel = { Text("Done") }
    )
}
```

### D. Collapsible Steps
```kotlin
step(
    title = "Billing Details",
    isCollapsible = true, // Tapping indicator or row toggles visibility of trailing content
    trailingLabel = {
        Card {
            Column(modifier = Modifier.padding(12.dp)) {
                Text("Pro Plan — $19/month", fontWeight = FontWeight.Bold)
                Text("Unlimited multiplatform steppers & themes")
            }
        }
    }
)
```

---

## 3. Progress State & Mathematical Semantics

`currentStep: () -> Float` drives all indicator states and connecting line progress:

| Value of `currentStep` | Step States (`StepState`) | Line Animation Behavior |
|---|---|---|
| `< 0f` (e.g. `-1f`) | All steps `Todo` | All connecting lines empty |
| `0f` | Step 0 `Current`, Step 1+ `Todo` | Line 0 progress = 0% |
| `0.5f` | Step 0 `Current`, Step 1+ `Todo` | Line between Step 0 and 1 is 50% filled with `progressColor` |
| `1.0f` | Step 0 `Done`, Step 1 `Current`, Step 2+ `Todo` | Line between Step 0 and 1 is 100% completed |
| `>= totalSteps` | All steps `Done` | All lines 100% completed |

---

## 4. Styling Catalog

### `KotStepStyle`
- `stepLayoutStyle`: `StepLayoutStyle.Horizontal` or `StepLayoutStyle.Vertical` (default: `Vertical`)
- `itemPadding`: `Dp` between steps (default: `8.dp`)
- `showCheckMarkOnDone`: `Boolean` showing check icon when completed (default: `true`)
- `ignoreCurrentState`: `Boolean` disables active state styling (default: `false`)
- `stepStyle`: `StepStyles` containing `onTodo`, `onCurrent`, `onDone`
- `lineStyle`: `LineStyles` containing `onTodo`, `onCurrent`, `onDone`

### `StepStyle`
- `stepSize`: Indicator dimensions `Dp` (default: `24.dp`)
- `stepShape`: Any Compose `Shape` (e.g. `CircleShape`, `RoundedCornerShape(8.dp)`)
- `stepColor`: Background fill `Color`
- `textStyle`: Typography for numbered titles
- `iconStyle`: `IconStyle(iconTint: Color, iconSize: Dp)`
- `borderStyle`: `BorderStyle(width: Dp, color: Color, shape: Shape)`

### `LineStyle` & `LineType`
- `lineColor`: Track background `Color`
- `progressColor`: Animated progress fill `Color`
- `lineLength`: Length `Dp` of line segment (default: `16.dp`)
- `lineThickness`: Thickness `Dp` (default: `2.dp`)
- `linePadding`: Segment margins `PaddingValues`
- `lineStrokeCap`: `StrokeCap.Round`, `StrokeCap.Square`, or `StrokeCap.Butt`
- `progressStrokeCap`: Stroke cap for active progress fill
- `lineType` & `progressType`:
  - `LineType.Solid`
  - `LineType.Dashed(dashLength: Dp, gapLength: Dp)`
  - `LineType.Dotted(gapLength: Dp)`

---

## 5. Engineering Constraints & Hard Rules

1. **Zero Android Imports in `commonMain`:**
   - Never import `android.*`, `R.*`, `LocalContext`, `Toast`, or `java.*` into `kotstep/src/commonMain`.
   - Verify: `grep -rn "android\." kotstep/src/commonMain` (must be empty).
2. **Layout Constraint Safety:**
   - Always clamp measurements: `desiredWidth.coerceAtMost(constraints.maxWidth)`.
   - In `HorizontalStepItem`, root column uses `Modifier.wrapContentWidth().then(modifier)` to prevent swallowing row width.
3. **API Backward Compatibility:**
   - Never break binary or source compatibility of the published `:kotstep` DSL.
   - V2 legacy stays sealed inside `androidMain`; do not touch or re-export in `commonMain`.
4. **Off-Screen Image Rendering:**
   - Desktop test target (`kotstep/src/desktopTest`) supports `ImageComposeScene` with `EncodedImageFormat.PNG` to export component previews directly to `docs/images/`.

---

## 6. Verification Workflow

```bash
# Verify shared desktop compilation and UI tests
./gradlew :kotstep:desktopTest

# Verify Android library and app build
./gradlew :kotstep:assembleDebug :app:assembleDebug

# Export updated component preview images
./gradlew :kotstep:desktopTest --tests "com.binayshaw7777.kotstep.v3.KotStepExportPreviewsTest"
```
