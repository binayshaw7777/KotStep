package com.binayshaw7777.kotstep.v2.component.layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.binayshaw7777.kotstep.v2.component.steps.VerticalStepItem
import com.binayshaw7777.kotstep.v2.model.step.Step
import com.binayshaw7777.kotstep.v2.model.step.StepState
import com.binayshaw7777.kotstep.v2.model.style.KotStepStyle

@Composable
fun VerticalKotStep(
    modifier: Modifier = Modifier,
    currentStep: () -> Float,
    style: KotStepStyle,
    steps: List<Step>,
    onClick: (Int) -> Unit = {}
) {
    require(steps.isNotEmpty()) { "Steps should not be empty" }
    require(currentStep() in -1f..(steps.size).toFloat()) { "Current step should be between 0 and total steps: ${steps.size} but it was ${currentStep()}" }

    if (style.isScrollable) {
        LazyColumn(
            modifier
                .fillMaxHeight()
                .then(modifier)
        ) {
            itemsIndexed(steps, key = { index, _ -> index }) { index, step ->

                val progress = if (index == currentStep().toInt()) {
                    currentStep() - currentStep().toInt()
                } else if (index < currentStep().toInt()) {
                    1f
                } else {
                    0f
                }

                val stepState = when {
                    style.ignoreCurrentState -> {
                        if (currentStep() >= index.toFloat()) StepState.Done else StepState.Todo
                    }

                    else -> {
                        when {
                            index < currentStep().toInt() -> StepState.Done
                            index == currentStep().toInt() -> StepState.Current
                            else -> StepState.Todo
                        }
                    }
                }

                VerticalStepItem(
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
        Column(
            modifier = Modifier.then(modifier),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            for (index in steps.indices) {
                // TODO: Key(...)
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

                VerticalStepItem(
                    style = style,
                    stepState = stepState,
                    progress = { progress },
                    isLastStep = index == steps.size - 1,
                    step = steps[index],
                    onClick = { onClick(index) }
                )
            }
        }
    }
}