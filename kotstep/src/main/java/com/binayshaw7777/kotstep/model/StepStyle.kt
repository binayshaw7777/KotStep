package com.binayshaw7777.kotstep.model

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * A data class for StepStyle which represents the style of the step (Item) in the stepper
 *
 * @param colors The colors for the step
 * @param lineThickness The thickness of the line
 * @param lineStyle The style of the line
 * @param showCheckMarkOnDone Whether to show check mark on done step
 * @param showStrokeOnCurrent Whether to show stroke on current step
 * @param strokeCap The stroke cap of the line (default is round)
 */
@Immutable
data class StepStyle(
    val colors: StepDefaults = StepDefaults.defaultColors(),
    val stepSize: Dp = 36.dp,
    val stepShape: Shape = CircleShape,
    val textSize: TextUnit = 16.sp,
    val iconSize: Dp = 24.dp,
    val lineThickness: Dp = 6.dp,
    val lineSize: Dp = 20.dp,
    val stepPadding: Dp = 0.dp,
    val lineStyle: LineStyle = LineStyle.SOLID,
    val showCheckMarkOnDone: Boolean = true,
    val showStrokeOnCurrent: Boolean = true,
    val strokeCap: StrokeCap = StrokeCap.Square
)

/**
 * A data class for StepDefaults
 *
 * @param todoContainerColor The color of the todo container
 * @param todoContentColor The color of the todo content
 * @param currentContainerColor The color of the current container
 * @param currentContentColor The color of the current content
 * @param doneContainerColor The color of the done container
 * @param doneContentColor The color of the done content
 */
@Immutable
data class StepDefaults(
    val todoContainerColor: Color = Color.Gray,
    val todoContentColor: Color = Color.DarkGray,
    val currentContainerColor: Color = Color.Blue,
    val currentContentColor: Color = Color.White,
    val doneContainerColor: Color = Color.Green,
    val doneContentColor: Color = Color.White
) {
    companion object {
        /**
         * Returns the default colors for the steps
         */
        fun defaultColors() = StepDefaults(
            todoContainerColor = Color.Gray,
            todoContentColor = Color.DarkGray,
            currentContainerColor = Color.Blue,
            currentContentColor = Color.White,
            doneContainerColor = Color.Green,
            doneContentColor = Color.White
        )
    }
}