---
name: kotstep
description: Guidance for developing, extending, styling, and consuming the KotStep stepper library (Compose Multiplatform v3 DSL, custom indicators, lines, animations, layout slots, constraint safety, and legacy v2 maintenance).
---

# KotStep Library Skill

KotStep is a customizable, high-performance Stepper UI library built for **Compose Multiplatform (CMP)** targeting Android, iOS, Desktop (JVM), and Web (Wasm).

## 1. Project & Module Architecture

```
KotStep/
├── kotstep/             # Core multiplatform stepper library (v3 DSL + v2 legacy)
│   └── src/
│       ├── commonMain/  # CMP v3 DSL (KotStep, KotStepScope, styles, animations)
│       ├── androidMain/ # v2 legacy steppers (ConstraintLayout Compose)
│       ├── desktopTest/ # Desktop JVM Compose UI testing
│       └── commonTest/  # Multiplatform logic and unit testing
├── kotstep-sdui/        # Optional Server-Driven UI extension library
├── app/                 # Android demo application and interactive showcases
├── desktopApp/          # Desktop JVM demo entrypoint
├── webApp/              # Wasm demo entrypoint
└── iosApp/              # iOS SwiftUI / CMP entrypoint
```

---

## 2. Core V3 DSL Usage

### Standard Horizontal Stepper
```kotlin
var currentStep by remember { mutableIntStateOf(1) }

KotStep(
    currentStep = currentStep.toFloat(),
    totalSteps = 4,
    style = KotStepStyle(
        layoutStyle = StepLayoutStyle.Horizontal(
            stepAlignment = StepAlignment.Center,
            lineAlignment = LineAlignment.Center
        ),
        stepStyle = StepItemStyle(
            shape = StepShape.Circle,
            size = 36.dp,
            colors = StepItemColors(
                todoColor = Color.LightGray,
                currentColor = Color(0xFF1E88E5),
                doneColor = Color(0xFF43A047)
            )
        ),
        lineStyle = LineStyle(
            lineType = LineType.Solid,
            thickness = 3.dp
        )
    ),
    onStepClick = { index ->
        currentStep = index
    }
) {
    step {
        trailingLabel = {
            Text(text = "Cart", style = MaterialTheme.typography.labelMedium)
        }
    }
    step {
        trailingLabel = {
            Text(text = "Address", style = MaterialTheme.typography.labelMedium)
        }
    }
    step {
        trailingLabel = {
            Text(text = "Payment", style = MaterialTheme.typography.labelMedium)
        }
    }
    step {
        trailingLabel = {
            Text(text = "Review", style = MaterialTheme.typography.labelMedium)
        }
    }
}
```

### Vertical Stepper with Custom Content & Badges
```kotlin
KotStep(
    currentStep = 2f,
    totalSteps = 3,
    style = KotStepStyle(
        layoutStyle = StepLayoutStyle.Vertical()
    )
) {
    step {
        indicator = {
            Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = Color.White)
        }
        trailingLabel = {
            Column {
                Text("Order Placed", fontWeight = FontWeight.Bold)
                Text("10:30 AM", color = Color.Gray, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
    step {
        indicator = {
            Text("2", color = Color.White)
        }
        trailingLabel = {
            Column {
                Text("Out for Delivery", fontWeight = FontWeight.Bold)
                Text("Estimated delivery by 2:00 PM", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
```

---

## 3. Engineering Rules & Best Practices

### A. Zero Android Leakage in `commonMain`
- Never import `android.*`, `R.*`, `LocalContext`, or `java.*` into `kotstep/src/commonMain` or `kotstep-sdui/src/commonMain`.
- Verify cleanliness:
  ```bash
  grep -rn "android\." kotstep/src/commonMain
  ```

### B. Layout Constraint Safety
- When measuring child slots (labels, indicators) inside custom Layout composables, **never** allow `minWidth > maxWidth` or `minHeight > maxHeight`.
- Always clamp measurements:
  ```kotlin
  val clampedWidth = desiredWidth.coerceAtMost(constraints.maxWidth)
  val clampedHeight = desiredHeight.coerceAtMost(constraints.maxHeight)
  ```

### C. API Backward Compatibility
- Never break binary or source compatibility of the published `:kotstep` DSL (`KotStep`, `KotStepStyle`, `StepState`, `StepItemStyle`, `LineStyle`).
- If adding new features (e.g. SDUI, badges, animations), implement them as additive extensions or in optional companion modules like `:kotstep-sdui`.

### D. Verification Workflow
```bash
# Verify multiplatform core compilation and tests
./gradlew :kotstep:desktopTest

# Verify Android demo
./gradlew :app:assembleDebug
```
