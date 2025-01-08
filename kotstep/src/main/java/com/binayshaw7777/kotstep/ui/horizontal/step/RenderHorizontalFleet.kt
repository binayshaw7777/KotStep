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
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import com.binayshaw7777.kotstep.components.horizontal.HorizontalFleetStep
import com.binayshaw7777.kotstep.model.StepState
import com.binayshaw7777.kotstep.model.StepStyle

/**
 * Renders a horizontal fleet stepper.
 *
 * @param modifier The modifier to be applied to the stepper.
 * @param totalSteps The total number of steps in the stepper.
 * @param currentStep The current step in the stepper (Treated as start Step).
 * @param stepStyle The style of the steps in the stepper.
 * @param duration The duration of each step in the stepper.
 * @param onStepClick The callback to be invoked when a step is clicked.
 */
@Composable
internal fun RenderHorizontalFleet(
    modifier: Modifier = Modifier,
    totalSteps: Int,
    currentStep: Number,
    stepStyle: StepStyle,
    duration: List<Long>,
    isPlaying: Boolean = true,
    onStepClick: (Int) -> Unit = {},
    onStepComplete: (Int) -> Unit = {}
) {

    require(currentStep.toFloat() in -1f..totalSteps.toFloat()) { "Current step should be between 0 and total steps but it was ${currentStep.toFloat()} and totalStep was: $totalSteps" }
    require(duration.size == totalSteps) { "Duration list should be equal to total steps. Total steps: $totalSteps and duration list size: ${duration.size}" }

    var size by remember { mutableStateOf(IntSize.Zero) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .onSizeChanged { size = it }
            .then(modifier),
        horizontalArrangement = Arrangement.spacedBy(stepStyle.lineStyle.linePaddingStart),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (index in 0 until totalSteps) {
            val stepState = when {
                index < currentStep.toInt() -> StepState.DONE
                index == currentStep.toInt() -> StepState.CURRENT
                else -> StepState.TODO
            }

            HorizontalFleetStep(
                totalSteps = totalSteps,
                index = index,
                currentStep = currentStep.toInt(),
                duration = duration[index],
                stepStyle = stepStyle,
                stepState = stepState,
                size = size,
                isPlaying = isPlaying,
                onClick = { onStepClick(index) },
                onStepComplete = onStepComplete
            )
        }
    }
}