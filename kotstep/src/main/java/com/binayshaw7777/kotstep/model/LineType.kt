package com.binayshaw7777.kotstep.model

/**
 * An Enum class for LineType
 *
 * SOLID -> Solid Line without any breaks (Default)
 *
 * DOTTED -> Dotted Line with small breaks
 *
 * DASHED -> Dashed Line with larger breaks
 *
 */
@Deprecated(
    message = "Use the new KotStep component package: import com.binayshaw7777.kotstep.v2.model.style.LineType which provides more functionality with version 3.0.0.",
    replaceWith = ReplaceWith("com.binayshaw7777.kotstep.v2.model.style.LineType"),
    level = DeprecationLevel.WARNING
)
enum class LineType {
    SOLID,
    DOTTED,
    DASHED
}