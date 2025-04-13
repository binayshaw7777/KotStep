package com.binayshaw7777.kotstep.v2.model.style

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class LineStyle(
    val lineColor: Color = Color.Gray,
    val progressColor: Color = Color.Green,
    val lineLength: Dp = 16.dp,
    val lineThickness: Dp = 2.dp,
    val linePadding: PaddingValues = PaddingValues(0.dp),
    val lineStrokeCap: StrokeCap = StrokeCap.Square,
    val progressStrokeCap: StrokeCap = StrokeCap.Square,
    val lineType: LineType = LineType.SOLID,
    val progressType: LineType = LineType.SOLID,
) {
    companion object {
        fun defaultTodo() = LineStyle(lineColor = Color.Gray.copy(alpha = 0.3f), progressColor = Color.Gray.copy(alpha = 0.3f))
        fun defaultCurrent() = LineStyle(lineColor = Color.Gray.copy(alpha = 0.3f), progressColor = Color.Blue)
        fun defaultDone() = LineStyle(lineColor = Color.Gray.copy(alpha = 0.3f), progressColor = Color.Green)
    }
}

data class LineStyles(
    val onTodo: LineStyle,
    val onCurrent: LineStyle,
    val onDone: LineStyle
) {
    companion object {
        fun default() = LineStyles(
            onTodo = LineStyle.defaultTodo(),
            onCurrent = LineStyle.defaultCurrent(),
            onDone = LineStyle.defaultDone()
        )
    }
}