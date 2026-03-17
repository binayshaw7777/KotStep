package com.binayshaw7777.kotstep.v3.component.layout

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.binayshaw7777.kotstep.v3.component.steps.VerticalStepItem
import com.binayshaw7777.kotstep.v3.model.step.Step
import com.binayshaw7777.kotstep.v3.model.step.StepState
import com.binayshaw7777.kotstep.v3.model.style.KotStepStyle
import com.binayshaw7777.kotstep.v3.util.ExperimentalKotStep
import kotlinx.collections.immutable.PersistentList

@OptIn(ExperimentalKotStep::class)
@Composable
internal fun VerticalKotStep(
    modifier: Modifier = Modifier,
    currentStep: () -> Float,
    style: KotStepStyle,
    steps: PersistentList<Step>,
    onClick: (Int) -> Unit = {}
) {
    require(steps.isNotEmpty()) { "Steps should not be empty" }
    require(currentStep() in -1f..(steps.size).toFloat()) { "Current step should be between 0 and total steps: ${steps.size} but it was ${currentStep()}" }

    val density = LocalDensity.current
    var maxLeadingLabelWidth by remember { mutableStateOf(0.dp) }
    var maxTrailingLabelWidth by remember { mutableStateOf(0.dp) }

    Column(
        modifier = Modifier.then(modifier),
        horizontalAlignment = Alignment.Start
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

                VerticalStepItem(
                    style = style,
                    stepState = stepState,
                    progress = { progress },
                    stepIndex = index,
                    isLastStep = index == steps.size - 1,
                    step = step,
                    reservedLeadingLabelWidth = maxLeadingLabelWidth,
                    reservedTrailingLabelWidth = maxTrailingLabelWidth,
                    onLeadingLabelMeasured = { size ->
                        val measuredWidth = with(density) { size.width.toDp() }
                        if (measuredWidth > maxLeadingLabelWidth) {
                            maxLeadingLabelWidth = measuredWidth
                        }
                    },
                    onTrailingLabelMeasured = { size ->
                        val measuredWidth = with(density) { size.width.toDp() }
                        if (measuredWidth > maxTrailingLabelWidth) {
                            maxTrailingLabelWidth = measuredWidth
                        }
                    },
                    onClick = { onClick(index) }
                )
            }
        }
    }
}
