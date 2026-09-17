package com.binayshaw7777.kotstep.demo

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
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

private val LYFT_BACKGROUND = Color(0xFF0F0E17)
private val LYFT_PURPLE = Color(0xFF7952DE)
private val LYFT_PURPLE_DARK = Color(0xFF28186D)
private val LYFT_MAGENTA = Color(0xFFFF00BF)
private val LYFT_TEXT_PRIMARY = Color.White
private val LYFT_TEXT_SECONDARY = Color(0xFF9E9EA7)
private val LYFT_TEXT_MUTED = Color(0xFF757580)
private val LYFT_DIVIDER = Color(0xFF272635)
private val LYFT_CARD_PURPLE = Color(0xFF261863)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalKotStep::class)
@Composable
fun LyftDemo(
    modifier: Modifier = Modifier,
    onBack: (() -> Unit)? = null
) {
    var currentStep by remember { mutableFloatStateOf(2.0f) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "May 15, 11:40 AM",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = LYFT_TEXT_PRIMARY
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { onBack?.invoke() }) {
                        Icon(
                            imageVector = if (onBack != null) Icons.AutoMirrored.Filled.ArrowBack else Icons.Default.Close,
                            contentDescription = "Close",
                            tint = LYFT_TEXT_PRIMARY
                        )
                    }
                },
                actions = {
                    // Balancing spacer for centered title
                    Spacer(modifier = Modifier.width(48.dp))
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = LYFT_BACKGROUND)
            )
        },
        containerColor = LYFT_BACKGROUND
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Ride Route Header
            Text(
                text = "Ride route",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = LYFT_TEXT_PRIMARY
            )

            // KotStep Vertical Stepper for Lyft Route
            Box(modifier = Modifier.fillMaxWidth()) {
                KotStep(
                    currentStep = { currentStep },
                    style = getLyftKotStepStyle()
                ) {
                    // Step 0: Ride accepted
                    step(
                        content = {
                            LyftRingIndicator(
                                ringColor = LYFT_PURPLE
                            )
                        },
                        trailingLabel = {
                            LyftStepLabel(
                                title = "Ride accepted",
                                time = "11:40 AM",
                                subtitle = null
                            )
                        },
                        onClick = { currentStep = 0f }
                    )

                    // Step 1: Picked up passenger
                    step(
                        content = {
                            LyftRingIndicator(
                                ringColor = LYFT_PURPLE
                            )
                        },
                        trailingLabel = {
                            LyftStepLabel(
                                title = "Picked up passenger",
                                time = "11:44 AM",
                                subtitle = "4 min 23 sec • 1.51 mi"
                            )
                        },
                        onClick = { currentStep = 1f }
                    )

                    // Step 2: Dropped off passenger
                    step(
                        content = {
                            LyftRingIndicator(
                                ringColor = LYFT_MAGENTA
                            )
                        },
                        trailingLabel = {
                            LyftStepLabel(
                                title = "Dropped off passenger",
                                time = "12:27 PM",
                                subtitle = "42 min 37 sec • 27.8 mi"
                            )
                        },
                        onClick = { currentStep = 2f }
                    )
                }
            }

            HorizontalDivider(color = LYFT_DIVIDER)

            // Earnings Section
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "Earnings",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = LYFT_TEXT_PRIMARY
                )
                Text(
                    text = "Includes pay for pickup",
                    fontSize = 13.sp,
                    color = LYFT_TEXT_SECONDARY
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                LyftEarningsRow(
                    label = "Passenger payment",
                    amount = "$77.99",
                    hasInfo = true
                )
                LyftEarningsRow(
                    label = "Est. external fees",
                    amount = "-$11.11",
                    hasChevron = true
                )
                LyftEarningsRow(
                    label = "Est. Lyft fee",
                    amount = "-$29.44",
                    hasInfo = true
                )

                HorizontalDivider(color = LYFT_DIVIDER)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Your earnings",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = LYFT_TEXT_PRIMARY
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "$37.44",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = LYFT_TEXT_PRIMARY
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = null,
                            tint = LYFT_TEXT_PRIMARY,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            // Weekly Earnings Commitment Card
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = LYFT_CARD_PURPLE,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        LyftShieldIcon(modifier = Modifier.size(24.dp))
                        Text(
                            text = "Weekly earnings commitment",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = LYFT_TEXT_PRIMARY
                        )
                    }

                    Text(
                        text = "Each week you'll earn 70% or more of passenger payments after external fees. If your earnings are less than 70% when the week ends you'll get paid an adjustment.",
                        fontSize = 13.sp,
                        color = Color(0xFFD4D0EC),
                        lineHeight = 18.sp
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.noRippleClick {}
                    ) {
                        Text(
                            text = "Track your weekly earnings",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFB39DDB)
                        )
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = null,
                            tint = Color(0xFFB39DDB),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            // Upfront pay footer explanation
            Text(
                text = "Upfront pay is calculated based on factors which may include estimated time and distance, travel to pickup, ride demand, and other market factors. With upfront pay you always receive the earnings shown with the ride request unless the trip details change significantly.",
                fontSize = 12.sp,
                color = LYFT_TEXT_MUTED,
                lineHeight = 17.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // KotStep Interactive Simulator
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF191824)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "KotStep Route Progress (${currentStep.toInt() + 1} / 3)",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = LYFT_TEXT_PRIMARY
                    )
                    Slider(
                        value = currentStep,
                        onValueChange = { currentStep = it },
                        valueRange = 0f..2f,
                        steps = 1,
                        colors = SliderDefaults.colors(
                            thumbColor = LYFT_MAGENTA,
                            activeTrackColor = LYFT_MAGENTA,
                            inactiveTrackColor = LYFT_PURPLE_DARK
                        )
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        OutlinedButton(
                            onClick = { currentStep = (currentStep - 1f).coerceAtLeast(0f) }
                        ) {
                            Text("Previous", fontSize = 11.sp, color = Color.White)
                        }
                        Button(
                            onClick = { currentStep = (currentStep + 1f).coerceAtMost(2f) },
                            colors = ButtonDefaults.buttonColors(containerColor = LYFT_MAGENTA)
                        ) {
                            Text("Next Step", fontSize = 11.sp, color = Color.White)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LyftStepLabel(
    title: String,
    time: String,
    subtitle: String?
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 14.dp, bottom = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = LYFT_TEXT_PRIMARY
            )
            Text(
                text = time,
                fontSize = 14.sp,
                color = LYFT_TEXT_PRIMARY
            )
        }
        if (subtitle != null) {
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subtitle,
                fontSize = 13.sp,
                color = LYFT_TEXT_SECONDARY
            )
        }
    }
}

@Composable
private fun LyftRingIndicator(
    ringColor: Color
) {
    Box(
        modifier = Modifier
            .size(18.dp)
            .background(LYFT_BACKGROUND, CircleShape)
            .border(2.5.dp, ringColor, CircleShape)
    )
}

@Composable
private fun LyftEarningsRow(
    label: String,
    amount: String,
    hasInfo: Boolean = false,
    hasChevron: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 15.sp,
            color = LYFT_TEXT_PRIMARY
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = amount,
                fontSize = 15.sp,
                color = LYFT_TEXT_PRIMARY
            )
            Spacer(modifier = Modifier.width(6.dp))
            if (hasInfo) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = LYFT_TEXT_SECONDARY,
                    modifier = Modifier.size(16.dp)
                )
            } else if (hasChevron) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = LYFT_TEXT_SECONDARY,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Composable
private fun LyftShieldIcon(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val shield = Path().apply {
            moveTo(w * 0.5f, 0f)
            lineTo(w, h * 0.2f)
            lineTo(w, h * 0.6f)
            cubicTo(w, h * 0.85f, w * 0.5f, h, w * 0.5f, h)
            cubicTo(w * 0.5f, h, 0f, h * 0.85f, 0f, h * 0.6f)
            lineTo(0f, h * 0.2f)
            close()
        }
        drawPath(shield, color = Color.White, style = androidx.compose.ui.graphics.drawscope.Stroke(width = 2.5f))
    }
}

@OptIn(ExperimentalKotStep::class)
private fun getLyftKotStepStyle(): KotStepStyle {
    return KotStepStyle(
        stepLayoutStyle = StepLayoutStyle.Vertical,
        showCheckMarkOnDone = false,
        stepStyle = StepStyles.default().copy(
            onDone = StepStyle(
                stepColor = Color.Transparent,
                stepSize = 18.dp,
                borderStyle = BorderStyle(width = 0.dp, color = Color.Transparent)
            ),
            onCurrent = StepStyle(
                stepColor = Color.Transparent,
                stepSize = 18.dp,
                borderStyle = BorderStyle(width = 0.dp, color = Color.Transparent)
            ),
            onTodo = StepStyle(
                stepColor = Color.Transparent,
                stepSize = 18.dp,
                borderStyle = BorderStyle(width = 0.dp, color = Color.Transparent)
            )
        ),
        lineStyle = LineStyles.default().copy(
            onDone = LineStyle(
                lineColor = LYFT_PURPLE,
                progressColor = LYFT_PURPLE,
                lineThickness = 2.5.dp,
                lineLength = 40.dp,
                linePadding = PaddingValues(vertical = 2.dp)
            ),
            onCurrent = LineStyle(
                lineColor = LYFT_PURPLE,
                progressColor = LYFT_PURPLE,
                lineThickness = 2.5.dp,
                lineLength = 40.dp,
                linePadding = PaddingValues(vertical = 2.dp)
            ),
            onTodo = LineStyle(
                lineColor = LYFT_DIVIDER,
                progressColor = LYFT_DIVIDER,
                lineThickness = 2.5.dp,
                lineLength = 40.dp,
                linePadding = PaddingValues(vertical = 2.dp)
            )
        )
    )
}
