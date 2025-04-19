package com.binayshaw7777.kotstep.v2.component.layout

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.binayshaw7777.kotstep.v2.component.steps.HorizontalStepItem
import com.binayshaw7777.kotstep.v2.model.step.Step
import com.binayshaw7777.kotstep.v2.model.step.StepState
import com.binayshaw7777.kotstep.v2.model.style.KotStepStyle

@Composable
fun HorizontalKotstep(
    modifier: Modifier = Modifier,
    currentStep: () -> Float,
    style: KotStepStyle,
    steps: List<Step>,
    onClick: (Int) -> Unit = {}
) {
    require(steps.isNotEmpty()) { "Steps should not be empty" }
    require(currentStep() in -1f..(steps.size).toFloat()) { "Current step should be between 0 and total steps: ${steps.size} but it was ${currentStep()}" }

    if (style.isScrollable) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .then(modifier)
        ) {
            itemsIndexed(steps, key = { index, _ -> index }) { index, step ->

                val progress = when {
                    index == currentStep().toInt() -> currentStep() - currentStep().toInt()
                    index < currentStep().toInt() -> 1f
                    else -> 0f
                }

                val stepState = if (style.ignoreCurrentState) {
                    if (currentStep() >= index.toFloat()) StepState.Done else StepState.Todo
                } else {
                    when {
                        index < currentStep().toInt() -> StepState.Done
                        index == currentStep().toInt() -> StepState.Current
                        else -> StepState.Todo
                    }
                }

                HorizontalStepItem(
                    style = style,
                    stepState = stepState,
                    progress = { progress },
                    isLastStep = index == steps.size - 1,
                    step = step,
                    onClick = { onClick(index) }
                )
            }
        }
    } else {
        Row(
            modifier = Modifier.fillMaxWidth().then(modifier),
            verticalAlignment = Alignment.Top
        ) {
            steps.forEachIndexed { index, step ->
                key(index) {
                    val progress = when {
                        index == currentStep().toInt() -> currentStep() - currentStep().toInt()
                        index < currentStep().toInt() -> 1f
                        else -> 0f
                    }

                    val stepState = if (style.ignoreCurrentState) {
                        if (currentStep() >= index.toFloat()) StepState.Done else StepState.Todo
                    } else {
                        when {
                            index < currentStep().toInt() -> StepState.Done
                            index == currentStep().toInt() -> StepState.Current
                            else -> StepState.Todo
                        }
                    }

                    HorizontalStepItem(
                        style = style,
                        stepState = stepState,
                        progress = { progress },
                        isLastStep = index == steps.size - 1,
                        step = step,
                        onClick = { onClick(index) }
                    )
                }
            }
        }
    }
}