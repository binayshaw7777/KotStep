package com.binayshaw7777.kotstep.demo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

// Groww Theme Colors
val GROWW_BACKGROUND = Color(0xFF121212)
val GROWW_SURFACE = Color(0xFF1E1E1E)
val GROWW_GREEN = Color(0xFF00D09C)
val GROWW_TEXT_PRIMARY = Color.White
val GROWW_TEXT_SECONDARY = Color(0xFF9E9E9E)
val GROWW_DIVIDER = Color(0xFF2C2C2C)

// Playground Stepper Shapes
enum class DemoStepShape(val displayName: String) {
    CIRCLE("Circle"),
    ROUNDED("Rounded"),
    CUT_CORNER("Cut Corner"),
    SQUARE("Square");

    fun toShape(): Shape = when (this) {
        CIRCLE -> CircleShape
        ROUNDED -> RoundedCornerShape(8.dp)
        CUT_CORNER -> CutCornerShape(8.dp)
        SQUARE -> RectangleShape
    }
}

@Composable
fun Modifier.noRippleClick(onClick: () -> Unit): Modifier = this.clickable(
    indication = null,
    interactionSource = remember { MutableInteractionSource() }
) {
    onClick()
}
