package com.binayshaw7777.kotstep.components.tabs

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke

@Composable
internal fun CurrentTab(
    circleColor: Color = Color.Blue,
    outerCircleRadius: Float? = null,
    innerCircleRadius: Float? = null,
    strokeThickness: Float = 4f
) {
    Canvas(modifier = Modifier.fillMaxSize()) {

        drawCircle(
            color = circleColor,
            radius = outerCircleRadius ?: (size.minDimension / 2f),
            center = center,
            style = Stroke(width = strokeThickness)
        )

        drawCircle(
            color = circleColor,
            radius = innerCircleRadius ?: ((size.minDimension / 2f) * 0.7f),
            center = center
        )
    }
}