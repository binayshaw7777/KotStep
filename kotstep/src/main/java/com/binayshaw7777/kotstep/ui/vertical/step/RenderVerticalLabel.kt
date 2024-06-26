package com.binayshaw7777.kotstep.ui.vertical.step

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
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

    require(currentStep in -1..totalSteps) { "Current step should be between -1 and total steps" }

    val context = LocalContext.current

    val displayMetrics = context.resources.displayMetrics

    val density = displayMetrics.density

    Column(modifier = modifier) {
        labels.forEachIndexed { index, label ->
            val stepState = when {
                index < currentStep -> StepState.DONE
                index == currentStep -> StepState.CURRENT
                else -> StepState.TODO
            }

            var labelHeight by remember { mutableStateOf(0.dp) }
            var isLabelMeasured by remember { mutableStateOf(false) }

            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Step number or icon
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier.wrapContentHeight()
                ) {

                    // Divider or vertical line
//                    if (totalSteps != 1 && index == labels.size - 1 && label != null) {
//                        val measuredLabelHeight =
//                            if (isLabelMeasured) (labelHeight / 2 + stepStyle.stepSize / 2) else stepStyle.lineSize
//                        VerticalDivider(
//                            modifier = Modifier
//                                .height(measuredLabelHeight + 8.dp)
//                                .width(stepStyle.lineThickness)
//                                .background(color = stepStyle.colors.currentContainerColor)
//                        )
//                    }

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(stepStyle.stepSize)
                            .background(
                                color = when (stepState) {
                                    StepState.DONE -> stepStyle.colors.doneContainerColor
                                    StepState.CURRENT -> stepStyle.colors.currentContainerColor
                                    StepState.TODO -> stepStyle.colors.todoContainerColor
                                },
                                shape = stepStyle.stepShape
                            )
                            .border(
                                width = if (stepState == StepState.CURRENT && stepStyle.showStrokeOnCurrent) 2.dp else 0.dp,
                                color = if (stepState == StepState.CURRENT) stepStyle.colors.currentContentColor else Color.Transparent,
                                shape = stepStyle.stepShape
                            )
                    ) {
                        if (stepState == StepState.DONE && stepStyle.showCheckMarkOnDone) {
                            // Use your own icon here, just an example
                            Text(
                                text = "✓",
                                color = stepStyle.colors.doneContentColor,
                                fontSize = stepStyle.textSize
                            )
                        } else {
                            Text(
                                text = (index + 1).toString(),
                                color = stepStyle.colors.currentContentColor,
                                fontSize = stepStyle.textSize
                            )
                        }
                    }

                    // Divider or vertical line
                    if (index != labels.size - 1) {
                        val measuredLabelHeight =
                            if (isLabelMeasured) labelHeight else stepStyle.lineSize
                        VerticalDivider(
                            modifier = Modifier
                                .height(measuredLabelHeight + 8.dp)
                                .width(stepStyle.lineThickness)
                                .background(color = stepStyle.colors.currentContainerColor)
                        )
                    }
                }

                // Label content
                label?.let { labelContent ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 16.dp)
                            .onGloballyPositioned { coordinates ->
                                if (!isLabelMeasured) {
                                    labelHeight = (coordinates.size.height.toFloat() / density).dp
                                    isLabelMeasured = true
                                }
                            },
                        contentAlignment = Alignment.TopStart
                    ) {
                        labelContent()
                    }
                }
            }
        }
    }
}