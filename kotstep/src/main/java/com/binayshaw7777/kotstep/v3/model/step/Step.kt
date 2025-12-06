package com.binayshaw7777.kotstep.v3.model.step

import androidx.compose.runtime.Composable
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
 * @property isCollapsible Set true to allow the progress to collapse on click.
 *
 * @since 3.0.0
 *
 */
internal data class Step(
    var title: String?,
    var content: (@Composable () -> Unit)?,
    var icon: ImageVector?,
    var onClick: (() -> Unit)?,
    var isCollapsible: Boolean = false,
    var label: (@Composable () -> Unit)?,
    var onDone: () -> Unit = {}
) {

    constructor(
        title: String,
        onClick: () -> Unit = {},
        label: (@Composable () -> Unit)? = null,
        isCollapsible: Boolean = false,
        onDone: () -> Unit = {}
    ) : this(title, null, null, onClick, isCollapsible, label, onDone) {
        this.title = title
        this.onClick = onClick
        this.isCollapsible = isCollapsible
        this.label = label
        this.onDone = onDone
    }

    constructor(
        imageVectorIcon: ImageVector,
        onClick: () -> Unit = {},
        isCollapsible: Boolean = false,
        label: (@Composable () -> Unit)? = null,
        onDone: () -> Unit = {}
    ) : this(null, null, imageVectorIcon, onClick, isCollapsible, label, onDone) {
        this.icon = imageVectorIcon
        this.onClick = onClick
        this.isCollapsible = isCollapsible
        this.label = label
        this.onDone = onDone
    }

    constructor(
        content: (@Composable () -> Unit)? = null,
        onClick: () -> Unit = {},
        isCollapsible: Boolean = false,
        label: (@Composable () -> Unit)? = null,
        onDone: () -> Unit = {}
    ) : this(null, content, null, onClick, isCollapsible, label, onDone) {
        this.content = content
        this.onClick = onClick
        this.isCollapsible = isCollapsible
        this.label = label
        this.onDone = onDone
    }
}