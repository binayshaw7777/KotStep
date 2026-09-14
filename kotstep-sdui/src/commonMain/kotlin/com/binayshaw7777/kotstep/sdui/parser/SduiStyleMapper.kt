package com.binayshaw7777.kotstep.sdui.parser

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.binayshaw7777.kotstep.sdui.model.*
import com.binayshaw7777.kotstep.sdui.resolver.DefaultSduiColorResolver
import com.binayshaw7777.kotstep.sdui.resolver.SduiColorResolver
import com.binayshaw7777.kotstep.v3.model.step.StepLayoutStyle
import com.binayshaw7777.kotstep.v3.model.style.*
import com.binayshaw7777.kotstep.v3.util.ExperimentalKotStep

@OptIn(ExperimentalKotStep::class)
object SduiStyleMapper {

    fun mapStyle(
        sduiStyle: SduiStyle,
        orientation: SduiOrientation,
        colorResolver: SduiColorResolver = DefaultSduiColorResolver
    ): KotStepStyle {
        val layoutStyle = when (orientation) {
            SduiOrientation.HORIZONTAL -> StepLayoutStyle.Horizontal
            SduiOrientation.VERTICAL -> StepLayoutStyle.Vertical
        }

        val stepStyles = mapStepStyles(sduiStyle.stepStyle, colorResolver)
        val lineStyles = mapLineStyles(sduiStyle.lineStyle, colorResolver)

        return KotStepStyle(
            stepLayoutStyle = layoutStyle,
            itemPadding = sduiStyle.itemPaddingDp.dp,
            showCheckMarkOnDone = sduiStyle.showCheckMarkOnDone,
            ignoreCurrentState = sduiStyle.ignoreCurrentState,
            stepStyle = stepStyles,
            lineStyle = lineStyles
        )
    }

    private fun mapStepStyles(
        config: SduiStepStyleConfig,
        colorResolver: SduiColorResolver
    ): StepStyles {
        val defaultStyles = StepStyles.default()

        val todo = config.todo?.let { mapStepStyleItem(it, defaultStyles.onTodo, colorResolver) } ?: defaultStyles.onTodo
        val current = config.current?.let { mapStepStyleItem(it, defaultStyles.onCurrent, colorResolver) } ?: defaultStyles.onCurrent
        val done = config.done?.let { mapStepStyleItem(it, defaultStyles.onDone, colorResolver) } ?: defaultStyles.onDone

        return StepStyles(
            onTodo = todo,
            onCurrent = current,
            onDone = done
        )
    }

    private fun mapStepStyleItem(
        item: SduiStepStyleItem,
        fallback: StepStyle,
        colorResolver: SduiColorResolver
    ): StepStyle {
        val resolvedColor = item.colorHex?.let { colorResolver.resolve(it) } ?: fallback.stepColor
        val resolvedSize = item.sizeDp?.dp ?: fallback.stepSize
        val resolvedShape = item.shape?.let { mapShape(it) } ?: fallback.stepShape

        val border = if (item.borderWidthDp != null && item.borderWidthDp > 0) {
            val borderColor = item.borderColorHex?.let { colorResolver.resolve(it) } ?: Color.Unspecified
            BorderStyle(
                width = item.borderWidthDp.dp,
                color = borderColor,
                shape = resolvedShape
            )
        } else {
            fallback.borderStyle
        }

        return fallback.copy(
            stepColor = resolvedColor,
            stepSize = resolvedSize,
            stepShape = resolvedShape,
            borderStyle = border
        )
    }

    private fun mapLineStyles(
        config: SduiLineStyleConfig,
        colorResolver: SduiColorResolver
    ): LineStyles {
        val defaultStyles = LineStyles.default()

        val todo = config.todo?.let { mapLineStyleItem(it, defaultStyles.onTodo, colorResolver) } ?: defaultStyles.onTodo
        val current = config.current?.let { mapLineStyleItem(it, defaultStyles.onCurrent, colorResolver) } ?: defaultStyles.onCurrent
        val done = config.done?.let { mapLineStyleItem(it, defaultStyles.onDone, colorResolver) } ?: defaultStyles.onDone

        return LineStyles(
            onTodo = todo,
            onCurrent = current,
            onDone = done
        )
    }

    private fun mapLineStyleItem(
        item: SduiLineStyleItem,
        fallback: LineStyle,
        colorResolver: SduiColorResolver
    ): LineStyle {
        val lineColor = item.lineColorHex?.let { colorResolver.resolve(it) } ?: fallback.lineColor
        val progressColor = item.progressColorHex?.let { colorResolver.resolve(it) } ?: fallback.progressColor
        val thickness = item.thicknessDp?.dp ?: fallback.lineThickness
        val length = item.lengthDp?.dp ?: fallback.lineLength
        val lineType = item.lineType?.let { mapLineType(it) } ?: fallback.lineType
        val progressType = item.progressType?.let { mapLineType(it) } ?: fallback.progressType

        return fallback.copy(
            lineColor = lineColor,
            progressColor = progressColor,
            lineThickness = thickness,
            lineLength = length,
            lineType = lineType,
            progressType = progressType
        )
    }

    private fun mapShape(shape: SduiShape): Shape {
        return when (shape) {
            SduiShape.CIRCLE -> CircleShape
            SduiShape.ROUNDED_SQUARE -> RoundedCornerShape(6.dp)
            SduiShape.SQUARE -> RectangleShape
        }
    }

    private fun mapLineType(lineType: SduiLineType): LineType {
        return when (lineType) {
            SduiLineType.SOLID -> LineType.Solid
            SduiLineType.DASHED -> LineType.Dashed()
            SduiLineType.DOTTED -> LineType.Dotted()
        }
    }
}
