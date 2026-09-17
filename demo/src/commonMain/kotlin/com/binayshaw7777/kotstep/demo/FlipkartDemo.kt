package com.binayshaw7777.kotstep.demo

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
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
import androidx.compose.ui.text.style.TextDecoration
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
private val FLIPKART_DARK_BLUE = Color(0xFF1B59C2)
private val FLIPKART_GREEN = Color(0xFF388E3C)
private val FLIPKART_YELLOW = Color(0xFFFF9F00)
private val FLIPKART_BG = Color(0xFFF1F3F6)
private val FLIPKART_TEXT_PRIMARY = Color(0xFF212121)
private val FLIPKART_TEXT_SECONDARY = Color(0xFF878787)
private val FLIPKART_TEXT_MUTED = Color(0xFF616161)
private val FLIPKART_DIVIDER = Color(0xFFE0E0E0)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalKotStep::class)
@Composable
fun FlipkartDemo(
    modifier: Modifier = Modifier,
    onBack: (() -> Unit)? = null
) {
    var currentStep by remember { mutableFloatStateOf(2.0f) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Order Details",
                        fontSize = 18.sp,
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
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = Color.White
                        )
                    }
                    Box(modifier = Modifier.padding(end = 8.dp)) {
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = "Cart",
                                tint = Color.White
                            )
                        }
                        Surface(
                            shape = CircleShape,
                            color = FLIPKART_YELLOW,
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(top = 6.dp, end = 6.dp)
                                .size(16.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "2",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = FLIPKART_TEXT_PRIMARY
                                )
                            }
                        }
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
            // Order ID & Copy Bar
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
                        text = "Order ID: OD32891048291048100",
                        fontSize = 13.sp,
                        color = FLIPKART_TEXT_SECONDARY
                    )
                    Text(
                        text = "COPY",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = FLIPKART_BLUE
                    )
                }
            }

            // Product Summary Card
            Surface(
                color = Color.White,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    FlipkartPhoneThumbnail(modifier = Modifier.size(80.dp))

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Apple iPhone 15 (Black, 128 GB)",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = FLIPKART_TEXT_PRIMARY,
                            lineHeight = 18.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Seller: SuperComNet",
                                fontSize = 12.sp,
                                color = FLIPKART_TEXT_SECONDARY
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            FlipkartAssuredBadge()
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "₹54,999",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = FLIPKART_TEXT_PRIMARY
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "₹79,900",
                                fontSize = 12.sp,
                                color = FLIPKART_TEXT_SECONDARY,
                                textDecoration = TextDecoration.LineThrough
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "31% off",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = FLIPKART_GREEN
                            )
                        }
                    }
                }
            }

            // Order Status Stepper Card
            Surface(
                color = Color.White,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Order Status",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = FLIPKART_TEXT_PRIMARY,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    // KotStep Vertical Stepper
                    KotStep(
                        modifier = Modifier.fillMaxWidth().padding(start = 4.dp),
                        currentStep = { currentStep },
                        style = getFlipkartKotStepStyle()
                    ) {
                        // Step 0: Order Confirmed
                        step(
                            icon = Icons.Default.Check,
                            trailingLabel = {
                                FlipkartStepLabel(
                                    title = "Order Confirmed",
                                    subtitle = "Sun, 15th Sep '24 - 10:24 AM",
                                    details = "Your order has been placed successfully."
                                )
                            },
                            onClick = { currentStep = 0f }
                        )

                        // Step 1: Shipped
                        step(
                            icon = Icons.Default.Check,
                            trailingLabel = {
                                FlipkartStepLabel(
                                    title = "Shipped",
                                    subtitle = "Mon, 16th Sep '24 - 06:30 PM",
                                    details = "Item has been packed and picked up by courier partner EKART (FMPC0921827364)."
                                )
                            },
                            onClick = { currentStep = 1f }
                        )

                        // Step 2: Out for Delivery
                        step(
                            icon = Icons.Default.Check,
                            trailingLabel = {
                                FlipkartStepLabel(
                                    title = "Out for Delivery",
                                    subtitle = "Today, Expected by 9:00 PM",
                                    details = "Courier delivery agent Rahul K. (+91 98112 34567) is out for delivery in your area.",
                                    highlightSubtitle = true
                                )
                            },
                            onClick = { currentStep = 2f }
                        )

                        // Step 3: Delivered
                        step(
                            icon = Icons.Default.Check,
                            trailingLabel = {
                                FlipkartStepLabel(
                                    title = "Delivery",
                                    subtitle = "Expected by 17th Sep '24",
                                    details = null
                                )
                            },
                            onClick = { currentStep = 3f }
                        )
                    }
                }
            }

            // Delivery Address Card
            Surface(
                color = Color.White,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = FLIPKART_BLUE,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Delivery Address",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = FLIPKART_TEXT_PRIMARY
                            )
                        }
                        Text(
                            text = "CHANGE",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = FLIPKART_BLUE
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Rahul Sharma",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = FLIPKART_TEXT_PRIMARY
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Flat 402, Sunshine Apartments, 100ft Road, Indiranagar, Bengaluru, Karnataka - 560038",
                        fontSize = 12.sp,
                        color = FLIPKART_TEXT_MUTED,
                        lineHeight = 16.sp
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Phone: +91 98765 43210",
                        fontSize = 12.sp,
                        color = FLIPKART_TEXT_MUTED
                    )
                }
            }

            // Price Details Card
            Surface(
                color = Color.White,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Price Details",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = FLIPKART_TEXT_PRIMARY
                    )
                    HorizontalDivider(color = FLIPKART_DIVIDER)
                    FlipkartPriceRow("List Price", "₹79,900")
                    FlipkartPriceRow("Selling Price", "₹54,999")
                    FlipkartPriceRow("Special Discount", "-₹24,901", isDiscount = true)
                    FlipkartPriceRow("Delivery Charges", "FREE", isFree = true)
                    HorizontalDivider(color = FLIPKART_DIVIDER)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Total Amount",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = FLIPKART_TEXT_PRIMARY
                        )
                        Text(
                            text = "₹54,999",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = FLIPKART_TEXT_PRIMARY
                        )
                    }
                    Text(
                        text = "You saved ₹24,901 on this order",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = FLIPKART_GREEN
                    )
                }
            }

            // Action Buttons
            Surface(
                color = Color.White,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = {},
                        modifier = Modifier.weight(1f).height(44.dp),
                        shape = RoundedCornerShape(4.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFD4D4D4))
                    ) {
                        Text(
                            text = "Need Help?",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = FLIPKART_TEXT_PRIMARY
                        )
                    }

                    OutlinedButton(
                        onClick = {},
                        modifier = Modifier.weight(1f).height(44.dp),
                        shape = RoundedCornerShape(4.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFD4D4D4))
                    ) {
                        Text(
                            text = "Cancel Order",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = FLIPKART_TEXT_PRIMARY
                        )
                    }
                }
            }

            // Interactive KotStep simulation controls
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "KotStep Flipkart Step Simulator (${currentStep.toInt() + 1} / 4)",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = FLIPKART_TEXT_PRIMARY
                    )
                    Slider(
                        value = currentStep,
                        onValueChange = { currentStep = it },
                        valueRange = 0f..3f,
                        steps = 2,
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
                            Text("Previous Step", fontSize = 11.sp)
                        }
                        Button(
                            onClick = { currentStep = (currentStep + 1f).coerceAtMost(3f) },
                            colors = ButtonDefaults.buttonColors(containerColor = FLIPKART_BLUE)
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
private fun FlipkartStepLabel(
    title: String,
    subtitle: String,
    details: String?,
    highlightSubtitle: Boolean = false
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 14.dp, bottom = 8.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = FLIPKART_TEXT_PRIMARY
        )
        Text(
            text = subtitle,
            fontSize = 12.sp,
            fontWeight = if (highlightSubtitle) FontWeight.Bold else FontWeight.Normal,
            color = if (highlightSubtitle) FLIPKART_GREEN else FLIPKART_TEXT_SECONDARY
        )
        if (details != null) {
            Text(
                text = details,
                fontSize = 12.sp,
                color = FLIPKART_TEXT_MUTED,
                lineHeight = 16.sp,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}

@Composable
private fun FlipkartPriceRow(
    label: String,
    value: String,
    isDiscount: Boolean = false,
    isFree: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontSize = 13.sp, color = FLIPKART_TEXT_MUTED)
        Text(
            text = value,
            fontSize = 13.sp,
            fontWeight = if (isDiscount || isFree) FontWeight.Bold else FontWeight.Normal,
            color = if (isDiscount || isFree) FLIPKART_GREEN else FLIPKART_TEXT_PRIMARY
        )
    }
}

@OptIn(ExperimentalKotStep::class)
private fun getFlipkartKotStepStyle(): KotStepStyle {
    return KotStepStyle(
        stepLayoutStyle = StepLayoutStyle.Vertical,
        showCheckMarkOnDone = true,
        stepStyle = StepStyles.default().copy(
            onDone = StepStyle(
                stepColor = FLIPKART_GREEN,
                stepSize = 22.dp,
                iconStyle = IconStyle(iconTint = Color.White, iconSize = 14.dp)
            ),
            onCurrent = StepStyle(
                stepColor = FLIPKART_GREEN,
                stepSize = 22.dp,
                iconStyle = IconStyle(iconTint = Color.White, iconSize = 14.dp)
            ),
            onTodo = StepStyle(
                stepColor = Color(0xFFE0E0E0),
                stepSize = 22.dp,
                borderStyle = BorderStyle(width = 1.5.dp, color = Color(0xFFBDBDBD)),
                iconStyle = IconStyle(iconTint = Color.Transparent, iconSize = 0.dp)
            )
        ),
        lineStyle = LineStyles.default().copy(
            onDone = LineStyle(
                lineColor = FLIPKART_GREEN,
                progressColor = FLIPKART_GREEN,
                lineThickness = 2.5.dp,
                lineLength = 46.dp,
                linePadding = PaddingValues(vertical = 2.dp)
            ),
            onCurrent = LineStyle(
                lineColor = Color(0xFFE0E0E0),
                progressColor = FLIPKART_GREEN,
                lineThickness = 2.5.dp,
                lineLength = 46.dp,
                linePadding = PaddingValues(vertical = 2.dp)
            ),
            onTodo = LineStyle(
                lineColor = Color(0xFFE0E0E0),
                progressColor = Color(0xFFE0E0E0),
                lineThickness = 2.5.dp,
                lineLength = 46.dp,
                linePadding = PaddingValues(vertical = 2.dp)
            )
        )
    )
}

@Composable
private fun FlipkartPhoneThumbnail(modifier: Modifier = Modifier) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = Color(0xFFF9FAFB),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE5E7EB)),
        modifier = modifier
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            // Phone body
            drawRoundRect(
                color = Color(0xFF1F2937),
                topLeft = Offset(w * 0.2f, h * 0.1f),
                size = androidx.compose.ui.geometry.Size(w * 0.6f, h * 0.8f),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(10f, 10f)
            )
            // Screen
            drawRoundRect(
                color = Color(0xFF374151),
                topLeft = Offset(w * 0.24f, h * 0.14f),
                size = androidx.compose.ui.geometry.Size(w * 0.52f, h * 0.72f),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(6f, 6f)
            )
            // Dynamic Island
            drawRoundRect(
                color = Color.Black,
                topLeft = Offset(w * 0.4f, h * 0.16f),
                size = androidx.compose.ui.geometry.Size(w * 0.2f, h * 0.04f),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(4f, 4f)
            )
        }
    }
}

@Composable
private fun FlipkartAssuredBadge() {
    Surface(
        shape = RoundedCornerShape(4.dp),
        color = Color(0xFF0074E4)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "f",
                fontSize = 11.sp,
                fontWeight = FontWeight.ExtraBold,
                color = FLIPKART_YELLOW
            )
            Spacer(modifier = Modifier.width(2.dp))
            Text(
                text = "Assured",
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}
