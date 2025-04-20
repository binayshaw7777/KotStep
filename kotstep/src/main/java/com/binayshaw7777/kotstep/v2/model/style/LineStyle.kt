package com.binayshaw7777.kotstep.v2.model.style

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.binayshaw7777.kotstep.v2.model.step.StepState

/**
 * Represents the styling options for a line in a progress indicator or similar UI element.
 *
 * This data class allows you to customize the appearance of both the base line and the progress portion of the line.
 *
 * @property lineColor The color of the base line. Defaults to `Color.Gray`.
 * @property progressColor The color of the progress portion of the line. Defaults to `Color.Green`.
 * @property lineLength The length of each individual line segment. Defaults to `16.dp`.
 * @property lineThickness The thickness of the line. Defaults to `2.dp`.
 * @property linePadding Padding around the line. Defaults to no padding (`PaddingValues(0.dp)`).
 * @property lineStrokeCap The shape of the ends of the base line. Defaults to `StrokeCap.Square`.
 * @property progressStrokeCap The shape of the ends of the progress portion of the line. Defaults to `StrokeCap.Square`.
 * @property lineType The type of the base line (e.g., solid, dashed). Defaults to `LineType.SOLID`.
 * @property progressType The type of the progress portion of the line (e.g., solid, dashed). Defaults to `LineType.SOLID`.
 *
 * @since 2.4.0
 */
@Immutable
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

        /**
         * Creates a default [LineStyle] with a semi-transparent gray color for both the line and progress.
         *
         * This function provides a convenient way to get a pre-configured [LineStyle] suitable for
         * representing a "default" or inactive state, often used as a placeholder or background element.
         * The line color and progress color are both set to a light gray with an alpha of 0.3f, making
         * them semi-transparent.
         *
         * @return A [LineStyle] object with the line color and progress color set to semi-transparent gray.
         *
         * @since 2.4.0
         */
        fun defaultTodo() = LineStyle(lineColor = Color.Gray.copy(alpha = 0.3f), progressColor = Color.Gray.copy(alpha = 0.3f))


        /**
         * Creates a default [LineStyle] with a semi-transparent gray line and a blue progress indicator.
         *
         * The default line style consists of:
         * - `lineColor`: Gray color with 30% opacity (alpha = 0.3f).
         * - `progressColor`: Solid blue color.
         *
         * This function is useful for quickly setting up a basic visual style for line-based UI elements,
         * such as progress bars or charts, where you want to differentiate between the background line and the
         * foreground progress indicator.
         *
         * @return A [LineStyle] object with the default line and progress colors.
         *
         * @since 2.4.0
         */
        fun defaultCurrent() = LineStyle(lineColor = Color.Gray.copy(alpha = 0.3f), progressColor = Color.Blue)


        /**
         * Creates a default [LineStyle] for a completed or "done" state.
         *
         * This function provides a pre-configured [LineStyle] with a gray line and a green progress color,
         * suitable for indicating that a process or step is finished. The gray line is semi-transparent.
         *
         * @return A [LineStyle] object with the following properties:
         *   - `lineColor`: A gray color with 30% opacity (alpha = 0.3f).
         *   - `progressColor`: A solid green color.
         *
         * @since 2.4.0
         */
        fun defaultDone() = LineStyle(lineColor = Color.Gray.copy(alpha = 0.3f), progressColor = Color.Green)
    }
}


/**
 * Represents the styling for lines in different states (todo, current, done).
 *
 * This data class holds [LineStyle] instances for each state, allowing for
 * customization of how lines are drawn depending on their status.
 *
 * @property onTodo The [LineStyle] to apply to lines that represent "todo" items.
 * @property onCurrent The [LineStyle] to apply to lines that represent the "current" item.
 * @property onDone The [LineStyle] to apply to lines that represent "done" items.
 *
 * @since 2.4.0
 */
@Immutable
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


/**
 * Retrieves the appropriate line color based on the given [StepState].
 *
 * This function maps a [StepState] to a specific line color defined within the [LineStyles] configuration.
 * It uses a `when` expression to determine the correct color based on the provided `stepState`.
 *
 * @param stepState The current state of the step. Can be one of [StepState.Todo], [StepState.Current], or [StepState.Done].
 * @return The [Color] to be used for the line corresponding to the given `stepState`.
 *         - [LineStyles.onTodo.lineColor] if `stepState` is [StepState.Todo].
 *         - [LineStyles.onCurrent.lineColor] if `stepState` is [StepState.Current].
 *         - [LineStyles.onDone.lineColor] if `stepState` is [StepState.Done].
 * @see LineStyles
 * @see StepState
 *
 * @since 2.4.0
 */
@Composable
fun LineStyles.getLineColorForState(stepState: StepState): Color {
    return when (stepState) {
        StepState.Todo -> this.onTodo.lineColor
        StepState.Current -> this.onCurrent.lineColor
        StepState.Done -> this.onDone.lineColor
    }
}


/**
 * Retrieves the appropriate progress color based on the given [StepState].
 *
 * This function maps a [StepState] (Todo, Current, or Done) to a specific color
 * defined within the current [LineStyles] configuration. It's intended for
 * visually representing the progress of a step in a UI, such as in a progress
 * bar or stepper component.
 *
 * @param stepState The current state of the step. It can be one of:
 *   - [StepState.Todo]: Represents a step that has not yet been started.
 *   - [StepState.Current]: Represents a step that is currently in progress.
 *   - [StepState.Done]: Represents a step that has been completed.
 * @return The [Color] associated with the given [stepState] in the current [LineStyles].
 *   - If [stepState] is [StepState.Todo], returns the `progressColor` from the `onTodo` style within [LineStyles].
 *   - If [stepState] is [StepState.Current], returns the `progressColor` from the `onCurrent` style within [LineStyles].
 *   - If [stepState] is [StepState.Done], returns the `progressColor` from the `onDone` style within [LineStyles].
 *
 * @see LineStyles
 * @see StepState
 *
 * @since 2.4.0
 */
@Composable
fun LineStyles.getProgressColorForState(stepState: StepState): Color {
    return when (stepState) {
        StepState.Todo -> this.onTodo.progressColor
        StepState.Current -> this.onCurrent.progressColor
        StepState.Done -> this.onDone.progressColor
    }
}


/**
 * Retrieves the line length (in Dp) associated with a given [StepState].
 *
 * This function determines the length of a line based on the current state of a step
 * in a multi-step process. Different states (Todo, Current, Done) can have different
 * line lengths defined in the [LineStyles] configuration.
 *
 * @param stepState The current state of the step (Todo, Current, or Done).
 * @return The line length (in Dp) corresponding to the provided [stepState].
 * @see StepState
 * @see LineStyles
 *
 * @since 2.4.0
 */
@Composable
fun LineStyles.getLineLengthForState(stepState: StepState): Dp {
    return when (stepState) {
        StepState.Todo -> this.onTodo.lineLength
        StepState.Current -> this.onCurrent.lineLength
        StepState.Done -> this.onDone.lineLength
    }
}