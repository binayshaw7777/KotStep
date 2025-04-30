package com.binayshaw7777.kotstep.v2

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.binayshaw7777.kotstep.v2.component.layout.HorizontalKotStep
import com.binayshaw7777.kotstep.v2.component.layout.VerticalKotStep
import com.binayshaw7777.kotstep.v2.model.KotStepScope
import com.binayshaw7777.kotstep.v2.model.step.StepLayoutStyle
import com.binayshaw7777.kotstep.v2.model.style.KotStepStyle
import com.binayshaw7777.kotstep.v2.util.ExperimentalKotStep

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
 * @sample com.binayshaw7777.kotstep.v2.samples.KotStepHorizontalExample
 *
 * @sample com.binayshaw7777.kotstep.v2.samples.KotStepVerticalExample
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
    val steps = KotStepScope().apply(content).buildSteps()

    when (style.stepLayoutStyle) {
        StepLayoutStyle.Vertical -> {
            VerticalKotStep(
                modifier = modifier,
                currentStep = currentStep,
                style = style,
                steps = steps,
                onClick = { index ->
                    steps[index].onClick?.invoke()
                }
            )
        }

        StepLayoutStyle.Horizontal -> {
            HorizontalKotStep(
                modifier = modifier,
                currentStep = currentStep,
                style = style,
                steps = steps,
                onClick = { index ->
                    steps[index].onClick?.invoke()
                }
            )
        }
    }
}