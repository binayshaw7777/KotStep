package com.binayshaw7777.kotstep.sdui.resolver

import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Interface to resolve string icon identifiers into Compose [ImageVector]s.
 */
fun interface SduiIconResolver {
    fun resolve(iconName: String): ImageVector?
}

/**
 * Default icon resolver returning null (so standard text/indicator is used unless overridden).
 */
object DefaultSduiIconResolver : SduiIconResolver {
    override fun resolve(iconName: String): ImageVector? = null
}
