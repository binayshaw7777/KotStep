package com.binayshaw7777.kotstep.ui.vertical

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.binayshaw7777.kotstep.components.VerticalStep
import com.binayshaw7777.kotstep.model.Step
import com.binayshaw7777.kotstep.model.StepState
import com.binayshaw7777.kotstep.model.StepStyle

@JvmName("VerticalStepperWithStep")
@Composable
fun VerticalStepper(
    modifier: Modifier = Modifier,
    currentStep: Int,
    stepStyle: StepStyle = StepStyle(),
    steps: List<Step>,
    totalSteps: Int = steps.size
) {
    require(steps.isNotEmpty()) { "Total steps should be greater than 0" }
    require(currentStep in 0..totalSteps) { "Current step should be between 0 and total steps" }

    Column(
        modifier = Modifier.then(modifier),
        horizontalAlignment = Alignment.Start
    ) {
        steps.forEachIndexed { index, step ->
            val isLastStep = index == totalSteps - 1
            val stepState = when {
                index < currentStep -> StepState.DONE
                index == currentStep -> StepState.CURRENT
                else -> StepState.TODO
            }

            VerticalStep(
                stepStyle = stepStyle,
                step = step,
                stepState = stepState,
                isLastStep = isLastStep
            )
        }
    }
}