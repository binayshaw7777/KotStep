package com.binayshaw7777.kotstep.ui.vertical.step

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntSize
import com.binayshaw7777.kotstep.components.vertical.VerticalLabelStep
import com.binayshaw7777.kotstep.components.vertical.VerticalNumberedStep
import com.binayshaw7777.kotstep.model.StepState
import com.binayshaw7777.kotstep.model.StepStyle

@Composable
fun RenderVerticalLabel(
    modifier: Modifier,
    totalSteps: Int,
    currentStep: Int,
    labels: List<(@Composable () -> Unit)?>,
    stepStyle: StepStyle
) {

    require(labels.any { it != null }) {
        "At least one element in the contents list should be non-null. " +
                "If all elements are null, consider using 'NumberedStepper' instead."
    }

    require(currentStep in -1..totalSteps) { "Current step should be between 0 and total steps" }

    val context = LocalContext.current

    val displayMetrics = context.resources.displayMetrics

    //Width And Height Of Screen
    val width = displayMetrics.widthPixels
    val height = displayMetrics.heightPixels

    //Device Density
    val density = displayMetrics.density

    var contentSize by remember { mutableStateOf(IntSize.Zero) }

    Column(
        modifier = Modifier.then(modifier),
        horizontalAlignment = Alignment.Start
    ) {

        for (i in 0 until totalSteps) {
            val stepState = when {
                i < currentStep -> StepState.DONE
                i == currentStep -> StepState.CURRENT
                else -> StepState.TODO
            }

            if (labels[i] == null) {
                // Just render the step

                VerticalNumberedStep(
                    stepStyle = stepStyle,
                    stepState = stepState,
                    stepNumber = i + 1,
                    isLastStep = i == totalSteps - 1,
                )
            } else {
                // Render the step along with label but with ConstraintLayout

                VerticalLabelStep(
                    stepStyle = stepStyle,
                    stepState = stepState,
                    stepNumber = i + 1,
                    label = { labels[i] },
                    isLastStep = i == totalSteps - 1,
                )
            }

//            Row(
//                verticalAlignment = Alignment.Top,
//                horizontalArrangement = Arrangement.Start,
//                modifier = Modifier
//                    .onSizeChanged { contentSize = it }
//                    .then(modifier)
//            ) {
//
//                VerticalNumberedStep(
//                    stepStyle = stepStyle,
//                    stepState = stepState,
//                    stepNumber = i + 1,
//                    isLastStep = i == totalSteps - 1,
//                )
//
//                labels[i]?.let { label ->
//                    Spacer(modifier = Modifier.width(16.dp))
//
//                    Box(
//                        modifier = Modifier
//                            .weight(1f)
//                            .widthIn(max = width.dp - 16.dp - stepStyle.stepSize)
//                    ) {
//                        label()
//                    }
//                }
//            }
        }
    }
}