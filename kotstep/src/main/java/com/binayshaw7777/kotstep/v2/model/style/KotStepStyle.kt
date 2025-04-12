package com.binayshaw7777.kotstep.v2.model.style

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.binayshaw7777.kotstep.v2.model.step.StepLayoutStyle

@Immutable
data class KotStepStyle(
    val stepLayoutStyle: StepLayoutStyle = StepLayoutStyle.VerticalCentered,
    val itemPadding: Dp = 8.dp,
    val showCheckMarkOnDone: Boolean = true,
    val isScrollable: Boolean = false,
    val ignoreCurrentState: Boolean = false,
    val stepStyle: StepStyles = StepStyles.default(),
    val lineStyle: LineStyles = LineStyles.default()
)