package com.binayshaw7777.kotstep.demo

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class DemoItem(
    val screen: DemoScreen,
    val title: String,
    val category: String,
    val description: String,
    val tag: String,
    val accentColor: Color,
    val icon: ImageVector
)

val demoItems = listOf(
    DemoItem(
        screen = DemoScreen.DOORDASH,
        title = "DoorDash",
        category = "Food Delivery Tracker",
        description = "Horizontal stepper with custom SF map route, Dasher car marker, storefront pickup, and tip controls.",
        tag = "Horizontal • 4 Steps",
        accentColor = Color(0xFFFF3008),
        icon = Icons.Default.Home
    ),
    DemoItem(
        screen = DemoScreen.MEESHO,
        title = "Meesho",
        category = "E-Commerce Order Tracking",
        description = "Vertical timeline with signature green dots, active halo indicator on Shipped, and milestone history.",
        tag = "Vertical • 3 Steps",
        accentColor = Color(0xFF009668),
        icon = Icons.Default.ShoppingCart
    ),
    DemoItem(
        screen = DemoScreen.FLIPKART,
        title = "Flipkart",
        category = "Order Details & Transit",
        description = "Vertical stepper with green checkmarks on confirmed/shipped steps, EKART tracking, and address card.",
        tag = "Vertical • 4 Steps",
        accentColor = Color(0xFF2874F0),
        icon = Icons.Default.ShoppingCart
    ),
    DemoItem(
        screen = DemoScreen.FLIPKART_RETURN,
        title = "Flipkart Return",
        category = "Return & Refund Journey",
        description = "Horizontal journey (Requested → Approved → Pickup → Refund) with OTP badge & refund breakdown.",
        tag = "Horizontal • 4 Steps",
        accentColor = Color(0xFFFF9F00),
        icon = Icons.Default.Refresh
    ),
    DemoItem(
        screen = DemoScreen.LYFT,
        title = "Lyft",
        category = "Ride Route & Driver Earnings",
        description = "Dark theme vertical stepper with hollow purple & magenta rings, trip details, and earnings breakdown.",
        tag = "Vertical • 3 Steps",
        accentColor = Color(0xFFFF00BF),
        icon = Icons.Default.Home
    ),
    DemoItem(
        screen = DemoScreen.GROWW,
        title = "Groww",
        category = "Mutual Fund & SIP Status",
        description = "Dark vertical timeline tracking order approval, payment confirmation, and NAV units allocation.",
        tag = "Vertical • 3 Steps",
        accentColor = Color(0xFF00D09C),
        icon = Icons.Default.Star
    ),
    DemoItem(
        screen = DemoScreen.PLAYGROUND,
        title = "Playground",
        category = "Interactive Configurator",
        description = "Test all shapes (Circle, Rounded, Cut-Corner), line thickness, length, stroke caps, and colors live.",
        tag = "Interactive Builder",
        accentColor = Color(0xFF3B82F6),
        icon = Icons.Default.Settings
    ),
    DemoItem(
        screen = DemoScreen.COLLAPSIBLE,
        title = "Collapsible",
        category = "Expandable Step Flow",
        description = "Horizontal and vertical steppers with animated collapsible detail cards embedded within steps.",
        tag = "V3 Feature",
        accentColor = Color(0xFF8B5CF6),
        icon = Icons.Default.Check
    )
)

@Composable
fun HomeScreen(
    onSelectScreen: (DemoScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF111827))
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Hero Header Card
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color(0xFF1F2937),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF374151)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = Color(0xFF10B981)
                    ) {
                        Text(
                            text = "KotStep V3",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                    Text(
                        text = "Compose Multiplatform",
                        fontSize = 12.sp,
                        color = Color(0xFF9CA3AF)
                    )
                }

                Text(
                    text = "Sample Showcases",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Text(
                    text = "Explore real-world app steppers and interactive playgrounds powered by KotStep. Select any screen to inspect its UI and controls.",
                    fontSize = 13.sp,
                    color = Color(0xFF9CA3AF),
                    lineHeight = 18.sp
                )
            }
        }

        // Section Title: Real-World Apps
        Text(
            text = "Real-World Apps",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            letterSpacing = 0.5.sp
        )

        // Cards for Real-World Apps
        demoItems.take(6).forEach { item ->
            HomeScreenItemCard(item = item, onClick = { onSelectScreen(item.screen) })
        }

        // Section Title: Component Demos
        Text(
            text = "Component & Feature Demos",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            letterSpacing = 0.5.sp,
            modifier = Modifier.padding(top = 8.dp)
        )

        // Cards for Playground & Collapsible
        demoItems.drop(6).forEach { item ->
            HomeScreenItemCard(item = item, onClick = { onSelectScreen(item.screen) })
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun HomeScreenItemCard(
    item: DemoItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1F2937)),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF2E3B4E))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Colored icon container
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = item.accentColor.copy(alpha = 0.15f),
                border = androidx.compose.foundation.BorderStroke(1.dp, item.accentColor.copy(alpha = 0.3f)),
                modifier = Modifier.size(48.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = null,
                        tint = item.accentColor,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            // Text Info
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = item.title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = Color(0xFF374151)
                    ) {
                        Text(
                            text = item.tag,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFFE5E7EB),
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(3.dp))

                Text(
                    text = item.category,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = item.accentColor
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = item.description,
                    fontSize = 12.sp,
                    color = Color(0xFF9CA3AF),
                    lineHeight = 16.sp
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Arrow Right
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Navigate",
                tint = Color(0xFF6B7280),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
