package com.binayshaw7777.kotstep.components.divider

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
import com.binayshaw7777.kotstep.model.LineType

@Composable
internal fun KotStepHorizontalDivider(
    modifier: Modifier = Modifier,
    width: Dp,
    height: Dp = 1.dp,
    lineTrackColor: Color = Color.Gray,
    lineProgressColor: Color = Color.Black,
    lineTrackStyle: LineType = LineType.SOLID,
    lineProgressStyle: LineType = LineType.SOLID,
    progress: Float = 1f,
    strokeCap: StrokeCap = StrokeCap.Round
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress.coerceIn(0f, 1f),
        animationSpec = tween(300), label = "line_progress_animation"
    )

    println("Animated progress value is: $animatedProgress")

    Canvas(
        modifier = modifier
            .width(width)
            .height(height)
    ) {
        val trackPathEffect = when (lineTrackStyle) {
            LineType.SOLID -> null
            LineType.DASHED -> PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
            LineType.DOTTED -> PathEffect.dashPathEffect(floatArrayOf(5f, 5f), 0f)
        }
        val progressPathEffect = when (lineProgressStyle) {
            LineType.SOLID -> null
            LineType.DASHED -> PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
            LineType.DOTTED -> PathEffect.dashPathEffect(floatArrayOf(5f, 5f), 0f)
        }

        // Draw background line
        if (lineTrackStyle != LineType.DOTTED) {
            drawLine(
                color = lineTrackColor,
                start = Offset(0f, size.height / 2),
                end = Offset(size.width, size.height / 2),
                strokeWidth = height.toPx(),
                pathEffect = trackPathEffect,
                cap = strokeCap
            )
        } else {
            val dotRadius = height.toPx() / 2
            val spaceBetweenDots = dotRadius * 4
            val totalDots = (size.width / spaceBetweenDots).toInt()

            for (i in 0 until totalDots) {
                val x = i * spaceBetweenDots + dotRadius

                drawCircle(
                    color = if (x <= size.width * animatedProgress) lineProgressColor else lineTrackColor,
                    radius = dotRadius,
                    center = Offset(x, size.height / 2)
                )
            }
        }

        // Draw progress line
        if (lineProgressStyle != LineType.DOTTED) {
            drawLine(
                color = lineProgressColor,
                start = Offset(0f, size.height / 2),
                end = Offset(size.width * animatedProgress, size.height / 2),
                strokeWidth = height.toPx(),
                pathEffect = progressPathEffect,
                cap = strokeCap
            )
        } else {
            val dotRadius = height.toPx() / 2
            val spaceBetweenDots = dotRadius * 4
            val totalDots = (size.width / spaceBetweenDots).toInt()

            for (i in 0 until totalDots) {
                val x = i * spaceBetweenDots + dotRadius

                drawCircle(
                    color = if (x <= size.width * animatedProgress) lineProgressColor else lineTrackColor,
                    radius = dotRadius,
                    center = Offset(x, size.height / 2)
                )
            }
        }
    }
}