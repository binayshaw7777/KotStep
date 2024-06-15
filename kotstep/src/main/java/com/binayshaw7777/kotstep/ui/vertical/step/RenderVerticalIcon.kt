package com.binayshaw7777.kotstep.ui.vertical.step

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.binayshaw7777.kotstep.components.VerticalIconStep
import com.binayshaw7777.kotstep.model.StepState
import com.binayshaw7777.kotstep.model.StepStyle

/**
 * Render the vertical stepper with icons
 *
 * @param totalSteps The total number of steps
 * @param currentStep The current step
 * @param icons The icons for the steps
 * @param stepStyle The style of the step
 */
@Composable
fun RenderVerticalIcon(
    modifier: Modifier = Modifier,
    totalSteps: Int,
    currentStep: Int,
    stepStyle: StepStyle = StepStyle(),
    icons: List<ImageVector>
) {
    Log.d("VerticalStepper", "Total Steps: $totalSteps and Current Step: $currentStep")
    require(icons.isNotEmpty()) { "Icons should not be empty" }
    require(currentStep in -1..totalSteps) { "Current step should be between 0 and total steps" }

    Column(
        modifier = Modifier.then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        for (i in 0 until totalSteps) {
            val stepState = when {
                i < currentStep -> StepState.DONE
                i == currentStep -> StepState.CURRENT
                else -> StepState.TODO
            }

            Log.d("VerticalStepper", "Step $i State: $stepState and totalSteps: ${icons.size}")
            VerticalIconStep(
                stepStyle = stepStyle,
                stepState = stepState,
                stepIcon = icons[i],
                isLastStep = i == totalSteps - 1,
            )
        }
    }
}