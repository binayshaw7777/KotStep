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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.binayshaw7777.kotstep.v3.KotStep
import com.binayshaw7777.kotstep.v3.model.step.StepLayoutStyle
import com.binayshaw7777.kotstep.v3.model.style.BorderStyle
import com.binayshaw7777.kotstep.v3.model.style.IconStyle
import com.binayshaw7777.kotstep.v3.model.style.KotStepStyle
import com.binayshaw7777.kotstep.v3.model.style.LineStyle
import com.binayshaw7777.kotstep.v3.model.style.LineStyles
import com.binayshaw7777.kotstep.v3.model.style.StepStyle
import com.binayshaw7777.kotstep.v3.model.style.StepStyles
import com.binayshaw7777.kotstep.v3.util.ExperimentalKotStep

private val FLIPKART_BLUE = Color(0xFF2874F0)
private val FLIPKART_GREEN = Color(0xFF388E3C)
private val FLIPKART_BG = Color(0xFFF1F3F6)
private val FLIPKART_TEXT_PRIMARY = Color(0xFF212121)
private val FLIPKART_TEXT_SECONDARY = Color(0xFF878787)
private val FLIPKART_TEXT_MUTED = Color(0xFF616161)
private val FLIPKART_BORDER = Color(0xFFE0E0E0)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalKotStep::class)
@Composable
fun FlipkartReturnDemo(
    modifier: Modifier = Modifier,
    onBack: (() -> Unit)? = null
) {
    var currentStep by remember { mutableFloatStateOf(1.8f) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Return & Refund Tracking",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onBack?.invoke() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = FLIPKART_BLUE)
            )
        },
        containerColor = FLIPKART_BG
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Return ID Banner
            Surface(
                color = Color.White,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Return ID: RTN-8941029410",
                        fontSize = 13.sp,
                        color = FLIPKART_TEXT_SECONDARY
                    )
                    Text(
                        text = "DETAILS",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = FLIPKART_BLUE
                    )
                }
            }

            // Product Item Card
            Surface(
                color = Color.White,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HeadphonesThumbnail(modifier = Modifier.size(72.dp))

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Sony WH-1000XM5 Wireless Noise Cancelling Headphones",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = FLIPKART_TEXT_PRIMARY,
                            lineHeight = 18.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Refund Amount: ₹26,990",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = FLIPKART_GREEN
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Reason: Left earcup sound issue",
                            fontSize = 12.sp,
                            color = FLIPKART_TEXT_SECONDARY
                        )
                    }
                }
            }

            // Horizontal Stepper Card
            Surface(
                color = Color.White,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Return Journey",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = FLIPKART_TEXT_PRIMARY,
                        modifier = Modifier.align(Alignment.Start).padding(bottom = 16.dp)
                    )

                    // KotStep Horizontal Stepper
                    KotStep(
                        modifier = Modifier.fillMaxWidth(),
                        currentStep = { currentStep },
                        style = getFlipkartHorizontalKotStepStyle()
                    ) {
                        step(
                            icon = Icons.Default.Check,
                            trailingLabel = {
                                Text(
                                    text = "Requested\n14 Sep",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = FLIPKART_TEXT_PRIMARY,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            },
                            onClick = { currentStep = 0f }
                        )

                        step(
                            icon = Icons.Default.Check,
                            trailingLabel = {
                                Text(
                                    text = "Approved\n15 Sep",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = FLIPKART_TEXT_PRIMARY,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            },
                            onClick = { currentStep = 1f }
                        )

                        step(
                            icon = Icons.Default.Check,
                            trailingLabel = {
                                Text(
                                    text = "Pickup\nToday",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (currentStep >= 2f) FLIPKART_GREEN else FLIPKART_BLUE,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            },
                            onClick = { currentStep = 2f }
                        )

                        step(
                            icon = Icons.Default.Check,
                            trailingLabel = {
                                Text(
                                    text = "Refund\nPending",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = if (currentStep >= 3f) FLIPKART_GREEN else FLIPKART_TEXT_SECONDARY,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            },
                            onClick = { currentStep = 3f }
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                }
            }

            // Pickup Details Card
            Surface(
                color = Color.White,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Pickup Scheduled",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = FLIPKART_TEXT_PRIMARY
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Pickup executive arriving Today between 02:00 PM - 05:00 PM",
                        fontSize = 13.sp,
                        color = FLIPKART_TEXT_MUTED
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Agent: Suresh Verma (+91 91234 56789)",
                        fontSize = 12.sp,
                        color = FLIPKART_TEXT_SECONDARY
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Return Verification OTP Code
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFFE8F5E9),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFC8E6C9)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Return Verification Code",
                                    fontSize = 12.sp,
                                    color = FLIPKART_TEXT_MUTED
                                )
                                Text(
                                    text = "Share OTP with agent during pickup",
                                    fontSize = 11.sp,
                                    color = FLIPKART_TEXT_SECONDARY
                                )
                            }
                            Text(
                                text = "4821",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = FLIPKART_GREEN,
                                letterSpacing = 2.sp
                            )
                        }
                    }
                }
            }

            // Refund Destination Card
            Surface(
                color = Color.White,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "Refund Details",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = FLIPKART_TEXT_PRIMARY
                    )
                    Text(
                        text = "Refund will be transferred to original payment method (HDFC Credit Card ending in 4092) within 2-4 hours of successful item pickup.",
                        fontSize = 12.sp,
                        color = FLIPKART_TEXT_MUTED,
                        lineHeight = 16.sp
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Total Refund Amount", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = FLIPKART_TEXT_PRIMARY)
                        Text("₹26,990", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = FLIPKART_GREEN)
                    }
                }
            }

            // Interactive KotStep Tester
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "KotStep Horizontal Controller (${((currentStep * 10).toInt()) / 10f} / 3.0)",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = FLIPKART_TEXT_PRIMARY
                    )
                    Slider(
                        value = currentStep,
                        onValueChange = { currentStep = it },
                        valueRange = 0f..3f,
                        colors = SliderDefaults.colors(
                            thumbColor = FLIPKART_BLUE,
                            activeTrackColor = FLIPKART_BLUE
                        )
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        OutlinedButton(
                            onClick = { currentStep = (currentStep - 1f).coerceAtLeast(0f) }
                        ) {
                            Text("Prev Stage", fontSize = 11.sp)
                        }
                        Button(
                            onClick = { currentStep = (currentStep + 1f).coerceAtMost(3f) },
                            colors = ButtonDefaults.buttonColors(containerColor = FLIPKART_BLUE)
                        ) {
                            Text("Next Stage", fontSize = 11.sp)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalKotStep::class)
private fun getFlipkartHorizontalKotStepStyle(): KotStepStyle {
    return KotStepStyle(
        stepLayoutStyle = StepLayoutStyle.Horizontal,
        showCheckMarkOnDone = true,
        stepStyle = StepStyles.default().copy(
            onDone = StepStyle(
                stepColor = FLIPKART_GREEN,
                stepSize = 26.dp,
                iconStyle = IconStyle(iconTint = Color.White, iconSize = 14.dp)
            ),
            onCurrent = StepStyle(
                stepColor = FLIPKART_BLUE,
                stepSize = 28.dp,
                iconStyle = IconStyle(iconTint = Color.White, iconSize = 14.dp)
            ),
            onTodo = StepStyle(
                stepColor = Color(0xFFE0E0E0),
                stepSize = 26.dp,
                borderStyle = BorderStyle(width = 1.5.dp, color = Color(0xFFBDBDBD)),
                iconStyle = IconStyle(iconTint = Color.Transparent, iconSize = 0.dp)
            )
        ),
        lineStyle = LineStyles.default().copy(
            onDone = LineStyle(
                lineColor = FLIPKART_GREEN,
                progressColor = FLIPKART_GREEN,
                lineThickness = 3.dp,
                lineLength = 46.dp,
                linePadding = PaddingValues(horizontal = 2.dp)
            ),
            onCurrent = LineStyle(
                lineColor = Color(0xFFE0E0E0),
                progressColor = FLIPKART_BLUE,
                lineThickness = 3.dp,
                lineLength = 46.dp,
                linePadding = PaddingValues(horizontal = 2.dp)
            ),
            onTodo = LineStyle(
                lineColor = Color(0xFFE0E0E0),
                progressColor = Color(0xFFE0E0E0),
                lineThickness = 3.dp,
                lineLength = 46.dp,
                linePadding = PaddingValues(horizontal = 2.dp)
            )
        )
    )
}

@Composable
private fun HeadphonesThumbnail(modifier: Modifier = Modifier) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = Color(0xFFF3F4F6),
        modifier = modifier
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            // Headband arc
            drawArc(
                color = Color(0xFF4B5563),
                startAngle = 180f,
                sweepAngle = 180f,
                useCenter = false,
                topLeft = Offset(w * 0.2f, h * 0.15f),
                size = androidx.compose.ui.geometry.Size(w * 0.6f, h * 0.5f),
                style = androidx.compose.ui.graphics.drawscope.Stroke(width = 4f)
            )
            // Left earcup
            drawRoundRect(
                color = Color(0xFF9CA3AF),
                topLeft = Offset(w * 0.15f, h * 0.45f),
                size = androidx.compose.ui.geometry.Size(w * 0.18f, h * 0.35f),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(8f, 8f)
            )
            // Right earcup
            drawRoundRect(
                color = Color(0xFF9CA3AF),
                topLeft = Offset(w * 0.67f, h * 0.45f),
                size = androidx.compose.ui.geometry.Size(w * 0.18f, h * 0.35f),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(8f, 8f)
            )
        }
    }
}
