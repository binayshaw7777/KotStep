package com.binayshaw7777.kotstep.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun KotSlider(
    modifier: Modifier = Modifier,
    sliderHeader: String,
    currentValue: Int,
    range: ClosedFloatingPointRange<Float> = 1f..10f,
    steps: Int = 10,
    onValueChange: (Int) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(20.dp)
    ) {
        Text(
            text = "$sliderHeader: $currentValue",
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Slider(
            value = currentValue.toFloat(),
            onValueChange = { newValue ->
                 onValueChange(newValue.toInt())
            },
            valueRange = 1f..10f, // Set the range of Total Steps
            steps = 10, // Divide the range into 10 steps
            modifier = Modifier.fillMaxWidth().then(modifier)
        )
    }
}