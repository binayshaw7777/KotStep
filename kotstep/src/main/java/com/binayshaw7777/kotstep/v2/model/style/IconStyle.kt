package com.binayshaw7777.kotstep.v2.model.style

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Represents the styling for an icon, including its tint and size.
 *
 * @property iconTint The color to tint the icon. Defaults to [Color.Unspecified], which means no tinting is applied.
 * @property iconSize The size of the icon, represented as [Dp]. Defaults to 16.dp.
 *
 * @since 2.4.0
 */
data class IconStyle(
    val iconTint: Color = Color.Unspecified,
    val iconSize: Dp = 16.dp
)