package com.binayshaw7777.kotstep.ui.vertical.step

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.binayshaw7777.kotstep.components.vertical.VerticalNumberWithLabelStep
import com.binayshaw7777.kotstep.model.StepState
import com.binayshaw7777.kotstep.model.StepStyle

/**
 * Renders a vertical numbered stepper with labels.
 *
 * @param modifier The modifier to be applied to the stepper.
 * @param totalSteps The total number of steps in the stepper.
 * @param currentStep The current step in the stepper.
 * @param stepStyle The style of the steps in the stepper.
 * @param labels The labels to be displayed in the steps.
 * @param onStepClick A callback that is invoked when a step is clicked.
 */
@Composable
internal fun RenderVerticalNumberWithLabel(
    modifier: Modifier,
    totalSteps: Int,
    currentStep: Int,
    stepStyle: StepStyle,
    labels: List<(@Composable () -> Unit)?>,
    onStepClick: (Int) -> Unit = {}
) {

    require(labels.any { it != null }) {
        "At least one element in the contents list should be non-null. " +
                "If all elements are null, consider using 'NumberedStepper' instead."
    }

    require(currentStep in -1..totalSteps) { "Current step should be between -1 and total steps" }

    Column(modifier = modifier) {

        labels.forEachIndexed { index, label ->
            val stepState = when {
                index < currentStep -> StepState.DONE
                index == currentStep -> StepState.CURRENT
                else -> StepState.TODO
            }

            VerticalNumberWithLabelStep(
                stepStyle = stepStyle,
                stepState = stepState,
                stepNumber = index + 1,
                label = label,
                isLastStep = index == labels.size - 1
            ) { onStepClick(index) }
        }
    }
}