package com.binayshaw7777.kotstep.ui.vertical.step

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.binayshaw7777.kotstep.components.vertical.VerticalNumberedStep
import com.binayshaw7777.kotstep.model.StepState
import com.binayshaw7777.kotstep.model.StepStyle

/**
 * Render the vertical number
 *
 * @param totalSteps The total number of steps
 * @param currentStep The current step
 * @param stepStyle The style of the step
 */
@Composable
fun RenderVerticalNumber(
    modifier: Modifier = Modifier,
    totalSteps: Int,
    currentStep: Int,
    stepStyle: StepStyle
) {

    Log.d("VerticalStepper", "Total Steps: $totalSteps and Current Step: $currentStep")
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

            VerticalNumberedStep(
                stepStyle = stepStyle,
                stepState = stepState,
                stepNumber = i + 1,
                isLastStep = i == totalSteps - 1,
            )
        }
    }
}
