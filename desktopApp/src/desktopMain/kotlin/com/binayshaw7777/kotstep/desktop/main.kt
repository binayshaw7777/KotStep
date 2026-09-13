package com.binayshaw7777.kotstep.desktop

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.binayshaw7777.kotstep.demo.DemoApp

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "KotStep Demo") {
        DemoApp()
    }
}
