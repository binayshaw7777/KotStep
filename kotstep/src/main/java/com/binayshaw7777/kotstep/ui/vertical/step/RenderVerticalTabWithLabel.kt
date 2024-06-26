package com.binayshaw7777.kotstep.ui.vertical.step

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.binayshaw7777.kotstep.components.vertical.VerticalTabWithLabelStep
import com.binayshaw7777.kotstep.model.StepState
import com.binayshaw7777.kotstep.model.StepStyle

@Composable
internal fun RenderVerticalTabWithLabel(
    modifier: Modifier = Modifier,
    totalSteps: Int,
    currentStep: Int,
    stepStyle: StepStyle,
    labels: List<(@Composable () -> Unit)?>
) {
    require(labels.any { it != null }) {
        "At least one element in the contents list should be non-null. " +
                "If all elements are null, consider using 'NumberedStepper' instead."
    }

    require(currentStep in -1..totalSteps) { "Current step should be between -1 and total steps" }

    Column(modifier = modifier) {

        labels.forEachIndexed { index, label ->
            val stepState = when {
                index < currentStep -> StepState.DONE
                index == currentStep -> StepState.CURRENT
                else -> StepState.TODO
            }

            VerticalTabWithLabelStep(
                stepStyle = stepStyle,
                stepState = stepState,
                label = label,
                isLastStep = index == labels.size - 1
            )
        }
    }
}