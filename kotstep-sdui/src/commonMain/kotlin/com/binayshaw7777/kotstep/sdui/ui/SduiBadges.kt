package com.binayshaw7777.kotstep.sdui.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SduiErrorBadge(
    modifier: Modifier = Modifier,
    tint: Color = Color(0xFFE53935),
    badgeSize: Dp = 16.dp
) {
    Box(
        modifier = modifier.size(badgeSize),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "!",
            color = tint,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun SduiLockBadge(
    modifier: Modifier = Modifier,
    tint: Color = Color(0xFF757575),
    badgeSize: Dp = 16.dp
) {
    Canvas(modifier = modifier.size(badgeSize)) {
        val w = size.width
        val h = size.height

        // Lock shackle (top half)
        drawArc(
            color = tint,
            startAngle = 180f,
            sweepAngle = 180f,
            useCenter = false,
            topLeft = Offset(w * 0.25f, h * 0.1f),
            size = Size(w * 0.5f, h * 0.5f),
            style = Stroke(width = w * 0.14f, cap = StrokeCap.Round)
        )

        // Lock body (bottom half)
        drawRoundRect(
            color = tint,
            topLeft = Offset(w * 0.15f, h * 0.45f),
            size = Size(w * 0.7f, h * 0.5f),
            cornerRadius = CornerRadius(w * 0.12f, w * 0.12f)
        )
    }
}

@Composable
fun SduiSkippedBadge(
    modifier: Modifier = Modifier,
    tint: Color = Color(0xFF9E9E9E),
    badgeSize: Dp = 16.dp
) {
    Box(
        modifier = modifier.size(badgeSize),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "-",
            color = tint,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
    }
}
