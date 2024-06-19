package com.binayshaw7777.kotstep.components.tabs

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke

/**
 * Represents the todo tab in a tab stepper.
 *
 * @param strokeColor The color of the stroke.
 * @param circleRadius The radius of the circle.
 * @param strokeThickness The thickness of the stroke.
 */
@Composable
internal fun TodoTab(
    strokeColor: Color = Color.Gray,
    circleRadius: Float? = null,
    strokeThickness: Float = 4f,
) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        drawCircle(
            color = strokeColor,
            radius = circleRadius ?: (size.minDimension / 2f),
            center = center,
            style = Stroke(width = strokeThickness)
        )
    }
}