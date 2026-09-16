package com.binayshaw7777.kotstep.web

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import com.binayshaw7777.kotstep.demo.DemoApp

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    CanvasBasedWindow("KotStep Demo") {
        DemoApp()
    }
}
