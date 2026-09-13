package com.binayshaw7777.kotstep.v3.model.style

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.binayshaw7777.kotstep.v3.model.step.StepState
import com.binayshaw7777.kotstep.v3.util.ExperimentalKotStep

/**
 * Defines the visual style of a step in a stepper or similar UI component.
 *
 * This class allows customization of various aspects of a step's appearance, including:
 * - **Step Color:** The background color of the step indicator.
 * - **Step Size:** The size (width and height) of the step indicator.
 * - **Step Shape:** The shape of the step indicator (e.g., circle, rectangle).
 * - **Text Style:** The style of any text displayed within the step.
 * - **Icon Style:** The style of any icon displayed within the step.
 * - **Border Style:** The style of the border around the step indicator.
 *
 * **Note:**
 * - Font size in `textStyle` should ideally be less than or equal to `stepSize` * 0.75f.
 * - Icon size (determined by `iconStyle`) should ideally be less than or equal to `stepSize` * 0.75f.
 *
 * @property stepColor The background color of the step indicator. Defaults to `Color.Gray`.
 * @property stepSize The size (width and height) of the step indicator. Defaults to `24.dp`.
 * @property stepShape The shape of the step indicator. Defaults to `CircleShape`.
 * @property textStyle The style of any text displayed within the step. Defaults to black text with a font size of 16sp.
 * @property iconStyle The style of any icon displayed within the step. Defaults to an empty `IconStyle`.
 * @property borderStyle The style of the border around the step indicator. Defaults to an empty `BorderStyle`.
 *
 * @since 3.0.0
 */
@ExperimentalKotStep
@Immutable
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

/**
 * Represents the different styles applied to a step in a stepper component based on its state (todo, current, done).
 *
 * This class encapsulates the styling for each state of a step, allowing for customization of the step's appearance.
 *
 * @property onTodo The style applied to a step that is in the "todo" state (not yet reached).
 * @property onCurrent The style applied to the currently active step.
 * @property onDone The style applied to a step that has been completed.
 *
 * @since 3.0.0
 */
@ExperimentalKotStep
@Immutable
data class StepStyles(
    val onTodo: StepStyle,
    val onCurrent: StepStyle,
    val onDone: StepStyle
) {
    companion object {

        /**
         * Creates a `StepStyles` object with default styles for TODO, current, and done steps.
         *
         * This function provides a convenient way to initialize `StepStyles` with predefined styles.
         * It uses the `defaultTodo()`, `defaultCurrent()`, and `defaultDone()` methods of the `StepStyle`
         * class to set the styles for each step state.
         *
         * @return A `StepStyles` object with default styles applied.
         * @see StepStyles
         * @see StepStyle
         * @see StepStyle.defaultTodo
         * @see StepStyle.defaultCurrent
         * @see StepStyle.defaultDone
         *
         * @since 3.0.0
         */
        fun default() = StepStyles(
            onTodo = StepStyle.defaultTodo(),
            onCurrent = StepStyle.defaultCurrent(),
            onDone = StepStyle.defaultDone()
        )

        /**
         * Provides a set of default styles for a step-based UI component.
         *
         * This function allows you to define custom styles for each step state (todo, current, done)
         * while falling back to sensible defaults if specific styles are not provided.
         *
         * @param onTodo The style to be applied to steps that are yet to be completed (todo). If null,
         *               it defaults to [StepStyle.defaultTodo()].
         * @param onCurrent The style to be applied to the currently active step. If null, it defaults to
         *                  [StepStyle.defaultCurrent()].
         * @param onDone The style to be applied to steps that have already been completed (done). If null,
         *               it defaults to [StepStyle.defaultDone()].
         * @return A [StepStyles] object containing the specified or default styles for each step state.
         *
         * @see StepStyle
         * @see StepStyle.defaultTodo
         * @see StepStyle.defaultCurrent
         * @see StepStyle.defaultDone
         * @see StepStyles
         *
         * @since 3.0.2
         */
        fun default(
            onTodo: StepStyle? = null,
            onCurrent: StepStyle? = null,
            onDone: StepStyle? = null
        ) = StepStyles(
            onTodo = onTodo ?: StepStyle.defaultTodo(),
            onCurrent = onCurrent ?: StepStyle.defaultCurrent(),
            onDone = onDone ?: StepStyle.defaultDone()
        )
    }
}



/**
 * Returns the appropriate color for a given [StepState].
 *
 * This function maps a [StepState] to a specific [Color] defined within the [StepStyles] object.
 * It is used to visually represent the state of a step in a UI component, such as a stepper or progress indicator.
 *
 * @param stepState The current [StepState] of the step (Todo, Current, or Done).
 * @return The [Color] associated with the given [stepState].
 *
 * ```
 * val myStepStyles = StepStyles(
 *     onTodo = StepStyle(Color.Gray),
 *     onCurrent = StepStyle(Color.Blue),
 *     onDone = StepStyle(Color.Green)
 * )
 *
 * val todoColor = myStepStyles.getColorForState(StepState.Todo) // Returns Color.Gray
 * val currentColor = myStepStyles.getColorForState(StepState.Current) // Returns Color.Blue
 * val doneColor = myStepStyles.getColorForState(StepState.Done) // Returns Color.Green
 * ```
 *
 *
 * @see StepState
 * @see StepStyles
 * @see StepStyle
 *
 * @since 3.0.0
 */
@OptIn(ExperimentalKotStep::class)
@Composable
internal fun StepStyles.getColorForState(stepState: StepState): Color {
    return when (stepState) {
        StepState.Todo -> this.onTodo.stepColor
        StepState.Current -> this.onCurrent.stepColor
        StepState.Done -> this.onDone.stepColor
    }
}

/**
 * Represents the different states a step in a multi-step process can have.
 *
 * @since 3.0.0
 */
@OptIn(ExperimentalKotStep::class)
@Composable
internal fun StepStyles.getSizeForState(stepState: StepState): Dp {
    return when (stepState) {
        StepState.Todo -> this.onTodo.stepSize
        StepState.Current -> this.onCurrent.stepSize
        StepState.Done -> this.onDone.stepSize
    }
}