package com.binayshaw7777.kotstep.components.vertical

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.binayshaw7777.kotstep.components.tabs.CurrentTab
import com.binayshaw7777.kotstep.components.tabs.DoneTab
import com.binayshaw7777.kotstep.components.tabs.TodoTab
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
 * @param onClick A callback that is invoked when the step is clicked.
 */
@Composable
internal fun VerticalTabStep(
    modifier: Modifier = Modifier,
    stepStyle: StepStyle,
    stepState: StepState,
    isLastStep: Boolean,
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
            VerticalDivider(
                modifier = Modifier.heightIn(max = stepStyle.lineSize),
                thickness = stepStyle.lineThickness,
                color = containerColor
            )
        }
    }
}