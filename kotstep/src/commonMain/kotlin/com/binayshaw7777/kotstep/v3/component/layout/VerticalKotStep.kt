package com.binayshaw7777.kotstep.v3.component.layout

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.binayshaw7777.kotstep.v3.component.steps.VerticalStepItem
import com.binayshaw7777.kotstep.v3.model.step.Step
import com.binayshaw7777.kotstep.v3.model.style.KotStepStyle
import com.binayshaw7777.kotstep.v3.util.ExperimentalKotStep
import kotlinx.collections.immutable.PersistentList

private const val REVERSE_STAGGER_TOTAL_MS = 200L

@OptIn(ExperimentalKotStep::class)
@Composable
internal fun VerticalKotStep(
    modifier: Modifier = Modifier,
    currentStepState: State<Float>,
    style: KotStepStyle,
    steps: PersistentList<Step>,
    onClick: (Int) -> Unit = {}
) {
    require(steps.isNotEmpty()) { "Steps should not be empty" }

    val maxLeadingLabelWidth = remember { mutableStateOf(0.dp) }
    val maxTrailingLabelWidth = remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    val onLeadingLabelMeasured = remember(density) {
        { size: IntSize ->
            val measuredWidth = with(density) { size.width.toDp() }
            if (measuredWidth > maxLeadingLabelWidth.value) {
                maxLeadingLabelWidth.value = measuredWidth
            }
        }
    }
    val onTrailingLabelMeasured = remember(density) {
        { size: IntSize ->
            val measuredWidth = with(density) { size.width.toDp() }
            if (measuredWidth > maxTrailingLabelWidth.value) {
                maxTrailingLabelWidth.value = measuredWidth
            }
        }
    }

    val targetCurrent = currentStepState.value
    val previousTarget = remember { floatArrayOf(targetCurrent) }
    val isBackward = targetCurrent < previousTarget[0]
    val oldFloor = previousTarget[0].toInt().coerceIn(0, steps.size - 1)
    val newFloor = targetCurrent.toInt().coerceIn(0, steps.size - 1)

    fun staggerDelayMs(index: Int): Long {
        if (!isBackward || index <= newFloor || index > oldFloor) return 0L
        val span = (oldFloor - newFloor).coerceAtLeast(1)
        return (oldFloor - index).toLong() * REVERSE_STAGGER_TOTAL_MS / span
    }

    SideEffect {
        previousTarget[0] = targetCurrent
    }

    Column(
        modifier = Modifier.then(modifier),
        horizontalAlignment = Alignment.Start
    ) {
        steps.forEachIndexed { index, _ ->
            key(index) {
                VerticalStepItem(
                    currentStepState = currentStepState,
                    staggerDelayMs = staggerDelayMs(index),
                    step = steps[index],
                    style = style,
                    stepIndex = index,
                    isLastStep = index == steps.size - 1,
                    maxLeadingLabelWidth = maxLeadingLabelWidth,
                    maxTrailingLabelWidth = maxTrailingLabelWidth,
                    onLeadingLabelMeasured = onLeadingLabelMeasured,
                    onTrailingLabelMeasured = onTrailingLabelMeasured,
                    onClick = onClick
                )
            }
        }
    }
}