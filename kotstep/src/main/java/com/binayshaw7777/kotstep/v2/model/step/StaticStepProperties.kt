package com.binayshaw7777.kotstep.v2.model.step

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp
import com.binayshaw7777.kotstep.v2.model.style.KotStepStyle
import com.binayshaw7777.kotstep.v2.model.style.LineStyle
import com.binayshaw7777.kotstep.v2.model.style.LineType
import com.binayshaw7777.kotstep.v2.model.style.StepStyle


/**
 * Represents the properties that define the visual appearance of a step in a stepper or similar component.
 *
 * @property maxSize The maximum width allocated for the step and its associated line.
 *                   This helps in controlling the overall size and layout of the steps.
 * @property stepStyle The style properties for the step's circular or custom shape.
 *                     This includes color, size, and any other visual aspects of the step itself.
 * @property lineStyle The style properties for the line connecting the steps.
 *                     This includes color, thickness, and any other visual attributes of the connecting line.
 * @property lineTrackType The type of the line representing the uncompleted portion of the step progression.
 *                           This could be a solid line, dashed line, etc. See [LineType].
 * @property lineProgressType The type of the line representing the completed portion of the step progression.
 *                            This could be a solid line, dashed line, etc. See [LineType].
 * @property trackStrokeCap The type of stroke cap applied to the end of the line track (uncompleted portion).
 *                         This controls the shape of the line's end, e.g., round, square, or butt. See [StrokeCap].
 * @property progressStrokeCap The type of stroke cap applied to the end of the line progress (completed portion).
 *                            This controls the shape of the line's end, e.g., round, square, or butt. See [StrokeCap].
 *
 * @since 3.0.0
 */
@Immutable
internal data class StaticStepProperties(
    val maxSize: Dp,
    val stepStyle: StepStyle,
    val lineStyle: LineStyle,
    val lineTrackType: LineType,
    val lineProgressType: LineType,
    val trackStrokeCap: StrokeCap,
    val progressStrokeCap: StrokeCap
)


/**
 * Calculates the static properties of a step based on the given style and step state.
 *
 * This function determines the visual attributes of a step, such as its maximum width,
 * style, and line properties, based on the current [StepState] and the provided [KotStepStyle].
 * The calculated properties are meant to be static and remain consistent unless the style
 * or the step state changes.
 *
 * @param style The [KotStepStyle] that defines the styling for the step and its lines.
 * @param stepState The [StepState] representing the current state of the step (Todo, Current, or Done).
 * @return A [StaticStepProperties] object containing the calculated properties for the step.
 *
 * @see KotStepStyle
 * @see StepState
 * @see StaticStepProperties
 *
 * @since 3.0.0
 */
@Composable
internal fun calculateStaticStepProperties(style: KotStepStyle, stepState: StepState): StaticStepProperties {
    return remember(style.stepStyle, style.lineStyle, stepState) {
        derivedStateOf {
            StaticStepProperties(
                maxSize = maxOf(
                    style.stepStyle.onTodo.stepSize,
                    style.stepStyle.onCurrent.stepSize,
                    style.stepStyle.onDone.stepSize
                ),
                stepStyle = when (stepState) {
                    StepState.Todo -> style.stepStyle.onTodo
                    StepState.Current -> style.stepStyle.onCurrent
                    StepState.Done -> style.stepStyle.onDone
                },
                lineStyle = when (stepState) {
                    StepState.Todo -> style.lineStyle.onTodo
                    StepState.Current -> style.lineStyle.onCurrent
                    StepState.Done -> style.lineStyle.onDone
                },
                lineTrackType = when (stepState) {
                    StepState.Todo -> style.lineStyle.onTodo.lineType
                    StepState.Current -> style.lineStyle.onCurrent.lineType
                    StepState.Done -> style.lineStyle.onDone.lineType
                },
                lineProgressType = when (stepState) {
                    StepState.Todo -> style.lineStyle.onTodo.progressType
                    StepState.Current -> style.lineStyle.onCurrent.progressType
                    StepState.Done -> style.lineStyle.onDone.progressType
                },
                trackStrokeCap = when (stepState) {
                    StepState.Todo -> style.lineStyle.onTodo.lineStrokeCap
                    StepState.Current -> style.lineStyle.onCurrent.lineStrokeCap
                    StepState.Done -> style.lineStyle.onDone.lineStrokeCap
                },
                progressStrokeCap = when (stepState) {
                    StepState.Todo -> style.lineStyle.onTodo.progressStrokeCap
                    StepState.Current -> style.lineStyle.onCurrent.progressStrokeCap
                    StepState.Done -> style.lineStyle.onDone.progressStrokeCap
                }
            )
        }
    }.value
}