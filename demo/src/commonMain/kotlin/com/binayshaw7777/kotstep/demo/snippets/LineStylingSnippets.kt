package com.binayshaw7777.kotstep.demo.snippets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import com.binayshaw7777.kotstep.v3.KotStep
import com.binayshaw7777.kotstep.v3.model.step.StepLayoutStyle
import com.binayshaw7777.kotstep.v3.model.style.KotStepStyle
import com.binayshaw7777.kotstep.v3.model.style.LineStyle
import com.binayshaw7777.kotstep.v3.model.style.LineStyles
import com.binayshaw7777.kotstep.v3.model.style.LineType
import com.binayshaw7777.kotstep.v3.util.ExperimentalKotStep

/**
 * Dashed and Dotted Lines Stepper Snippet
 *
 * Demonstrates how to configure dashed and dotted line styles between steps.
 *
 * Options:
 * - [LineType.Dashed(dashLength, gapLength)]: Configures dash length and gap size.
 * - [LineType.Dotted(gapLength)]: Configures dot gaps.
 * - [LineStyle.lineStrokeCap]: Configures cap shape (Round, Square, Butt).
 */
@OptIn(ExperimentalKotStep::class)
@Composable
fun DashedAndDottedLineSnippet(
    modifier: Modifier = Modifier
) {
    val step by remember { mutableFloatStateOf(1f) }

    KotStep(
        modifier = modifier.fillMaxWidth().padding(16.dp),
        currentStep = { step },
        style = KotStepStyle(
            stepLayoutStyle = StepLayoutStyle.Horizontal,
            lineStyle = LineStyles.default().copy(
                onTodo = LineStyle(
                    lineLength = 60.dp,
                    lineThickness = 3.dp,
                    lineColor = Color(0xFFCBD5E1),
                    lineType = LineType.Dotted(gapLength = 6.dp)
                ),
                onCurrent = LineStyle(
                    lineLength = 60.dp,
                    lineThickness = 3.dp,
                    lineColor = Color(0xFFCBD5E1),
                    progressColor = Color(0xFFF59E0B),
                    lineType = LineType.Dashed(dashLength = 6.dp, gapLength = 6.dp),
                    progressType = LineType.Dashed(dashLength = 6.dp, gapLength = 6.dp)
                ),
                onDone = LineStyle(
                    lineLength = 60.dp,
                    lineThickness = 3.dp,
                    lineColor = Color(0xFF10B981),
                    progressColor = Color(0xFF10B981),
                    lineType = LineType.Solid
                )
            )
        )
    ) {
        step(title = "1", trailingLabel = { Text("Design") })
        step(title = "2", trailingLabel = { Text("Code") })
        step(title = "3", trailingLabel = { Text("Test") })
        step(title = "4", trailingLabel = { Text("Deploy") })
    }
}
