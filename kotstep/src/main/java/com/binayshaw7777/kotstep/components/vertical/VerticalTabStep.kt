package com.binayshaw7777.kotstep.components.vertical

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import com.binayshaw7777.kotstep.components.divider.KotStepVerticalDivider
import com.binayshaw7777.kotstep.components.tabs.CurrentTab
import com.binayshaw7777.kotstep.components.tabs.DoneTab
import com.binayshaw7777.kotstep.components.tabs.TodoTab
import com.binayshaw7777.kotstep.model.LineType
import com.binayshaw7777.kotstep.model.StepState
import com.binayshaw7777.kotstep.model.StepStyle
import com.binayshaw7777.kotstep.util.noRippleClickable

/**
 * Renders a vertical tab step.
 *
 * @param modifier The modifier to be applied to the step.
 * @param stepStyle The style of the step.
 * @param stepState The state of the step.
 * @param isLastStep A flag indicating whether the step is the last step in the stepper.
 * @param lineProgress The progress of the line connecting the step to the next step.
 * @param onClick A callback that is invoked when the step is clicked.
 */
@Composable
internal fun VerticalTabStep(
    modifier: Modifier = Modifier,
    stepStyle: StepStyle,
    stepState: StepState,
    isLastStep: Boolean,
    lineProgress: Float,
    onClick: () -> Unit
) {

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

    Column(
        modifier = Modifier
            .noRippleClickable { onClick() }
            .then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(stepStyle.stepSize)
        ) {
            when (stepState) {
                StepState.TODO -> {
                    TodoTab(
                        strokeColor = containerColor,
                        strokeThickness = stepStyle.lineStyle.lineThickness.value
                    )
                }

                StepState.CURRENT -> {
                    CurrentTab(
                        circleColor = containerColor,
                        strokeThickness = stepStyle.lineStyle.lineThickness.value
                    )
                }

                StepState.DONE -> {
                    DoneTab(
                        circleColor = containerColor,
                        showTick = stepStyle.showCheckMarkOnDone,
                        tickColor = contentColor
                    )
                }
            }
        }

        if (!isLastStep) {
            KotStepVerticalDivider(
                modifier = Modifier.padding(
                    top = stepStyle.lineStyle.linePaddingTop,
                    bottom = stepStyle.lineStyle.linePaddingBottom
                ),
                height = stepStyle.lineStyle.lineSize,
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
    }
}