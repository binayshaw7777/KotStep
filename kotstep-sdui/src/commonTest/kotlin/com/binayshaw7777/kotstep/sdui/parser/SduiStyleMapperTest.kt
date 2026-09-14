package com.binayshaw7777.kotstep.sdui.parser

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.binayshaw7777.kotstep.sdui.model.*
import com.binayshaw7777.kotstep.sdui.resolver.DefaultSduiColorResolver
import com.binayshaw7777.kotstep.sdui.resolver.SduiColorResolver
import com.binayshaw7777.kotstep.v3.model.step.StepLayoutStyle
import com.binayshaw7777.kotstep.v3.model.style.LineType
import com.binayshaw7777.kotstep.v3.util.ExperimentalKotStep
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalKotStep::class)
class SduiStyleMapperTest {

    @Test
    fun defaultColorResolverParsesSixHex() {
        assertEquals(Color(0xFF1E88E5), DefaultSduiColorResolver.resolve("#1E88E5"))
        assertEquals(Color(0xFF1E88E5), DefaultSduiColorResolver.resolve("1E88E5"))
        assertEquals(Color(0xFFFFFFFF), DefaultSduiColorResolver.resolve("#ffffff"))
        assertEquals(Color(0xFF000000), DefaultSduiColorResolver.resolve("#000000"))
    }

    @Test
    fun defaultColorResolverParsesEightHex() {
        assertEquals(Color(0x801E88E5), DefaultSduiColorResolver.resolve("#801E88E5"))
        assertEquals(Color(0x801E88E5), DefaultSduiColorResolver.resolve("801E88E5"))
        assertEquals(Color(0x00000000), DefaultSduiColorResolver.resolve("#00000000"))
        assertEquals(Color(0xFFFFFFFF), DefaultSduiColorResolver.resolve("#FFFFFFFF"))
    }

    @Test
    fun defaultColorResolverParsesThreeHex() {
        // #F00 -> #FF0000
        assertEquals(Color(0xFFFF0000), DefaultSduiColorResolver.resolve("#F00"))
        assertEquals(Color(0xFFFF0000), DefaultSduiColorResolver.resolve("f00"))
        // #0F0 -> #00FF00
        assertEquals(Color(0xFF00FF00), DefaultSduiColorResolver.resolve("#0f0"))
        // #00F -> #0000FF
        assertEquals(Color(0xFF0000FF), DefaultSduiColorResolver.resolve("#00f"))
        // #FFF -> #FFFFFF
        assertEquals(Color(0xFFFFFFFF), DefaultSduiColorResolver.resolve("#FFF"))
    }

    @Test
    fun defaultColorResolverFallsBackOnInvalidStrings() {
        assertEquals(Color.Unspecified, DefaultSduiColorResolver.resolve(null))
        assertEquals(Color.Unspecified, DefaultSduiColorResolver.resolve(""))
        assertEquals(Color.Unspecified, DefaultSduiColorResolver.resolve("   "))
        assertEquals(Color.Unspecified, DefaultSduiColorResolver.resolve("not-a-color"))
        assertEquals(Color.Unspecified, DefaultSduiColorResolver.resolve("#12"))
        assertEquals(Color.Unspecified, DefaultSduiColorResolver.resolve("#12345"))
        assertEquals(Color.Unspecified, DefaultSduiColorResolver.resolve("#1234567"))
        assertEquals(Color.Unspecified, DefaultSduiColorResolver.resolve("#123456789"))
        assertEquals(Color.Unspecified, DefaultSduiColorResolver.resolve("#GGGGGG"))
    }

    @Test
    fun mapStyleOrientations() {
        val style = SduiStyle()
        val horizontal = SduiStyleMapper.mapStyle(style, SduiOrientation.HORIZONTAL)
        assertEquals(StepLayoutStyle.Horizontal, horizontal.stepLayoutStyle)

        val vertical = SduiStyleMapper.mapStyle(style, SduiOrientation.VERTICAL)
        assertEquals(StepLayoutStyle.Vertical, vertical.stepLayoutStyle)
    }

    @Test
    fun mapStyleLineTypes() {
        val sduiStyle = SduiStyle(
            lineStyle = SduiLineStyleConfig(
                todo = SduiLineStyleItem(
                    lineType = SduiLineType.SOLID,
                    progressType = SduiLineType.SOLID,
                    lineColorHex = "#E0E0E0",
                    thicknessDp = 2,
                    lengthDp = 20
                ),
                current = SduiLineStyleItem(
                    lineType = SduiLineType.DASHED,
                    progressType = SduiLineType.DASHED,
                    lineColorHex = "#CCCCCC",
                    progressColorHex = "#1E88E5",
                    thicknessDp = 3,
                    lengthDp = 24
                ),
                done = SduiLineStyleItem(
                    lineType = SduiLineType.DOTTED,
                    progressType = SduiLineType.DOTTED,
                    lineColorHex = "#43A047",
                    thicknessDp = 4,
                    lengthDp = 28
                )
            )
        )

        val mapped = SduiStyleMapper.mapStyle(sduiStyle, SduiOrientation.HORIZONTAL)

        // Todo line
        assertEquals(LineType.Solid, mapped.lineStyle.onTodo.lineType)
        assertEquals(LineType.Solid, mapped.lineStyle.onTodo.progressType)
        assertEquals(Color(0xFFE0E0E0), mapped.lineStyle.onTodo.lineColor)
        assertEquals(2.dp, mapped.lineStyle.onTodo.lineThickness)
        assertEquals(20.dp, mapped.lineStyle.onTodo.lineLength)

        // Current line
        assertEquals(LineType.Dashed(), mapped.lineStyle.onCurrent.lineType)
        assertEquals(LineType.Dashed(), mapped.lineStyle.onCurrent.progressType)
        assertEquals(Color(0xFFCCCCCC), mapped.lineStyle.onCurrent.lineColor)
        assertEquals(Color(0xFF1E88E5), mapped.lineStyle.onCurrent.progressColor)
        assertEquals(3.dp, mapped.lineStyle.onCurrent.lineThickness)
        assertEquals(24.dp, mapped.lineStyle.onCurrent.lineLength)

        // Done line
        assertEquals(LineType.Dotted(), mapped.lineStyle.onDone.lineType)
        assertEquals(LineType.Dotted(), mapped.lineStyle.onDone.progressType)
        assertEquals(Color(0xFF43A047), mapped.lineStyle.onDone.lineColor)
        assertEquals(4.dp, mapped.lineStyle.onDone.lineThickness)
        assertEquals(28.dp, mapped.lineStyle.onDone.lineLength)
    }

    @Test
    fun mapStyleShapesAndBorders() {
        val sduiStyle = SduiStyle(
            itemPaddingDp = 12,
            showCheckMarkOnDone = false,
            ignoreCurrentState = true,
            stepStyle = SduiStepStyleConfig(
                todo = SduiStepStyleItem(
                    shape = SduiShape.CIRCLE,
                    colorHex = "#E0E0E0",
                    sizeDp = 24
                ),
                current = SduiStepStyleItem(
                    shape = SduiShape.ROUNDED_SQUARE,
                    colorHex = "#1E88E5",
                    sizeDp = 28,
                    borderWidthDp = 2,
                    borderColorHex = "#1565C0"
                ),
                done = SduiStepStyleItem(
                    shape = SduiShape.SQUARE,
                    colorHex = "#43A047",
                    sizeDp = 24,
                    borderWidthDp = 0
                )
            )
        )

        val mapped = SduiStyleMapper.mapStyle(sduiStyle, SduiOrientation.HORIZONTAL)

        assertEquals(12.dp, mapped.itemPadding)
        assertEquals(false, mapped.showCheckMarkOnDone)
        assertEquals(true, mapped.ignoreCurrentState)

        // Todo step
        assertEquals(CircleShape, mapped.stepStyle.onTodo.stepShape)
        assertEquals(Color(0xFFE0E0E0), mapped.stepStyle.onTodo.stepColor)
        assertEquals(24.dp, mapped.stepStyle.onTodo.stepSize)

        // Current step
        assertEquals(RoundedCornerShape(6.dp), mapped.stepStyle.onCurrent.stepShape)
        assertEquals(Color(0xFF1E88E5), mapped.stepStyle.onCurrent.stepColor)
        assertEquals(28.dp, mapped.stepStyle.onCurrent.stepSize)
        assertEquals(2.dp, mapped.stepStyle.onCurrent.borderStyle.width)
        assertEquals(Color(0xFF1565C0), mapped.stepStyle.onCurrent.borderStyle.color)
        assertEquals(RoundedCornerShape(6.dp), mapped.stepStyle.onCurrent.borderStyle.shape)

        // Done step
        assertEquals(RectangleShape, mapped.stepStyle.onDone.stepShape)
        assertEquals(Color(0xFF43A047), mapped.stepStyle.onDone.stepColor)
        assertEquals(24.dp, mapped.stepStyle.onDone.stepSize)
    }

    @Test
    fun mapStyleUsesCustomColorResolver() {
        val customResolver = SduiColorResolver { hex ->
            if (hex == "brand-primary") Color(0xFF6200EE) else Color.Black
        }

        val sduiStyle = SduiStyle(
            stepStyle = SduiStepStyleConfig(
                current = SduiStepStyleItem(colorHex = "brand-primary")
            )
        )

        val mapped = SduiStyleMapper.mapStyle(sduiStyle, SduiOrientation.HORIZONTAL, customResolver)
        assertEquals(Color(0xFF6200EE), mapped.stepStyle.onCurrent.stepColor)
    }
}
