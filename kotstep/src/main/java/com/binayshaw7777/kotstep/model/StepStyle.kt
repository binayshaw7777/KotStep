package com.binayshaw7777.kotstep.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * A data class for StepStyle
 *
 * @param colors The colors for the step
 * @param lineThickness The thickness of the line
 * @param lineStyle The style of the line
 * @param showCheckMarkOnDone Whether to show check mark on done step
 * @param showStrokeOnCurrent Whether to show stroke on current step
 */
@Immutable
data class StepStyle(
    val colors: StepDefaults = StepDefaults.defaultColors(),
    val stepSize: Dp = 36.dp,
    val lineThickness: Dp = 1.dp,
    val lineStyle: LineStyle = LineStyle.SOLID,
    val showCheckMarkOnDone: Boolean = true,
    val showStrokeOnCurrent: Boolean = true
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

//internal val todoContainerColor: Color = Color.Unspecified
//internal val todoContentColor: Color = Color.Unspecified
//internal val currentContainerColor: Color = Color.Unspecified
//internal val currentContentColor: Color = Color.Unspecified
//internal val doneContainerColor: Color = Color.Unspecified
//internal val doneContentColor: Color = Color.Unspecified
//
//internal var stepColors: StepDefaults = StepDefaults(
//    todoContainerColor = todoContainerColor,
//    todoContentColor = todoContentColor,
//    currentContainerColor = currentContainerColor,
//    currentContentColor = currentContentColor,
//    doneContainerColor = doneContainerColor,
//    doneContentColor = doneContentColor
//)
//
//@Immutable
//data class StepStyle(
//    val colors: StepDefaults = StepDefaults.stepColors()
//)
//
//@Immutable
//data class StepDefaults(
//    val todoContainerColor: Color = Color.Unspecified,
//    val todoContentColor: Color = Color.Unspecified,
//    val currentContainerColor: Color = Color.Unspecified,
//    val currentContentColor: Color = Color.Unspecified,
//    val doneContainerColor: Color = Color.Unspecified,
//    val doneContentColor: Color = Color.Unspecified
//) {
//    companion object {
//        fun stepColors(): StepDefaults {
//            stepColors = stepColors.copy(
//                todoContainerColor = todoContainerColor,
//                todoContentColor = todoContentColor,
//                currentContainerColor = currentContainerColor,
//                currentContentColor = currentContentColor,
//                doneContainerColor = doneContainerColor,
//                doneContentColor = doneContentColor
//            )
//            return stepColors
//        }
//    }
//}