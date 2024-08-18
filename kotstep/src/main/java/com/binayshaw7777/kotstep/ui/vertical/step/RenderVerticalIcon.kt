package com.binayshaw7777.kotstep.ui.vertical.step

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.binayshaw7777.kotstep.components.vertical.VerticalIconStep
import com.binayshaw7777.kotstep.model.StepState
import com.binayshaw7777.kotstep.model.StepStyle

/**
 * Renders a vertical icon stepper.
 *
 * @param modifier The modifier to be applied to the stepper.
 * @param totalSteps The total number of steps in the stepper.
 * @param currentStep The current step in the stepper.
 * @param stepStyle The style of the steps in the stepper.
 * @param icons The icons to be displayed in the steps.
 * @param onStepClick A callback that is invoked when a step is clicked.
 */
@Composable
internal fun RenderVerticalIcon(
    modifier: Modifier = Modifier,
    totalSteps: Int,
    currentStep: Number,
    stepStyle: StepStyle = StepStyle(),
    icons: List<ImageVector>,
    onStepClick: (Int) -> Unit = {}
) {
    require(icons.isNotEmpty()) { "Icons should not be empty" }
    require(currentStep.toFloat() in -1f..totalSteps.toFloat()) { "Current step should be between 0 and total steps" }

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

            VerticalIconStep(
                stepStyle = stepStyle,
                stepState = stepState,
                stepIcon = icons[i],
                isLastStep = i == totalSteps - 1,
                lineProgress = lineProgress,
                onClick = { onStepClick(i) }
            )
        }
    }
}