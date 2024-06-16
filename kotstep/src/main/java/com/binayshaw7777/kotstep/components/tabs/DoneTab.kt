package com.binayshaw7777.kotstep.components.tabs

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

/**
 * Represents the done tab in a tab stepper.
 *
 * @param circleColor The color of the circle.
 * @param circleRadius The radius of the circle.
 */
@Composable
internal fun DoneTab(
    circleColor: Color = Color.Green,
    circleRadius: Float? = null
) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        drawCircle(
            color = circleColor,
            radius = circleRadius ?: (size.minDimension / 2f),
            center = center
        )
    }
}