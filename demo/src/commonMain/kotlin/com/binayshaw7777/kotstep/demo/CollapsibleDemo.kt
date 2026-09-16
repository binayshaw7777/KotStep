package com.binayshaw7777.kotstep.demo

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import com.binayshaw7777.kotstep.v3.model.style.LineType
import com.binayshaw7777.kotstep.v3.model.style.StepStyle
import com.binayshaw7777.kotstep.v3.model.style.StepStyles
import com.binayshaw7777.kotstep.v3.util.ExperimentalKotStep

@OptIn(ExperimentalKotStep::class)
@Composable
fun CollapsibleDemo(modifier: Modifier = Modifier) {
    var currentStep by remember { mutableFloatStateOf(-1f) }
    var isCollapsible by remember { mutableStateOf(true) }
    val totalSteps = 6

    val sharedStyle = remember {
        KotStepStyle(
            stepLayoutStyle = StepLayoutStyle.Horizontal,
            showCheckMarkOnDone = false,
            ignoreCurrentState = false,
            stepStyle = StepStyles.default().copy(
                onTodo = StepStyle(
                    stepSize = 42.dp,
                    stepColor = Color(0xFF4B5563),
                    textStyle = androidx.compose.ui.text.TextStyle(color = Color.White),
                    borderStyle = BorderStyle(width = 2.dp, color = Color(0xFFEF4444))
                ),
                onCurrent = StepStyle(
                    stepSize = 48.dp,
                    stepColor = Color(0xFF1F2937),
                    textStyle = androidx.compose.ui.text.TextStyle(color = Color.White),
                    borderStyle = BorderStyle(width = 2.dp, color = Color(0xFF60A5FA))
                ),
                onDone = StepStyle(
                    stepSize = 42.dp,
                    stepColor = Color(0xFF10B981),
                    textStyle = androidx.compose.ui.text.TextStyle(color = Color.White),
                    borderStyle = BorderStyle(width = 2.dp, color = Color(0xFF047857))
                )
            ),
            lineStyle = LineStyles.default().copy(
                onTodo = LineStyle(
                    lineThickness = 6.dp,
                    lineLength = 60.dp,
                    linePadding = PaddingValues(2.dp),
                    lineColor = Color(0xFF4B5563)
                ),
                onCurrent = LineStyle(
                    lineThickness = 6.dp,
                    lineLength = 60.dp,
                    linePadding = PaddingValues(2.dp),
                    lineType = LineType.Dashed(),
                    progressType = LineType.Dashed(),
                    lineColor = Color(0xFF4B5563),
                    progressColor = Color(0xFF60A5FA)
                ),
                onDone = LineStyle(
                    lineThickness = 6.dp,
                    lineLength = 60.dp,
                    linePadding = PaddingValues(2.dp),
                    lineColor = Color(0xFF10B981),
                    progressColor = Color(0xFF10B981)
                )
            )
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "Collapsible Stepper Examples",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        // Progress Controls Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1F2937)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    onClick = {
                        if (currentStep <= 0f) currentStep = -1f
                        else currentStep -= 0.25f
                    },
                    enabled = currentStep > -0.75f
                ) {
                    Text("Previous")
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text("Collapsible", fontSize = 12.sp, color = Color.LightGray)
                    Switch(
                        checked = isCollapsible,
                        onCheckedChange = { isCollapsible = it }
                    )
                }

                Button(
                    onClick = {
                        if (currentStep < 0f) currentStep = 0f
                        else currentStep += 0.25f
                    },
                    enabled = currentStep < totalSteps.toFloat()
                ) {
                    Text(if (currentStep < 0f) "Start" else "Next")
                }
            }
        }

        Text(
            text = "Progress: ${if (currentStep < 0f) "Not started" else "${((currentStep + 1) * 100).toInt() / 100f} / $totalSteps"}",
            style = MaterialTheme.typography.bodyMedium
        )

        HorizontalDivider(color = Color(0xFF374151))

        // Horizontal Collapsible Section
        Text(
            text = "Horizontal Collapsible Stepper",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF111827)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                KotStep(
                    currentStep = { currentStep },
                    style = sharedStyle.copy(stepLayoutStyle = StepLayoutStyle.Horizontal)
                ) {
                    step(
                        title = "1",
                        leadingLabel = { Text("Start", fontSize = 12.sp, color = Color.White) },
                        isCollapsible = isCollapsible
                    )
                    step(
                        icon = Icons.Default.Star,
                        isCollapsible = isCollapsible
                    )
                    step(
                        icon = Icons.Default.Search,
                        isCollapsible = isCollapsible
                    )
                    step(
                        title = "3",
                        trailingLabel = {
                            var expanded by remember { mutableStateOf(false) }
                            Card(
                                modifier = Modifier.noRippleClick { expanded = !expanded },
                                colors = CardDefaults.cardColors(containerColor = Color(0xFF374151))
                            ) {
                                Column(modifier = Modifier.padding(8.dp)) {
                                    Text("Step 3 Details", fontSize = 11.sp, color = Color.White)
                                    AnimatedVisibility(expanded) {
                                        Text("Expanded collapsible content!", fontSize = 10.sp, color = Color.LightGray)
                                    }
                                }
                            }
                        },
                        isCollapsible = isCollapsible
                    )
                    step(title = "4", isCollapsible = isCollapsible)
                    step(icon = Icons.Default.Check, isCollapsible = isCollapsible)
                }
            }
        }

        HorizontalDivider(color = Color(0xFF374151))

        // Vertical Collapsible Section
        Text(
            text = "Vertical Collapsible Stepper",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF111827)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Box(modifier = Modifier.padding(16.dp)) {
                KotStep(
                    currentStep = { currentStep },
                    style = sharedStyle.copy(stepLayoutStyle = StepLayoutStyle.Vertical)
                ) {
                    step(
                        title = "1",
                        leadingLabel = { Icon(Icons.Default.Search, contentDescription = null, tint = Color.White) },
                        isCollapsible = isCollapsible
                    )
                    step(
                        icon = Icons.Default.Star,
                        isCollapsible = isCollapsible
                    )
                    step(
                        icon = Icons.Default.Check,
                        isCollapsible = isCollapsible
                    )
                    step(
                        title = "3",
                        trailingLabel = {
                            var expanded by remember { mutableStateOf(false) }
                            Card(
                                modifier = Modifier.noRippleClick { expanded = !expanded },
                                colors = CardDefaults.cardColors(containerColor = Color(0xFF374151))
                            ) {
                                Column(modifier = Modifier.padding(8.dp)) {
                                    Text("Click to expand order details", fontSize = 12.sp, color = Color.White)
                                    AnimatedVisibility(expanded) {
                                        Column(modifier = Modifier.padding(top = 4.dp)) {
                                            Text("• Subtotal: $120.00", fontSize = 11.sp, color = Color.LightGray)
                                            Text("• Shipping: Free", fontSize = 11.sp, color = Color.LightGray)
                                            Text("• Tax: $9.60", fontSize = 11.sp, color = Color.LightGray)
                                        }
                                    }
                                }
                            }
                        },
                        isCollapsible = isCollapsible
                    )
                    step(title = "4", isCollapsible = isCollapsible)
                    step(icon = Icons.Default.Check, isCollapsible = isCollapsible)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
