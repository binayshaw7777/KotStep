package com.binayshaw7777.kotstep.demo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

enum class DemoScreen(val title: String) {
    DOORDASH("DoorDash"),
    MEESHO("Meesho"),
    FLIPKART("Flipkart"),
    FLIPKART_RETURN("Flipkart Return"),
    LYFT("Lyft"),
    GROWW("Groww"),
    PLAYGROUND("Playground"),
    COLLAPSIBLE("Collapsible")
}

@Composable
fun DemoApp() {
    var currentScreen by remember { mutableStateOf<DemoScreen?>(null) }

    MaterialTheme(
        colorScheme = darkColorScheme(
            background = Color(0xFF111827),
            surface = Color(0xFF1F2937),
            primary = Color(0xFF10B981)
        )
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            when (val screen = currentScreen) {
                null -> {
                    HomeScreen(
                        onSelectScreen = { selected ->
                            currentScreen = selected
                        }
                    )
                }

                DemoScreen.DOORDASH -> DoorDashDemo(onBack = { currentScreen = null })
                DemoScreen.MEESHO -> MeeshoDemo(onBack = { currentScreen = null })
                DemoScreen.FLIPKART -> FlipkartDemo(onBack = { currentScreen = null })
                DemoScreen.FLIPKART_RETURN -> FlipkartReturnDemo(onBack = { currentScreen = null })
                DemoScreen.LYFT -> LyftDemo(onBack = { currentScreen = null })
                DemoScreen.GROWW -> GrowwDemo(onBack = { currentScreen = null })
                DemoScreen.PLAYGROUND -> PlaygroundDemo(onBack = { currentScreen = null })
                DemoScreen.COLLAPSIBLE -> CollapsibleDemo(onBack = { currentScreen = null })
            }
        }
    }
}
