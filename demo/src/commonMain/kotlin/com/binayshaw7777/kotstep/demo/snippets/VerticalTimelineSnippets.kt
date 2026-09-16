package com.binayshaw7777.kotstep.demo.snippets

import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.binayshaw7777.kotstep.v3.KotStep
import com.binayshaw7777.kotstep.v3.model.step.StepLayoutStyle
import com.binayshaw7777.kotstep.v3.model.style.BorderStyle
import com.binayshaw7777.kotstep.v3.model.style.KotStepStyle
import com.binayshaw7777.kotstep.v3.model.style.LineStyle
import com.binayshaw7777.kotstep.v3.model.style.LineStyles
import com.binayshaw7777.kotstep.v3.model.style.LineType
import com.binayshaw7777.kotstep.v3.model.style.StepStyle
import com.binayshaw7777.kotstep.v3.model.style.StepStyles
import com.binayshaw7777.kotstep.v3.util.ExperimentalKotStep

/**
 * Delivery Tracking Timeline Snippet
 *
 * Demonstrates a vertical timeline stepper utilizing both leading and trailing label slots.
 * - [leadingLabel]: Timestamps / event times
 * - [trailingLabel]: Event title and descriptive text
 *
 * Options:
 * - [StepLayoutStyle.Vertical]: Arranges steps in vertical orientation.
 * - [LineStyle.lineLength]: Controls distance between vertical steps (e.g. 32.dp).
 * - [StepStyle.borderStyle]: Adds highlight rings around the active delivery step.
 */
@OptIn(ExperimentalKotStep::class)
@Composable
fun DeliveryTrackingTimelineSnippet(
    modifier: Modifier = Modifier
) {
    val currentStep by remember { mutableFloatStateOf(2f) }

    KotStep(
        modifier = modifier.fillMaxWidth().padding(16.dp),
        currentStep = { currentStep },
        style = KotStepStyle(
            stepLayoutStyle = StepLayoutStyle.Vertical,
            stepStyle = StepStyles.default().copy(
                onTodo = StepStyle(
                    stepSize = 28.dp,
                    stepColor = Color(0xFFCBD5E1),
                    textStyle = androidx.compose.ui.text.TextStyle(color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                ),
                onCurrent = StepStyle(
                    stepSize = 32.dp,
                    stepColor = Color(0xFF6366F1),
                    textStyle = androidx.compose.ui.text.TextStyle(color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp),
                    borderStyle = BorderStyle(width = 3.dp, color = Color(0xFFC7D2FE))
                ),
                onDone = StepStyle(
                    stepSize = 28.dp,
                    stepColor = Color(0xFF10B981),
                    textStyle = androidx.compose.ui.text.TextStyle(color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                )
            ),
            lineStyle = LineStyles.default().copy(
                onTodo = LineStyle(
                    lineLength = 32.dp,
                    lineThickness = 3.dp,
                    lineColor = Color(0xFFE2E8F0),
                    lineStrokeCap = StrokeCap.Round
                ),
                onCurrent = LineStyle(
                    lineLength = 32.dp,
                    lineThickness = 3.dp,
                    lineColor = Color(0xFFE2E8F0),
                    progressColor = Color(0xFF6366F1),
                    lineStrokeCap = StrokeCap.Round
                ),
                onDone = LineStyle(
                    lineLength = 32.dp,
                    lineThickness = 3.dp,
                    lineColor = Color(0xFF10B981),
                    progressColor = Color(0xFF10B981),
                    lineStrokeCap = StrokeCap.Round
                )
            )
        )
    ) {
        step(
            title = "1",
            leadingLabel = {
                Text(
                    text = "09:30 AM",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF64748B),
                    modifier = Modifier.padding(end = 16.dp, top = 4.dp)
                )
            },
            trailingLabel = {
                Column(modifier = Modifier.padding(start = 14.dp)) {
                    Text("Order Placed", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF0F172A))
                    Text("Your order #89201 has been confirmed", fontSize = 12.sp, color = Color(0xFF64748B))
                }
            }
        )
        step(
            title = "2",
            leadingLabel = {
                Text(
                    text = "11:15 AM",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF64748B),
                    modifier = Modifier.padding(end = 16.dp, top = 4.dp)
                )
            },
            trailingLabel = {
                Column(modifier = Modifier.padding(start = 14.dp)) {
                    Text("Package Packed", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF0F172A))
                    Text("Fulfilled by seller hub warehouse", fontSize = 12.sp, color = Color(0xFF64748B))
                }
            }
        )
        step(
            title = "3",
            leadingLabel = {
                Text(
                    text = "02:45 PM",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF6366F1),
                    modifier = Modifier.padding(end = 16.dp, top = 4.dp)
                )
            },
            trailingLabel = {
                Column(modifier = Modifier.padding(start = 14.dp)) {
                    Text("Out for Delivery", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF6366F1))
                    Text("Courier agent on the way to your address", fontSize = 12.sp, color = Color(0xFF64748B))
                }
            }
        )
        step(
            title = "4",
            leadingLabel = {
                Text(
                    text = "Estimated",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF94A3B8),
                    modifier = Modifier.padding(end = 16.dp, top = 4.dp)
                )
            },
            trailingLabel = {
                Column(modifier = Modifier.padding(start = 14.dp)) {
                    Text("Delivered", fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = Color(0xFF94A3B8))
                    Text("Pending signature upon receipt", fontSize = 12.sp, color = Color(0xFF94A3B8))
                }
            }
        )
    }
}

/**
 * Vertical Minimal Rail Snippet
 *
 * Compact vertical indicator rail without leading timestamps, ideal for tasks or workflow checklists.
 */
@OptIn(ExperimentalKotStep::class)
@Composable
fun VerticalMinimalRailSnippet(
    modifier: Modifier = Modifier
) {
    KotStep(
        modifier = modifier.fillMaxWidth().padding(16.dp),
        currentStep = { 2f },
        style = KotStepStyle(
            stepLayoutStyle = StepLayoutStyle.Vertical,
            stepStyle = StepStyles.default().copy(
                onTodo = StepStyle(stepSize = 26.dp, stepColor = Color(0xFFCBD5E1)),
                onCurrent = StepStyle(stepSize = 30.dp, stepColor = Color(0xFF3B82F6), borderStyle = BorderStyle(width = 3.dp, color = Color(0xFFBFDBFE))),
                onDone = StepStyle(stepSize = 26.dp, stepColor = Color(0xFF10B981))
            ),
            lineStyle = LineStyles.default().copy(
                onTodo = LineStyle(lineLength = 32.dp, lineThickness = 3.dp, lineColor = Color(0xFFE2E8F0)),
                onCurrent = LineStyle(lineLength = 32.dp, lineThickness = 3.dp, lineColor = Color(0xFFE2E8F0), progressColor = Color(0xFF3B82F6)),
                onDone = LineStyle(lineLength = 32.dp, lineThickness = 3.dp, lineColor = Color(0xFF10B981), progressColor = Color(0xFF10B981))
            )
        )
    ) {
        step(
            title = "1",
            trailingLabel = {
                Column(modifier = Modifier.padding(start = 12.dp)) {
                    Text("Database Schema Migration", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF0F172A))
                    Text("All PostgreSQL tables updated", fontSize = 11.sp, color = Color(0xFF64748B))
                }
            }
        )
        step(
            title = "2",
            trailingLabel = {
                Column(modifier = Modifier.padding(start = 12.dp)) {
                    Text("OAuth 2.0 Integration", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF0F172A))
                    Text("Google & Apple login configured", fontSize = 11.sp, color = Color(0xFF64748B))
                }
            }
        )
        step(
            title = "3",
            trailingLabel = {
                Column(modifier = Modifier.padding(start = 12.dp)) {
                    Text("GraphQL Gateway Routes", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF3B82F6))
                    Text("Validating federation endpoints", fontSize = 11.sp, color = Color(0xFF64748B))
                }
            }
        )
        step(
            title = "4",
            trailingLabel = {
                Column(modifier = Modifier.padding(start = 12.dp)) {
                    Text("Production Rollout", fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = Color(0xFF94A3B8))
                    Text("Traffic canary test pending", fontSize = 11.sp, color = Color(0xFF94A3B8))
                }
            }
        )
    }
}

/**
 * Vertical Dashed Flight Transit Route Snippet
 *
 * Demonstrates a vertical travel/shipping route using LineType.Dashed.
 */
@OptIn(ExperimentalKotStep::class)
@Composable
fun VerticalDashedRouteSnippet(
    modifier: Modifier = Modifier
) {
    KotStep(
        modifier = modifier.fillMaxWidth().padding(16.dp),
        currentStep = { 2f },
        style = KotStepStyle(
            stepLayoutStyle = StepLayoutStyle.Vertical,
            stepStyle = StepStyles.default().copy(
                onTodo = StepStyle(stepSize = 28.dp, stepColor = Color(0xFFE2E8F0)),
                onCurrent = StepStyle(stepSize = 32.dp, stepColor = Color(0xFF0284C7), borderStyle = BorderStyle(width = 3.dp, color = Color(0xFFBAE6FD))),
                onDone = StepStyle(stepSize = 28.dp, stepColor = Color(0xFF0369A1))
            ),
            lineStyle = LineStyles.default().copy(
                onTodo = LineStyle(lineLength = 32.dp, lineThickness = 3.dp, lineColor = Color(0xFFCBD5E1), lineType = LineType.Dashed(dashLength = 6.dp, gapLength = 6.dp)),
                onCurrent = LineStyle(lineLength = 32.dp, lineThickness = 3.dp, lineColor = Color(0xFFCBD5E1), progressColor = Color(0xFF0284C7), lineType = LineType.Dashed(dashLength = 6.dp, gapLength = 6.dp)),
                onDone = LineStyle(lineLength = 32.dp, lineThickness = 3.dp, lineColor = Color(0xFF0369A1), progressColor = Color(0xFF0369A1), lineType = LineType.Dashed(dashLength = 6.dp, gapLength = 6.dp))
            )
        )
    ) {
        step(
            title = "1",
            leadingLabel = { Text("08:00 AM", fontSize = 12.sp, color = Color(0xFF64748B), modifier = Modifier.padding(end = 16.dp, top = 4.dp)) },
            trailingLabel = {
                Column(modifier = Modifier.padding(start = 14.dp)) {
                    Text("San Francisco (SFO)", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text("Flight UA 402 - Departed Terminal 3", fontSize = 12.sp, color = Color(0xFF64748B))
                }
            }
        )
        step(
            title = "2",
            leadingLabel = { Text("01:15 PM", fontSize = 12.sp, color = Color(0xFF64748B), modifier = Modifier.padding(end = 16.dp, top = 4.dp)) },
            trailingLabel = {
                Column(modifier = Modifier.padding(start = 14.dp)) {
                    Text("Denver (DEN)", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text("Landed Gate B22 - Transfer connection", fontSize = 12.sp, color = Color(0xFF64748B))
                }
            }
        )
        step(
            title = "3",
            leadingLabel = { Text("05:40 PM", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0284C7), modifier = Modifier.padding(end = 16.dp, top = 4.dp)) },
            trailingLabel = {
                Column(modifier = Modifier.padding(start = 14.dp)) {
                    Text("Chicago (ORD)", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF0284C7))
                    Text("Boarding Flight UA 890 - Gate C10", fontSize = 12.sp, color = Color(0xFF64748B))
                }
            }
        )
        step(
            title = "4",
            leadingLabel = { Text("09:30 PM", fontSize = 12.sp, color = Color(0xFF94A3B8), modifier = Modifier.padding(end = 16.dp, top = 4.dp)) },
            trailingLabel = {
                Column(modifier = Modifier.padding(start = 14.dp)) {
                    Text("New York (JFK)", fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = Color(0xFF94A3B8))
                    Text("Estimated arrival Terminal 7", fontSize = 12.sp, color = Color(0xFF94A3B8))
                }
            }
        )
    }
}

/**
 * Vertical Mixed Slots Permutation Snippet
 *
 * Demonstrates slot presence combinations across steps in a single timeline:
 * - Step 1: Both leading and trailing label
 * - Step 2: Just leading label (no trailing label)
 * - Step 3: Just trailing label (no leading label)
 * - Step 4: None (indicator only, no labels)
 *
 * Layout engine dynamically reserves `maxLeadingLabelWidth` so the vertical
 * indicator column remains 100% straight and aligned.
 */
@OptIn(ExperimentalKotStep::class)
@Composable
fun VerticalMixedSlotsSnippet(
    modifier: Modifier = Modifier
) {
    KotStep(
        modifier = modifier.fillMaxWidth().padding(16.dp),
        currentStep = { 2f },
        style = KotStepStyle(
            stepLayoutStyle = StepLayoutStyle.Vertical,
            stepStyle = StepStyles.default().copy(
                onTodo = StepStyle(stepSize = 28.dp, stepColor = Color(0xFFCBD5E1), textStyle = androidx.compose.ui.text.TextStyle(color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)),
                onCurrent = StepStyle(stepSize = 32.dp, stepColor = Color(0xFF6366F1), textStyle = androidx.compose.ui.text.TextStyle(color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp), borderStyle = BorderStyle(width = 3.dp, color = Color(0xFFC7D2FE))),
                onDone = StepStyle(stepSize = 28.dp, stepColor = Color(0xFF10B981), textStyle = androidx.compose.ui.text.TextStyle(color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp))
            ),
            lineStyle = LineStyles.default().copy(
                onTodo = LineStyle(lineLength = 32.dp, lineThickness = 3.dp, lineColor = Color(0xFFE2E8F0), lineStrokeCap = StrokeCap.Round),
                onCurrent = LineStyle(lineLength = 32.dp, lineThickness = 3.dp, lineColor = Color(0xFFE2E8F0), progressColor = Color(0xFF6366F1), lineStrokeCap = StrokeCap.Round),
                onDone = LineStyle(lineLength = 32.dp, lineThickness = 3.dp, lineColor = Color(0xFF10B981), progressColor = Color(0xFF10B981), lineStrokeCap = StrokeCap.Round)
            )
        )
    ) {
        // Step 1: BOTH leading and trailing
        step(
            title = "1",
            leadingLabel = {
                Text("09:00 AM", fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Color(0xFF64748B), modifier = Modifier.padding(end = 16.dp, top = 4.dp))
            },
            trailingLabel = {
                Column(modifier = Modifier.padding(start = 14.dp)) {
                    Text("Order Placed", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF0F172A))
                    Text("Both leading and trailing labels", fontSize = 12.sp, color = Color(0xFF64748B))
                }
            }
        )

        // Step 2: JUST leading label
        step(
            title = "2",
            leadingLabel = {
                Text("11:30 AM", fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Color(0xFF64748B), modifier = Modifier.padding(end = 16.dp, top = 4.dp))
            }
        )

        // Step 3: JUST trailing label
        step(
            title = "3",
            trailingLabel = {
                Column(modifier = Modifier.padding(start = 14.dp)) {
                    Text("Out for Delivery", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF6366F1))
                    Text("Trailing label only (no leading label)", fontSize = 12.sp, color = Color(0xFF64748B))
                }
            }
        )

        // Step 4: NONE (neither leading nor trailing)
        step(
            title = "4"
        )
    }
}


