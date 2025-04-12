package com.binayshaw7777.kotstep.v2.component

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
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

        KotStep(
            currentStep = { currentStep }
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
            step(title = "3")
            step(title = "4")
            step(title = "5")
        }
    }
}