package com.binayshaw7777.kotstep.ui.vertical.step

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.binayshaw7777.kotstep.components.vertical.VerticalNumberedStep
import com.binayshaw7777.kotstep.model.StepState
import com.binayshaw7777.kotstep.model.StepStyle

/**
 * Renders a vertical numbered stepper.
 *
 * @param modifier The modifier to be applied to the stepper.
 * @param totalSteps The total number of steps in the stepper.
 * @param currentStep The current step in the stepper.
 * @param stepStyle The style of the steps in the stepper.
 * @param onStepClick A callback that is invoked when a step is clicked.
 */
@Composable
internal fun RenderVerticalNumber(
    modifier: Modifier = Modifier,
    totalSteps: Int,
    currentStep: Number,
    stepStyle: StepStyle,
    onStepClick: (Int) -> Unit = {}
) {

    require(currentStep.toFloat() in -1f..totalSteps.toFloat()) { "Current step should be between -1.0 and total steps" }

    Column(
        modifier = Modifier.then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        for (i in 0 until totalSteps) {
            val stepState = when {
                i < currentStep.toInt() -> StepState.DONE
                i == currentStep.toInt() -> StepState.CURRENT
                else -> StepState.TODO
            }

            val lineProgress = if (i == currentStep.toInt()) {
                currentStep.toFloat() - currentStep.toInt()
            } else if (i < currentStep.toInt()) {
                1f
            } else {
                0f
            }

            VerticalNumberedStep(
                stepStyle = stepStyle,
                stepState = stepState,
                stepNumber = i + 1,
                isLastStep = i == totalSteps - 1,
                lineProgress = lineProgress
            ) { onStepClick(i) }
        }
    }
}
