package com.binayshaw7777.kotstep.components.vertical

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.binayshaw7777.kotstep.components.divider.KotStepVerticalDivider
import com.binayshaw7777.kotstep.model.LineType
import com.binayshaw7777.kotstep.model.StepState
import com.binayshaw7777.kotstep.model.StepStyle
import com.binayshaw7777.kotstep.util.maybeApplyBorder
import com.binayshaw7777.kotstep.util.noRippleClickable

/**
 * Represents a single step in a vertical numbered stepper.
 *
 * @param modifier The modifier to be applied to the step.
 * @param stepStyle The style of the step.
 * @param stepState The current state of the step.
 * @param stepNumber The number to be displayed in the step.
 * @param isLastStep Whether the step is the last step in the stepper.
 * @param lineProgress The progress of the line (fractional value).
 * @param onClick A callback that is invoked when the step is clicked.
 */
@Composable
internal fun VerticalNumberedStep(
    modifier: Modifier = Modifier,
    stepStyle: StepStyle,
    stepState: StepState,
    stepNumber: Int,
    isLastStep: Boolean,
    lineProgress: Float,
    onClick: () -> Unit
) {

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

    Column(
        modifier = Modifier
            .noRippleClickable { onClick() }
            .then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Defines Text or Tick/Done Icon
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(stepStyle.stepSize)
                .clip(stepStyle.stepShape)
                .maybeApplyBorder(
                    strokeColor = when (stepState) {
                        StepState.TODO -> stepStyle.colors.todoStepStrokeColor
                        StepState.CURRENT -> stepStyle.colors.currentStepStrokeColor
                        StepState.DONE -> stepStyle.colors.doneStepStrokeColor
                    },
                    strokeThickness = stepStyle.stepStroke,
                    shape = stepStyle.stepShape
                )
                .background(containerColor)
        ) {
            if (stepState == StepState.DONE && stepStyle.showCheckMarkOnDone) {
                Icon(
                    imageVector = Icons.Default.Done,
                    tint = stepStyle.colors.checkMarkColor,
                    contentDescription = "Done"
                )
            } else {
                Text(
                    text = stepNumber.toString(),
                    color = contentColor,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Display is continuous line if not completed
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