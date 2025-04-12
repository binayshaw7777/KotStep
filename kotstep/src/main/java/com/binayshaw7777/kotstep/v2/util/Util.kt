package com.binayshaw7777.kotstep.v2.util

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.binayshaw7777.kotstep.v2.model.step.StepLayoutStyle
import com.binayshaw7777.kotstep.v2.model.style.BorderStyle
import com.binayshaw7777.kotstep.v2.model.style.KotStepStyle
import com.binayshaw7777.kotstep.v2.model.style.LineStyle
import com.binayshaw7777.kotstep.v2.model.style.LineStyles
import com.binayshaw7777.kotstep.v2.model.style.StepStyle
import com.binayshaw7777.kotstep.v2.model.style.StepStyles

object Util {

    fun getKotStepStyle(): KotStepStyle {
        return KotStepStyle(
            stepLayoutStyle = StepLayoutStyle.Vertical,
            isScrollable = true,
            showCheckMarkOnDone = true,
            ignoreCurrentState = false,
            stepStyle = StepStyles.default().copy(
                onTodo = StepStyle.defaultTodo().copy(
                    stepSize = 50.dp,
                    stepColor = Color.Gray,
                ),
                onCurrent = StepStyle.defaultTodo().copy(
                    stepSize = 60.dp,
                    stepColor = Color.DarkGray,
                    borderStyle = BorderStyle(width = 2.dp, color = Color.Gray)
                ),
                onDone = StepStyle.defaultTodo().copy(
                    stepSize = 50.dp,
                    stepColor = Color.Green,
                )
            ),
            lineStyle = LineStyles.default().copy(
                onTodo = LineStyle.defaultTodo().copy(
                    lineThickness = 4.dp,
                    lineLength = 20.dp
                ),
                onCurrent = LineStyle.defaultCurrent().copy(
                    lineThickness = 4.dp,
                    lineLength = 20.dp,
                    linePadding = PaddingValues(4.dp)
                ),
                onDone = LineStyle.defaultDone().copy(
                    lineThickness = 4.dp,
                    lineLength = 20.dp
                )
            )
        )
    }
}