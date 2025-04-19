package com.binayshaw7777.kotstep.v2.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.binayshaw7777.kotstep.v2.component.layout.HorizontalKotstep
import com.binayshaw7777.kotstep.v2.component.layout.VerticalKotStep
import com.binayshaw7777.kotstep.v2.model.KotStepScope
import com.binayshaw7777.kotstep.v2.model.step.StepLayoutStyle
import com.binayshaw7777.kotstep.v2.model.style.KotStepStyle

@Composable
fun KotStep(
    modifier: Modifier = Modifier,
    currentStep: () -> Float,
    style: KotStepStyle = KotStepStyle(),
    content: KotStepScope.() -> Unit
) {
    val steps = KotStepScope().apply(content).buildSteps()

    when (style.stepLayoutStyle) {
        StepLayoutStyle.Vertical, StepLayoutStyle.VerticalCentered -> {
            VerticalKotStep(
                modifier = modifier,
                currentStep = currentStep,
                style = style,
                steps = steps,
                onClick = { index ->
                    steps[index].onClick?.invoke()
                }
            )
        }

        StepLayoutStyle.Horizontal, StepLayoutStyle.HorizontalCentered -> {
            HorizontalKotstep(
                modifier = modifier,
                currentStep = currentStep,
                style = style,
                steps = steps,
                onClick = { index ->
                    steps[index].onClick?.invoke()
                }
            )
        }
    }
}