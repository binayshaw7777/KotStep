package com.binayshaw7777.kotstep.ui.theme.presentation.v2

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.binayshaw7777.kotstep.utils.Utils.getKotStepStyle
import com.binayshaw7777.kotstep.v2.model.step.StepLayoutStyle
import com.binayshaw7777.kotstep.v2.samples.KotStepHorizontalExample
import com.binayshaw7777.kotstep.v2.samples.KotStepVerticalExample
import com.binayshaw7777.kotstep.v2.util.ExperimentalKotStep

@OptIn(ExperimentalKotStep::class)
@Preview(showBackground = true, backgroundColor = 0xFF1C2526)
@Composable
fun KotStepPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        val stepStyle = getKotStepStyle()
        var currentStep by remember { mutableFloatStateOf(-1f) }

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

        KotStepHorizontalExample(currentStep = { currentStep }, stepStyle = stepStyle.copy(stepLayoutStyle = StepLayoutStyle.Horizontal))

        KotStepVerticalExample(currentStep = { currentStep }, stepStyle = stepStyle)
    }
}

@Composable
private fun Counter(
    modifier: Modifier = Modifier,
    currentStep: () -> Float,
    totalSteps: Int,
    onChange: (Float) -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .then(modifier), horizontalArrangement = Arrangement.SpaceAround
    ) {

        AnimatedVisibility(
            visible = currentStep() >= -0.75f,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Button(
                onClick = {
                    if (currentStep() == 0f) {
                        onChange(-1f)
                    } else {
                        onChange(currentStep() - 0.25f)
                    }
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