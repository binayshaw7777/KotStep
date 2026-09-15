package com.binayshaw7777.kotstep.demo.snippets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
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
 * Fintech Dark Theme Stepper Snippet
 *
 * Demonstrates a sleek dark mode KYC / authentication stepper
 * with vibrant cyan accents and slate muted tones.
 */
@OptIn(ExperimentalKotStep::class)
@Composable
fun FintechDarkThemeSnippet(
    modifier: Modifier = Modifier
) {
    val currentStep by remember { mutableFloatStateOf(1.3f) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFF0F172A), RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        KotStep(
            currentStep = { currentStep },
            style = KotStepStyle(
                stepLayoutStyle = StepLayoutStyle.Horizontal,
                stepStyle = StepStyles.default().copy(
                    onTodo = StepStyle(
                        stepSize = 34.dp,
                        stepColor = Color(0xFF334155),
                        textStyle = androidx.compose.ui.text.TextStyle(color = Color(0xFF94A3B8), fontSize = 13.sp)
                    ),
                    onCurrent = StepStyle(
                        stepSize = 38.dp,
                        stepColor = Color(0xFF0284C7),
                        textStyle = androidx.compose.ui.text.TextStyle(color = Color.White, fontSize = 13.sp),
                        borderStyle = BorderStyle(width = 3.dp, color = Color(0xFF38BDF8))
                    ),
                    onDone = StepStyle(
                        stepSize = 34.dp,
                        stepColor = Color(0xFF10B981),
                        textStyle = androidx.compose.ui.text.TextStyle(color = Color.White, fontSize = 13.sp)
                    )
                ),
                lineStyle = LineStyles.default().copy(
                    onTodo = LineStyle(
                        lineLength = 60.dp,
                        lineThickness = 4.dp,
                        lineColor = Color(0xFF334155),
                        lineStrokeCap = StrokeCap.Round
                    ),
                    onCurrent = LineStyle(
                        lineLength = 60.dp,
                        lineThickness = 4.dp,
                        lineColor = Color(0xFF334155),
                        progressColor = Color(0xFF0284C7),
                        lineStrokeCap = StrokeCap.Round,
                        progressStrokeCap = StrokeCap.Round
                    ),
                    onDone = LineStyle(
                        lineLength = 60.dp,
                        lineThickness = 4.dp,
                        lineColor = Color(0xFF10B981),
                        progressColor = Color(0xFF10B981),
                        lineStrokeCap = StrokeCap.Round
                    )
                )
            )
        ) {
            step(title = "1", trailingLabel = { Text("Personal", color = Color.White, fontSize = 12.sp) })
            step(title = "2", trailingLabel = { Text("Identity", color = Color(0xFF38BDF8), fontSize = 12.sp) })
            step(title = "3", trailingLabel = { Text("Biometrics", color = Color(0xFF64748B), fontSize = 12.sp) })
            step(title = "4", trailingLabel = { Text("Review", color = Color(0xFF64748B), fontSize = 12.sp) })
        }
    }
}

/**
 * Dark Mode Vertical CI/CD Pipeline Snippet
 *
 * Demonstrates a dark terminal DevOps build/deploy timeline with duration badges.
 */
@OptIn(ExperimentalKotStep::class)
@Composable
fun VerticalDarkPipelineSnippet(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFF0F172A), RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        KotStep(
            currentStep = { 2f },
            style = KotStepStyle(
                stepLayoutStyle = StepLayoutStyle.Vertical,
                stepStyle = StepStyles.default().copy(
                    onTodo = StepStyle(stepSize = 28.dp, stepColor = Color(0xFF1E293B), textStyle = androidx.compose.ui.text.TextStyle(color = Color(0xFF64748B), fontWeight = FontWeight.Bold, fontSize = 12.sp), borderStyle = BorderStyle(width = 1.dp, color = Color(0xFF334155))),
                    onCurrent = StepStyle(stepSize = 32.dp, stepColor = Color(0xFF06B6D4), textStyle = androidx.compose.ui.text.TextStyle(color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 13.sp), borderStyle = BorderStyle(width = 3.dp, color = Color(0xFF67E8F9))),
                    onDone = StepStyle(stepSize = 28.dp, stepColor = Color(0xFF10B981), textStyle = androidx.compose.ui.text.TextStyle(color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp))
                ),
                lineStyle = LineStyles.default().copy(
                    onTodo = LineStyle(lineLength = 32.dp, lineThickness = 3.dp, lineColor = Color(0xFF1E293B), lineStrokeCap = StrokeCap.Round),
                    onCurrent = LineStyle(lineLength = 32.dp, lineThickness = 3.dp, lineColor = Color(0xFF1E293B), progressColor = Color(0xFF06B6D4), lineStrokeCap = StrokeCap.Round),
                    onDone = LineStyle(lineLength = 32.dp, lineThickness = 3.dp, lineColor = Color(0xFF10B981), progressColor = Color(0xFF10B981), lineStrokeCap = StrokeCap.Round)
                )
            )
        ) {
            step(
                title = "1",
                leadingLabel = { Text("14s", fontSize = 12.sp, color = Color(0xFF34D399), modifier = Modifier.padding(end = 16.dp, top = 4.dp)) },
                trailingLabel = {
                    Column(modifier = Modifier.padding(start = 14.dp)) {
                        Text("Static Code Analysis & Lint", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFFF1F5F9))
                        Text("ktlint & detekt passed with 0 warnings", fontSize = 12.sp, color = Color(0xFF94A3B8))
                    }
                }
            )
            step(
                title = "2",
                leadingLabel = { Text("52s", fontSize = 12.sp, color = Color(0xFF34D399), modifier = Modifier.padding(end = 16.dp, top = 4.dp)) },
                trailingLabel = {
                    Column(modifier = Modifier.padding(start = 14.dp)) {
                        Text("Multiplatform Test Suites", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFFF1F5F9))
                        Text("Android, Desktop & iOS simulator tests passed", fontSize = 12.sp, color = Color(0xFF94A3B8))
                    }
                }
            )
            step(
                title = "3",
                leadingLabel = { Text("Running", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF22D3EE), modifier = Modifier.padding(end = 16.dp, top = 4.dp)) },
                trailingLabel = {
                    Column(modifier = Modifier.padding(start = 14.dp)) {
                        Text("Docker Container Build", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF22D3EE))
                        Text("Building OCI multi-arch image layer 4/7", fontSize = 12.sp, color = Color(0xFF94A3B8))
                    }
                }
            )
            step(
                title = "4",
                leadingLabel = { Text("Queued", fontSize = 12.sp, color = Color(0xFF64748B), modifier = Modifier.padding(end = 16.dp, top = 4.dp)) },
                trailingLabel = {
                    Column(modifier = Modifier.padding(start = 14.dp)) {
                        Text("Deploy to Production", fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = Color(0xFF64748B))
                        Text("Canary deployment to cluster us-central1", fontSize = 12.sp, color = Color(0xFF64748B))
                    }
                }
            )
        }
    }
}

