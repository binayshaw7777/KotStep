package com.binayshaw7777.kotstep.components.tabs

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.LayoutDirection

/**
 * Represents the todo tab in a tab stepper.
 *
 * @param strokeColor The color of the stroke.
 * @param strokeThickness The thickness of the stroke.
 * @param stepShape The shape of the step.
 */
@Composable
internal fun TodoTab(
    strokeColor: Color = Color.Gray,
    strokeThickness: Float = 4f,
    stepShape: Shape = CircleShape
) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        drawOutline(
            outline = stepShape.createOutline(size, layoutDirection = LayoutDirection.Ltr, density = this),
            color = strokeColor,
            style = Stroke(width = strokeThickness)
        )
    }
}