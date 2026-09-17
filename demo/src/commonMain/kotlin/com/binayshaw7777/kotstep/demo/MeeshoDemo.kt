package com.binayshaw7777.kotstep.demo

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Share
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
import androidx.compose.runtime.mutableStateOf
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

private val MEESHO_GREEN = Color(0xFF009668)
private val MEESHO_GREEN_LIGHT = Color(0xFFE8F7F0)
private val MEESHO_GREEN_HALO = Color(0xFFBBE9D7)
private val MEESHO_PINK = Color(0xFF9E1B55)
private val MEESHO_PINK_ACCENT = Color(0xFFE91E63)
private val MEESHO_TEXT_PRIMARY = Color(0xFF262626)
private val MEESHO_TEXT_SECONDARY = Color(0xFF757575)
private val MEESHO_TEXT_MUTED = Color(0xFF616161)
private val MEESHO_DIVIDER = Color(0xFFEFEFEF)
private val MEESHO_BORDER = Color(0xFFD4D4D4)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalKotStep::class)
@Composable
fun MeeshoDemo(
    modifier: Modifier = Modifier,
    onBack: (() -> Unit)? = null
) {
    var currentStep by remember { mutableFloatStateOf(1.0f) }
    var showMoreTracking by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "ORDER DETAILS",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MEESHO_TEXT_PRIMARY,
                        letterSpacing = 0.5.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onBack?.invoke() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MEESHO_TEXT_PRIMARY
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Margin Banner
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                color = MEESHO_GREEN_LIGHT,
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFC3EAD6))
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    MeeshoRupeeIcon(modifier = Modifier.size(36.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Margin: ₹0 ",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = MEESHO_GREEN
                            )
                            Text(
                                text = "(Margin for the below product)",
                                fontSize = 12.sp,
                                color = MEESHO_TEXT_SECONDARY
                            )
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Please collect margin from your customer",
                            fontSize = 13.sp,
                            color = MEESHO_TEXT_MUTED
                        )
                    }
                }
            }

            // Product Details Section
            Text(
                text = "Product Details",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MEESHO_TEXT_PRIMARY
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Earphone thumbnail illustration
                ProductThumbnailView(modifier = Modifier.size(72.dp))

                Spacer(modifier = Modifier.width(14.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "realme wired earphone buds",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MEESHO_TEXT_PRIMARY
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Price  ₹163",
                        fontSize = 13.sp,
                        color = MEESHO_TEXT_SECONDARY
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Size  Free Size",
                        fontSize = 13.sp,
                        color = MEESHO_TEXT_SECONDARY
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Qty  1",
                        fontSize = 13.sp,
                        color = MEESHO_TEXT_SECONDARY
                    )
                }

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = MEESHO_TEXT_SECONDARY,
                    modifier = Modifier.size(20.dp)
                )
            }

            HorizontalDivider(color = MEESHO_DIVIDER)

            // Order Tracking Section
            Text(
                text = "Order Tracking",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MEESHO_TEXT_PRIMARY
            )

            // KotStep Vertical Stepper
            Box(modifier = Modifier.fillMaxWidth().padding(start = 6.dp)) {
                KotStep(
                    currentStep = { currentStep },
                    style = getMeeshoKotStepStyle()
                ) {
                    // Step 0: Order Placed
                    step(
                        content = {
                            MeeshoStepDot(
                                isDone = currentStep >= 0f,
                                isCurrent = currentStep in 0f..0.99f
                            )
                        },
                        trailingLabel = {
                            MeeshoStepLabel(
                                title = "Order Placed",
                                subtitle = "11:02 AM, 05 May, 2022"
                            )
                        },
                        onClick = { currentStep = 0f }
                    )

                    // Step 1: Shipped
                    step(
                        content = {
                            MeeshoStepDot(
                                isDone = currentStep >= 1f,
                                isCurrent = currentStep in 1f..1.99f
                            )
                        },
                        trailingLabel = {
                            MeeshoStepLabel(
                                title = "Shipped",
                                subtitle = "Reached at mam-chennai, chennai\n09:18 PM, 31 May, 2022"
                            )
                        },
                        onClick = { currentStep = 1f }
                    )

                    // Step 2: Delivered
                    step(
                        content = {
                            MeeshoStepDot(
                                isDone = currentStep >= 2f,
                                isCurrent = currentStep >= 2f
                            )
                        },
                        trailingLabel = {
                            MeeshoStepLabel(
                                title = "Delivered",
                                subtitle = "Expected by 22 May, 2022"
                            )
                        },
                        onClick = { currentStep = 2f }
                    )
                }
            }

            // SHOW MORE Button
            Row(
                modifier = Modifier
                    .noRippleClick { showMoreTracking = !showMoreTracking }
                    .padding(start = 32.dp, top = 2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (showMoreTracking) "SHOW LESS" else "SHOW MORE",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = MEESHO_PINK_ACCENT
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = if (showMoreTracking) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = MEESHO_PINK_ACCENT,
                    modifier = Modifier.size(16.dp)
                )
            }

            AnimatedVisibility(visible = showMoreTracking) {
                Surface(
                    color = Color(0xFFFAFAFA),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth().padding(start = 24.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("• 05 May, 11:30 AM: Order verified by supplier", fontSize = 12.sp, color = MEESHO_TEXT_MUTED)
                        Text("• 06 May, 02:15 PM: Packed & ready for courier pickup", fontSize = 12.sp, color = MEESHO_TEXT_MUTED)
                        Text("• 08 May, 07:45 PM: In transit from Delhi hub to Chennai", fontSize = 12.sp, color = MEESHO_TEXT_MUTED)
                        Text("• 31 May, 09:18 PM: Reached at mam-chennai delivery hub", fontSize = 12.sp, color = MEESHO_TEXT_MUTED)
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Action Buttons: Open Tracking Link & Share
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = {},
                    modifier = Modifier.weight(1f).height(42.dp),
                    shape = RoundedCornerShape(4.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF262626))
                ) {
                    ExternalLinkIcon(modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Open Tracking Link",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MEESHO_TEXT_PRIMARY
                    )
                }

                OutlinedButton(
                    onClick = {},
                    modifier = Modifier.weight(0.7f).height(42.dp),
                    shape = RoundedCornerShape(4.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF262626))
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share",
                        tint = MEESHO_TEXT_PRIMARY,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Share",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MEESHO_TEXT_PRIMARY
                    )
                }
            }

            HorizontalDivider(color = MEESHO_DIVIDER)

            // Return/Exchange Order
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Return/Exchange Order",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MEESHO_TEXT_PRIMARY
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = MEESHO_TEXT_PRIMARY,
                    modifier = Modifier.size(20.dp)
                )
            }

            HorizontalDivider(color = MEESHO_DIVIDER)

            // Interactive KotStep tester
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF7F7F7)),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "KotStep Status Simulator (Step: ${currentStep.toInt() + 1} of 3)",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MEESHO_TEXT_PRIMARY
                    )
                    Slider(
                        value = currentStep,
                        onValueChange = { currentStep = it },
                        valueRange = 0f..2f,
                        steps = 1,
                        colors = SliderDefaults.colors(
                            thumbColor = MEESHO_GREEN,
                            activeTrackColor = MEESHO_GREEN
                        )
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        OutlinedButton(
                            onClick = { currentStep = (currentStep - 1f).coerceAtLeast(0f) }
                        ) {
                            Text("Previous", fontSize = 11.sp)
                        }
                        Button(
                            onClick = { currentStep = (currentStep + 1f).coerceAtMost(2f) },
                            colors = ButtonDefaults.buttonColors(containerColor = MEESHO_GREEN)
                        ) {
                            Text("Next Step", fontSize = 11.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MeeshoStepLabel(
    title: String,
    subtitle: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 14.dp, bottom = 4.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = MEESHO_TEXT_PRIMARY
        )
        Text(
            text = subtitle,
            fontSize = 12.sp,
            color = MEESHO_TEXT_SECONDARY,
            lineHeight = 16.sp
        )
    }
}

// Indicator matching Meesho's style: solid green dot when done, halo around dot when current, grey hollow circle when todo
@Composable
private fun MeeshoStepDot(
    isDone: Boolean,
    isCurrent: Boolean
) {
    Box(
        modifier = Modifier.size(20.dp),
        contentAlignment = Alignment.Center
    ) {
        if (isCurrent && isDone) {
            // Soft green outer halo ring
            Surface(
                shape = CircleShape,
                color = MEESHO_GREEN_HALO,
                modifier = Modifier.size(20.dp)
            ) {}
            // Inner green dot
            Surface(
                shape = CircleShape,
                color = MEESHO_GREEN,
                modifier = Modifier.size(10.dp)
            ) {}
        } else if (isDone) {
            // Small solid green dot
            Surface(
                shape = CircleShape,
                color = MEESHO_GREEN,
                modifier = Modifier.size(10.dp)
            ) {}
        } else {
            // Small grey ring
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .border(1.5.dp, MEESHO_BORDER, CircleShape)
            )
        }
    }
}

@OptIn(ExperimentalKotStep::class)
private fun getMeeshoKotStepStyle(): KotStepStyle {
    return KotStepStyle(
        stepLayoutStyle = StepLayoutStyle.Vertical,
        showCheckMarkOnDone = false,
        stepStyle = StepStyles.default().copy(
            onDone = StepStyle(
                stepColor = Color.Transparent,
                stepSize = 20.dp,
                borderStyle = BorderStyle(width = 0.dp, color = Color.Transparent)
            ),
            onCurrent = StepStyle(
                stepColor = Color.Transparent,
                stepSize = 20.dp,
                borderStyle = BorderStyle(width = 0.dp, color = Color.Transparent)
            ),
            onTodo = StepStyle(
                stepColor = Color.Transparent,
                stepSize = 20.dp,
                borderStyle = BorderStyle(width = 0.dp, color = Color.Transparent)
            )
        ),
        lineStyle = LineStyles.default().copy(
            onDone = LineStyle(
                lineColor = MEESHO_GREEN,
                progressColor = MEESHO_GREEN,
                lineThickness = 2.dp,
                lineLength = 36.dp,
                linePadding = PaddingValues(vertical = 2.dp)
            ),
            onCurrent = LineStyle(
                lineColor = MEESHO_BORDER,
                progressColor = MEESHO_GREEN,
                lineThickness = 2.dp,
                lineLength = 36.dp,
                linePadding = PaddingValues(vertical = 2.dp)
            ),
            onTodo = LineStyle(
                lineColor = MEESHO_BORDER,
                progressColor = MEESHO_BORDER,
                lineThickness = 2.dp,
                lineLength = 36.dp,
                linePadding = PaddingValues(vertical = 2.dp)
            )
        )
    )
}

@Composable
private fun MeeshoRupeeIcon(modifier: Modifier = Modifier) {
    Surface(
        shape = RoundedCornerShape(6.dp),
        color = MEESHO_GREEN,
        modifier = modifier
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = "₹",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Composable
private fun ProductThumbnailView(modifier: Modifier = Modifier) {
    Surface(
        shape = RoundedCornerShape(4.dp),
        color = Color(0xFFFFF7ED),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFED7AA)),
        modifier = modifier
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            // Earphone box yellow background
            drawRect(Color(0xFFF59E0B), topLeft = Offset(w * 0.15f, h * 0.15f), size = androidx.compose.ui.geometry.Size(w * 0.4f, h * 0.7f))
            // Earbuds wire
            val wire = Path().apply {
                moveTo(w * 0.65f, h * 0.2f)
                lineTo(w * 0.65f, h * 0.8f)
                moveTo(w * 0.8f, h * 0.25f)
                lineTo(w * 0.8f, h * 0.8f)
            }
            drawPath(wire, color = Color(0xFF1F2937), style = androidx.compose.ui.graphics.drawscope.Stroke(width = 3f))
            // Earbud heads
            drawCircle(Color(0xFF111827), radius = 5f, center = Offset(w * 0.65f, h * 0.2f))
            drawCircle(Color(0xFF111827), radius = 5f, center = Offset(w * 0.8f, h * 0.25f))
        }
    }
}

@Composable
private fun ExternalLinkIcon(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        // Box
        val boxPath = Path().apply {
            moveTo(w * 0.45f, h * 0.15f)
            lineTo(w * 0.15f, h * 0.15f)
            lineTo(w * 0.15f, h * 0.85f)
            lineTo(w * 0.85f, h * 0.85f)
            lineTo(w * 0.85f, h * 0.55f)
        }
        drawPath(boxPath, color = MEESHO_TEXT_PRIMARY, style = androidx.compose.ui.graphics.drawscope.Stroke(width = 2.5f))
        // Arrow pointing top right
        drawLine(MEESHO_TEXT_PRIMARY, Offset(w * 0.45f, h * 0.55f), Offset(w * 0.85f, h * 0.15f), strokeWidth = 2.5f)
        drawLine(MEESHO_TEXT_PRIMARY, Offset(w * 0.65f, h * 0.15f), Offset(w * 0.85f, h * 0.15f), strokeWidth = 2.5f)
        drawLine(MEESHO_TEXT_PRIMARY, Offset(w * 0.85f, h * 0.15f), Offset(w * 0.85f, h * 0.35f), strokeWidth = 2.5f)
    }
}
