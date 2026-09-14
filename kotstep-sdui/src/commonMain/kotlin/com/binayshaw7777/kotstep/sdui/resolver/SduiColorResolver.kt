package com.binayshaw7777.kotstep.sdui.resolver

import androidx.compose.ui.graphics.Color

/**
 * Interface to resolve hexadecimal color strings into Compose [Color]s.
 */
fun interface SduiColorResolver {
    fun resolve(colorHex: String?): Color
}

/**
 * Default implementation supporting #RRGGBB, #AARRGGBB, and #RGB formats with sensible fallbacks.
 */
object DefaultSduiColorResolver : SduiColorResolver {
    override fun resolve(colorHex: String?): Color {
        if (colorHex.isNullOrBlank()) return Color.Unspecified

        val clean = colorHex.trim().removePrefix("#")
        return try {
            when (clean.length) {
                3 -> {
                    // E.g. "F00" -> "FF0000"
                    val r = clean.substring(0, 1).repeat(2)
                    val g = clean.substring(1, 2).repeat(2)
                    val b = clean.substring(2, 3).repeat(2)
                    val hexVal = "$r$g$b".toLong(16)
                    Color(hexVal or 0xFF000000)
                }
                6 -> {
                    val hexVal = clean.toLong(16)
                    Color(hexVal or 0xFF000000)
                }
                8 -> {
                    val hexVal = clean.toLong(16)
                    Color(hexVal)
                }
                else -> Color.Unspecified
            }
        } catch (e: Exception) {
            Color.Unspecified
        }
    }
}
