package com.binayshaw7777.kotstep.v3.component.layout

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.binayshaw7777.kotstep.v3.component.steps.HorizontalStepItem
import com.binayshaw7777.kotstep.v3.model.step.Step
import com.binayshaw7777.kotstep.v3.model.style.KotStepStyle
import com.binayshaw7777.kotstep.v3.util.ExperimentalKotStep
import kotlinx.collections.immutable.PersistentList

@OptIn(ExperimentalKotStep::class)
@Composable
internal fun HorizontalKotStep(
    modifier: Modifier = Modifier,
    currentStep: () -> Float,
    style: KotStepStyle,
    steps: PersistentList<Step>,
    onClick: (Int) -> Unit = {}
) {
    require(steps.isNotEmpty()) { "Steps should not be empty" }

    val density = LocalDensity.current
    var maxLeadingLabelHeight by remember { mutableStateOf(0.dp) }
    var maxTrailingLabelHeight by remember { mutableStateOf(0.dp) }

    Row(
        modifier = Modifier.fillMaxWidth().then(modifier),
        verticalAlignment = Alignment.Top
    ) {
        steps.forEachIndexed { index, step ->
            key(index) {
                HorizontalStepItem(
                    currentStep = currentStep,
                    step = step,
                    style = style,
                    stepIndex = index,
                    isLastStep = index == steps.size - 1,
                    reservedLeadingLabelHeight = maxLeadingLabelHeight,
                    reservedTrailingLabelHeight = maxTrailingLabelHeight,
                    onLeadingLabelMeasured = { size ->
                        val measuredHeight = with(density) { size.height.toDp() }
                        if (measuredHeight > maxLeadingLabelHeight) {
                            maxLeadingLabelHeight = measuredHeight
                        }
                    },
                    onTrailingLabelMeasured = { size ->
                        val measuredHeight = with(density) { size.height.toDp() }
                        if (measuredHeight > maxTrailingLabelHeight) {
                            maxTrailingLabelHeight = measuredHeight
                        }
                    },
                    onClick = { onClick(index) }
                )
            }
        }
    }
}
