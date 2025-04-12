package com.binayshaw7777.kotstep.v2.model.step

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * [Step] is data class used to create one step.
 * Each step can either have a text, icon or none.
 * If both text and icon are not passed, it shows a bulletin style step.
 *
 * @property title The title of the step.
 * @property onClick The callback to be invoked when the step is clicked.
 * @property content The content inside the step.
 * @property onDone The callback to check if the step is done.
 *
 * @since 2.4.0
 *
 */
data class Step(
    var title: String?,
    var content: (@Composable () -> Unit)?,
//    var painterIcon: Painter?,
    var imageVectorIcon: ImageVector?,
    var onClick: (() -> Unit)?,
    var label: (@Composable () -> Unit)?,
    var onDone: () -> Unit = {}
) {

    // For title
    constructor(
        title: String,
        onClick: () -> Unit = {},
        label: (@Composable () -> Unit)? = null,
        onDone: () -> Unit = {}
    ) : this(title, null, null, onClick, label, onDone) {
        this.title = title
        this.onClick = onClick
        this.label = label
        this.onDone = onDone
    }

    constructor(
        painterIcon: Painter,
        onClick: () -> Unit = {},
        label: (@Composable () -> Unit)? = null,
        onDone: () -> Unit = {}
    ) : this(null, null, null, onClick, label, onDone) {
        this.onClick = onClick
        this.label = label
        this.onDone = onDone
    }

    constructor(
        imageVectorIcon: ImageVector,
        onClick: () -> Unit = {},
        label: (@Composable () -> Unit)? = null,
        onDone: () -> Unit = {}
    ) : this(null, null, imageVectorIcon, onClick, label, onDone) {
        this.imageVectorIcon = imageVectorIcon
        this.onClick = onClick
        this.label = label
        this.onDone = onDone
    }

    constructor(
        content: (@Composable () -> Unit)? = null,
        onClick: () -> Unit = {},
        label: (@Composable () -> Unit)? = null,
        onDone: () -> Unit = {}
    ) : this(null, content, null, onClick, label, onDone) {
        this.content = content
        this.onClick = onClick
        this.label = label
        this.onDone = onDone
    }
}