package com.binayshaw7777.kotstep.demo.snippets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
 * Basic Horizontal Stepper Snippet
 *
 * Demonstrates a 4-step horizontal checkout flow with custom colors,
 * indicator sizes, active border stroke, and trailing step labels.
 *
 * Options:
 * - [currentStep]: Continuous Float value (e.g. 1.5f represents 50% between step 1 and 2).
 * - [StepStyle.borderStyle]: Adds an active ring around the current step indicator.
 * - [LineStyle.lineStrokeCap]: Controls line roundness (Round, Square, Butt).
 */
@OptIn(ExperimentalKotStep::class)
@Composable
fun HorizontalCheckoutSnippet(
    modifier: Modifier = Modifier
) {
    var step by remember { mutableFloatStateOf(1.5f) }

    Column(
        modifier = modifier.fillMaxWidth().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        KotStep(
            currentStep = { step },
            style = KotStepStyle(
                stepLayoutStyle = StepLayoutStyle.Horizontal,
                stepStyle = StepStyles.default().copy(
                    onTodo = StepStyle(
                        stepSize = 36.dp,
                        stepColor = Color(0xFFE2E8F0),
                        textStyle = androidx.compose.ui.text.TextStyle(color = Color(0xFF64748B), fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    ),
                    onCurrent = StepStyle(
                        stepSize = 40.dp,
                        stepColor = Color(0xFF2563EB),
                        textStyle = androidx.compose.ui.text.TextStyle(color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp),
                        borderStyle = BorderStyle(width = 3.dp, color = Color(0xFF93C5FD))
                    ),
                    onDone = StepStyle(
                        stepSize = 36.dp,
                        stepColor = Color(0xFF10B981),
                        textStyle = androidx.compose.ui.text.TextStyle(color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    )
                ),
                lineStyle = LineStyles.default().copy(
                    onTodo = LineStyle(
                        lineLength = 64.dp,
                        lineThickness = 4.dp,
                        lineColor = Color(0xFFCBD5E1),
                        lineStrokeCap = StrokeCap.Round
                    ),
                    onCurrent = LineStyle(
                        lineLength = 64.dp,
                        lineThickness = 4.dp,
                        lineColor = Color(0xFFE2E8F0),
                        progressColor = Color(0xFF2563EB),
                        lineStrokeCap = StrokeCap.Round,
                        progressStrokeCap = StrokeCap.Round
                    ),
                    onDone = LineStyle(
                        lineLength = 64.dp,
                        lineThickness = 4.dp,
                        lineColor = Color(0xFF10B981),
                        progressColor = Color(0xFF10B981),
                        lineStrokeCap = StrokeCap.Round
                    )
                )
            )
        ) {
            step(title = "1", trailingLabel = { Text("Cart", fontSize = 12.sp, fontWeight = FontWeight.Medium) })
            step(title = "2", trailingLabel = { Text("Shipping", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2563EB)) })
            step(title = "3", trailingLabel = { Text("Payment", fontSize = 12.sp, fontWeight = FontWeight.Medium) })
            step(title = "4", trailingLabel = { Text("Review", fontSize = 12.sp, fontWeight = FontWeight.Medium) })
        }
    }
}

/**
 * Interactive Horizontal Stepper Snippet with Navigation Controls
 *
 * Demonstrates wiring next/previous buttons and direct step click events
 * to animate stepper state.
 */
@OptIn(ExperimentalKotStep::class)
@Composable
fun InteractiveHorizontalSnippet(
    modifier: Modifier = Modifier
) {
    var stepIndex by remember { mutableFloatStateOf(0f) }
    val totalSteps = 4

    Column(
        modifier = modifier.fillMaxWidth().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        KotStep(
            currentStep = { stepIndex },
            style = KotStepStyle(stepLayoutStyle = StepLayoutStyle.Horizontal)
        ) {
            step(title = "Account", onClick = { stepIndex = 0f })
            step(title = "Profile", onClick = { stepIndex = 1f })
            step(title = "Preferences", onClick = { stepIndex = 2f })
            step(title = "Finish", onClick = { stepIndex = 3f })
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedButton(
                onClick = { if (stepIndex > 0f) stepIndex -= 1f },
                enabled = stepIndex > 0f
            ) {
                Text("Previous")
            }

            Button(
                onClick = { if (stepIndex < totalSteps.toFloat()) stepIndex += 1f },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB))
            ) {
                Text(if (stepIndex >= (totalSteps - 1).toFloat()) "Complete" else "Next")
            }
        }
    }
}

/**
 * Animated Fractional Progress Snippet
 *
 * Demonstrates how continuous Float progress values drive the line animation
 * between steps in real-time.
 */
@OptIn(ExperimentalKotStep::class)
@Composable
fun FractionalProgressSnippet(
    modifier: Modifier = Modifier
) {
    var progressValue by remember { mutableFloatStateOf(1.25f) }

    Column(
        modifier = modifier.fillMaxWidth().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        KotStep(
            currentStep = { progressValue },
            style = KotStepStyle(stepLayoutStyle = StepLayoutStyle.Horizontal)
        ) {
            step(title = "A")
            step(title = "B")
            step(title = "C")
            step(title = "D")
        }

        Spacer(modifier = Modifier.height(16.dp))

        val roundedValue = (progressValue * 100).toInt() / 100f
        Text(
            text = "Current Step Value: $roundedValue",
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp
        )

        Slider(
            value = progressValue,
            onValueChange = { progressValue = it },
            valueRange = -1f..4f,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
    }
}

/**
 * Dual Label Horizontal Stepper Snippet
 *
 * Demonstrates both leading (top step number) and trailing (bottom step title) labels.
 */
@OptIn(ExperimentalKotStep::class)
@Composable
fun HorizontalDualLabelSnippet(
    modifier: Modifier = Modifier
) {
    KotStep(
        modifier = modifier.fillMaxWidth(),
        currentStep = { 1.5f },
        style = KotStepStyle(
            stepLayoutStyle = StepLayoutStyle.Horizontal,
            stepStyle = StepStyles.default().copy(
                onTodo = StepStyle(stepSize = 36.dp, stepColor = Color(0xFFE2E8F0)),
                onCurrent = StepStyle(
                    stepSize = 40.dp,
                    stepColor = Color(0xFF2563EB),
                    borderStyle = BorderStyle(width = 3.dp, color = Color(0xFF93C5FD))
                ),
                onDone = StepStyle(stepSize = 36.dp, stepColor = Color(0xFF10B981))
            ),
            lineStyle = LineStyles.default().copy(
                onTodo = LineStyle(lineLength = 110.dp, lineThickness = 4.dp, lineColor = Color(0xFFCBD5E1)),
                onCurrent = LineStyle(lineLength = 110.dp, lineThickness = 4.dp, lineColor = Color(0xFFE2E8F0), progressColor = Color(0xFF2563EB)),
                onDone = LineStyle(lineLength = 110.dp, lineThickness = 4.dp, lineColor = Color(0xFF10B981), progressColor = Color(0xFF10B981))
            )
        )
    ) {
        step(
            title = "1",
            leadingLabel = { Text("STEP 01", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF10B981), modifier = Modifier.padding(bottom = 6.dp)) },
            trailingLabel = { Text("Account", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF0F172A), modifier = Modifier.padding(top = 6.dp)) }
        )
        step(
            title = "2",
            leadingLabel = { Text("STEP 02", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2563EB), modifier = Modifier.padding(bottom = 6.dp)) },
            trailingLabel = { Text("Profile", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2563EB), modifier = Modifier.padding(top = 6.dp)) }
        )
        step(
            title = "3",
            leadingLabel = { Text("STEP 03", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF94A3B8), modifier = Modifier.padding(bottom = 6.dp)) },
            trailingLabel = { Text("Security", fontSize = 13.sp, fontWeight = FontWeight.Medium, color = Color(0xFF64748B), modifier = Modifier.padding(top = 6.dp)) }
        )
        step(
            title = "4",
            leadingLabel = { Text("STEP 04", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF94A3B8), modifier = Modifier.padding(bottom = 6.dp)) },
            trailingLabel = { Text("Review", fontSize = 13.sp, fontWeight = FontWeight.Medium, color = Color(0xFF64748B), modifier = Modifier.padding(top = 6.dp)) }
        )
    }
}

/**
 * Minimalist Dots Stepper Snippet
 *
 * Demonstrates clean pagination/onboarding dots without text labels.
 */
@OptIn(ExperimentalKotStep::class)
@Composable
fun MinimalistDotsSnippet(
    modifier: Modifier = Modifier
) {
    KotStep(
        modifier = modifier.fillMaxWidth(),
        currentStep = { 2f },
        style = KotStepStyle(
            stepLayoutStyle = StepLayoutStyle.Horizontal,
            showCheckMarkOnDone = false,
            stepStyle = StepStyles.default().copy(
                onTodo = StepStyle(stepSize = 12.dp, stepColor = Color(0xFFCBD5E1)),
                onCurrent = StepStyle(
                    stepSize = 16.dp,
                    stepColor = Color(0xFF6366F1),
                    borderStyle = BorderStyle(width = 3.dp, color = Color(0xFFC7D2FE))
                ),
                onDone = StepStyle(stepSize = 12.dp, stepColor = Color(0xFF10B981))
            ),
            lineStyle = LineStyles.default().copy(
                onTodo = LineStyle(lineLength = 48.dp, lineThickness = 3.dp, lineColor = Color(0xFFE2E8F0)),
                onCurrent = LineStyle(lineLength = 48.dp, lineThickness = 3.dp, lineColor = Color(0xFFE2E8F0), progressColor = Color(0xFF6366F1)),
                onDone = LineStyle(lineLength = 48.dp, lineThickness = 3.dp, lineColor = Color(0xFF10B981), progressColor = Color(0xFF10B981))
            )
        )
    ) {
        step()
        step()
        step()
        step()
        step()
    }
}

