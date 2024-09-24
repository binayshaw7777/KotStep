package com.binayshaw7777.kotstep.util

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

/**
 * A modifier that makes a Composable clickable without any ripple effect.
 *
 * Usage
 *
 * ```
 * Modifier.noRippleClickable {
 *    // Do something
 * }
 * ```
 * @property onClick The lambda to be invoked or executed when the Composable is clicked.
 * @see Modifier.clickable
 */
internal fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    this.clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}

internal fun Any.log(tag: String = "My Logger") {
    Log.d(tag, this.toString())
}


// Helper function to apply border if stroke color is provided
internal fun Modifier.maybeApplyBorder(strokeColor: Color?, strokeThickness: Float, shape: Shape) =
    strokeColor?.let {
        this.border(BorderStroke(strokeThickness.dp, it), shape)
    } ?: this