package com.binayshaw7777.kotstep.v2.model.style

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


/**
 * Represents the different types of lines that can be drawn.
 *
 * This sealed class defines the possible line styles:
 * - [Solid]: A continuous, unbroken line.
 * - [Dashed]: A line consisting of alternating dashes and gaps.
 * - [Dotted]: A line consisting of dots separated by gaps.
 *
 * @since 3.0.0
 */
@Immutable
sealed class LineType {
    @Immutable
    data object Solid : LineType()

    @Immutable
    data class Dashed(
        val dashLength: Dp = 1.dp,
        val gapLength: Dp = 15.dp
    ) : LineType()

    @Immutable
    data class Dotted(
        val gapLength: Dp = 8.dp
    ) : LineType()
}