package com.binayshaw7777.kotstep.ui.horizontal.step

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import com.binayshaw7777.kotstep.components.horizontal.HorizontalIconStep
import com.binayshaw7777.kotstep.model.StepState
import com.binayshaw7777.kotstep.model.StepStyle

/**
 * Renders a horizontal icon stepper.
 *
 * @param modifier The modifier to be applied to the stepper.
 * @param totalSteps The total number of steps in the stepper.
 * @param currentStep The current step in the stepper.
 * @param stepStyle The style of the steps in the stepper.
 * @param icons The icons to be displayed in the steps.
 * @param onStepClick A callback that is invoked when a step is clicked.
 */
@Composable
internal fun RenderHorizontalIcon(
    modifier: Modifier = Modifier,
    totalSteps: Int,
    currentStep: Number,
    stepStyle: StepStyle = StepStyle(),
    icons: List<ImageVector>,
    onStepClick: (Int) -> Unit = {}
) {

    require(icons.isNotEmpty()) { "Icons should not be empty" }
    require(currentStep in -1..totalSteps) { "Current step should be between 0 and total steps" }

    var size by remember { mutableStateOf(IntSize.Zero) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .onSizeChanged { size = it }
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        for (i in 0 until totalSteps) {
            val stepState = when {
                i < currentStep.toInt() -> StepState.DONE
                i == currentStep -> StepState.CURRENT
                else -> StepState.TODO
            }

            HorizontalIconStep(
                stepStyle = stepStyle,
                stepState = stepState,
                totalSteps = totalSteps,
                stepIcon = icons[i],
                isLastStep = i == totalSteps - 1,
                size = size
            ) { onStepClick(i) }
        }
    }
}