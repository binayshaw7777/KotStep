package com.binayshaw7777.kotstep.v3.util

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role

internal object Util {

    @Composable
    fun Modifier.onClick(onClick: () -> Unit): Modifier = this.clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() },
        role = Role.Button
    ) {
        onClick()
    }
}