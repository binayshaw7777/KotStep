package com.binayshaw7777.kotstep.components.vertical

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.binayshaw7777.kotstep.components.divider.KotStepVerticalDivider
import com.binayshaw7777.kotstep.components.tabs.CurrentTab
import com.binayshaw7777.kotstep.components.tabs.DoneTab
import com.binayshaw7777.kotstep.components.tabs.TodoTab
import com.binayshaw7777.kotstep.model.LineType
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
 * @property lineProgress The progress of the line connecting the step to the next step.
 * @property onClick A callback that is invoked when the step is clicked.
 */
@Composable
internal fun VerticalTabWithLabelStep(
    modifier: Modifier = Modifier,
    stepStyle: StepStyle,
    stepState: StepState,
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
            StepState.CURRENT -> stepStyle.colors.currentContainerColor
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
                .constrainAs(iconBox) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
        ) {
            when (stepState) {
                StepState.TODO -> {
                    TodoTab(
                        strokeColor = containerColor,
                        strokeThickness = stepStyle.stepStroke,
                        stepShape = stepStyle.stepShape
                    )
                }

                StepState.CURRENT -> {
                    CurrentTab(
                        circleColor = containerColor,
                        strokeThickness = stepStyle.stepStroke,
                        stepShape = stepStyle.stepShape
                    )
                }

                StepState.DONE -> {
                    DoneTab(
                        circleColor = containerColor,
                        showTick = stepStyle.showCheckMarkOnDone,
                        tickColor = contentColor,
                        stepShape = stepStyle.stepShape
                    )
                }
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