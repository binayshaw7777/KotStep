package com.binayshaw7777.kotstep.components.horizontal

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.binayshaw7777.kotstep.model.StepState
import com.binayshaw7777.kotstep.model.StepStyle

/**
 * Represents a single step in a horizontal fleet progress bar.
 *
 * This composable displays a horizontal line representing a single step in a sequence.
 * It visually indicates whether the step is completed, currently active, or yet to be done.
 * The progress of the current step is animated based on the provided `isPlaying` state and `duration`.
 *
 * @param modifier Modifier for styling and layout of the step.
 * @param totalSteps The total number of steps in the fleet.
 * @param index The index of the current step (starting from 0).
 * @param duration The duration in milliseconds for animating the progress of a single step.
 * @param currentStep The index of the currently active step.
 * @param stepStyle The style configuration for the step's appearance.
 * @param isPlaying Boolean indicating whether the progress animation is currently playing.
 * @param stepState The current state of the step (TODO, CURRENT, or DONE).
 * @param size The size of the overall fleet container. Used to calculate step widths.
 * @param onClick Callback invoked when the step is clicked.
 * @param onStepComplete Callback invoked when the progress of the current step reaches 100%.
 */
@Composable
internal fun HorizontalFleetStep(
    modifier: Modifier = Modifier,
    totalSteps: Int,
    index: Int,
    duration: Long,
    currentStep: Int,
    stepStyle: StepStyle,
    isPlaying: Boolean,
    stepState: StepState,
    size: IntSize,
    onClick: () -> Unit,
    onStepComplete: () -> Unit
) {
    val containerColor: Color = when (stepState) {
        StepState.TODO -> stepStyle.colors.todoContainerColor
        StepState.CURRENT -> stepStyle.colors.currentContainerColor
        StepState.DONE -> stepStyle.colors.doneContainerColor
    }
    val density = LocalDensity.current

    val progress = remember { Animatable(0f) }

    var hasCompleted by remember { mutableStateOf(false) }

    LaunchedEffect(currentStep) {
        progress.snapTo(0f)
    }

    LaunchedEffect(currentStep, isPlaying) {
        hasCompleted = false
        when {
            index < currentStep -> {
                progress.snapTo(1f)
            }

            index == currentStep -> {
                if (progress.value == 0f) {
                    progress.snapTo(0f)
                }

                if (isPlaying) {
                    val remainingDuration = (duration * (1f - progress.value)).toLong()

                    progress.animateTo(
                        targetValue = 1f,
                        animationSpec = tween(
                            durationMillis = remainingDuration.toInt(),
                            easing = LinearEasing
                        )
                    )
                } else {
                    progress.stop()
                }
            }

            else -> {
                progress.snapTo(0f)
            }
        }
    }

    LaunchedEffect(progress.value) {
        if (progress.value == 1f && index == currentStep && !hasCompleted) {
            hasCompleted = true
            onStepComplete()
        }
    }

    LaunchedEffect(currentStep, isPlaying) {
        when {
            index < currentStep -> {
                progress.snapTo(1f)
            }

            index == currentStep -> {
                if (isPlaying) {
                    val startValue = progress.value
                    progress.animateTo(
                        targetValue = 1f,
                        animationSpec = tween(
                            durationMillis = ((1f - startValue) * duration).toInt(),
                            easing = LinearEasing
                        )
                    )
                }
            }

            else -> {
                progress.snapTo(0f)
            }
        }
    }

    val stepModifier = remember(size, totalSteps, stepStyle) {
        Modifier
            .padding(stepStyle.stepPadding)
            .then(
                with(density) {
                    if (totalSteps > 1) {
                        Modifier
                            .widthIn(max = size.width.toDp() / totalSteps)
                            .padding(2.dp)
                    } else {
                        Modifier
                            .fillMaxWidth()
                            .padding(2.dp)
                    }
                }
            )
            .height(stepStyle.lineStyle.lineThickness)
            .clickable(onClick = onClick)
    }

    LinearProgressIndicator(
        progress = { progress.value },
        modifier = stepModifier.then(modifier),
        color = containerColor,
        trackColor = stepStyle.colors.todoLineColor,
        strokeCap = stepStyle.lineStyle.progressStrokeCap,
        gapSize = (-15).dp,
        drawStopIndicator = {}

    )
}