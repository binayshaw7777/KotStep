package com.binayshaw7777.kotstep.ui.vertical.step

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.binayshaw7777.kotstep.components.vertical.VerticalNumberedStep
import com.binayshaw7777.kotstep.model.StepState
import com.binayshaw7777.kotstep.model.StepStyle

@Composable
fun RenderVerticalLabel(
    modifier: Modifier,
    totalSteps: Int,
    currentStep: Int,
    labels: List<(@Composable () -> Unit)?>,
    stepStyle: StepStyle
) {

    require(labels.any { it != null }) {
        "At least one element in the contents list should be non-null. " +
                "If all elements are null, consider using 'NumberedStepper' instead."
    }

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

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {

                VerticalNumberedStep(
                    stepStyle = stepStyle,
                    stepState = stepState,
                    stepNumber = i + 1,
                    isLastStep = i == totalSteps - 1,
                )

                labels[i]?.let { label ->
                    Spacer(modifier = Modifier.width(16.dp))

                    Box(modifier = Modifier.weight(1f)) {
                        label()
                    }
                }
            }
        }
    }
}