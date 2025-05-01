package com.binayshaw7777.kotstep.v3.util

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import com.binayshaw7777.kotstep.v3.model.step.StepLayoutStyle
import com.binayshaw7777.kotstep.v3.model.style.BorderStyle
import com.binayshaw7777.kotstep.v3.model.style.KotStepStyle
import com.binayshaw7777.kotstep.v3.model.style.LineStyle
import com.binayshaw7777.kotstep.v3.model.style.LineStyles
import com.binayshaw7777.kotstep.v3.model.style.LineType
import com.binayshaw7777.kotstep.v3.model.style.StepStyle
import com.binayshaw7777.kotstep.v3.model.style.StepStyles

internal object Util {

    @OptIn(ExperimentalKotStep::class)
    internal fun getKotStepStyle(): KotStepStyle {
        return KotStepStyle(
            stepLayoutStyle = StepLayoutStyle.Vertical,
            showCheckMarkOnDone = false,
            ignoreCurrentState = false,
            stepStyle = StepStyles.default().copy(
                onTodo = StepStyle.defaultTodo().copy(
                    stepSize = 50.dp,
                    stepColor = Color.Gray,
                    borderStyle = BorderStyle(width = 2.dp, color = Color.Red)
                ),
                onCurrent = StepStyle.defaultTodo().copy(
                    stepSize = 60.dp,
                    stepColor = Color.DarkGray,
                    borderStyle = BorderStyle(width = 2.dp, color = Color.Gray)
                ),
                onDone = StepStyle.defaultTodo().copy(
                    stepSize = 50.dp,
                    stepColor = Color.Green,
                    borderStyle = BorderStyle(width = 2.dp, color = Color.DarkGray)
                )
            ),
            lineStyle = LineStyles.default().copy(
                onTodo = LineStyle.defaultTodo().copy(
                    lineThickness = 10.dp,
                    lineLength = 100.dp,
                    linePadding = PaddingValues(2.dp)
                ),
                onCurrent = LineStyle.defaultCurrent().copy(
                    lineThickness = 10.dp,
                    lineLength = 100.dp,
                    linePadding = PaddingValues(2.dp),
                    lineType = LineType.Dashed(),
                    progressType = LineType.Dashed(),
                    progressStrokeCap = StrokeCap.Butt,
                    lineStrokeCap = StrokeCap.Butt
                ),
                onDone = LineStyle.defaultDone().copy(
                    lineThickness = 10.dp,
                    lineLength = 100.dp,
                    linePadding = PaddingValues(2.dp),
                    lineType = LineType.Dotted(gapLength = 24.dp),
                    progressType = LineType.Dotted(gapLength = 24.dp)
                )
            )
        )
    }

    @Composable
    fun Modifier.onClick(onClick: () -> Unit): Modifier = this.clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}
