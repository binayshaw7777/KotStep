package com.binayshaw7777.kotstep.components.horizontal

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.binayshaw7777.kotstep.model.StepState
import com.binayshaw7777.kotstep.model.StepStyle

/**
 * Horizontal Icon Step
 *
 * @param modifier Modifier
 * @param stepStyle StepStyle
 * @param stepState StepState
 * @param totalSteps Int
 * @param stepIcon ImageVector
 * @param isLastStep Boolean
 * @param size IntSize
 */
@Composable
fun HorizontalIconStep(
    modifier: Modifier = Modifier,
    stepStyle: StepStyle,
    stepState: StepState,
    totalSteps: Int,
    stepIcon: ImageVector,
    isLastStep: Boolean,
    size: IntSize
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

    val borderStrokeColor: BorderStroke = if (stepState == StepState.CURRENT) {
        BorderStroke(2.dp, stepStyle.colors.currentContainerColor)
    } else {
        BorderStroke(0.dp, Color.Unspecified)
    }

    Row(
        modifier = Modifier
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
                .border(borderStrokeColor, shape = stepStyle.stepShape)
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
            HorizontalDivider(
                thickness = stepStyle.lineThickness,
                color = containerColor
            )
        }
    }
}