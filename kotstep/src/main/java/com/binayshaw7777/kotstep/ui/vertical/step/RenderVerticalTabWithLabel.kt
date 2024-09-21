package com.binayshaw7777.kotstep.ui.vertical.step

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.binayshaw7777.kotstep.components.vertical.VerticalTabWithLabelStep
import com.binayshaw7777.kotstep.model.StepState
import com.binayshaw7777.kotstep.model.StepStyle

/**
 * A composable function that renders a vertical stepper with labels.
 *
 * @property modifier A [Modifier] to be applied to the stepper. Defaults to [Modifier].
 * @property totalSteps The total number of steps in the stepper.
 * @property currentStep The current step in the stepper. This should be between -1 and total steps.
 * @property stepStyle The style properties for the steps in the stepper.
 * @property trailingLabels The labels to be displayed in right side of the steps. The size of this list should be equal to [totalSteps].
 * @property onStepClick A callback that is invoked when a step is clicked.
 */
@Composable
internal fun RenderVerticalTabWithLabel(
    modifier: Modifier = Modifier,
    totalSteps: Int,
    currentStep: Number,
    stepStyle: StepStyle,
    trailingLabels: List<(@Composable () -> Unit)?>,
    onStepClick: (Int) -> Unit = {}
) {
    require(trailingLabels.any { it != null }) {
        "At least one element in the contents list should be non-null. " +
                "If all elements are null, consider using 'NumberedStepper' instead."
    }

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

            VerticalTabWithLabelStep(
                stepStyle = stepStyle,
                stepState = stepState,
                trailingLabel = trailingLabel,
                isLastStep = index == trailingLabels.size - 1,
                lineProgress = lineProgress
            ) { onStepClick(index) }
        }
    }
}