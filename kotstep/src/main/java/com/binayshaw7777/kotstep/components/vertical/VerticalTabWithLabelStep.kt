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
import com.binayshaw7777.kotstep.util.noRippleClickable

/**
 * A composable function that renders a vertical stepper with labels.
 *
 * @property modifier A [Modifier] to be applied to the stepper. Defaults to [Modifier].
 * @property stepStyle The style properties for the steps in the stepper.
 * @property stepState The state of the step.
 * @property trailingLabel The label to be displayed on the right side for the step.
 * @property isLastStep A flag indicating if the step is the last step in the stepper.
 * @property onClick A callback that is invoked when the step is clicked.
 */
@Composable
internal fun VerticalTabWithLabelStep(
    modifier: Modifier = Modifier,
    stepStyle: StepStyle,
    stepState: StepState,
    trailingLabel: (@Composable () -> Unit)?,
    isLastStep: Boolean,
    onClick: () -> Unit
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

    val lineColor: Color by transition.animateColor(label = "lineColor") {
        when (it) {
            StepState.TODO -> stepStyle.colors.todoLineColor
            StepState.CURRENT -> stepStyle.colors.currentLineColor
            StepState.DONE -> stepStyle.colors.doneLineColor
        }
    }

    var labelHeight by remember { mutableStateOf(0.dp) }
    var isLabelMeasured by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.Top,
        modifier = Modifier
            .noRippleClickable { onClick() }
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
                    if (isLabelMeasured) maxOf(labelHeight, stepStyle.lineSize) else stepStyle.lineSize

                VerticalDivider(
                    modifier = Modifier
                        .height(measuredLabelHeight + 8.dp)
                        .width(stepStyle.lineThickness)
                        .background(lineColor)
                )
            }
        }

        trailingLabel?.let { labelContent ->
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