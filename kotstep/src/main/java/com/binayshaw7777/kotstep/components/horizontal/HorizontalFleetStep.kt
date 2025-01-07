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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.binayshaw7777.kotstep.model.StepState
import com.binayshaw7777.kotstep.model.StepStyle

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
    onStepComplete: (Int) -> Unit
) {
    val containerColor: Color = when (stepState) {
        StepState.TODO -> stepStyle.colors.todoContainerColor
        StepState.CURRENT -> stepStyle.colors.currentContainerColor
        StepState.DONE -> stepStyle.colors.doneContainerColor
    }
    val density = LocalDensity.current

    val progress = remember { Animatable(0f) }

    LaunchedEffect(currentStep) {
        when {
            index < currentStep -> {
                progress.snapTo(1f)
            }

            index == currentStep -> {
                progress.snapTo(0f)
                progress.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(
                        durationMillis = duration.toInt(),
                        easing = LinearEasing
                    )
                )
            }

            else -> {
                progress.snapTo(0f)
            }
        }
    }

    LaunchedEffect(progress.value) {
        if (progress.value == 1f) {
            onStepComplete(currentStep)
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
        strokeCap = stepStyle.lineStyle.progressStrokeCap
    )
}