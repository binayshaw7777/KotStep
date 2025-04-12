package com.binayshaw7777.kotstep.v2.samples

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import com.binayshaw7777.kotstep.R
import com.binayshaw7777.kotstep.v2.component.KotStep

/**
 * Demonstrates how to add a step with a title.
 */
@Composable
fun StepWithTitle() {
    KotStep(currentStep = { 1f }) {
        step(title = "1")
    }
}

///**
// * Demonstrates how to add a step with a painter icon.
// */
//@Composable
//fun StepWithPainterIcon() {
//    val painterIcon = painterResource(id = R.drawable.kotlin)
//    KotStep(currentStep = { 1f }) {
//        step(icon = painterIcon)
//    }
//}

/**
 * Demonstrates how to add a step with an image vector icon.
 */
@Composable
fun StepWithImageVectorIcon() {
    val imageVector = ImageVector.vectorResource(R.drawable.kotlin)
    KotStep(currentStep = { 1f }) {
        step(icon = imageVector)
    }
}

/**
 * Demonstrates how to add a step with custom composable content.
 */
@Composable
fun StepWithCustomContent() {
    KotStep(currentStep = { 1f }) {
        step(content = { Text("Hi") })
    }
}