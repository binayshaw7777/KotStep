package com.binayshaw7777.kotstep.ui.vertical.step

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.binayshaw7777.kotstep.components.vertical.VerticalTabStep
import com.binayshaw7777.kotstep.model.StepState
import com.binayshaw7777.kotstep.model.StepStyle

/**
 * Renders a vertical tab stepper.
 *
 * @param modifier The modifier to be applied to the stepper.
 * @param totalSteps The total number of steps in the stepper.
 * @param currentStep The current step in the stepper.
 * @param stepStyle The style of the steps in the stepper.
 * @param onStepClick A callback that is invoked when a step is clicked.
 */
@Composable
internal fun RenderVerticalTab(
    modifier: Modifier = Modifier,
    totalSteps: Int,
    currentStep: Number,
    stepStyle: StepStyle = StepStyle(),
    onStepClick: (Int) -> Unit = {}
) {

    require(currentStep.toFloat() in -1f..totalSteps.toFloat()) { "Current step should be between 0 and total steps but it was ${currentStep.toFloat()}" }

    Column(
        modifier = Modifier.then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        for (index in 0 until totalSteps) {
            val stepState = when {
                index < currentStep.toInt() -> StepState.DONE
                index == currentStep.toInt() -> StepState.CURRENT
                else -> StepState.TODO
            }

            val lineProgress = if (index == currentStep.toInt()) {
                currentStep.toFloat() - currentStep.toInt()
            } else if (index < currentStep.toInt()) {
                1f
            } else {
                0f
            }

            VerticalTabStep(
                stepStyle = stepStyle,
                stepState = stepState,
                isLastStep = index == totalSteps - 1,
                lineProgress = lineProgress,
            ) { onStepClick(index) }
        }
    }
}