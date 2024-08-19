package com.binayshaw7777.kotstep.components.horizontal

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.binayshaw7777.kotstep.components.divider.KotStepHorizontalDivider
import com.binayshaw7777.kotstep.model.LineType
import com.binayshaw7777.kotstep.model.StepState
import com.binayshaw7777.kotstep.model.StepStyle
import com.binayshaw7777.kotstep.util.noRippleClickable

/**
 * Represents a single step in a horizontal icon stepper.
 *
 * @param modifier The modifier to be applied to the step.
 * @param stepStyle The style of the step.
 * @param stepState The current state of the step.
 * @param totalSteps The total number of steps in the stepper.
 * @param stepIcon The icon to be displayed in the step.
 * @param isLastStep Whether the step is the last step in the stepper.
 * @param size The size of the stepper.
 * @param lineProgress The progress of the line (fractional value).
 * @param onClick A callback that is invoked when the step is clicked.
 */
@Composable
internal fun HorizontalIconStep(
    modifier: Modifier = Modifier,
    stepStyle: StepStyle,
    stepState: StepState,
    totalSteps: Int,
    stepIcon: ImageVector,
    isLastStep: Boolean,
    size: IntSize,
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

    val lineStyle: LineType = when (stepState) {
        StepState.TODO -> stepStyle.lineStyle.todoLineType
        StepState.CURRENT -> stepStyle.lineStyle.currentLineType
        StepState.DONE -> stepStyle.lineStyle.doneLineType
    }

    val strokeCap: StrokeCap = when (stepState) {
        StepState.TODO -> StrokeCap.Round
        StepState.CURRENT -> StrokeCap.Square
        StepState.DONE -> stepStyle.lineStyle.strokeCap
    }

    Row(
        modifier = Modifier
            .noRippleClickable { onClick() }
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
        // Defines Text or Tick/Done Icon
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(stepStyle.stepSize)
                .clip(stepStyle.stepShape)
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
                .background(containerColor)
        ) {
            if (stepState == StepState.DONE && stepStyle.showCheckMarkOnDone) {
                Icon(
                    imageVector = Icons.Default.Done,
                    tint = contentColor,
                    contentDescription = "Done"
                )
            } else {
                Icon(
                    imageVector = stepIcon,
                    tint = contentColor,
                    contentDescription = "Step Icon"
                )
            }
        }

        // Display is continuous line if not completed
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
                lineStyle = lineStyle,
                progress = lineProgress,
                strokeCap = strokeCap
            )
        }
    }
}