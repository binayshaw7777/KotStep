package com.binayshaw7777.kotstep.demo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class DemoScreen(val title: String) {
    GROWW("Groww App"),
    PLAYGROUND("Playground"),
    COLLAPSIBLE("Collapsible")
}

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun DemoApp() {
    var selectedScreenIndex by remember { mutableIntStateOf(0) }
    val screens = DemoScreen.entries

    MaterialTheme(
        colorScheme = darkColorScheme(
            background = Color(0xFF111827),
            surface = Color(0xFF1F2937),
            primary = Color(0xFF10B981)
        )
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                Column(modifier = Modifier.fillMaxWidth().background(Color(0xFF111827))) {
                    Box(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp)) {
                        Text(
                            text = "KotStep Multiplatform Demo",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    PrimaryTabRow(
                        selectedTabIndex = selectedScreenIndex,
                        containerColor = Color(0xFF1F2937),
                        contentColor = Color(0xFF10B981)
                    ) {
                        screens.forEachIndexed { index, screen ->
                            Tab(
                                selected = selectedScreenIndex == index,
                                onClick = { selectedScreenIndex = index },
                                text = {
                                    Text(
                                        text = screen.title,
                                        fontSize = 13.sp,
                                        fontWeight = if (selectedScreenIndex == index) FontWeight.Bold else FontWeight.Normal,
                                        color = if (selectedScreenIndex == index) Color(0xFF10B981) else Color(0xFF9CA3AF)
                                    )
                                }
                            )
                        }
                    }
                }
            },
            containerColor = Color(0xFF111827)
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                when (screens[selectedScreenIndex]) {
                    DemoScreen.GROWW -> GrowwDemo()
                    DemoScreen.PLAYGROUND -> PlaygroundDemo()
                    DemoScreen.COLLAPSIBLE -> CollapsibleDemo()
                }
            }
        }
    }
}
