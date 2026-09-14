# Core Stepper V3 DSL

The `:kotstep` library provides a unified, declarative DSL for Compose Multiplatform.

---

## Basic Usage

```kotlin
import androidx.compose.runtime.*
import com.binayshaw7777.kotstep.v3.KotStep
import com.binayshaw7777.kotstep.v3.model.step.StepLayoutStyle
import com.binayshaw7777.kotstep.v3.model.style.KotStepStyle
import com.binayshaw7777.kotstep.v3.util.ExperimentalKotStep

@OptIn(ExperimentalKotStep::class)
@Composable
fun RegistrationStepper() {
    var currentStep by remember { mutableFloatStateOf(0f) }

    KotStep(
        currentStep = { currentStep },
        style = KotStepStyle(stepLayoutStyle = StepLayoutStyle.Horizontal)
    ) {
        step(title = "Account", onClick = { currentStep = 0f })
        step(title = "Profile", onClick = { currentStep = 1f })
        step(title = "Security", onClick = { currentStep = 2f })
        step(title = "Done", onClick = { currentStep = 3f })
    }
}
```

---

## Current Step Semantics

`currentStep` is evaluated via a lambda `() -> Float`:
- **`0.0f`**: First step active (`CURRENT`).
- **`1.0f`**: Second step active (`CURRENT`), first step marked `DONE`.
- **`0.5f`**: Connecting line between step 0 and step 1 is 50% animated/filled.
- **`-1.0f`**: All steps in `TODO` state.

---

## Step DSL Variants

### 1. Title Indicator
Shows a sequential number or short text inside the indicator:
```kotlin
step(title = "1")
```

### 2. Icon Indicator
Renders an `ImageVector` inside the step indicator:
```kotlin
step(
    icon = Icons.Default.ShoppingCart,
    onClick = { /* navigate */ }
)
```

### 3. Custom Indicator Content
Fully customizable Compose slot inside the indicator circle/badge:
```kotlin
step(
    content = {
        CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
    }
)
```

### 4. Leading and Trailing Labels
Add context outside the indicator (above/below in horizontal, or left/right in vertical):
```kotlin
step(
    icon = Icons.Default.Check,
    leadingLabel = {
        Text("10:30 AM", style = MaterialTheme.typography.labelSmall)
    },
    trailingLabel = {
        Column {
            Text("Order Placed", fontWeight = FontWeight.Bold)
            Text("Payment confirmed", style = MaterialTheme.typography.bodySmall)
        }
    }
)
```

### 5. Collapsible Steps
Allow tapping steps to expand and collapse secondary details:
```kotlin
step(
    title = "Invoice Details",
    isCollapsible = true,
    trailingLabel = {
        Text("Click to expand invoice breakdown")
    }
)
```

---

## Horizontal vs Vertical Layouts

Switch orientation easily via `style.stepLayoutStyle`:

=== "Horizontal Layout"
    ```kotlin
    KotStepStyle(
        stepLayoutStyle = StepLayoutStyle.Horizontal
    )
    ```

=== "Vertical Layout"
    ```kotlin
    KotStepStyle(
        stepLayoutStyle = StepLayoutStyle.Vertical
    )
    ```
