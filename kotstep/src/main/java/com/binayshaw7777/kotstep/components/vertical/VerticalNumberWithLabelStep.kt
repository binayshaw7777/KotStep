package com.binayshaw7777.kotstep.components.vertical

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.binayshaw7777.kotstep.model.StepState
import com.binayshaw7777.kotstep.model.StepStyle
import com.binayshaw7777.kotstep.util.noRippleClickable

/**
 * Represents a single step in a vertical numbered stepper.
 *
 * @param modifier The modifier to be applied to the step.
 * @param stepStyle The style of the step.
 * @param stepState The current state of the step.
 * @param stepNumber The number to be displayed in the step.
 * @param trailingLabel The label to be displayed on the right side for each step.
 * @param isLastStep Whether the step is the last step in the stepper.
 * @param onClick A callback that is invoked when the step is clicked.
 */
@Composable
internal fun VerticalNumberWithLabelStep(
    modifier: Modifier = Modifier,
    stepStyle: StepStyle,
    stepState: StepState,
    stepNumber: Int,
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
            StepState.CURRENT -> stepStyle.colors.todoContainerColor
            StepState.DONE -> stepStyle.colors.doneContainerColor
        }
    }

    val contentColor: Color by transition.animateColor(label = "contentColor") {
        when (it) {
            StepState.TODO -> stepStyle.colors.todoContentColor
            StepState.CURRENT -> stepStyle.colors.currentContentColor
            StepState.DONE -> stepStyle.colors.doneContentColor
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
                modifier = Modifier
                    .size(stepStyle.stepSize)
                    .clip(stepStyle.stepShape)
                    .background(containerColor)
                    .then(
                        if (stepState == StepState.CURRENT && stepStyle.showStrokeOnCurrent) {
                            Modifier.border(BorderStroke(2.dp, stepStyle.colors.currentContainerColor), shape = stepStyle.stepShape)
                        } else {
                            Modifier
                        }
                    )
            ) {
                if (stepState == StepState.DONE && stepStyle.showCheckMarkOnDone) {
                    Icon(
                        imageVector = Icons.Default.Done,
                        tint = contentColor,
                        contentDescription = "Done"
                    )
                } else {
                    Text(
                        text = stepNumber.toString(),
                        color = contentColor,
                        fontSize = stepStyle.textSize
                    )
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