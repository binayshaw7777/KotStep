package com.binayshaw7777.kotstep.components.tabs

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.unit.LayoutDirection

/**
 * Represents the current tab in a tab stepper.
 *
 * @param circleColor The color of the circle.
 * @param strokeThickness The thickness of the stroke.
 * @param stepShape The shape of the step.
 */
@Composable
internal fun CurrentTab(
    circleColor: Color = Color.Blue,
    strokeThickness: Float = 4f,
    stepShape: Shape = CircleShape
) {

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .clip(stepShape)
    ) {
        drawOutline(
            outline = stepShape.createOutline(size, layoutDirection = LayoutDirection.Ltr, density = this),
            color = circleColor,
            style = Stroke(width = strokeThickness)
        )

        val innerSize = Size(size.width * 0.7f, size.height * 0.7f)
        val offsetX = (size.width - innerSize.width) / 2f
        val offsetY = (size.height - innerSize.height) / 2f

        translate(left = offsetX, top = offsetY) {
            drawOutline(
                outline = stepShape.createOutline(
                    size = innerSize,
                    layoutDirection = LayoutDirection.Ltr,
                    density = this
                ),
                color = circleColor,
                style = Fill
            )
        }
    }
}