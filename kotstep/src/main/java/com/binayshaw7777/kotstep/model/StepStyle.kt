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
 * A data class that defines the style of a step in a stepper component.
 *
 * @property colors The colors of the steps.
 * @property stepSize The size of the step.
 * @property stepShape The shape of the step.
 * @property textSize The size of the text.
 * @property iconSize The size of the icon.
 * @property lineThickness The thickness of the line.
 * @property lineSize The size of the line.
 * @property stepPadding The padding of the step.
 * @property lineStyle The style of the line.
 * @property linePaddingStart The start padding of the line (Applied only to Horizontal Steppers).
 * @property linePaddingEnd The end padding of the line (Applied only to Horizontal Steppers).
 * @property linePaddingTop The top padding of the line (Applied only to Vertical Steppers).
 * @property linePaddingBottom The bottom padding of the line (Applied only to Vertical Steppers).
 * @property showCheckMarkOnDone Whether to show the check mark on done.
 * @property showStrokeOnCurrent Whether to show the stroke on current.
 * @property strokeCap The cap of the stroke.
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
    val linePaddingStart: Dp = 0.dp,
    val linePaddingEnd: Dp = 0.dp,
    val linePaddingTop: Dp = 0.dp,
    val linePaddingBottom: Dp = 0.dp,
    val showCheckMarkOnDone: Boolean = true,
    val showStrokeOnCurrent: Boolean = true,
    val strokeCap: StrokeCap = StrokeCap.Square
)

/**
 * A data class that defines the default colors for the steps.
 *
 * @property todoContainerColor The color of the todo container.
 * @property todoContentColor The color of the todo content.
 * @property todoLineColor The color of the todo line.
 * @property currentContainerColor The color of the current container.
 * @property currentContentColor The color of the current content.
 * @property currentLineColor The color of the current line.
 * @property doneContainerColor The color of the done container.
 * @property doneContentColor The color of the done content.
 * @property doneLineColor The color of the done line.
 */
@Immutable
data class StepDefaults(
    val todoContainerColor: Color = Color.Gray,
    val todoContentColor: Color = Color.DarkGray,
    val todoLineColor: Color = Color.Gray,
    val currentContainerColor: Color = Color.Blue,
    val currentContentColor: Color = Color.White,
    val currentLineColor: Color = Color.Blue,
    val doneContainerColor: Color = Color.Green,
    val doneContentColor: Color = Color.White,
    val doneLineColor: Color = Color.Green
) {
    companion object {
        /**
         * Returns the default colors for the steps.
         *
         * @return The default [StepDefaults] instance.
         */
        fun defaultColors() = StepDefaults(
            todoContainerColor = Color.Gray,
            todoContentColor = Color.DarkGray,
            todoLineColor = Color.Gray,
            currentContainerColor = Color.Blue,
            currentContentColor = Color.White,
            currentLineColor = Color.Blue,
            doneContainerColor = Color.Green,
            doneContentColor = Color.White,
            doneLineColor = Color.Green
        )
    }
}