package com.binayshaw7777.kotstep.components.tabs

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

/**
 * Represents the done tab in a tab stepper.
 *
 * @param circleColor The color of the circle.
 * @param showTick Whether to show the tick icon.
 * @param checkMarkColor The color of the tick icon.
 * @param stepShape The shape of the step.
 */
@Composable
internal fun DoneTab(
    circleColor: Color = Color.Green,
    showTick: Boolean = false,
    checkMarkColor: Color = Color.White,
    stepShape: Shape = CircleShape
) {
    val painter = rememberVectorPainter(Icons.Default.Done)

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .clip(stepShape)
    ) {
        drawOutline(
            outline = stepShape.createOutline(
                size,
                layoutDirection = LayoutDirection.Ltr,
                density = this
            ),
            color = circleColor,
            style = Fill
        )

        if (showTick) {
            val iconSize = (size.minDimension * 0.65f)
            val iconOffset = (size.minDimension - iconSize) / 2f

            translate(left = iconOffset, top = iconOffset) {
                with(painter) {
                    draw(
                        size = Size(iconSize, iconSize),
                        alpha = 1f,
                        colorFilter = ColorFilter.tint(checkMarkColor)
                    )
                }
            }
        }
    }
}