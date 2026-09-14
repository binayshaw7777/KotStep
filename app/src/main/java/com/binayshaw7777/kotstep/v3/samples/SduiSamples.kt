package com.binayshaw7777.kotstep.v3.samples

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.binayshaw7777.kotstep.sdui.model.SduiAction
import com.binayshaw7777.kotstep.sdui.model.SduiFlow
import com.binayshaw7777.kotstep.sdui.model.SduiIndicator
import com.binayshaw7777.kotstep.sdui.model.SduiIndicatorType
import com.binayshaw7777.kotstep.sdui.model.SduiMutation
import com.binayshaw7777.kotstep.sdui.model.SduiStep
import com.binayshaw7777.kotstep.sdui.model.SduiStepPatch
import com.binayshaw7777.kotstep.sdui.model.SduiStepState
import com.binayshaw7777.kotstep.sdui.parser.SduiParser
import com.binayshaw7777.kotstep.sdui.resolver.SduiIconResolver
import com.binayshaw7777.kotstep.sdui.state.SduiApplyResult
import com.binayshaw7777.kotstep.sdui.state.SduiStateManager
import com.binayshaw7777.kotstep.sdui.ui.KotStepSdui
import com.binayshaw7777.kotstep.v3.util.ExperimentalKotStep

// =========================================================================================
// 1. Realistic Mock JSON Flows
// =========================================================================================

/**
 * Realistic e-commerce checkout flow:
 * Cart -> Address -> Payment -> Review with subtitles, prices, and icons.
 */
val CheckoutFlowJson: String = """
{
  "schemaVersion": "1.0",
  "flowId": "checkout-order-4891",
  "title": "Express Checkout",
  "stateModel": "CLIENT_OPTIMISTIC",
  "orientation": "HORIZONTAL",
  "currentStepId": "step-payment",
  "currentStepProgress": 0.0,
  "stateVersion": 1,
  "style": {
    "itemPaddingDp": 8,
    "showCheckMarkOnDone": true,
    "ignoreCurrentState": false,
    "stepStyle": {
      "todo":    { "colorHex": "#475569", "sizeDp": 32, "shape": "CIRCLE" },
      "current": { "colorHex": "#3B82F6", "sizeDp": 36, "shape": "CIRCLE", "borderWidthDp": 2, "borderColorHex": "#1D4ED8" },
      "done":    { "colorHex": "#10B981", "sizeDp": 32, "shape": "CIRCLE" },
      "error":   { "colorHex": "#EF4444", "sizeDp": 36, "shape": "CIRCLE", "borderWidthDp": 2, "borderColorHex": "#B91C1C" },
      "locked":  { "colorHex": "#64748B", "sizeDp": 32, "shape": "CIRCLE" }
    },
    "lineStyle": {
      "todo":    { "lineColorHex": "#334155", "progressColorHex": "#334155", "thicknessDp": 3, "lineType": "SOLID", "progressType": "SOLID" },
      "current": { "lineColorHex": "#334155", "progressColorHex": "#3B82F6", "thicknessDp": 3, "lineType": "SOLID", "progressType": "SOLID" },
      "done":    { "lineColorHex": "#10B981", "progressColorHex": "#10B981", "thicknessDp": 3, "lineType": "SOLID", "progressType": "SOLID" }
    }
  },
  "steps": [
    {
      "id": "step-cart",
      "ordinal": 0,
      "state": "DONE",
      "indicator": {
        "type": "ICON",
        "value": "shopping_cart"
      },
      "title": "Cart",
      "subtitle": "3 items • $129.99",
      "action": {
        "type": "NAVIGATE",
        "target": "cart_summary"
      },
      "metadata": {
        "price": "129.99",
        "itemCount": "3"
      }
    },
    {
      "id": "step-address",
      "ordinal": 1,
      "state": "DONE",
      "indicator": {
        "type": "ICON",
        "value": "local_shipping"
      },
      "title": "Address",
      "subtitle": "Express Priority • $15.00",
      "action": {
        "type": "NAVIGATE",
        "target": "shipping_selection"
      },
      "metadata": {
        "shippingCost": "15.00",
        "type": "Express"
      }
    },
    {
      "id": "step-payment",
      "ordinal": 2,
      "state": "CURRENT",
      "indicator": {
        "type": "ICON",
        "value": "payment"
      },
      "title": "Payment",
      "subtitle": "Visa ending in •••• 4242",
      "action": {
        "type": "NAVIGATE",
        "target": "payment_sheet"
      },
      "metadata": {
        "method": "Card",
        "fee": "0.00"
      }
    },
    {
      "id": "step-review",
      "ordinal": 3,
      "state": "TODO",
      "indicator": {
        "type": "ICON",
        "value": "receipt"
      },
      "title": "Review",
      "subtitle": "Total: $144.99",
      "action": {
        "type": "NAVIGATE",
        "target": "order_confirmation"
      },
      "metadata": {
        "total": "144.99"
      }
    }
  ]
}
""".trimIndent()

/**
 * Realistic order tracking flow:
 * Placed -> Processing -> Out for Delivery -> Delivered with status badges and timestamps.
 */
val TrackingFlowJson: String = """
{
  "schemaVersion": "1.0",
  "flowId": "tracking-order-78932",
  "title": "Live Order Tracking #KS-78932",
  "stateModel": "SERVER_AUTHORITATIVE",
  "orientation": "VERTICAL",
  "currentStepId": "step-out-for-delivery",
  "currentStepProgress": 0.0,
  "stateVersion": 2,
  "style": {
    "itemPaddingDp": 10,
    "showCheckMarkOnDone": true,
    "ignoreCurrentState": false,
    "stepStyle": {
      "todo":    { "colorHex": "#475569", "sizeDp": 32, "shape": "CIRCLE" },
      "current": { "colorHex": "#06B6D4", "sizeDp": 36, "shape": "CIRCLE", "borderWidthDp": 3, "borderColorHex": "#0891B2" },
      "done":    { "colorHex": "#10B981", "sizeDp": 32, "shape": "CIRCLE" },
      "error":   { "colorHex": "#EF4444", "sizeDp": 36, "shape": "CIRCLE", "borderWidthDp": 2, "borderColorHex": "#B91C1C" },
      "locked":  { "colorHex": "#64748B", "sizeDp": 32, "shape": "CIRCLE" }
    },
    "lineStyle": {
      "todo":    { "lineColorHex": "#334155", "progressColorHex": "#334155", "thicknessDp": 3, "lengthDp": 36, "lineType": "SOLID", "progressType": "SOLID" },
      "current": { "lineColorHex": "#334155", "progressColorHex": "#06B6D4", "thicknessDp": 3, "lengthDp": 36, "lineType": "SOLID", "progressType": "SOLID" },
      "done":    { "lineColorHex": "#10B981", "progressColorHex": "#10B981", "thicknessDp": 3, "lengthDp": 36, "lineType": "SOLID", "progressType": "SOLID" }
    }
  },
  "steps": [
    {
      "id": "step-placed",
      "ordinal": 0,
      "state": "DONE",
      "indicator": {
        "type": "ICON",
        "value": "check"
      },
      "title": "Order Placed",
      "subtitle": "Confirmed & payment processed successfully",
      "leadingText": "10:24 AM",
      "metadata": {
        "statusBadge": "CONFIRMED",
        "badgeColor": "#10B981"
      }
    },
    {
      "id": "step-processing",
      "ordinal": 1,
      "state": "DONE",
      "indicator": {
        "type": "ICON",
        "value": "inventory_2"
      },
      "title": "Processing & Packed",
      "subtitle": "Prepared at Regional Logistics Hub North",
      "leadingText": "11:45 AM",
      "metadata": {
        "statusBadge": "PACKED",
        "badgeColor": "#10B981"
      }
    },
    {
      "id": "step-out-for-delivery",
      "ordinal": 2,
      "state": "CURRENT",
      "indicator": {
        "type": "ICON",
        "value": "local_shipping"
      },
      "title": "Out for Delivery",
      "subtitle": "Courier Marcus on delivery route • ETA 25 mins",
      "leadingText": "01:15 PM",
      "metadata": {
        "statusBadge": "IN_TRANSIT",
        "badgeColor": "#06B6D4"
      }
    },
    {
      "id": "step-delivered",
      "ordinal": 3,
      "state": "LOCKED",
      "indicator": {
        "type": "ICON",
        "value": "home"
      },
      "title": "Delivered",
      "subtitle": "Pending front porch drop-off & digital signature",
      "leadingText": "ETA 02:00 PM",
      "metadata": {
        "statusBadge": "PENDING",
        "badgeColor": "#64748B"
      }
    }
  ]
}
""".trimIndent()

/**
 * Initial flow for dynamic live mutations:
 * Tokyo Hub -> Air Dispatch -> Destination Delivery.
 */
val DynamicMutationInitialJson: String = """
{
  "schemaVersion": "1.0",
  "flowId": "dynamic-flow-901",
  "title": "Cross-Border Fulfillment",
  "stateModel": "CLIENT_OPTIMISTIC",
  "orientation": "VERTICAL",
  "currentStepId": "step-air-dispatch",
  "currentStepProgress": 0.0,
  "stateVersion": 1,
  "style": {
    "itemPaddingDp": 10,
    "showCheckMarkOnDone": true,
    "ignoreCurrentState": false,
    "stepStyle": {
      "todo":    { "colorHex": "#475569", "sizeDp": 32, "shape": "CIRCLE" },
      "current": { "colorHex": "#8B5CF6", "sizeDp": 36, "shape": "CIRCLE", "borderWidthDp": 2, "borderColorHex": "#7C3AED" },
      "done":    { "colorHex": "#10B981", "sizeDp": 32, "shape": "CIRCLE" },
      "error":   { "colorHex": "#EF4444", "sizeDp": 36, "shape": "CIRCLE", "borderWidthDp": 2, "borderColorHex": "#B91C1C" },
      "locked":  { "colorHex": "#64748B", "sizeDp": 32, "shape": "CIRCLE" }
    },
    "lineStyle": {
      "todo":    { "lineColorHex": "#334155", "progressColorHex": "#334155", "thicknessDp": 3, "lengthDp": 36, "lineType": "SOLID", "progressType": "SOLID" },
      "current": { "lineColorHex": "#334155", "progressColorHex": "#8B5CF6", "thicknessDp": 3, "lengthDp": 36, "lineType": "SOLID", "progressType": "SOLID" },
      "done":    { "lineColorHex": "#10B981", "progressColorHex": "#10B981", "thicknessDp": 3, "lengthDp": 36, "lineType": "SOLID", "progressType": "SOLID" }
    }
  },
  "steps": [
    {
      "id": "step-origin",
      "ordinal": 0,
      "state": "DONE",
      "indicator": {
        "type": "ICON",
        "value": "shopping_cart"
      },
      "title": "Origin Warehouse",
      "subtitle": "Order verified at Tokyo International Terminal",
      "leadingText": "08:30 AM"
    },
    {
      "id": "step-air-dispatch",
      "ordinal": 1,
      "state": "CURRENT",
      "indicator": {
        "type": "ICON",
        "value": "local_shipping"
      },
      "title": "Air Transit Dispatch",
      "subtitle": "Loaded onto Flight #NH-108 Haneda -> SFO",
      "leadingText": "11:00 AM"
    },
    {
      "id": "step-destination",
      "ordinal": 2,
      "state": "TODO",
      "indicator": {
        "type": "ICON",
        "value": "home"
      },
      "title": "Final Destination Delivery",
      "subtitle": "Doorstep delivery via local carrier partner",
      "leadingText": "Pending"
    }
  ]
}
""".trimIndent()

// =========================================================================================
// 2. Standard Icon Resolver
// =========================================================================================

val StandardSduiIconResolver: SduiIconResolver = SduiIconResolver { iconName ->
    when (iconName.lowercase()) {
        "shopping_cart", "cart" -> Icons.Default.ShoppingCart
        "local_shipping", "shipping", "truck" -> Icons.Default.LocalShipping
        "payment", "credit_card", "card" -> Icons.Default.Payment
        "check", "check_circle", "done" -> Icons.Default.Check
        "inventory_2", "inventory", "box", "package" -> Icons.Default.Inventory2
        "home", "house", "destination" -> Icons.Default.Home
        "policy", "security", "customs" -> Icons.Default.Policy
        "receipt", "receipt_long", "review" -> Icons.Default.Receipt
        "error", "warning", "alert" -> Icons.Default.Error
        else -> null
    }
}

// =========================================================================================
// 3. Interactive Sample Screen
// =========================================================================================

enum class SduiTab(val label: String) {
    CHECKOUT("E-Commerce Checkout"),
    ORDER_TRACKING("Live Order Tracking"),
    DYNAMIC_MUTATIONS("Dynamic Mutations")
}

@OptIn(ExperimentalKotStep::class)
@Composable
fun KotStepSduiSampleScreen(
    modifier: Modifier = Modifier
) {
    var selectedTab by rememberSaveable { mutableStateOf(SduiTab.CHECKOUT) }
    val context = LocalContext.current

    Surface(
        modifier = modifier.fillMaxSize(),
        color = Color(0xFF0F172A) // Slate-900 dark theme
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Screen Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "KotStep SDUI Showcase",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                    Text(
                        text = "Server-Driven Stepper UI with Live JSON Mutations",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color(0xFF94A3B8)
                        )
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF1E293B))
                        .border(1.dp, Color(0xFF334155), RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "SDUI v1.0",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = Color(0xFF38BDF8),
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Navigation Tabs
            ScrollableTabRow(
                selectedTabIndex = selectedTab.ordinal,
                containerColor = Color(0xFF1E293B),
                contentColor = Color(0xFF38BDF8),
                edgePadding = 0.dp,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTab.ordinal]),
                        color = Color(0xFF38BDF8)
                    )
                },
                divider = {}
            ) {
                SduiTab.entries.forEach { tab ->
                    Tab(
                        selected = selectedTab == tab,
                        onClick = { selectedTab = tab },
                        text = {
                            Text(
                                text = tab.label,
                                fontSize = 13.sp,
                                fontWeight = if (selectedTab == tab) FontWeight.Bold else FontWeight.Medium,
                                color = if (selectedTab == tab) Color(0xFF38BDF8) else Color(0xFF94A3B8)
                            )
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Tab Content
            when (selectedTab) {
                SduiTab.CHECKOUT -> CheckoutSampleTab(
                    onActionDispatched = { action ->
                        Toast.makeText(context, "Action: ${action.type} -> ${action.target}", Toast.LENGTH_SHORT).show()
                    }
                )
                SduiTab.ORDER_TRACKING -> OrderTrackingSampleTab(
                    onActionDispatched = { action ->
                        Toast.makeText(context, "Tracking: ${action.target}", Toast.LENGTH_SHORT).show()
                    }
                )
                SduiTab.DYNAMIC_MUTATIONS -> DynamicMutationsSampleTab()
            }
        }
    }
}

// =========================================================================================
// Tab 1: E-Commerce Checkout
// =========================================================================================

@OptIn(ExperimentalKotStep::class)
@Composable
private fun CheckoutSampleTab(
    onActionDispatched: (SduiAction) -> Unit
) {
    val initialFlow = remember { SduiParser.parseFlow(CheckoutFlowJson) }
    val manager = remember { SduiStateManager(initialFlow) }
    val flow by manager.flow.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Stepper Card
        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.elevatedCardColors(containerColor = Color(0xFF1E293B)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = flow.title ?: "Checkout Flow",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                    Text(
                        text = "Step ${(manager.computeCurrentStepFloat().toInt() + 1)} of ${flow.steps.size}",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = Color(0xFF10B981),
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // KotStep SDUI Horizontal Stepper
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(vertical = 8.dp)
                ) {
                    KotStepSdui(
                        manager = manager,
                        iconResolver = StandardSduiIconResolver,
                        onStepClick = { stepId ->
                            manager.optimisticAdvance(stepId)
                            Toast.makeText(context, "Navigating to: $stepId", Toast.LENGTH_SHORT).show()
                        },
                        onAction = onActionDispatched
                    )
                }
            }
        }

        // Active Step Details Card
        val activeStep = flow.steps.find { it.id == flow.currentStepId } ?: flow.steps.firstOrNull()
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Active Step: ${activeStep?.title.orEmpty()}",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF38BDF8)
                    )
                )

                Text(
                    text = activeStep?.subtitle.orEmpty(),
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xFFCBD5E1))
                )

                HorizontalDivider(color = Color(0xFF334155))

                // Mock Checkout Summary
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Subtotal (3 Items):", color = Color(0xFF94A3B8))
                    Text(text = "$129.99", color = Color.White, fontWeight = FontWeight.Medium)
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Express Shipping:", color = Color(0xFF94A3B8))
                    Text(text = "$15.00", color = Color.White, fontWeight = FontWeight.Medium)
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Order Total:", fontWeight = FontWeight.Bold, color = Color.White)
                    Text(
                        text = "$144.99",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF10B981),
                        fontSize = 18.sp
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Navigation Controls
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    val currentIndex = flow.steps.indexOfFirst { it.id == flow.currentStepId }.let {
                        if (it == -1) flow.steps.indexOfFirst { s -> s.state == SduiStepState.CURRENT }.coerceAtLeast(0) else it
                    }

                    OutlinedButton(
                        onClick = {
                            if (currentIndex > 0) {
                                val prevStep = flow.steps[currentIndex - 1]
                                manager.optimisticAdvance(prevStep.id)
                            }
                        },
                        enabled = currentIndex > 0
                    ) {
                        Text("Previous")
                    }

                    Button(
                        onClick = {
                            if (currentIndex < flow.steps.size - 1) {
                                val nextStep = flow.steps[currentIndex + 1]
                                manager.optimisticAdvance(nextStep.id)
                            } else {
                                Toast.makeText(context, "Order Placed Successfully! 🎉", Toast.LENGTH_LONG).show()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981))
                    ) {
                        Text(if (currentIndex == flow.steps.size - 1) "Confirm & Pay" else "Next Step")
                    }
                }
            }
        }
    }
}

// =========================================================================================
// Tab 2: Live Order Tracking
// =========================================================================================

@OptIn(ExperimentalKotStep::class)
@Composable
private fun OrderTrackingSampleTab(
    onActionDispatched: (SduiAction) -> Unit
) {
    val initialFlow = remember { SduiParser.parseFlow(TrackingFlowJson) }
    val manager = remember { SduiStateManager(initialFlow) }
    val flow by manager.flow.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Delivery Status Header Card
        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.elevatedCardColors(containerColor = Color(0xFF1E293B)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Package In Transit",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF06B6D4)
                        )
                    )

                    // Live Status Badge
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF0E7490))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "LIVE GPS",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }

                Text(
                    text = "Estimated Delivery: Today by 02:00 PM",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                )

                Text(
                    text = "Carrier: KotStep Logistics • Tracking: #KS-78932",
                    style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF94A3B8))
                )
            }
        }

        // Stepper Card with Vertical KotStep SDUI
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    text = "Tracking Timeline",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFE2E8F0)
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                KotStepSdui(
                    manager = manager,
                    iconResolver = StandardSduiIconResolver,
                    onStepClick = { stepId ->
                        Toast.makeText(context, "Event details: $stepId", Toast.LENGTH_SHORT).show()
                    },
                    onAction = onActionDispatched
                )
            }
        }

        // Tracking Actions
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                modifier = Modifier.weight(1f),
                onClick = {
                    manager.applyServerFlow(SduiParser.parseFlow(TrackingFlowJson))
                    Toast.makeText(context, "Tracking refreshed from backend", Toast.LENGTH_SHORT).show()
                }
            ) {
                Text("Refresh")
            }

            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    val currentId = flow.currentStepId
                    val currentIndex = flow.steps.indexOfFirst { it.id == currentId }
                    if (currentIndex < flow.steps.size - 1) {
                        val nextStep = flow.steps[currentIndex + 1]
                        manager.optimisticAdvance(nextStep.id)
                        Toast.makeText(context, "Advanced to: ${nextStep.title}", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Package already marked delivered!", Toast.LENGTH_SHORT).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF06B6D4))
            ) {
                Text("Advance Event")
            }
        }
    }
}

// =========================================================================================
// Tab 3: Dynamic Mutations
// =========================================================================================

@OptIn(ExperimentalKotStep::class)
@Composable
private fun DynamicMutationsSampleTab() {
    val initialFlow = remember { SduiParser.parseFlow(DynamicMutationInitialJson) }
    val manager = remember { SduiStateManager(initialFlow) }
    val flow by manager.flow.collectAsState()
    val context = LocalContext.current

    var latestLog by remember { mutableStateOf("Initial flow loaded (v1)") }
    var showRawJson by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .animateContentSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Live Stepper Viewport
        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.elevatedCardColors(containerColor = Color(0xFF1E293B)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = flow.title ?: "Dynamic Flow",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )
                        Text(
                            text = "State Version: v${flow.stateVersion} • Total Steps: ${flow.steps.size}",
                            style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFF8B5CF6))
                        )
                    }

                    // Version Tag
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFF6D28D9))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "v${flow.stateVersion}",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                KotStepSdui(
                    manager = manager,
                    iconResolver = StandardSduiIconResolver,
                    onStepClick = { stepId ->
                        manager.optimisticAdvance(stepId)
                        latestLog = "Optimistic advance to '$stepId'"
                    }
                )
            }
        }

        // Live Log Banner
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF10B981))
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = latestLog,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontFamily = FontFamily.Monospace,
                        color = Color(0xFFE2E8F0),
                        fontSize = 11.sp
                    ),
                    maxLines = 2
                )
            }
        }

        // Control Panel: Mutation Actions
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Live Server Mutations",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFF1F5F9)
                    )
                )

                // Row 1: Inject Customs Step & Advance Step
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Button 1: Inject Customs Step
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            val currentFlow = manager.flow.value
                            if (currentFlow.steps.any { it.id == "step-customs" }) {
                                latestLog = "Customs step already injected!"
                                Toast.makeText(context, "Already present!", Toast.LENGTH_SHORT).show()
                            } else {
                                val nextVersion = currentFlow.stateVersion + 1
                                val insertMutation = SduiMutation.InsertStep(
                                    afterStepId = "step-air-dispatch",
                                    step = SduiStep(
                                        id = "step-customs",
                                        ordinal = 2,
                                        state = SduiStepState.CURRENT,
                                        indicator = SduiIndicator(
                                            type = SduiIndicatorType.ICON,
                                            value = "policy"
                                        ),
                                        title = "Customs Declaration & Clearance",
                                        subtitle = "Border control regulatory inspection",
                                        leadingText = "02:30 PM"
                                    )
                                )
                                val updatePrevious = SduiMutation.UpdateStep(
                                    stepId = "step-air-dispatch",
                                    patch = SduiStepPatch(state = SduiStepState.DONE)
                                )
                                val result = manager.applyMutations(
                                    mutations = listOf(updatePrevious, insertMutation),
                                    newVersion = nextVersion
                                )
                                latestLog = "Mutation: Injected 'Customs Declaration' (result: $result, v$nextVersion)"
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B5CF6))
                    ) {
                        Text("Inject Customs Step", fontSize = 12.sp)
                    }

                    // Button 2: Advance Step
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            val currentFlow = manager.flow.value
                            val currentIndex = currentFlow.steps.indexOfFirst { it.id == currentFlow.currentStepId }
                            if (currentIndex != -1 && currentIndex < currentFlow.steps.size - 1) {
                                val nextStep = currentFlow.steps[currentIndex + 1]
                                manager.optimisticAdvance(nextStep.id)
                                latestLog = "Optimistic Advance: Moved to '${nextStep.title}'"
                            } else {
                                latestLog = "Flow already at final step"
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3B82F6))
                    ) {
                        Text("Advance Step", fontSize = 12.sp)
                    }
                }

                // Row 2: Trigger Server Error & Simulate Push Update
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Button 3: Trigger Server Error
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            val currentFlow = manager.flow.value
                            val targetId = currentFlow.currentStepId.ifBlank {
                                currentFlow.steps.firstOrNull()?.id ?: "step-air-dispatch"
                            }
                            val nextVersion = currentFlow.stateVersion + 1
                            val errorMutation = SduiMutation.UpdateStep(
                                stepId = targetId,
                                patch = SduiStepPatch(
                                    state = SduiStepState.ERROR,
                                    errorMessage = "Air Transit Hold: Flight delayed due to weather (Code #429)"
                                )
                            )
                            val result = manager.applyMutations(listOf(errorMutation), nextVersion)
                            latestLog = "Triggered ERROR on '$targetId' (result: $result, v$nextVersion)"
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF4444))
                    ) {
                        Text("Trigger Server Error", fontSize = 12.sp)
                    }

                    // Button 4: Simulate Push Update
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            val currentFlow = manager.flow.value
                            val nextVersion = currentFlow.stateVersion + 1
                            val targetId = currentFlow.currentStepId
                            val updatedSteps = currentFlow.steps.map { step ->
                                if (step.id == targetId) {
                                    step.copy(
                                        state = SduiStepState.CURRENT,
                                        errorMessage = null,
                                        subtitle = "Push Update: Clearance granted • Flight departed Haneda"
                                    )
                                } else step
                            }
                            val result = manager.applyServerFlow(
                                currentFlow.copy(
                                    stateVersion = nextVersion,
                                    steps = updatedSteps
                                )
                            )
                            latestLog = "Push Applied: Flight departed Haneda (result: $result, v$nextVersion)"
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981))
                    ) {
                        Text("Simulate Push Update", fontSize = 12.sp)
                    }
                }

                // Row 3: Rollback & Reset Flow
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            manager.rollback()
                            latestLog = "Rolled back to last confirmed server flow"
                        }
                    ) {
                        Text("Rollback", fontSize = 12.sp)
                    }

                    OutlinedButton(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            manager.applyServerFlow(SduiParser.parseFlow(DynamicMutationInitialJson))
                            latestLog = "Flow reset to initial state (v1)"
                        }
                    ) {
                        Text("Reset Flow", fontSize = 12.sp)
                    }
                }

                // Raw JSON Inspector Toggle
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    FilterChip(
                        selected = showRawJson,
                        onClick = { showRawJson = !showRawJson },
                        label = {
                            Text(if (showRawJson) "Hide Raw JSON" else "View Flow JSON", fontSize = 11.sp)
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0xFF38BDF8),
                            selectedLabelColor = Color.Black
                        )
                    )
                }

                AnimatedVisibility(visible = showRawJson) {
                    val encodedJson = remember(flow) {
                        try {
                            SduiParser.encodeFlow(flow)
                        } catch (e: Exception) {
                            "Error encoding JSON: ${e.message}"
                        }
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF020617)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = encodedJson,
                            modifier = Modifier.padding(12.dp),
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontFamily = FontFamily.Monospace,
                                fontSize = 10.sp,
                                color = Color(0xFF38BDF8)
                            )
                        )
                    }
                }
            }
        }
    }
}
