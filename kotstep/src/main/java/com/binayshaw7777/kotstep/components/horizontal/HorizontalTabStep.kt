package com.binayshaw7777.kotstep.components.horizontal

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import com.binayshaw7777.kotstep.components.divider.KotStepHorizontalDivider
import com.binayshaw7777.kotstep.components.tabs.CurrentTab
import com.binayshaw7777.kotstep.components.tabs.DoneTab
import com.binayshaw7777.kotstep.components.tabs.TodoTab
import com.binayshaw7777.kotstep.model.LineType
import com.binayshaw7777.kotstep.model.StepState
import com.binayshaw7777.kotstep.model.StepStyle
import com.binayshaw7777.kotstep.util.noRippleClickable

/**
 * Represents a single step in a horizontal tab stepper.
 *
 * @param modifier The modifier to be applied to the step.
 * @param stepStyle The style of the step.
 * @param stepState The current state of the step.
 * @param totalSteps The total number of steps in the stepper.
 * @param isLastStep Whether the step is the last step in the stepper.
 * @param size The size of the stepper.
 * @param lineProgress The progress of the line (fractional value).
 * @param onClick The callback to be invoked when the step is clicked.
 */
@Composable
internal fun HorizontalTabStep(
    modifier: Modifier = Modifier,
    stepStyle: StepStyle,
    stepState: StepState,
    totalSteps: Int,
    isLastStep: Boolean,
    size: IntSize,
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

    Row(
        modifier = Modifier
            .noRippleClickable {
                onClick()
            }
            .then(
                with(LocalDensity.current) {
                    if (totalSteps > 1) {
                        Modifier
                            .widthIn(max = size.width.toDp() / totalSteps)
                    } else {
                        Modifier
                            .fillMaxWidth()
                    }
                }
            )
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically
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
            KotStepHorizontalDivider(
                modifier = Modifier.padding(
                    start = stepStyle.lineStyle.linePaddingStart,
                    end = stepStyle.lineStyle.linePaddingEnd
                ),
                height = stepStyle.lineStyle.lineThickness,
                width = stepStyle.lineStyle.lineSize,
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