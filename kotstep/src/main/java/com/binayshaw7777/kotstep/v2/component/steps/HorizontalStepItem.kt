package com.binayshaw7777.kotstep.v2.component.steps

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.binayshaw7777.kotstep.v2.model.step.Step
import com.binayshaw7777.kotstep.v2.model.step.StepState
import com.binayshaw7777.kotstep.v2.model.style.KotStepStyle

@Composable
fun HorizontalStepItem(
    modifier: Modifier = Modifier,
    progress: () -> Float,
    step: Step,
    style: KotStepStyle,
    stepState: StepState,
    isLastStep: Boolean,
    onClick: () -> Unit = {}
) {

}