package com.binayshaw7777.kotstep.ui.vertical.step

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.binayshaw7777.kotstep.components.vertical.VerticalIconWithLabelStep
import com.binayshaw7777.kotstep.model.StepState
import com.binayshaw7777.kotstep.model.StepStyle

/**
 * Renders a vertical icon stepper with labels.
 *
 * @param modifier The modifier to be applied to the stepper.
 * @param totalSteps The total number of steps in the stepper.
 * @param currentStep The current step in the stepper.
 * @param stepStyle The style of the steps in the stepper.
 * @param icons The icons to be displayed in the steps.
 * @param labels The labels to be displayed in the steps.
 * @param onStepClick A callback that is invoked when a step is clicked.
 */
@Composable
internal fun RenderVerticalIconWithLabel(
    modifier: Modifier,
    totalSteps: Int,
    currentStep: Int,
    stepStyle: StepStyle,
    icons: List<ImageVector>,
    labels: List<(@Composable () -> Unit)?>,
    onStepClick: (Int) -> Unit = {}
) {

    require(icons.isNotEmpty()) { "Icons should not be empty" }
    require(labels.any { it != null }) {
        "At least one element in the contents list should be non-null. " +
                "If all elements are null, consider using 'NumberedStepper' instead."
    }
    require(icons.size >= labels.size) { "Icons should be equal to or greater than labels" }
    require(currentStep in -1..totalSteps) { "Current step should be between -1 and total steps" }

    Column(modifier = modifier) {

        labels.forEachIndexed { index, label ->
            val stepState = when {
                index < currentStep -> StepState.DONE
                index == currentStep -> StepState.CURRENT
                else -> StepState.TODO
            }

            VerticalIconWithLabelStep(
                stepStyle = stepStyle,
                stepState = stepState,
                stepIcon = icons[index],
                label = label,
                isLastStep = index == labels.size - 1
            ) { onStepClick(index) }
        }
    }
}