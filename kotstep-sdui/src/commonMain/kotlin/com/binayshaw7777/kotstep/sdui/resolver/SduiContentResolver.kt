package com.binayshaw7777.kotstep.sdui.resolver

import androidx.compose.runtime.Composable

/**
 * Interface to resolve custom content types and key-value properties into Compose content lambdas.
 */
fun interface SduiContentResolver {
    fun resolve(contentType: String, props: Map<String, String>): (@Composable () -> Unit)?
}

/**
 * Default content resolver returning null.
 */
object DefaultSduiContentResolver : SduiContentResolver {
    override fun resolve(contentType: String, props: Map<String, String>): (@Composable () -> Unit)? = null
}
