package com.binayshaw7777.kotstep.demo.snippets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.binayshaw7777.kotstep.v3.KotStep
import com.binayshaw7777.kotstep.v3.model.step.StepLayoutStyle
import com.binayshaw7777.kotstep.v3.model.style.BorderStyle
import com.binayshaw7777.kotstep.v3.model.style.IconStyle
import com.binayshaw7777.kotstep.v3.model.style.KotStepStyle
import com.binayshaw7777.kotstep.v3.model.style.LineStyles
import com.binayshaw7777.kotstep.v3.model.style.StepStyle
import com.binayshaw7777.kotstep.v3.model.style.StepStyles
import com.binayshaw7777.kotstep.v3.util.ExperimentalKotStep

/**
 * Vector Icons Stepper Snippet
 *
 * Demonstrates replacing numbers with Compose [ImageVector] icons.
 * Shows how to configure custom icon tint and size per step state.
 *
 * Options:
 * - [StepStyle.iconStyle]: Sets icon tint and icon size.
 * - [step(icon = ...)]: Direct overload for vector icons.
 */
@OptIn(ExperimentalKotStep::class)
@Composable
fun VectorIconStepperSnippet(
    modifier: Modifier = Modifier
) {
    val step by remember { mutableFloatStateOf(2f) }

    KotStep(
        modifier = modifier.fillMaxWidth().padding(16.dp),
        currentStep = { step },
        style = KotStepStyle(
            stepLayoutStyle = StepLayoutStyle.Horizontal,
            stepStyle = StepStyles.default().copy(
                onTodo = StepStyle(
                    stepSize = 38.dp,
                    stepColor = Color(0xFFE9D5FF),
                    iconStyle = IconStyle(iconTint = Color(0xFF9333EA), iconSize = 18.dp)
                ),
                onCurrent = StepStyle(
                    stepSize = 44.dp,
                    stepColor = Color(0xFF9333EA),
                    iconStyle = IconStyle(iconTint = Color.White, iconSize = 22.dp),
                    borderStyle = BorderStyle(width = 3.dp, color = Color(0xFFD8B4FE))
                ),
                onDone = StepStyle(
                    stepSize = 38.dp,
                    stepColor = Color(0xFF7E22CE),
                    iconStyle = IconStyle(iconTint = Color.White, iconSize = 18.dp)
                )
            )
        )
    ) {
        step(icon = Icons.Default.Home, trailingLabel = { Text("Start") })
        step(icon = Icons.Default.Person, trailingLabel = { Text("Profile") })
        step(icon = Icons.Default.Star, trailingLabel = { Text("Perks") })
        step(icon = Icons.Default.Favorite, trailingLabel = { Text("Done") })
    }
}

/**
 * Custom Shape Stepper Snippet
 *
 * Demonstrates using custom shapes such as [RoundedCornerShape]
 * instead of circles for the step badges.
 */
@OptIn(ExperimentalKotStep::class)
@Composable
fun CustomShapeBadgeStepperSnippet(
    modifier: Modifier = Modifier
) {
    val step by remember { mutableFloatStateOf(1f) }

    KotStep(
        modifier = modifier.fillMaxWidth().padding(16.dp),
        currentStep = { step },
        style = KotStepStyle(
            stepLayoutStyle = StepLayoutStyle.Horizontal,
            stepStyle = StepStyles.default().copy(
                onTodo = StepStyle(
                    stepSize = 36.dp,
                    stepShape = RoundedCornerShape(10.dp),
                    stepColor = Color(0xFFE2E8F0)
                ),
                onCurrent = StepStyle(
                    stepSize = 40.dp,
                    stepShape = RoundedCornerShape(12.dp),
                    stepColor = Color(0xFF0284C7),
                    borderStyle = BorderStyle(width = 2.dp, color = Color(0xFF38BDF8), shape = RoundedCornerShape(12.dp))
                ),
                onDone = StepStyle(
                    stepSize = 36.dp,
                    stepShape = RoundedCornerShape(10.dp),
                    stepColor = Color(0xFF10B981)
                )
            )
        )
    ) {
        step(title = "1", trailingLabel = { Text("Plan") })
        step(title = "2", trailingLabel = { Text("Build") })
        step(title = "3", trailingLabel = { Text("Test") })
        step(title = "4", trailingLabel = { Text("Ship") })
    }
}

/**
 * Fully Custom Composable Content Indicator Snippet
 *
 * Demonstrates passing custom arbitrary composables (e.g. animated spinners,
 * custom badges) into the step indicator slot via [step(content = ...)].
 */
@OptIn(ExperimentalKotStep::class)
@Composable
fun ComposableContentStepperSnippet(
    modifier: Modifier = Modifier
) {
    val step by remember { mutableFloatStateOf(1f) }

    KotStep(
        modifier = modifier.fillMaxWidth().padding(16.dp),
        currentStep = { step },
        style = KotStepStyle(stepLayoutStyle = StepLayoutStyle.Horizontal)
    ) {
        step(
            icon = Icons.Default.Check,
            trailingLabel = { Text("Verified") }
        )
        step(
            content = {
                Box(contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                }
            },
            trailingLabel = { Text("Processing") }
        )
        step(
            title = "3",
            trailingLabel = { Text("Complete") }
        )
    }
}

/**
 * Geometric Cut-Corner Stepper Snippet
 *
 * Demonstrates high-contrast gaming/cyberpunk styling with CutCornerShape.
 */
@OptIn(ExperimentalKotStep::class)
@Composable
fun CutCornerGamingSnippet(
    modifier: Modifier = Modifier
) {
    KotStep(
        modifier = modifier.fillMaxWidth().padding(16.dp),
        currentStep = { 1.5f },
        style = KotStepStyle(
            stepLayoutStyle = StepLayoutStyle.Horizontal,
            stepStyle = StepStyles.default().copy(
                onTodo = StepStyle(
                    stepSize = 36.dp,
                    stepShape = CutCornerShape(8.dp),
                    stepColor = Color(0xFF1E293B),
                    textStyle = androidx.compose.ui.text.TextStyle(color = Color(0xFF64748B), fontWeight = FontWeight.Bold, fontSize = 13.sp),
                    borderStyle = BorderStyle(width = 1.dp, color = Color(0xFF334155), shape = CutCornerShape(8.dp))
                ),
                onCurrent = StepStyle(
                    stepSize = 42.dp,
                    stepShape = CutCornerShape(8.dp),
                    stepColor = Color(0xFFD97706),
                    textStyle = androidx.compose.ui.text.TextStyle(color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 14.sp),
                    borderStyle = BorderStyle(width = 3.dp, color = Color(0xFFFDE68A), shape = CutCornerShape(8.dp))
                ),
                onDone = StepStyle(
                    stepSize = 36.dp,
                    stepShape = CutCornerShape(8.dp),
                    stepColor = Color(0xFFF59E0B),
                    textStyle = androidx.compose.ui.text.TextStyle(color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 13.sp),
                    borderStyle = BorderStyle(width = 1.dp, color = Color(0xFFFBBF24), shape = CutCornerShape(8.dp))
                )
            )
        )
    ) {
        step(title = "1", trailingLabel = { Text("Phase Alpha", fontSize = 12.sp, modifier = Modifier.padding(top = 6.dp)) })
        step(title = "2", trailingLabel = { Text("Phase Beta", fontSize = 12.sp, modifier = Modifier.padding(top = 6.dp)) })
        step(title = "3", trailingLabel = { Text("Phase Gamma", fontSize = 12.sp, modifier = Modifier.padding(top = 6.dp)) })
        step(title = "4", trailingLabel = { Text("Launch Final", fontSize = 12.sp, modifier = Modifier.padding(top = 6.dp)) })
    }
}

/**
 * Vertical Milestone Icons Snippet
 *
 * Demonstrates vertical roadmap milestones using custom ImageVector icons.
 */
@OptIn(ExperimentalKotStep::class)
@Composable
fun VerticalMilestoneIconsSnippet(
    modifier: Modifier = Modifier
) {
    KotStep(
        modifier = modifier.fillMaxWidth().padding(16.dp),
        currentStep = { 2f },
        style = KotStepStyle(
            stepLayoutStyle = StepLayoutStyle.Vertical,
            stepStyle = StepStyles.default().copy(
                onTodo = StepStyle(stepSize = 32.dp, stepColor = Color(0xFFE2E8F0), iconStyle = IconStyle(iconTint = Color(0xFF94A3B8), iconSize = 16.dp)),
                onCurrent = StepStyle(stepSize = 36.dp, stepColor = Color(0xFF8B5CF6), iconStyle = IconStyle(iconTint = Color.White, iconSize = 18.dp), borderStyle = BorderStyle(width = 3.dp, color = Color(0xFFDDD6FE))),
                onDone = StepStyle(stepSize = 32.dp, stepColor = Color(0xFF7C3AED), iconStyle = IconStyle(iconTint = Color.White, iconSize = 16.dp))
            )
        )
    ) {
        step(
            icon = Icons.Default.Home,
            leadingLabel = { Text("Q1 2026", fontSize = 12.sp, color = Color(0xFF64748B), modifier = Modifier.padding(end = 16.dp, top = 6.dp)) },
            trailingLabel = {
                Column(modifier = Modifier.padding(start = 14.dp)) {
                    Text("Foundation & Core Architecture", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text("Multiplatform Gradle setup & initial DSL v3", fontSize = 12.sp, color = Color(0xFF64748B))
                }
            }
        )
        step(
            icon = Icons.Default.Person,
            leadingLabel = { Text("Q2 2026", fontSize = 12.sp, color = Color(0xFF64748B), modifier = Modifier.padding(end = 16.dp, top = 6.dp)) },
            trailingLabel = {
                Column(modifier = Modifier.padding(start = 14.dp)) {
                    Text("Compose Multiplatform Targets", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text("Desktop (Skiko), iOS and Wasm engine support", fontSize = 12.sp, color = Color(0xFF64748B))
                }
            }
        )
        step(
            icon = Icons.Default.Star,
            leadingLabel = { Text("Q3 2026", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF8B5CF6), modifier = Modifier.padding(end = 16.dp, top = 6.dp)) },
            trailingLabel = {
                Column(modifier = Modifier.padding(start = 14.dp)) {
                    Text("Public V3 Release & Showcase", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF8B5CF6))
                    Text("Comprehensive documentation and interactive demo", fontSize = 12.sp, color = Color(0xFF64748B))
                }
            }
        )
        step(
            icon = Icons.Default.Favorite,
            leadingLabel = { Text("Q4 2026", fontSize = 12.sp, color = Color(0xFF94A3B8), modifier = Modifier.padding(end = 16.dp, top = 6.dp)) },
            trailingLabel = {
                Column(modifier = Modifier.padding(start = 14.dp)) {
                    Text("Ecosystem & Theme Presets", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                    Text("Pre-packaged theme marketplace & extensions", fontSize = 12.sp, color = Color(0xFF94A3B8))
                }
            }
        )
    }
}

