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
 * @property stepStroke The stroke of the step.
 * @property textSize The size of the text.
 * @property iconSize The size of the icon.
 * @property stepPadding The padding of the step.
 * @property showCheckMarkOnDone Whether to show the check mark on done.
 * @property ignoreCurrentState If set to true, shows DONE instead of CURRENT state.
 */
@Immutable
data class StepStyle(
    val colors: StepDefaults = StepDefaults.defaultColors(),
    val lineStyle: LineDefault = LineDefault.defaultLine(),
    val stepSize: Dp = 36.dp,
    val stepShape: Shape = CircleShape,
    val stepStroke: Float = 2f,
    val textSize: TextUnit = 16.sp,
    val iconSize: Dp = 24.dp,
    val stepPadding: Dp = 0.dp,
    val showCheckMarkOnDone: Boolean = true,
    val ignoreCurrentState: Boolean = false
)


/**
 * A data class that defines the default line style for the steps.
 *
 * @property lineThickness The thickness of the line.
 * @property lineSize The size of the line.
 * @property linePaddingStart The start padding of the line.
 * @property linePaddingEnd The end padding of the line.
 * @property linePaddingTop The top padding of the line.
 * @property linePaddingBottom The bottom padding of the line.
 * @property trackStrokeCap The stroke cap of the track.
 * @property progressStrokeCap The stroke cap of the progress.
 * @property todoLineTrackType The track type of the todo line.
 * @property currentLineTrackType The track type of the current line.
 * @property doneLineTrackType The track type of the done line.
 * @property todoLineProgressType The progress type of the todo line.
 * @property currentLineProgressType The progress type of the current line.
 * @property doneLineProgressType The progress type of the done line.
 */
@Immutable
data class LineDefault(
    val lineThickness: Dp = 6.dp,
    val lineSize: Dp = 20.dp,
    val linePaddingStart: Dp = 0.dp,
    val linePaddingEnd: Dp = 0.dp,
    val linePaddingTop: Dp = 0.dp,
    val linePaddingBottom: Dp = 0.dp,
    val trackStrokeCap: StrokeCap = StrokeCap.Square,
    val progressStrokeCap: StrokeCap = StrokeCap.Square,
    val todoLineTrackType: LineType = LineType.SOLID,
    val currentLineTrackType: LineType = LineType.SOLID,
    val doneLineTrackType: LineType = LineType.SOLID,
    val todoLineProgressType: LineType = LineType.SOLID,
    val currentLineProgressType: LineType = LineType.SOLID,
    val doneLineProgressType: LineType = LineType.SOLID
) {
    companion object {
        fun defaultLine() = LineDefault(
            lineThickness = 6.dp,
            lineSize = 20.dp,
            linePaddingStart = 0.dp,
            linePaddingEnd = 0.dp,
            linePaddingTop = 0.dp,
            linePaddingBottom = 0.dp,
            trackStrokeCap = StrokeCap.Square,
            progressStrokeCap = StrokeCap.Square
        )
    }
}

/**
 * A data class that defines the default colors for the steps.
 *
 * @property todoContainerColor The color of the todo container.
 * @property todoContentColor The color of the todo content.
 * @property todoLineColor The color of the todo line.
 * @property todoStepStrokeColor The color of the todo step stroke.
 * @property currentContainerColor The color of the current container.
 * @property currentContentColor The color of the current content.
 * @property currentLineColor The color of the current line.
 * @property currentStepStrokeColor The color of the current step stroke.
 * @property doneContainerColor The color of the done container.
 * @property doneContentColor The color of the done content.
 * @property doneLineColor The color of the done line.
 * @property doneStepStrokeColor The color of the done step stroke.
 * @property checkMarkColor The color of the check mark.
 */
@Immutable
data class StepDefaults(
    val todoContainerColor: Color = Color.Gray,
    val todoContentColor: Color = Color.DarkGray,
    val todoLineColor: Color = Color.Gray,
    val todoStepStrokeColor: Color? = null,
    val currentContainerColor: Color = Color.Blue,
    val currentContentColor: Color = Color.White,
    val currentLineColor: Color = Color.Blue,
    val currentStepStrokeColor: Color? = null,
    val doneContainerColor: Color = Color.Green,
    val doneContentColor: Color = Color.White,
    val doneLineColor: Color = Color.Green,
    val doneStepStrokeColor: Color? = null,
    val checkMarkColor: Color = Color.Black
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
            todoStepStrokeColor = null,
            currentContainerColor = Color.Blue,
            currentContentColor = Color.White,
            currentLineColor = Color.Blue,
            currentStepStrokeColor = null,
            doneContainerColor = Color.Green,
            doneContentColor = Color.White,
            doneLineColor = Color.Green,
            doneStepStrokeColor = null,
            checkMarkColor = Color.Black
        )
    }
}