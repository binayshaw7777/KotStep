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
 * @property trailingLabels The labels to be displayed in right side of the steps. The size of this list should be equal to [totalSteps].
 * @param onStepClick A callback that is invoked when a step is clicked.
 */
@Composable
internal fun RenderVerticalIconWithLabel(
    modifier: Modifier,
    totalSteps: Int,
    currentStep: Number,
    stepStyle: StepStyle,
    icons: List<ImageVector>,
    trailingLabels: List<(@Composable () -> Unit)?>,
    onStepClick: (Int) -> Unit = {}
) {

    require(icons.isNotEmpty()) { "Icons should not be empty" }
    require(trailingLabels.any { it != null }) {
        "At least one element in the contents list should be non-null. " +
                "If all elements are null, consider using 'NumberedStepper' instead."
    }
    require(icons.size >= trailingLabels.size) { "Icons should be equal to or greater than labels" }
    require(currentStep.toFloat() in -1f..totalSteps.toFloat()) { "Current step should be between -1 and total steps but it was ${currentStep.toFloat()}" }

    Column(modifier = modifier) {

        trailingLabels.forEachIndexed { index, trailingLabel ->
            val stepState = when {
                stepStyle.ignoreCurrentState -> {
                    if (currentStep.toFloat() >= index.toFloat()) StepState.DONE else StepState.TODO
                }
                else -> {
                    when {
                        index < currentStep.toInt() -> StepState.DONE
                        index == currentStep.toInt() -> StepState.CURRENT
                        else -> StepState.TODO
                    }
                }
            }

            val lineProgress = if (index == currentStep.toInt()) {
                currentStep.toFloat() - currentStep.toInt()
            } else if (index < currentStep.toInt()) {
                1f
            } else {
                0f
            }

            VerticalIconWithLabelStep(
                stepStyle = stepStyle,
                stepState = stepState,
                stepIcon = icons[index],
                trailingLabel = trailingLabel,
                isLastStep = index == trailingLabels.size - 1,
                lineProgress = lineProgress
            ) { onStepClick(index) }
        }
    }
}