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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.binayshaw7777.kotstep.model.LineStyle

@Composable
internal fun KotStepVerticalDivider(
    modifier: Modifier = Modifier,
    height: Dp,
    width: Dp = 1.dp,
    lineTrackColor: Color = Color.Gray,
    lineProgressColor: Color = Color.Black,
    lineStyle: LineStyle = LineStyle.SOLID,
    progress: Float = 1f
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
        val pathEffect = when (lineStyle) {
            LineStyle.SOLID -> null
            LineStyle.DASHED -> PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
            LineStyle.DOTTED -> PathEffect.dashPathEffect(floatArrayOf(5f, 5f), 0f)
        }

        if (lineStyle != LineStyle.DOTTED) {
            // Draw background line
            drawLine(
                color = lineTrackColor,
                start = Offset(size.width / 2, 0f),
                end = Offset(size.width / 2, size.height),
                strokeWidth = width.toPx(),
                pathEffect = pathEffect
            )

            // Draw progress line
            drawLine(
                color = lineProgressColor,
                start = Offset(size.width / 2, 0f),
                end = Offset(size.width / 2, size.height * animatedProgress),
                strokeWidth = width.toPx(),
                pathEffect = pathEffect
            )
        } else {
            val dotRadius = width.toPx() / 2
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