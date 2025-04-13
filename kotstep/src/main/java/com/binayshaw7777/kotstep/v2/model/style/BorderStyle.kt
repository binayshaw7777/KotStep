package com.binayshaw7777.kotstep.v2.model.style

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class BorderStyle(
    val width: Dp = 1.dp,
    val color: Color = Color.Unspecified,
    val shape: Shape = CircleShape
)