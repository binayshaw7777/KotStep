package com.binayshaw7777.kotstep.components.vertical

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.binayshaw7777.kotstep.components.divider.KotStepVerticalDivider
import com.binayshaw7777.kotstep.model.LineType
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
 * @param lineProgress The progress of the line connecting the steps.
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
    lineProgress: Float,
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

    val lineColor: Color by transition.animateColor(label = "lineColor") {
        when (it) {
            StepState.TODO -> stepStyle.colors.todoLineColor
            StepState.CURRENT -> stepStyle.colors.currentLineColor
            StepState.DONE -> stepStyle.colors.doneLineColor
        }
    }

    val lineTrackStyle: LineType = when (stepState) {
        StepState.TODO -> stepStyle.lineStyle.todoLineTrackType
        StepState.CURRENT -> stepStyle.lineStyle.currentLineTrackType
        StepState.DONE -> stepStyle.lineStyle.doneLineTrackType
    }

    val lineProgressStyle: LineType = when (stepState) {
        StepState.TODO -> stepStyle.lineStyle.todoLineProgressType
        StepState.CURRENT -> stepStyle.lineStyle.currentLineProgressType
        StepState.DONE -> stepStyle.lineStyle.doneLineProgressType
    }

    val trackStrokeCap: StrokeCap = when (stepState) {
        StepState.TODO -> StrokeCap.Round
        StepState.CURRENT -> StrokeCap.Square
        StepState.DONE -> stepStyle.lineStyle.trackStrokeCap
    }

    val progressStrokeCap: StrokeCap = when (stepState) {
        StepState.TODO -> StrokeCap.Round
        StepState.CURRENT -> StrokeCap.Square
        StepState.DONE -> stepStyle.lineStyle.progressStrokeCap
    }

    var labelHeight by remember { mutableStateOf(0.dp) }
    var isLabelMeasured by remember { mutableStateOf(false) }


    ConstraintLayout(
        modifier = Modifier
            .noRippleClickable { onClick() }
            .fillMaxWidth()
            .then(modifier)
    ) {
        val (iconBox, divider, labelBox) = createRefs()

        // Icon Box
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(stepStyle.stepSize)
                .clip(stepStyle.stepShape)
                .background(containerColor)
                .then(
                    if (stepState == StepState.CURRENT && stepStyle.showStrokeOnCurrent) {
                        Modifier.border(
                            BorderStroke(2.dp, stepStyle.colors.currentContainerColor),
                            shape = stepStyle.stepShape
                        )
                    } else {
                        Modifier
                    }
                )
                .constrainAs(iconBox) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
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

        // Vertical Divider (Line)
        if (!isLastStep) {
            val measuredLabelHeight =
                if (isLabelMeasured) maxOf(
                    labelHeight,
                    stepStyle.lineStyle.lineSize
                ) else stepStyle.lineStyle.lineSize

            KotStepVerticalDivider(
                modifier = Modifier
                    .padding(
                        top = stepStyle.lineStyle.linePaddingTop,
                        bottom = stepStyle.lineStyle.linePaddingBottom
                    )
                    .constrainAs(divider) {
                        top.linkTo(iconBox.bottom, margin = stepStyle.lineStyle.linePaddingTop)
                        start.linkTo(iconBox.start)
                        end.linkTo(iconBox.end)
                        bottom.linkTo(parent.bottom, margin = stepStyle.lineStyle.linePaddingBottom)
                    },
                height = measuredLabelHeight,
                width = stepStyle.lineStyle.lineThickness,
                lineTrackColor = stepStyle.colors.todoLineColor,
                lineProgressColor = lineColor,
                lineTrackStyle = lineTrackStyle,
                lineProgressStyle = lineProgressStyle,
                progress = lineProgress,
                trackStrokeCap = trackStrokeCap,
                progressStrokeCap = progressStrokeCap
            )
        }

        // Trailing Label
        trailingLabel?.let { labelContent ->
            Box(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .onGloballyPositioned { coordinates ->
                        if (!isLabelMeasured) {
                            labelHeight = (coordinates.size.height.toFloat() / density).dp
                            isLabelMeasured = true
                        }
                    }
                    .constrainAs(labelBox) {
                        top.linkTo(iconBox.top)
                        start.linkTo(iconBox.end)
                        end.linkTo(parent.end)
                        width = androidx.constraintlayout.compose.Dimension.fillToConstraints
                    },
                contentAlignment = Alignment.TopStart
            ) {
                labelContent()
            }
        }
    }
}