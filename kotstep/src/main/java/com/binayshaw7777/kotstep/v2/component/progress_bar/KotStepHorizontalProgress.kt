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
import com.binayshaw7777.kotstep.v2.model.style.LineType

/**
 * Composable function that draws a horizontal progress indicator.
 *
 * This function creates a horizontal line that represents a progress value. The line can be styled
 * with different colors, line types (solid, dashed, dotted), and stroke caps. The progress is
 * animated when the `progress` value changes.
 *
 * The function uses a `Canvas` to draw the progress indicator and supports different line styles
 * using `PathEffect` and different stroke caps using `StrokeCap`.
 *
 * @param modifier The modifier to be applied to the Canvas.
 * @param width A lambda function that returns the width of the progress line in Dp (length of the bar).
 * @param height A lambda function that returns the height of the progress line in Dp (thickness of the bar). Defaults to 1.dp.
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
 * @param progress A lambda function that returns the progress value (0.0 to 1.0).
 * @param trackStrokeCap The stroke cap style for the background/track line.
 * @param progressStrokeCap The stroke cap style for the progress line.
 *
 * @since 2.4.0
 */
@Composable
internal fun KotStepHorizontalProgress(
    modifier: Modifier = Modifier,
    width: () -> Dp,
    height: () -> Dp = { 1.dp },
    lineTrackColor: Color = Color.Gray,
    lineProgressColor: Color = Color.Black,
    lineTrackStyle: LineType = LineType.SOLID,
    lineProgressStyle: LineType = LineType.SOLID,
    progress: () -> Float = { 1f },
    trackStrokeCap: StrokeCap = StrokeCap.Round,
    progressStrokeCap: StrokeCap = StrokeCap.Round
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress().coerceIn(0f, 1f),
        animationSpec = tween(300), label = "kotstep_Horizontal_line_progress_animation_v2"
    )

    Canvas(
        modifier = modifier
            .width(width())
            .height(height())
    ) {
        val centerY = size.height / 2

        val trackPathEffect = when (lineTrackStyle) {
            LineType.SOLID -> null
            LineType.DASHED -> PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
            LineType.DOTTED -> null // Handled by drawing circles
        }

        val progressPathEffect = when (lineProgressStyle) {
            LineType.SOLID -> null
            LineType.DASHED -> PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
            LineType.DOTTED -> null // Handled by drawing circles
        }

        // Draw background track
        if (lineTrackStyle != LineType.DOTTED) {
            drawLine(
                color = lineTrackColor,
                start = Offset(0f, centerY),
                end = Offset(size.width, centerY),
                strokeWidth = height().toPx(),
                pathEffect = trackPathEffect,
                cap = trackStrokeCap
            )
        } else {
            val dotRadius = height().toPx() / 2
            val spaceBetweenDots = dotRadius * 4
            val totalDots = (size.width / spaceBetweenDots).toInt()

            for (i in 0 until totalDots) {
                val x = i * spaceBetweenDots + dotRadius
                drawCircle(
                    color = lineTrackColor,
                    radius = dotRadius,
                    center = Offset(x, centerY)
                )
            }
        }

        // Draw progress
        if (lineProgressStyle != LineType.DOTTED) {
            val endX = size.width * animatedProgress
            if (endX > 0) {
                drawLine(
                    color = lineProgressColor,
                    start = Offset(0f, centerY),
                    end = Offset(endX, centerY),
                    strokeWidth = height().toPx(),
                    pathEffect = progressPathEffect,
                    cap = progressStrokeCap
                )
            }
        } else {
            val dotRadius = height().toPx() / 2
            val spaceBetweenDots = dotRadius * 4
            val totalProgressDots = (size.width * animatedProgress / spaceBetweenDots).toInt()

            for (i in 0 until totalProgressDots) {
                val x = i * spaceBetweenDots + dotRadius
                drawCircle(
                    color = lineProgressColor,
                    radius = dotRadius,
                    center = Offset(x, centerY)
                )
            }
        }
    }
}