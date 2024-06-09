package com.binayshaw7777.kotstep.ui.horizontal

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.binayshaw7777.kotstep.components.HorizontalStep
import com.binayshaw7777.kotstep.model.Step
import com.binayshaw7777.kotstep.model.StepComposable
import com.binayshaw7777.kotstep.model.StepState
import com.binayshaw7777.kotstep.model.StepStyle

@JvmName("HorizontalStepperWithComposable")
@Composable
fun HorizontalStepper(
    modifier: Modifier = Modifier,
    currentStep: Int,
    stepStyle: StepStyle = StepStyle(),
    steps: List<StepComposable>
) {
    require(steps.isNotEmpty()) { "Total steps should be greater than 0" }
    require(currentStep in 0..steps.size) { "Current step should be between 0 and total steps" }

    Row(
        modifier = Modifier.then(modifier),
        verticalAlignment = Alignment.CenterVertically
    ) {
        steps.forEachIndexed { index, step ->
            val isLastStep = index == steps.size - 1
            val stepState = when {
                index < currentStep -> StepState.DONE
                index == currentStep -> StepState.CURRENT
                else -> StepState.TODO
            }

            HorizontalStep(
                stepStyle = stepStyle,
                step = step,
                stepState = stepState,
                isLastStep = isLastStep
            )
        }
    }
}


@JvmName("HorizontalStepperWithStep")
@Composable
fun HorizontalStepper(
    modifier: Modifier = Modifier,
    currentStep: Int,
    stepStyle: StepStyle = StepStyle(),
    steps: List<Step>
) {
    require(steps.isNotEmpty()) { "Total steps should be greater than 0" }
    require(currentStep in 0..steps.size) { "Current step should be between 0 and total steps" }

    Row(
        modifier = Modifier.then(modifier),
        verticalAlignment = Alignment.CenterVertically
    ) {
        steps.forEachIndexed { index, step ->
            val isLastStep = index == steps.size - 1
            val stepState = when {
                index < currentStep -> StepState.DONE
                index == currentStep -> StepState.CURRENT
                else -> StepState.TODO
            }

            HorizontalStep(
                stepStyle = stepStyle,
                step = step,
                stepState = stepState,
                isLastStep = isLastStep
            )
        }
    }
}