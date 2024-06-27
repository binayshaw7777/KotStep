package com.binayshaw7777.kotstep.components.vertical

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
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
import com.binayshaw7777.kotstep.components.tabs.CurrentTab
import com.binayshaw7777.kotstep.components.tabs.DoneTab
import com.binayshaw7777.kotstep.components.tabs.TodoTab
import com.binayshaw7777.kotstep.model.StepState
import com.binayshaw7777.kotstep.model.StepStyle

@Composable
internal fun VerticalTabWithLabelStep(
    modifier: Modifier = Modifier,
    stepStyle: StepStyle,
    stepState: StepState,
    label: (@Composable () -> Unit)?,
    isLastStep: Boolean,
) {
    val context = LocalContext.current
    val displayMetrics = context.resources.displayMetrics
    val density = displayMetrics.density

    val transition = updateTransition(targetState = stepState, label = "")

    val containerColor: Color by transition.animateColor(label = "itemColor") {
        when (it) {
            StepState.TODO -> stepStyle.colors.todoContainerColor
            StepState.CURRENT -> stepStyle.colors.currentContainerColor
            StepState.DONE -> stepStyle.colors.doneContainerColor
        }
    }

    var labelHeight by remember { mutableStateOf(0.dp) }
    var isLabelMeasured by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.Top,
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.wrapContentHeight()
        ) {

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(stepStyle.stepSize)
            ) {
                when (stepState) {
                    StepState.TODO -> {
                        TodoTab(
                            strokeColor = containerColor,
                            strokeThickness = stepStyle.lineThickness.value
                        )
                    }

                    StepState.CURRENT -> {
                        CurrentTab(
                            circleColor = containerColor,
                            strokeThickness = stepStyle.lineThickness.value
                        )
                    }

                    StepState.DONE -> {
                        DoneTab(
                            circleColor = containerColor,
                        )
                    }
                }
            }

            if (!isLastStep) {
                val measuredLabelHeight =
                    if (isLabelMeasured) labelHeight else stepStyle.lineSize
                VerticalDivider(
                    modifier = Modifier
                        .height(measuredLabelHeight + 8.dp)
                        .width(stepStyle.lineThickness)
                        .background(containerColor)
                )
            }
        }

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