# Styling & Customization

KotStep gives you granular control over shapes, dimensions, colors, border strokes, and connecting line types.

---

## Configuring KotStepStyle

```kotlin
KotStepStyle(
    stepLayoutStyle = StepLayoutStyle.Horizontal,
    showCheckMarkOnDone = true,
    ignoreCurrentState = false,
    stepStyle = StepStyles.default().copy(
        onTodo = StepStyle.defaultTodo().copy(
            stepColor = Color(0xFF475569),
            stepSize = 32.dp
        ),
        onCurrent = StepStyle.defaultCurrent().copy(
            stepColor = Color(0xFF3B82F6),
            stepSize = 40.dp,
            borderStyle = BorderStyle(width = 3.dp, color = Color(0xFF1D4ED8))
        ),
        onDone = StepStyle.defaultDone().copy(
            stepColor = Color(0xFF10B981),
            stepSize = 32.dp
        )
    ),
    lineStyle = LineStyles.default().copy(
        onCurrent = LineStyle.defaultCurrent().copy(
            lineThickness = 3.dp,
            lineProgressColor = Color(0xFF3B82F6),
            lineType = LineType.Solid()
        )
    )
)
```

---

## Step Shapes

Customize indicator shapes:
- **`StepShape.Circle`** (Standard circular badge)
- **`StepShape.RoundedSquare`** (Soft-corner rounded rectangle)
- **`StepShape.Square`** (Geometric square)
- **Custom `Shape`**: Any Compose `androidx.compose.ui.graphics.Shape` (e.g. `CutCornerShape`).

---

## Connecting Line Types

KotStep supports 3 distinct line styles:

| Line Type | Description | Configuration |
|---|---|---|
| **Solid** | Continuous solid line | `LineType.Solid()` |
| **Dashed** | Alternating dashes and gaps | `LineType.Dashed(intervals = floatArrayOf(20f, 10f))` |
| **Dotted** | Repeated dots with round or square caps | `LineType.Dotted(intervals = floatArrayOf(8f, 8f))` |

---

## Sub-Pixel Progress Animations

When `currentStep` transitions between floating points (e.g. from `1.0f` to `2.0f`), connecting lines automatically animate using configurable animation specs:

```kotlin
progressAnimationSpec = tween(
    durationMillis = 400,
    easing = FastOutSlowInEasing
)
```
Reverse transitions (moving backwards) feature a stagger delay to ensure smooth, natural rewinding.
