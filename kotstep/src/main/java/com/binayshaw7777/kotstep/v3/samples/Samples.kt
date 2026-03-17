package com.binayshaw7777.kotstep.v3.samples

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
import com.binayshaw7777.kotstep.v3.KotStep
import com.binayshaw7777.kotstep.v3.model.style.KotStepStyle
import com.binayshaw7777.kotstep.v3.util.ExperimentalKotStep
import com.binayshaw7777.kotstep.v3.util.Util.getKotStepStyle
import com.binayshaw7777.kotstep.v3.util.Util.onClick

@OptIn(ExperimentalKotStep::class)
@Composable
internal fun StepWithTitle(modifier: Modifier = Modifier) {
    KotStep(currentStep = { 1f }) {
        step(title = "1")
    }
}

@OptIn(ExperimentalKotStep::class)
@Composable
internal fun StepWithImageVectorIcon(modifier: Modifier = Modifier) {
    val imageVector = ImageVector.vectorResource(R.drawable.kotlin)
    KotStep(currentStep = { 1f }) {
        step(icon = imageVector)
    }
}

@OptIn(ExperimentalKotStep::class)
@Composable
internal fun StepWithCustomContent(modifier: Modifier = Modifier) {
    KotStep(currentStep = { 1f }) {
        step(content = { Text("Hi") })
    }
}

@OptIn(ExperimentalKotStep::class)
@Composable
fun KotStepVerticalExample(
    modifier: Modifier = Modifier,
    currentStep: () -> Float,
    stepStyle: KotStepStyle = getKotStepStyle(),
    isCollapsible: Boolean = false
) {
    val context = LocalContext.current
    var showMoreItem by remember { mutableStateOf(false) }

    KotStep(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        currentStep = { currentStep() },
        style = stepStyle
    ) {
        step(
            title = "1",
            leadingLabel = {
                Icon(Icons.Default.Search, contentDescription = null)
            },
            onClick = {
                Toast.makeText(context, "Hi there", Toast.LENGTH_SHORT).show()
            },
            isCollapsible = isCollapsible
        )
        step(
            icon = Icons.Default.Star,
            isCollapsible = isCollapsible
        )
        step(
            content = {
                Image(
                    painter = painterResource(R.drawable.kotlin),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
            },
            isCollapsible = isCollapsible
        )
        step(
            title = "3",
            trailingLabel = {
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
            },
            onClick = {
                showMoreItem = showMoreItem.not()
            },
            isCollapsible = isCollapsible
        )
        step(
            title = "4",
            isCollapsible = isCollapsible
        )
        step(
            isCollapsible = isCollapsible
        )
    }
}

@OptIn(ExperimentalKotStep::class)
@Composable
fun KotStepHorizontalExample(
    modifier: Modifier = Modifier,
    currentStep: () -> Float,
    stepStyle: KotStepStyle = getKotStepStyle(),
    isCollapsible: Boolean = false,
) {
    val context = LocalContext.current
    var showMoreItem by remember { mutableStateOf(false) }

    KotStep(
        modifier = Modifier.horizontalScroll(rememberScrollState()),
        currentStep = { currentStep() },
        style = stepStyle
    ) {
        step(
            title = "1",
            leadingLabel = {
                Text("Start")
            },
            onClick = {
                Toast.makeText(context, "Hi there", Toast.LENGTH_SHORT).show()
            },
            isCollapsible = isCollapsible
        )
        step(
            icon = Icons.Default.Star,
            isCollapsible = isCollapsible
        )
        step(
            content = {
                Image(
                    painter = painterResource(R.drawable.kotlin),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
            },
            isCollapsible = isCollapsible
        )
        step(
            title = "3",
            trailingLabel = {
                Row(
                    Modifier
                        .background(Color.Gray.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
                        .onClick { showMoreItem = showMoreItem.not() }
                ) {
                    Text("Hello World This is a longer text you see...")
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
            },
            onClick = {
                showMoreItem = showMoreItem.not()
            },
            isCollapsible = isCollapsible
        )
        step(
            title = "4",
            isCollapsible = isCollapsible
        )
        step(
            isCollapsible = isCollapsible
        )
    }
}
