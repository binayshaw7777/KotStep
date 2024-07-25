package com.binayshaw7777.kotstep.util

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

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
fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    this.clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}

fun Any.log(tag: String = "My Logger") {
    Log.d(tag, this.toString())
}