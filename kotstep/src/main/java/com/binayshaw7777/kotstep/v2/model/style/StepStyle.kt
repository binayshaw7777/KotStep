package com.binayshaw7777.kotstep.v2.model.style

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Font size <= Step Size * 0.75f
// Icon size <= Step Size * 0.75f
data class StepStyle(
    val stepColor: Color = Color.Gray,
    val stepSize: Dp = 24.dp,
    val stepShape: Shape = CircleShape,
    val textStyle: TextStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
    val iconStyle: IconStyle = IconStyle(),
    val borderStyle: BorderStyle = BorderStyle()
) {
    companion object {
        fun defaultTodo() = StepStyle(stepColor = Color.Gray.copy(alpha = 0.3f))
        fun defaultCurrent() = StepStyle(stepColor = Color.Blue)
        fun defaultDone() = StepStyle(stepColor = Color.Green)
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