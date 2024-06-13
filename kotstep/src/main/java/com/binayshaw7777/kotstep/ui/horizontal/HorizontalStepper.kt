package com.binayshaw7777.kotstep.ui.horizontal

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import com.binayshaw7777.kotstep.components.HorizontalDashedStep
import com.binayshaw7777.kotstep.components.HorizontalStep
import com.binayshaw7777.kotstep.model.Step
import com.binayshaw7777.kotstep.model.StepComposable
import com.binayshaw7777.kotstep.model.StepState
import com.binayshaw7777.kotstep.model.StepStyle
import com.binayshaw7777.kotstep.model.StepperType

@JvmName("HorizontalStepperWithComposable")
@Composable
fun HorizontalStepper(
    modifier: Modifier = Modifier,
    currentStep: Int,
    stepStyle: StepStyle = StepStyle(),
    stepperType: StepperType = StepperType.SOLID,
    steps: List<StepComposable>,
    totalSteps: Int = steps.size
) {
    Log.d("HorizontalStepper", "Total Steps: $totalSteps and Current Step: $currentStep")
    require(steps.isNotEmpty()) { "Total steps should be greater than 0" }
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
        steps.forEachIndexed { index, step ->
            val isLastStep = index == totalSteps - 1
            val stepState = when {
                index < currentStep -> StepState.DONE
                index == currentStep -> StepState.CURRENT
                else -> StepState.TODO
            }

            when (stepperType) {
                StepperType.SOLID -> {
                    HorizontalStep(
                        stepStyle = stepStyle,
                        step = step,
                        stepState = stepState,
                        isLastStep = isLastStep
                    )

                }

                StepperType.DASHED -> {
                    Log.d("HorizontalStepper", "Row Size: $size and totalSteps: $totalSteps and divided width: ${size.width / totalSteps}")
                    HorizontalDashedStep(
                        stepStyle = stepStyle,
                        stepState = stepState,
                        totalSteps = totalSteps,
                        size = size
                    )
                }
            }
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
                isLastStep = isLastStep,
                stepShape = CircleShape
            )
        }
    }
}