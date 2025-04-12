package com.binayshaw7777.kotstep.v2.model.style

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class LineType {
    SOLID,
    DOTTED,
    DASHED
}

data class IconStyle(
    val iconTint: Color = Color.Unspecified,
    val iconSize: Dp = 16.dp
)

// Font size <= Step Size * 0.75f
// Icon size <= Step Size * 0.75f
data class StepStyle(
    val stepColor: Color = Color.Gray,
    val stepSize: Dp = 24.dp,
    val stepShape: Shape = CircleShape,
    val textStyle: TextStyle = TextStyle(color = Color.Gray, fontSize = 16.sp),
    val iconStyle: IconStyle = IconStyle(),
    val border: BorderStroke = BorderStroke(width = 1.dp, color = Color.DarkGray)
) {
    companion object {
        fun defaultTodo() = StepStyle(stepColor = Color.Gray.copy(alpha = 0.3f))
        fun defaultCurrent() = StepStyle(stepColor = Color.Blue)
        fun defaultDone() = StepStyle(stepColor = Color.Green)
    }
}

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
        fun defaultTodo() = LineStyle(lineColor = Color.Gray.copy(alpha = 0.3f))
        fun defaultCurrent() = LineStyle(lineColor = Color.Blue)
        fun defaultDone() = LineStyle(lineColor = Color.Green)
    }
}


data class StepStyles(
    val onTodo: StepStyle,
    val onCurrent: StepStyle,
    val onDone: StepStyle
) {
    companion object {
        fun default() = StepStyles(
            onTodo = StepStyle.defaultTodo(),
            onCurrent = StepStyle.defaultCurrent(),
            onDone = StepStyle.defaultDone()
        )
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