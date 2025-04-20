package com.binayshaw7777.kotstep.v2.model.style

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Represents the style of a border.
 *
 * This data class encapsulates the visual properties of a border, including its
 * width, color, and shape. It's designed to be used with UI elements that can
 * display borders, allowing for customization of the border's appearance.
 *
 * @property width The width of the border. Defaults to 1.dp.
 * @property color The color of the border. Defaults to [Color.Unspecified], which
 *                 typically means no color is explicitly set, allowing the
 *                 border's appearance to be determined by other factors.
 * @property shape The shape of the border. Defaults to [CircleShape]. You can use any shape like [RoundedCornerShape()]
 *
 * @since 2.4.0
 */
data class BorderStyle(
    val width: Dp = 1.dp,
    val color: Color = Color.Unspecified,
    val shape: Shape = CircleShape
)