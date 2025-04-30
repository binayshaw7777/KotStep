package com.binayshaw7777.kotstep.v2.component.progress_bar

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.binayshaw7777.kotstep.v2.model.step.StepState
import com.binayshaw7777.kotstep.v2.model.style.LineType
import com.binayshaw7777.kotstep.v2.util.ExperimentalKotStep

/**
 * Composable function that draws a vertical progress indicator.
 *
 * This function creates a vertical line that represents a progress value. The line can be styled
 * with different colors, line types (solid, dashed, dotted), and stroke caps. The progress is
 * animated when the `progress` value changes.
 *
 * The function uses a `Canvas` to draw the progress indicator and supports different line styles
 * using `PathEffect` and different stroke caps using `StrokeCap`.
 *
 * **Note:**
 * When the [StepState] is [StepState.Done], it does not draws line track. Instead it only draws line progress.
 * This means `lineTrackColor`
 *
 * @param modifier The modifier to be applied to the Canvas.
 * @param height A lambda function that returns the height of the progress line in Dp.
 * @param width A lambda function that returns the width of the progress line in Dp. Defaults to 1.dp.
 * @param lineTrackColor The color of the background/track line. Defaults to Gray.
 * @param lineProgressColor The color of the progress line. Defaults to Black.
 * @param lineTrackStyle The style of the background/track line (SOLID, DASHED, DOTTED).
 *   - `LineType.SOLID`: A solid line.
 *   - `LineType.DASHED`: A dashed line.
 *   - `LineType.DOTTED`: A dotted line.
 * @param lineProgressStyle The style of the progress line (SOLID, DASHED, DOTTED).
 *   - `LineType.SOLID`: A solid line.
 *   - `LineType.DASHED`: A dashed line.
 *   - `LineType.DOTTED`: A dotted line.
 * @param progress A lambda function that returns the progress value
 * @param trackStrokeCap The stroke cap style for the background/track line.
 * @param progressStrokeCap The stroke cap style for the progress line.
 *
 * @since 3.0.0
 *
 * */
@OptIn(ExperimentalKotStep::class)
@Composable
internal fun KotStepVerticalProgress(
    modifier: Modifier = Modifier,
    height: () -> Dp,
    width: () -> Dp = { 1.dp },
    lineTrackColor: Color = Color.Gray,
    lineProgressColor: Color = Color.Black,
    lineTrackStyle: LineType = LineType.Solid,
    lineProgressStyle: LineType = LineType.Solid,
    progress: () -> Float = { 1f },
    stepState: () -> StepState,
    trackStrokeCap: StrokeCap = StrokeCap.Butt,
    progressStrokeCap: StrokeCap = StrokeCap.Butt
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress().coerceIn(0f, 1f),
        animationSpec = tween(300), label = "kotstep_Vertical_line_progress_animation_v2"
    )

    Canvas(
        modifier = modifier
            .width(width())
            .height(height())
    ) {

        val centerX = size.width / 2

        // Draw background track
        when (lineTrackStyle) {
            is LineType.Solid -> {
                drawLine(
                    color = lineTrackColor,
                    start = Offset(centerX, 0f),
                    end = Offset(centerX, size.height),
                    strokeWidth = width().toPx(),
                    cap = trackStrokeCap
                )
            }

            is LineType.Dashed -> {
                drawLine(
                    color = lineTrackColor,
                    start = Offset(centerX, 0f),
                    end = Offset(centerX, size.height),
                    strokeWidth = width().toPx(),
                    pathEffect = PathEffect.dashPathEffect(
                        floatArrayOf(lineTrackStyle.dashLength.toPx(), lineTrackStyle.gapLength.toPx()),
                        0f
                    ),
                    cap = trackStrokeCap
                )
            }

            is LineType.Dotted -> {
                if (stepState() != StepState.Done) {
                    val dotRadius = width().toPx() / 2
                    val gapLengthPx = lineTrackStyle.gapLength.toPx()
                    val spaceBetweenDots = dotRadius * 2 + gapLengthPx

                    val totalDots = (size.height / spaceBetweenDots).toInt()

                    for (i in 0 until totalDots) {
                        val y = i * spaceBetweenDots + dotRadius
                        drawCircle(
                            color = lineTrackColor,
                            radius = dotRadius,
                            center = Offset(centerX, y)
                        )
                    }
                }

            }
        }

        // Draw progress
        val endY = size.height * animatedProgress
        if (endY > 0) {
            when (lineProgressStyle) {
                is LineType.Solid -> {
                    drawLine(
                        color = lineProgressColor,
                        start = Offset(centerX, 0f),
                        end = Offset(centerX, endY),
                        strokeWidth = width().toPx(),
                        cap = progressStrokeCap
                    )
                }

                is LineType.Dashed -> {
                    drawLine(
                        color = lineProgressColor,
                        start = Offset(centerX, 0f),
                        end = Offset(centerX, endY),
                        strokeWidth = width().toPx(),
                        pathEffect = PathEffect.dashPathEffect(
                            floatArrayOf(lineProgressStyle.dashLength.toPx(), lineProgressStyle.gapLength.toPx()),
                            0f
                        ),
                        cap = progressStrokeCap
                    )
                }

                is LineType.Dotted -> {
                    val dotRadius = width().toPx() / 2
                    val gapLengthPx = lineProgressStyle.gapLength.toPx()
                    val spaceBetweenDots = dotRadius * 2 + gapLengthPx

                    val totalProgressDots = (endY / spaceBetweenDots).toInt()

                    for (i in 0 until totalProgressDots) {
                        val y = i * spaceBetweenDots + dotRadius
                        drawCircle(
                            color = lineProgressColor,
                            radius = dotRadius,
                            center = Offset(centerX, y)
                        )
                    }
                }
            }
        }
    }
}