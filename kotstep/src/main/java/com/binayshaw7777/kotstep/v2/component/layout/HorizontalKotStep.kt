package com.binayshaw7777.kotstep.v2.component.layout

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.binayshaw7777.kotstep.v2.component.steps.HorizontalStepItem
import com.binayshaw7777.kotstep.v2.model.step.Step
import com.binayshaw7777.kotstep.v2.model.step.StepState
import com.binayshaw7777.kotstep.v2.model.style.KotStepStyle
import kotlinx.collections.immutable.PersistentList

/**
 * A composable function that displays a horizontal step indicator with customizable styling and behavior.
 *
 * This function renders a sequence of steps in a horizontal layout, visually representing the progress
 * through a multi-step process. It supports both scrollable and non-scrollable modes, defined by
 * adding `.horizontalScroll(rememberScrollState())` in the Modifier code
 *
 * @param modifier Modifier to be applied to the container of the steps. This allows for customization
 *        of the layout and appearance of the entire step indicator.
 * @param currentStep A lambda that returns the current step as a Float. This determines the current
 *        progress in the step sequence.
 *        - Values from 0 to `steps.size` indicate the current step being in progress.
 *        - Integer values represent completed steps.
 *        - Fractional values (e.g., 1.5) represent progress between two steps (1 and 2).
 *        - -1 will mean that the indicator will show all steps as not started.
 * @param style The style configuration for the steps, defining colors, sizes, and other visual
 *        attributes. This [KotStepStyle] object controls the look and feel of the step indicator.
 * @param steps A list of [Step] objects, each representing a single step in the sequence. Each step
 *        can have its own label, icon, and other properties.
 * @param onClick A lambda that is invoked when a step is clicked, providing the index of the clicked
 *        step. This allows for interactive behavior when a user interacts with the steps.
 *
 * @throws IllegalArgumentException If the `steps` list is empty.
 * @throws IllegalArgumentException If the `currentStep` value is outside the valid range of -1 to `steps.size`.
 *
 * @since 2.4.0
 *
 * */
@Composable
internal fun HorizontalKotStep(
    modifier: Modifier = Modifier,
    currentStep: () -> Float,
    style: KotStepStyle,
    steps: PersistentList<Step>,
    onClick: (Int) -> Unit = {}
) {
    require(steps.isNotEmpty()) { "Steps should not be empty" }
    require(currentStep() in -1f..(steps.size).toFloat()) { "Current step should be between 0 and total steps: ${steps.size} but it was ${currentStep()}" }

    Row(
        modifier = Modifier.fillMaxWidth().then(modifier),
        verticalAlignment = Alignment.Top
    ) {
        steps.forEachIndexed { index, step ->
            key(index) {
                val progress = when {
                    index == currentStep().toInt() -> currentStep() - currentStep().toInt()
                    index < currentStep().toInt() -> 1f
                    else -> 0f
                }

                val stepState = if (style.ignoreCurrentState) {
                    if (currentStep() >= index.toFloat()) StepState.Done else StepState.Todo
                } else {
                    when {
                        index < currentStep().toInt() -> StepState.Done
                        index == currentStep().toInt() -> StepState.Current
                        else -> StepState.Todo
                    }
                }

                HorizontalStepItem(
                    style = style,
                    stepState = stepState,
                    progress = { progress },
                    isLastStep = index == steps.size - 1,
                    step = step,
                    onClick = { onClick(index) }
                )
            }
        }
    }
}
