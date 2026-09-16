---
name: kotstep-v3
description: Expert guidance on implementing and customizing KotStep V3, a Jetpack Compose library for step-by-step UI components.
---

# KotStep V3 Implementation Guide

## Overview

KotStep V3 is an experimental Jetpack Compose library for creating customizable step-by-step UI components. It supports horizontal and vertical layouts with various step types (title, icon, custom content) and advanced features like progress indication, click handling, and collapsible steps. This guide provides comprehensive documentation based on the official wiki.

## Core Components

### 1. KotStep Composable
The main entry point is the `KotStep` composable, which uses a DSL to define steps.

```kotlin
KotStep(
    modifier = Modifier,
    currentStep = { 1.5f }, // Float for progress (1.5 = between step 1 and 2)
    style = KotStepStyle(),
    content = {
        // Define steps here
    }
)
```

### 2. KotStepStyle
Defines the overall style for the KotStep Stepper.

```kotlin
@ExperimentalKotStep
@Immutable
data class KotStepStyle(
    val stepLayoutStyle: StepLayoutStyle = StepLayoutStyle.Vertical,
    val itemPadding: Dp = 8.dp,
    val showCheckMarkOnDone: Boolean = true,
    val ignoreCurrentState: Boolean = false,
    val stepStyle: StepStyles = StepStyles.default(),
    val lineStyle: LineStyles = LineStyles.default()
)
```

| Property | Type | Default Value | Description |
|----------|------|---------------|-------------|
| `stepLayoutStyle` | `StepLayoutStyle` | `Vertical` | Layout orientation. |
| `itemPadding` | `Dp` | `8.dp` | Padding around each step. |
| `showCheckMarkOnDone` | `Boolean` | `true` | Show checkmark on done steps. |
| `ignoreCurrentState` | `Boolean` | `false` | Ignore current step state (only Todo/Done). |
| `stepStyle` | `StepStyles` | `StepStyles.default()` | Step appearance for all states. |
| `lineStyle` | `LineStyles` | `LineStyles.default()` | Line appearance for all states. |

**Note on `ignoreCurrentState`:** Only considers Todo and Done states, no Current state. [Learn more](https://github.com/binayshaw7777/KotStep/issues/35#issuecomment-2793997957)

### 3. Step Definition
Steps are defined using the `step()` function in the DSL scope.

**Title Step:**
```kotlin
step(
    title = "Step 1",
    onClick = { /* handle click */ },
    onDone = { /* handle completion */ },
    label = { Text("Additional content") },
    isCollapsible = false
)
```

**Icon Step:**
```kotlin
step(
    icon = Icons.Default.Star,
    onClick = { /* handle click */ },
    label = { Text("Icon step") }
)
```

**Custom Content Step:**
```kotlin
step(
    content = { 
        Image(painterResource(R.drawable.custom), contentDescription = null)
    },
    label = { Text("Custom step") }
)
```

**Default Step:**
```kotlin
step(
    onClick = { /* handle click */ },
    label = { Text("Default step") }
)
```

| Property | Type | Default | Description |
|----------|------|---------|-------------|
| `title` | `String` | - | Title (must not be blank) |
| `icon` | `ImageVector` | - | Image vector icon |
| `content` | `@Composable () -> Unit` | `null` | Custom composable |
| `onClick` | `() -> Unit` | `{}` | Click callback |
| `onDone` | `() -> Unit` | `{}` | Completion callback |
| `label` | `@Composable () -> Unit` | `{}` | Label composable |
| `isCollapsible` | `Boolean` | `false` | Allow collapse on click |

### 4. StepStyle & StepStyles
Defines individual step visual styles.

```kotlin
@ExperimentalKotStep
@Immutable
data class StepStyle(
    val stepColor: Color = Color.Gray,
    val stepSize: Dp = 24.dp,
    val stepShape: Shape = CircleShape,
    val textStyle: TextStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
    val iconStyle: IconStyle = IconStyle(),
    val borderStyle: BorderStyle = BorderStyle()
) {
    companion object {
        fun defaultTodo() = StepStyle(stepColor = Color.Gray.copy(alpha = 0.3f))
        fun defaultCurrent() = StepStyle(stepColor = Color.Blue)
        fun defaultDone() = StepStyle(stepColor = Color.Green)
    }
}
```

**Recommendations:** Font size and icon size should be ≤ 75% of `stepSize`.

```kotlin
@ExperimentalKotStep
@Immutable
data class StepStyles(
    val onTodo: StepStyle,
    val onCurrent: StepStyle,
    val onDone: StepStyle
) {
    companion object {
        fun default() = StepStyles(
            onTodo = StepStyle.defaultTodo(),
            onCurrent = StepStyle.defaultCurrent(),
            onDone = StepStyle.defaultDone()
        )
    }
}
```

### 5. LineStyle & LineStyles
Defines line styling for progress indicators.

```kotlin
@ExperimentalKotStep
@Immutable
data class LineStyle(
    val lineColor: Color = Color.Gray,
    val progressColor: Color = Color.Green,
    val lineLength: Dp = 16.dp,
    val lineThickness: Dp = 2.dp,
    val linePadding: PaddingValues = PaddingValues(0.dp),
    val lineStrokeCap: StrokeCap = StrokeCap.Square,
    val progressStrokeCap: StrokeCap = StrokeCap.Square,
    val lineType: LineType = LineType.Solid,
    val progressType: LineType = LineType.Solid,
) {
    companion object {
        fun defaultTodo() = LineStyle(lineColor = Color.Gray.copy(alpha = 0.3f), progressColor = Color.Gray.copy(alpha = 0.3f))
        fun defaultCurrent() = LineStyle(lineColor = Color.Gray.copy(alpha = 0.3f), progressColor = Color.Blue)
        fun defaultDone() = LineStyle(lineColor = Color.Gray.copy(alpha = 0.3f), progressColor = Color.Green)
    }
}
```

```kotlin
@ExperimentalKotStep
@Immutable
data class LineStyles(
    val onTodo: LineStyle,
    val onCurrent: LineStyle,
    val onDone: LineStyle
) {
    companion object {
        fun default() = LineStyles(
            onTodo = LineStyle.defaultTodo(),
            onCurrent = LineStyle.defaultCurrent(),
            onDone = LineStyle.defaultDone()
        )
    }
}
```

### 6. LineType
Defines line types.

```kotlin
@ExperimentalKotStep
@Immutable
sealed class LineType {
    @Immutable
    data object Solid : LineType()
    
    @Immutable
    data class Dashed(
        val dashLength: Dp = 10.dp,
        val gapLength: Dp = 15.dp
    ) : LineType()
    
    @Immutable
    data class Dotted(
        val gapLength: Dp = 8.dp
    ) : LineType()
}
```

### 7. BorderStyle & IconStyle
```kotlin
@ExperimentalKotStep
data class BorderStyle(
    val width: Dp = 1.dp,
    val color: Color = Color.Unspecified,
    val shape: Shape = CircleShape
)

@ExperimentalKotStep
data class IconStyle(
    val iconTint: Color = Color.Unspecified,
    val iconSize: Dp = 16.dp
)
```

### 8. StepLayoutStyle & StepState
```kotlin
@ExperimentalKotStep
@Immutable
enum class StepLayoutStyle {
    Vertical,
    Horizontal,
}

@ExperimentalKotStep
sealed class StepState {
    data object Todo : StepState()
    data object Current : StepState()
    data object Done : StepState()
}
```

### 9. Progress Indication
- Use `currentStep` as a Float: integer values indicate completed steps, fractional values show progress between steps
- Range: -1 (all steps not started) to `steps.size` (all completed)
- Example: `currentStep = { 2.3f }` means step 2 is 30% complete, step 3 is next

## Best Practices

### 1. State Management
- Use `remember` for current step state
- Handle step transitions smoothly with animations
- Consider using ViewModel for complex workflows

### 2. Accessibility
- Provide meaningful content descriptions for icons
- Ensure touch targets meet minimum size requirements
- Use semantic labels for screen readers

### 3. Performance
- Use `key()` for dynamic step lists
- Avoid recreating step content unnecessarily
- Consider lazy loading for large step lists

### 4. Error Handling
- Validate step count > 0
- Ensure currentStep is within valid range
- Handle empty or null step content gracefully

## Common Patterns

### Wizard/Onboarding Flow
```kotlin
@Composable
fun OnboardingWizard() {
    var currentStep by remember { mutableStateOf(0f) }
    
    KotStep(currentStep = { currentStep }) {
        step(title = "Welcome", onClick = { currentStep = 1f })
        step(title = "Setup", onClick = { currentStep = 2f })
        step(title = "Complete", onClick = { /* finish */ })
    }
}
```

### Progress Tracking
```kotlin
@Composable
fun TaskProgress(tasks: List<Task>) {
    val completedCount = tasks.count { it.isDone }
    val progress = completedCount.toFloat()
    
    KotStep(currentStep = { progress }) {
        tasks.forEach { task ->
            step(
                title = task.name,
                onDone = { task.markDone() }
            )
        }
    }
}
```

### Interactive Steps with Labels
```kotlin
@Composable
fun DetailedSteps() {
    KotStep(currentStep = { 1f }) {
        step(
            title = "Personal Info",
            label = { 
                Column {
                    Text("Enter your details")
                    // Additional UI
                }
            },
            onClick = { /* navigate to form */ }
        )
    }
}
```

### Vertical Step Example
```kotlin
@Composable
fun VerticalStepExample() {
    KotStep(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        currentStep = { 0f },
        style = KotStepStyle(stepLayoutStyle = StepLayoutStyle.Vertical)
    ) {
        step(title = "1")
        step(icon = Icons.Default.Star)
        step(
            content = {
                Image(
                    painter = painterResource(R.drawable.kotlin),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
            },
            label = { Text("Hello world.") },
            isCollapsible = true
        )
        step()
    }
}
```

### Horizontal Step Example
```kotlin
@Composable
fun HorizontalStepExample() {
    KotStep(
        modifier = Modifier.horizontalScroll(rememberScrollState()),
        currentStep = { 0f },
        style = KotStepStyle(stepLayoutStyle = StepLayoutStyle.Horizontal)
    ) {
        step(title = "1")
        step(icon = Icons.Default.Star)
        step(
            content = {
                Image(
                    painter = painterResource(R.drawable.kotlin),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
            },
            label = { Text("Hello world.") },
            isCollapsible = true
        )
        step()
    }
}
```

## Customization Examples

### Basic Customization
```kotlin
val customStyle = KotStepStyle(
    stepLayoutStyle = StepLayoutStyle.Vertical,
    showCheckMarkOnDone = false,
    stepStyle = StepStyles.default().copy(
        onCurrent = StepStyle.defaultCurrent().copy(stepColor = Color.Red)
    ),
    lineStyle = LineStyles.default().copy(
        onCurrent = LineStyle.defaultCurrent().copy(
            progressColor = Color.Red,
            lineType = LineType.Dashed(dashLength = 8.dp, gapLength = 8.dp)
        )
    )
)
```

### Advanced Customization
```kotlin
val advancedStyle = KotStepStyle(
    stepLayoutStyle = StepLayoutStyle.Vertical,
    showCheckMarkOnDone = false,
    ignoreCurrentState = false,
    stepStyle = StepStyles.default().copy(
        onTodo = StepStyle.defaultTodo().copy(
            stepSize = 50.dp,
            stepColor = Color.Gray,
            borderStyle = BorderStyle(width = 2.dp, color = Color.Red)
        ),
        onCurrent = StepStyle.defaultCurrent().copy(
            stepSize = 60.dp,
            stepColor = Color.DarkGray,
            borderStyle = BorderStyle(width = 2.dp, color = Color.Gray)
        ),
        onDone = StepStyle.defaultDone().copy(
            stepSize = 50.dp,
            stepColor = Color.Green,
            borderStyle = BorderStyle(width = 2.dp, color = Color.DarkGray)
        )
    ),
    lineStyle = LineStyles.default().copy(
        onTodo = LineStyle.defaultTodo().copy(
            lineThickness = 10.dp,
            lineLength = 100.dp,
            linePadding = PaddingValues(2.dp),
        ),
        onCurrent = LineStyle.defaultCurrent().copy(
            lineThickness = 4.dp,
            lineLength = 100.dp,
            linePadding = PaddingValues(2.dp),
            lineType = LineType.Dashed(dashLength = 20.dp, gapLength = 10.dp),
            progressType = LineType.Dashed(dashLength = 20.dp, gapLength = 10.dp)
        ),
        onDone = LineStyle.defaultDone().copy(
            lineThickness = 5.dp,
            lineLength = 100.dp,
            linePadding = PaddingValues(2.dp),
            lineType = LineType.Dotted(gapLength = 10.dp),
            progressType = LineType.Dotted(gapLength = 10.dp)
        )
    )
)
```

## Troubleshooting

### Steps Not Displaying
- Ensure `currentStep` is within valid range (-1 to steps.size)
- Check that steps list is not empty
- Verify `@ExperimentalKotStep` opt-in

### Layout Issues
- For horizontal layouts, add `horizontalScroll()` if needed
- For vertical layouts, add `verticalScroll()` if needed
- Check padding and sizing in `KotStepStyle`

### Click Handling
- Ensure `onClick` callbacks are properly defined
- Check for overlapping touch targets
- Verify step indices in click handlers

### Styling Problems
- Use `StepStyles` and `LineStyles` for customization
- Check color contrast for accessibility
- Test on different screen densities

## Migration from V2
- V3 uses DSL instead of style builders
- `currentStep` now accepts Float for smooth progress
- New `label` parameter for additional content
- `isCollapsible` for dynamic step expansion
- Experimental API requires opt-in

## Example Prompts for Agent Usage
* "Implement a 3-step onboarding flow with KotStep V3"
* "Add progress indication to my step component"
* "Customize the appearance of KotStep steps"
* "Handle step clicks and navigation in KotStep"
* "Migrate my V2 KotStep implementation to V3"
* "Create a vertical stepper with custom icons and labels"
* "Set up horizontal progress tracking with dashed lines"
