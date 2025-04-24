package com.binayshaw7777.kotstep.v2.component.label

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize


/**
 * Composable function that displays a labeled content within a Box.
 *
 * This function provides a layout for displaying a label positioned at the top start
 * of a Box with a specified padding and allows observing the size changes of the content area.
 *
 * @param modifier The [Modifier] to be applied to the Box containing the label.
 *                 It's applied after the internal padding and size change listener.
 * @param label A composable lambda that defines the label content. This content will be
 *              placed at the top start of the Box.
 * @param onSizeChanged A callback function that is invoked when the size of the content area changes.
 *                      It provides the new [IntSize] of the content area.
 *
 * @since 2.4.0
 */
@Composable
internal fun LabelContent(
    modifier: Modifier = Modifier,
    label: @Composable () -> Unit,
    onSizeChanged: (IntSize) -> Unit
) {
    Box(
        modifier = Modifier
            .wrapContentSize()
            .onSizeChanged { onSizeChanged(it) }
            .then(modifier),
        contentAlignment = Alignment.Center
    ) {
        label()
    }
}