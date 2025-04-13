package com.binayshaw7777.kotstep.v2.component

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.binayshaw7777.kotstep.R
import com.binayshaw7777.kotstep.v2.util.Util.getKotStepStyle

@Preview(showBackground = true, backgroundColor = 0xFF1C2526)
@Composable
fun KotStepPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        val stepStyle = getKotStepStyle()
        var currentStep by remember { mutableFloatStateOf(0f) }
        val context = LocalContext.current

        Counter(
            currentStep = { currentStep },
            totalSteps = 10,
            onChange = { newValue ->
                println("Current step before: $currentStep")
                currentStep = newValue
                println("Current step after: $currentStep")
            }
        )

        Spacer(Modifier.height(50.dp))

        KotStep(
            modifier = Modifier.verticalScroll(rememberScrollState()),
            currentStep = { currentStep },
            style = stepStyle
        ) {
            step(
                title = "1",
                onClick = {
                    Toast.makeText(context, "Hi there", Toast.LENGTH_SHORT).show()
                }
            )
            step(title = "2")
            step(icon = Icons.Default.Star)
            step(content = {
                Image(
                    painter = painterResource(R.drawable.kotlin),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
            })
            step(title = "3", label = {
                Card {
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
                    Text("Hello World")
                    Text("Hello World")
                    Text("Hello World")
                    Text("Hello World")
                    Text("Hello World")
                }
            })
            step(title = "4")
            step(title = "5")
            step(title = "6")
            step(title = "7")
            step()
        }
    }
}

@Composable
private fun Counter(
    modifier: Modifier = Modifier,
    currentStep: () -> Float,
    totalSteps: Int,
    onChange: (Float) -> Unit
) {
    Row(Modifier.fillMaxWidth().then(modifier), horizontalArrangement = Arrangement.SpaceAround) {

        AnimatedVisibility(
            visible = currentStep() >= -0.75f,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Button(
                onClick = {
                    onChange(currentStep() - 0.25f)
                }
            ) {
                Text(text = "Previous")
            }
        }

        Spacer(Modifier.weight(1f))

        AnimatedVisibility(
            visible = currentStep() < totalSteps.toFloat(),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Button(
                onClick = {
                    if (currentStep() == -1f) {
                        onChange(0f)
                    } else {
                        onChange(currentStep() + 0.25f)
                    }
                }
            ) {
                Text(
                    text =
                    if (currentStep() == -1f) "Start"
                    else if (currentStep() >= totalSteps) "Finish"
                    else "Next"
                )
            }
        }
    }
}