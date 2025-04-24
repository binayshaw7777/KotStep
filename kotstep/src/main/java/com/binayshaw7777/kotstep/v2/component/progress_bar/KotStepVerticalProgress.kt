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
 * Composable function that draws a vertical progress indicator.
 *
 * This function creates a vertical line that represents a progress value. The line can be styled
 * with different colors, line types (solid, dashed, dotted), and stroke caps. The progress is
 * animated when the `progress` value changes.
 *
 * The function uses a `Canvas` to draw the progress indicator and supports different line styles
 * using `PathEffect` and different stroke caps using `StrokeCap`.
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
 * @since 2.4.0
 *
 * */
@Composable
internal fun KotStepVerticalProgress(
    modifier: Modifier = Modifier,
    height: () -> Dp,
    width: () -> Dp = { 1.dp },
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
        animationSpec = tween(300), label = "kotstep_Vertical_line_progress_animation_v2"
    )

    Canvas(
        modifier = modifier
            .width(width())
            .height(height())
    ) {

        val trackPathEffect = when (lineTrackStyle) {
            LineType.SOLID -> null
            LineType.DASHED -> PathEffect.dashPathEffect(
                floatArrayOf(10f, 10f),
                0f
            )

            LineType.DOTTED -> PathEffect.dashPathEffect(
                floatArrayOf(5f, 5f),
                0f
            )
        }

        val progressPathEffect = when (lineProgressStyle) {
            LineType.SOLID -> null
            LineType.DASHED -> PathEffect.dashPathEffect(
                floatArrayOf(10f, 10f),
                0f
            )

            LineType.DOTTED -> PathEffect.dashPathEffect(
                floatArrayOf(5f, 5f),
                0f
            )
        }

        // Draw background line
        if (lineTrackStyle != LineType.DOTTED) {
            drawLine(
                color = lineTrackColor,
                start = Offset(size.width / 2, 0f),
                end = Offset(size.width / 2, size.height),
                strokeWidth = width().toPx(),
                pathEffect = trackPathEffect,
                cap = trackStrokeCap
            )

        } else {
            val dotRadius = width().toPx() / 2
            val spaceBetweenDots = dotRadius * 4
            val totalDots = (size.height / spaceBetweenDots).toInt()

            for (i in 0 until totalDots) {
                val y = i * spaceBetweenDots + dotRadius

                drawCircle(
                    color = lineProgressColor,
                    radius = dotRadius,
                    center = Offset(size.width / 2, y)
                )
            }
        }

        // Draw progress line
        if (lineProgressStyle != LineType.DOTTED) {
            val startY = 0f
            val endY = size.height * animatedProgress
            if (endY > startY) {
                drawLine(
                    color = lineProgressColor,
                    start = Offset(size.width / 2, startY),
                    end = Offset(size.width / 2, endY),
                    strokeWidth = width().toPx(),
                    pathEffect = progressPathEffect,
                    cap = progressStrokeCap
                )
            }
        } else {
            val dotRadius = width().toPx() / 2
            val spaceBetweenDots = dotRadius * 4
            val totalDots = (size.height / spaceBetweenDots).toInt()

            for (i in 0 until totalDots) {
                val y = i * spaceBetweenDots + dotRadius

                drawCircle(
                    color = lineProgressColor,
                    radius = dotRadius,
                    center = Offset(size.width / 2, y)
                )
            }
        }
    }
}