package com.binayshaw7777.kotstep.v2.samples

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.binayshaw7777.kotstep.R
import com.binayshaw7777.kotstep.v2.KotStep
import com.binayshaw7777.kotstep.v2.model.style.KotStepStyle
import com.binayshaw7777.kotstep.v2.util.Util.getKotStepStyle
import com.binayshaw7777.kotstep.v2.util.Util.onClick

/**
 * Demonstrates how to add a step with a title.
 */
@Composable
internal fun StepWithTitle(modifier: Modifier = Modifier) {
    KotStep(currentStep = { 1f }) {
        step(title = "1")
    }
}

/**
 * Demonstrates how to add a step with an image vector icon.
 */
@Composable
internal fun StepWithImageVectorIcon(modifier: Modifier = Modifier) {
    val imageVector = ImageVector.vectorResource(R.drawable.kotlin)
    KotStep(currentStep = { 1f }) {
        step(icon = imageVector)
    }
}

/**
 * Demonstrates how to add a step with custom composable content.
 */
@Composable
internal fun StepWithCustomContent(modifier: Modifier = Modifier) {
    KotStep(currentStep = { 1f }) {
        step(content = { Text("Hi") })
    }
}

@Composable
fun KotStepVerticalExample(
    modifier: Modifier = Modifier,
    currentStep: () -> Float,
    stepStyle: KotStepStyle = getKotStepStyle(),
) {
    val context = LocalContext.current
    var showMoreItem by remember { mutableStateOf(false) } // Just for example to show that steps can adapt the size of your passed composable labels

    KotStep(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        currentStep = { currentStep() }, // Provide the current step as a { Float }
        style = stepStyle // KotStepStyle
    ) {
        step(
            title = "1",
            onClick = {
                Toast.makeText(context, "Hi there", Toast.LENGTH_SHORT).show()
            }
        )
        step(icon = Icons.Default.Star)
        step(content = {
            Image(
                painter = painterResource(R.drawable.kotlin),
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
        })
        step(title = "3", label = {
            Card(Modifier.onClick { showMoreItem = showMoreItem.not() }) {
                Text("Hello World")
                Text("Hello World")
                Text("Hello World")
                Text("Hello World")
                Text("Hello World")
                Text("Hello World")
                Text("Hello World")
                Text("Hello World")
                Text("Hello World")
                Text("Hello World")

                AnimatedVisibility(showMoreItem) {
                    Column {
                        Text("Hello World")
                        Text("Hello World")
                        Text("Hello World")
                        Text("Hello World")
                        Text("Hello World")
                    }
                }
            }
        }, onClick = {
            showMoreItem = showMoreItem.not()
        })
        step(title = "4")
        step()
    }
}

@Composable
fun KotStepHorizontalExample(
    modifier: Modifier = Modifier,
    currentStep: () -> Float,
    stepStyle: KotStepStyle = getKotStepStyle(),
) {
    val context = LocalContext.current
    var showMoreItem by remember { mutableStateOf(false) } // Just for example to show that steps can adapt the size of your passed composable labels

    KotStep(
        modifier = Modifier.horizontalScroll(rememberScrollState()),
        currentStep = { currentStep() }, // Provide the current step as a { Float }
        style = stepStyle // KotStepStyle
    ) {
        step(
            title = "1",
            onClick = {
                Toast.makeText(context, "Hi there", Toast.LENGTH_SHORT).show()
            }
        )
        step(icon = Icons.Default.Star)
        step(content = {
            Image(
                painter = painterResource(R.drawable.kotlin),
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
        })
        step(title = "3", label = {
            Row(
                Modifier
                    .background(Color.Gray.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
                    .onClick { showMoreItem = showMoreItem.not() }
            ) {
                Text("Hello World This is not a joke")
                AnimatedVisibility(showMoreItem) {
                    Row {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp)
                        )
                    }
                }
            }
        }, onClick = {
            showMoreItem = showMoreItem.not()
        })
        step(title = "4")
        step()
    }
}