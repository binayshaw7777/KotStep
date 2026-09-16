package com.binayshaw7777.kotstep.demo.snippets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.binayshaw7777.kotstep.v3.KotStep
import com.binayshaw7777.kotstep.v3.model.step.StepLayoutStyle
import com.binayshaw7777.kotstep.v3.model.style.BorderStyle
import com.binayshaw7777.kotstep.v3.model.style.KotStepStyle
import com.binayshaw7777.kotstep.v3.model.style.LineStyle
import com.binayshaw7777.kotstep.v3.model.style.LineStyles
import com.binayshaw7777.kotstep.v3.model.style.StepStyle
import com.binayshaw7777.kotstep.v3.model.style.StepStyles
import com.binayshaw7777.kotstep.v3.util.ExperimentalKotStep

/**
 * Collapsible Order Details Stepper Snippet
 *
 * Demonstrates a vertical step with [isCollapsible = true]. Tapping the step indicator
 * or row toggles visibility of the trailing card content with built-in transition animations.
 */
@OptIn(ExperimentalKotStep::class)
@Composable
fun CollapsibleOrderDetailsSnippet(
    modifier: Modifier = Modifier
) {
    val currentStep by remember { mutableFloatStateOf(1f) }

    KotStep(
        modifier = modifier.fillMaxWidth().padding(16.dp),
        currentStep = { currentStep },
        style = KotStepStyle(
            stepLayoutStyle = StepLayoutStyle.Vertical,
            stepStyle = StepStyles.default().copy(
                onTodo = StepStyle(stepSize = 30.dp, stepColor = Color(0xFFCBD5E1)),
                onCurrent = StepStyle(
                    stepSize = 32.dp,
                    stepColor = Color(0xFF0D9488),
                    borderStyle = BorderStyle(width = 3.dp, color = Color(0xFF99F6E4))
                ),
                onDone = StepStyle(stepSize = 30.dp, stepColor = Color(0xFF10B981))
            ),
            lineStyle = LineStyles.default().copy(
                onTodo = LineStyle(lineLength = 40.dp, lineThickness = 3.dp, lineColor = Color(0xFFE2E8F0)),
                onCurrent = LineStyle(lineLength = 40.dp, lineThickness = 3.dp, lineColor = Color(0xFFE2E8F0), progressColor = Color(0xFF0D9488)),
                onDone = LineStyle(lineLength = 40.dp, lineThickness = 3.dp, lineColor = Color(0xFF10B981), progressColor = Color(0xFF10B981))
            )
        )
    ) {
        step(
            title = "1",
            trailingLabel = {
                Column(modifier = Modifier.padding(start = 14.dp)) {
                    Text("Account Setup", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF0F172A))
                    Text("Completed on 14 Sep, 10:30 AM", fontSize = 12.sp, color = Color(0xFF64748B))
                }
            }
        )
        step(
            title = "2",
            isCollapsible = true, // Tap to expand/collapse
            trailingLabel = {
                Column(modifier = Modifier.padding(start = 14.dp)) {
                    Text("Subscription Plan (Tap to Collapse)", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF0D9488))
                    Spacer(modifier = Modifier.height(4.dp))
                    androidx.compose.foundation.layout.Box(
                        modifier = Modifier
                            .background(Color.White, RoundedCornerShape(8.dp))
                            .border(1.dp, Color(0xFFCCFBF1), RoundedCornerShape(8.dp))
                            .padding(10.dp)
                    ) {
                        Column {
                            Text("Pro Plan — $19/month", fontWeight = FontWeight.SemiBold, fontSize = 12.sp, color = Color(0xFF0F172A))
                            Text("Includes unlimited multiplatform steppers & themes", fontSize = 11.sp, color = Color(0xFF64748B))
                        }
                    }
                }
            }
        )
        step(
            title = "3",
            trailingLabel = {
                Column(modifier = Modifier.padding(start = 14.dp)) {
                    Text("Billing Information", fontWeight = FontWeight.Medium, fontSize = 14.sp, color = Color(0xFF94A3B8))
                    Text("Next up after plan selection", fontSize = 12.sp, color = Color(0xFF94A3B8))
                }
            }
        )
    }
}
