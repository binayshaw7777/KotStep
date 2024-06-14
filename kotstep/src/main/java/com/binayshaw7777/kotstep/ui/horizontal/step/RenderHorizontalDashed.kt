package com.binayshaw7777.kotstep.ui.horizontal.step

import android.util.Log
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import com.binayshaw7777.kotstep.components.HorizontalDashedStep
import com.binayshaw7777.kotstep.model.StepState
import com.binayshaw7777.kotstep.model.StepStyle

@Composable
fun RenderHorizontalDashed(
    modifier: Modifier = Modifier,
    totalSteps: Int,
    currentStep: Int,
    stepStyle: StepStyle = StepStyle()
) {

    Log.d("HorizontalStepper", "Total Steps: $totalSteps and Current Step: $currentStep")
    require(currentStep in -1..totalSteps) { "Current step should be between 0 and total steps" }

    var size by remember { mutableStateOf(IntSize.Zero) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .onSizeChanged {
                size = it
            }
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically
    ) {

        for (i in 0 until totalSteps) {
            val stepState = when {
                i < currentStep -> StepState.DONE
                i == currentStep -> StepState.CURRENT
                else -> StepState.TODO
            }
            HorizontalDashedStep(
                stepStyle = stepStyle,
                stepState = stepState,
                totalSteps = totalSteps,
                size = size
            )
        }
    }
}