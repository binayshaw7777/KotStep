package com.binayshaw7777.kotstep.components.tabs

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke

/**
 * Represents the current tab in a tab stepper.
 *
 * @param circleColor The color of the circle.
 * @param outerCircleRadius The radius of the outer circle.
 * @param innerCircleRadius The radius of the inner circle.
 * @param strokeThickness The thickness of the stroke.
 */
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