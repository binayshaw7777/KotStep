package com.binayshaw7777.kotstep.v3.model.step

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

internal data class Step(
    var title: String?,
    var content: (@Composable () -> Unit)?,
    var icon: ImageVector?,
    var leadingLabel: (@Composable () -> Unit)?,
    var onClick: (() -> Unit)?,
    var isCollapsible: Boolean = false,
    var trailingLabel: (@Composable () -> Unit)?,
    var onDone: () -> Unit = {}
) {

    constructor(
        title: String,
        leadingLabel: (@Composable () -> Unit)? = null,
        onClick: () -> Unit = {},
        trailingLabel: (@Composable () -> Unit)? = null,
        isCollapsible: Boolean = false,
        onDone: () -> Unit = {}
    ) : this(title, null, null, leadingLabel, onClick, isCollapsible, trailingLabel, onDone)

    constructor(
        imageVectorIcon: ImageVector,
        leadingLabel: (@Composable () -> Unit)? = null,
        onClick: () -> Unit = {},
        isCollapsible: Boolean = false,
        trailingLabel: (@Composable () -> Unit)? = null,
        onDone: () -> Unit = {}
    ) : this(null, null, imageVectorIcon, leadingLabel, onClick, isCollapsible, trailingLabel, onDone)

    constructor(
        content: (@Composable () -> Unit)? = null,
        leadingLabel: (@Composable () -> Unit)? = null,
        onClick: () -> Unit = {},
        isCollapsible: Boolean = false,
        trailingLabel: (@Composable () -> Unit)? = null,
        onDone: () -> Unit = {}
    ) : this(null, content, null, leadingLabel, onClick, isCollapsible, trailingLabel, onDone)
}
