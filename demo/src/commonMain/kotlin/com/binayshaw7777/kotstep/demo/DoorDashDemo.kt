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
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.binayshaw7777.kotstep.v3.KotStep
import com.binayshaw7777.kotstep.v3.model.step.StepLayoutStyle
import com.binayshaw7777.kotstep.v3.model.style.IconStyle
import com.binayshaw7777.kotstep.v3.model.style.KotStepStyle
import com.binayshaw7777.kotstep.v3.model.style.LineStyle
import com.binayshaw7777.kotstep.v3.model.style.LineStyles
import com.binayshaw7777.kotstep.v3.model.style.StepStyle
import com.binayshaw7777.kotstep.v3.model.style.StepStyles
import com.binayshaw7777.kotstep.v3.util.ExperimentalKotStep

private val DOORDASH_RED = Color(0xFFFF3008)
private val DOORDASH_BLACK = Color(0xFF191919)
private val DOORDASH_DARK = Color(0xFF1E1E24)
private val DOORDASH_GRAY = Color(0xFFE5E7EB)
private val DOORDASH_ICON_GRAY = Color(0xFF6B7280)
private val DOORDASH_TEXT_MUTED = Color(0xFF4B5563)

@OptIn(ExperimentalKotStep::class)
@Composable
fun DoorDashDemo(
    modifier: Modifier = Modifier,
    onBack: (() -> Unit)? = null
) {
    var currentStep by remember { mutableFloatStateOf(1.4f) }
    var detailsExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF9FAFB))
    ) {
        // Upper section: Stylized city map with delivery route
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.42f)
        ) {
            DoorDashMapView(modifier = Modifier.fillMaxSize())

            // Top action buttons: Close (X) and Help pill
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = CircleShape,
                    color = Color.White,
                    shadowElevation = 4.dp,
                    modifier = Modifier.size(40.dp)
                ) {
                    IconButton(onClick = { onBack?.invoke() }) {
                        Icon(
                            imageVector = if (onBack != null) Icons.AutoMirrored.Filled.ArrowBack else Icons.Default.Close,
                            contentDescription = "Close",
                            tint = DOORDASH_BLACK,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color.White,
                    shadowElevation = 4.dp
                ) {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 18.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = "Help",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = DOORDASH_BLACK
                        )
                    }
                }
            }
        }

        // Lower section: Bottom sheet card with KotStep Horizontal Stepper
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.58f),
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            color = Color.White,
            shadowElevation = 12.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Header status
                Text(
                    text = "Picking up order",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = DOORDASH_DARK
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Arrives between ",
                        fontSize = 15.sp,
                        color = DOORDASH_TEXT_MUTED
                    )
                    Text(
                        text = "1:44 PM - 1:54 PM",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = DOORDASH_DARK
                    )
                }

                // KotStep Horizontal Stepper
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    KotStep(
                        modifier = Modifier.fillMaxWidth(),
                        currentStep = { currentStep },
                        style = getDoorDashKotStepStyle()
                    ) {
                        // Step 0: DoorDash brand icon
                        step(
                            content = { DoorDashLogoIcon() },
                            onClick = { currentStep = 0f }
                        )
                        // Step 1: Restaurant / Store icon
                        step(
                            content = { StoreIcon(isDone = currentStep >= 1f) },
                            onClick = { currentStep = 1f }
                        )
                        // Step 2: Dasher Car icon
                        step(
                            content = { CarIcon(isDone = currentStep >= 2f) },
                            onClick = { currentStep = 2f }
                        )
                        // Step 3: Destination Home icon
                        step(
                            content = { HomeDropIcon(isDone = currentStep >= 3f) },
                            onClick = { currentStep = 3f }
                        )
                    }
                }

                // Status message description
                Text(
                    text = "Your Dasher is now heading to Yifang Taiwan Fruit Tea to pick up your second order.",
                    fontSize = 14.sp,
                    color = DOORDASH_TEXT_MUTED,
                    lineHeight = 20.sp
                )

                HorizontalDivider(color = Color(0xFFF3F4F6))

                // Dasher info row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Your Dasher",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = DOORDASH_DARK
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Jason M.",
                            fontSize = 14.sp,
                            color = DOORDASH_TEXT_MUTED
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = Color(0xFFF3F4F6)
                        ) {
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 14.dp, vertical = 8.dp)
                            ) {
                                Text(
                                    text = "Add Tip",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = DOORDASH_DARK
                                )
                            }
                        }

                        Surface(
                            shape = CircleShape,
                            color = Color(0xFFF3F4F6),
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Call,
                                    contentDescription = "Call",
                                    tint = DOORDASH_DARK,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }

                        Surface(
                            shape = CircleShape,
                            color = Color(0xFFF3F4F6),
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Email,
                                    contentDescription = "Chat",
                                    tint = DOORDASH_DARK,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }

                // Expandable Details
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .noRippleClick { detailsExpanded = !detailsExpanded }
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "View all details",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = DOORDASH_DARK
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = if (detailsExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        tint = DOORDASH_DARK,
                        modifier = Modifier.size(18.dp)
                    )
                }

                AnimatedVisibility(visible = detailsExpanded) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAFB)),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            DoorDashDetailRow("Order #", "DD-92819284")
                            DoorDashDetailRow("Store", "Yifang Taiwan Fruit Tea")
                            DoorDashDetailRow("Items (2)", "Brown Sugar Pearl Milk, Mango Fruit Tea")
                            DoorDashDetailRow("Subtotal", "$17.40")
                            DoorDashDetailRow("Delivery Fee", "$0.00 (DashPass)")
                            DoorDashDetailRow("Total Paid", "$21.15")
                        }
                    }
                }

                // Promo Card at bottom
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAFB)),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        GiftBoxIcon(modifier = Modifier.size(36.dp))
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Invite friends, get $10 off",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = DOORDASH_DARK,
                            modifier = Modifier.weight(1f)
                        )
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFFE5E7EB),
                            modifier = Modifier.size(28.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                    contentDescription = null,
                                    tint = DOORDASH_DARK,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }

                // Interactive progress test controller
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF3F4F6)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = "KotStep Progress Controller (${((currentStep * 10).toInt()) / 10f} / 3.0)",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = DOORDASH_DARK
                        )
                        Slider(
                            value = currentStep,
                            onValueChange = { currentStep = it },
                            valueRange = 0f..3f,
                            colors = SliderDefaults.colors(
                                thumbColor = DOORDASH_RED,
                                activeTrackColor = DOORDASH_RED
                            )
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            OutlinedButton(
                                onClick = { currentStep = (currentStep - 1f).coerceAtLeast(0f) }
                            ) {
                                Text("Prev Step", fontSize = 11.sp)
                            }
                            Button(
                                onClick = { currentStep = (currentStep + 1f).coerceAtMost(3f) },
                                colors = ButtonDefaults.buttonColors(containerColor = DOORDASH_RED)
                            ) {
                                Text("Next Step", fontSize = 11.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DoorDashDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = DOORDASH_TEXT_MUTED, fontSize = 12.sp)
        Text(text = value, color = DOORDASH_DARK, fontSize = 12.sp, fontWeight = FontWeight.Medium)
    }
}

@OptIn(ExperimentalKotStep::class)
private fun getDoorDashKotStepStyle(): KotStepStyle {
    return KotStepStyle(
        stepLayoutStyle = StepLayoutStyle.Horizontal,
        showCheckMarkOnDone = false,
        stepStyle = StepStyles.default().copy(
            onDone = StepStyle(
                stepColor = DOORDASH_BLACK,
                stepSize = 34.dp,
                iconStyle = IconStyle(iconTint = Color.White, iconSize = 18.dp)
            ),
            onCurrent = StepStyle(
                stepColor = DOORDASH_BLACK,
                stepSize = 34.dp,
                iconStyle = IconStyle(iconTint = Color.White, iconSize = 18.dp)
            ),
            onTodo = StepStyle(
                stepColor = DOORDASH_GRAY,
                stepSize = 34.dp,
                iconStyle = IconStyle(iconTint = DOORDASH_ICON_GRAY, iconSize = 18.dp)
            )
        ),
        lineStyle = LineStyles.default().copy(
            onDone = LineStyle(
                lineColor = DOORDASH_BLACK,
                progressColor = DOORDASH_BLACK,
                lineThickness = 3.dp,
                lineLength = 48.dp,
                linePadding = PaddingValues(horizontal = 2.dp)
            ),
            onCurrent = LineStyle(
                lineColor = DOORDASH_GRAY,
                progressColor = DOORDASH_BLACK,
                lineThickness = 3.dp,
                lineLength = 48.dp,
                linePadding = PaddingValues(horizontal = 2.dp)
            ),
            onTodo = LineStyle(
                lineColor = DOORDASH_GRAY,
                progressColor = DOORDASH_GRAY,
                lineThickness = 3.dp,
                lineLength = 48.dp,
                linePadding = PaddingValues(horizontal = 2.dp)
            )
        )
    )
}

// Custom icons matching DoorDash UI
@Composable
private fun DoorDashLogoIcon() {
    Canvas(modifier = Modifier.size(18.dp)) {
        val path = Path().apply {
            moveTo(size.width * 0.15f, size.height * 0.55f)
            cubicTo(
                size.width * 0.15f, size.height * 0.3f,
                size.width * 0.45f, size.height * 0.25f,
                size.width * 0.85f, size.height * 0.45f
            )
            cubicTo(
                size.width * 0.9f, size.height * 0.55f,
                size.width * 0.8f, size.height * 0.7f,
                size.width * 0.65f, size.height * 0.7f
            )
            lineTo(size.width * 0.35f, size.height * 0.7f)
            close()
        }
        drawPath(path, color = Color.White)
    }
}

@Composable
private fun StoreIcon(isDone: Boolean) {
    val tint = if (isDone) Color.White else DOORDASH_ICON_GRAY
    Canvas(modifier = Modifier.size(16.dp)) {
        // Store roof & body
        val w = size.width
        val h = size.height
        val roof = Path().apply {
            moveTo(0f, h * 0.35f)
            lineTo(w * 0.5f, 0f)
            lineTo(w, h * 0.35f)
            close()
        }
        drawPath(roof, color = tint)
        drawRect(
            color = tint,
            topLeft = Offset(w * 0.15f, h * 0.35f),
            size = androidx.compose.ui.geometry.Size(w * 0.7f, h * 0.6f)
        )
        // Store entrance
        drawRect(
            color = if (isDone) DOORDASH_BLACK else Color.White,
            topLeft = Offset(w * 0.38f, h * 0.55f),
            size = androidx.compose.ui.geometry.Size(w * 0.24f, h * 0.4f)
        )
    }
}

@Composable
private fun CarIcon(isDone: Boolean) {
    val tint = if (isDone) Color.White else DOORDASH_ICON_GRAY
    Canvas(modifier = Modifier.size(16.dp)) {
        val w = size.width
        val h = size.height
        // Car cabin & body
        val body = Path().apply {
            moveTo(w * 0.1f, h * 0.65f)
            lineTo(w * 0.2f, h * 0.4f)
            lineTo(w * 0.75f, h * 0.4f)
            lineTo(w * 0.9f, h * 0.65f)
            lineTo(w * 0.95f, h * 0.75f)
            lineTo(w * 0.05f, h * 0.75f)
            close()
        }
        drawPath(body, color = tint)
        // Wheels
        drawCircle(
            color = if (isDone) DOORDASH_BLACK else Color.White,
            radius = w * 0.12f,
            center = Offset(w * 0.28f, h * 0.75f)
        )
        drawCircle(
            color = if (isDone) DOORDASH_BLACK else Color.White,
            radius = w * 0.12f,
            center = Offset(w * 0.72f, h * 0.75f)
        )
    }
}

@Composable
private fun HomeDropIcon(isDone: Boolean) {
    Icon(
        imageVector = Icons.Default.Home,
        contentDescription = null,
        tint = if (isDone) Color.White else DOORDASH_ICON_GRAY,
        modifier = Modifier.size(18.dp)
    )
}

@Composable
private fun GiftBoxIcon(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        // Box
        drawRect(
            color = Color(0xFF3B82F6),
            topLeft = Offset(w * 0.15f, h * 0.35f),
            size = androidx.compose.ui.geometry.Size(w * 0.7f, h * 0.6f)
        )
        // Ribbon horizontal
        drawRect(
            color = Color(0xFFEF4444),
            topLeft = Offset(w * 0.15f, h * 0.58f),
            size = androidx.compose.ui.geometry.Size(w * 0.7f, h * 0.14f)
        )
        // Ribbon vertical
        drawRect(
            color = Color(0xFFEF4444),
            topLeft = Offset(w * 0.43f, h * 0.35f),
            size = androidx.compose.ui.geometry.Size(w * 0.14f, h * 0.6f)
        )
        // Bow
        drawCircle(
            color = Color(0xFFEF4444),
            radius = w * 0.12f,
            center = Offset(w * 0.38f, h * 0.26f)
        )
        drawCircle(
            color = Color(0xFFEF4444),
            radius = w * 0.12f,
            center = Offset(w * 0.62f, h * 0.26f)
        )
    }
}

// Map visualization with route polyline, neighborhood labels, Dasher pin & Destination
@Composable
private fun DoorDashMapView(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(Color(0xFFE8ECE9))
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height

            // Background blocks
            drawRect(Color(0xFFF3F4F1), topLeft = Offset(0f, 0f), size = androidx.compose.ui.geometry.Size(w, h))

            // Water section on right/bottom-right (Bay)
            val water = Path().apply {
                moveTo(w * 0.85f, 0f)
                lineTo(w, 0f)
                lineTo(w, h)
                lineTo(w * 0.88f, h)
                cubicTo(w * 0.78f, h * 0.7f, w * 0.9f, h * 0.35f, w * 0.85f, 0f)
                close()
            }
            drawPath(water, color = Color(0xFFD6E4F0))

            // Main city grid roads
            val roadColor = Color.White
            val roadBorder = Color(0xFFDDE1DD)

            val roads = listOf(
                Pair(Offset(0f, h * 0.15f), Offset(w, h * 0.15f)),
                Pair(Offset(0f, h * 0.32f), Offset(w, h * 0.32f)),
                Pair(Offset(0f, h * 0.52f), Offset(w, h * 0.52f)),
                Pair(Offset(0f, h * 0.72f), Offset(w, h * 0.72f)),
                Pair(Offset(w * 0.22f, 0f), Offset(w * 0.22f, h)),
                Pair(Offset(w * 0.44f, 0f), Offset(w * 0.44f, h)),
                Pair(Offset(w * 0.68f, 0f), Offset(w * 0.68f, h)),
                // Diagonal Market street
                Pair(Offset(w * 0.1f, h * 0.8f), Offset(w * 0.85f, h * 0.2f))
            )

            for ((start, end) in roads) {
                drawLine(roadBorder, start, end, strokeWidth = 14f)
                drawLine(roadColor, start, end, strokeWidth = 10f)
            }

            // Route path: Dasher -> Store -> Customer
            val routePath = Path().apply {
                moveTo(w * 0.28f, h * 0.35f) // Dasher car
                lineTo(w * 0.42f, h * 0.35f)
                lineTo(w * 0.44f, h * 0.62f)
                lineTo(w * 0.63f, h * 0.62f)
                lineTo(w * 0.65f, h * 0.75f) // Store
                lineTo(w * 0.76f, h * 0.5f)  // Dropoff
            }
            drawLine(Color.White, Offset(w * 0.28f, h * 0.35f), Offset(w * 0.42f, h * 0.35f), strokeWidth = 8f)
            drawPath(routePath, color = Color(0xFF1E1E24), style = Stroke(width = 6f))
        }

        // Neighborhood labels
        Text(
            text = "MARINA DISTRICT",
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF9CA3AF),
            modifier = Modifier.align(Alignment.TopStart).padding(start = 24.dp, top = 65.dp)
        )
        Text(
            text = "WESTERN ADDITION",
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF9CA3AF),
            modifier = Modifier.align(Alignment.CenterStart).padding(start = 70.dp, bottom = 40.dp)
        )
        Text(
            text = "SOMA",
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF9CA3AF),
            modifier = Modifier.align(Alignment.CenterEnd).padding(end = 60.dp, bottom = 20.dp)
        )

        // Marker 1: Dasher Red Pin (Hayes Valley)
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 90.dp, bottom = 80.dp)
        ) {
            Surface(
                shape = CircleShape,
                color = DOORDASH_RED,
                shadowElevation = 6.dp,
                modifier = Modifier.size(34.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    CarIcon(isDone = true)
                }
            }
        }

        // Marker 2: Store Pin (Yifang Taiwan Fruit Tea)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(start = 90.dp, top = 40.dp)
        ) {
            Surface(
                shape = CircleShape,
                color = DOORDASH_BLACK,
                shadowElevation = 6.dp,
                modifier = Modifier.size(30.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    StoreIcon(isDone = true)
                }
            }
            Surface(
                shape = RoundedCornerShape(4.dp),
                color = Color.White,
                shadowElevation = 2.dp,
                modifier = Modifier.padding(top = 2.dp)
            ) {
                Text(
                    text = "Yifang Taiwan Fruit Tea",
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    color = DOORDASH_DARK,
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                )
            }
        }

        // Marker 3: Home destination pin
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 55.dp, bottom = 100.dp)
        ) {
            Surface(
                shape = CircleShape,
                color = DOORDASH_BLACK,
                shadowElevation = 6.dp,
                modifier = Modifier.size(30.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}
