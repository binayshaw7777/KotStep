package com.binayshaw7777.kotstep.v3.component.layout

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.binayshaw7777.kotstep.v3.component.steps.VerticalStepItem
import com.binayshaw7777.kotstep.v3.model.step.Step
import com.binayshaw7777.kotstep.v3.model.step.StepState
import com.binayshaw7777.kotstep.v3.model.style.KotStepStyle
import com.binayshaw7777.kotstep.v3.util.ExperimentalKotStep
import kotlinx.collections.immutable.PersistentList

/**
 * A composable function that displays a vertical step indicator.
 *
 * This function renders a series of steps vertically, visually indicating the progress
 * and state of each step. It supports both scrollable and non-scrollable layouts,
 * adding `.verticalScroll(rememberScrollState())` in the Modifier code
 *
 * @param modifier Modifier for styling and layout adjustments of the step indicator.
 * @param currentStep A lambda function that provides the current step as a float.
 *                    The value should be between -1 and the total number of steps.
 *                    -1 means that no step has been started.
 *                    Values between 0 and `steps.size - 1` represent a step that is currently in progress.
 *                    `steps.size` represents a completed step.
 *                    Fractional parts indicate the progress within the current step (e.g., 0.5 means 50% complete).
 * @param style The style configuration for the step indicator, determining its appearance and behavior.
 * @param steps A list of [Step] objects, each representing a single step in the sequence.
 * @param onClick A lambda function that is invoked when a step is clicked.
 *                It receives the index of the clicked step.
 *
 * @throws IllegalArgumentException If the `steps` list is empty.
 * @throws IllegalArgumentException If the `currentStep` value is outside the valid range of -1 to `steps.size`.
 *
 * @since 3.0.0
 * */
@OptIn(ExperimentalKotStep::class)
@Composable
internal fun VerticalKotStep(
    modifier: Modifier = Modifier,
    currentStep: () -> Float,
    style: KotStepStyle,
    steps: PersistentList<Step>,
    onClick: (Int) -> Unit = {}
) {
    require(steps.isNotEmpty()) { "Steps should not be empty" }
    require(currentStep() in -1f..(steps.size).toFloat()) { "Current step should be between 0 and total steps: ${steps.size} but it was ${currentStep()}" }

    Column(
        modifier = Modifier.then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally
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

                VerticalStepItem(
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