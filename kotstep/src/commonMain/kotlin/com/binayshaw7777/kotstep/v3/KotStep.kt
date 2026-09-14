package com.binayshaw7777.kotstep.v3

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import com.binayshaw7777.kotstep.v3.component.layout.HorizontalKotStep
import com.binayshaw7777.kotstep.v3.component.layout.VerticalKotStep
import com.binayshaw7777.kotstep.v3.model.KotStepScope
import com.binayshaw7777.kotstep.v3.model.step.StepLayoutStyle
import com.binayshaw7777.kotstep.v3.model.style.KotStepStyle
import com.binayshaw7777.kotstep.v3.util.ExperimentalKotStep

/**
 * A composable function that renders a step-based UI, either horizontally or vertically.
 *
 * `KotStep` allows you to define a series of steps, each with its own content and optional click action.
 * The steps can be displayed in a vertical or horizontal layout, with optional centering.
 * The current step is indicated visually based on the provided [currentStep] value.
 *
 * @param modifier The modifier to apply to the overall layout of the steps.
 * @param currentStep A lambda that provides the current step as a Float. This value determines which
 *                    step is visually highlighted as active. Values should typically be within the range
 *                    of `0.0` to `steps.size - 1`. Intermediate values represent progress between steps.
 *                    For instance, if you have 3 steps, 0.0 is the first step, 1.0 is the second, 2.0 is the third.
 *                    And 0.5 is half way between the first and second step.
 * @param style The styling to apply to the steps. See [KotStepStyle] for available customization options.
 * @param content A lambda that provides a [KotStepScope] for defining the steps. Within this scope,
 *                you can use functions like `step()` to add individual steps to the layout.
 *
 * @since 3.0.0
 **/
@ExperimentalKotStep
@Composable
fun KotStep(
    modifier: Modifier = Modifier,
    currentStep: () -> Float,
    style: KotStepStyle = KotStepStyle(),
    content: KotStepScope.() -> Unit
) {
    val latestCurrentStep = rememberUpdatedState(currentStep)
    val currentStepState: State<Float> = remember(currentStep) { derivedStateOf { latestCurrentStep.value() } }
    val steps = KotStepScope().apply(content).buildSteps()

    val currentSteps = rememberUpdatedState(steps)
    val onStepClick = remember {
        { index: Int ->
            currentSteps.value.getOrNull(index)?.onClick?.invoke()
            Unit
        }
    }

    when (style.stepLayoutStyle) {
        StepLayoutStyle.Vertical -> {
            VerticalKotStep(
                modifier = modifier,
                currentStepState = currentStepState,
                style = style,
                steps = steps,
                onClick = onStepClick
            )
        }

        StepLayoutStyle.Horizontal -> {
            HorizontalKotStep(
                modifier = modifier,
                currentStepState = currentStepState,
                style = style,
                steps = steps,
                onClick = onStepClick
            )
        }
    }
}