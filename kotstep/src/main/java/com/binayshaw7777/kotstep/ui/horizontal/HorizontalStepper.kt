package com.binayshaw7777.kotstep.ui.horizontal

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.binayshaw7777.kotstep.model.HorizontalStepperStyle
import com.binayshaw7777.kotstep.ui.horizontal.step.RenderHorizontalDashed
import com.binayshaw7777.kotstep.ui.horizontal.step.RenderHorizontalIcon
import com.binayshaw7777.kotstep.ui.horizontal.step.RenderHorizontalNumber
import com.binayshaw7777.kotstep.ui.horizontal.step.RenderHorizontalTab


/**
 * Renders a horizontal stepper.
 *
 * @param style The style of the stepper.
 */
@Composable
fun HorizontalStepper(modifier: Modifier = Modifier, style: HorizontalStepperStyle) {
    when (style) {
        is HorizontalStepperStyle.Tab -> RenderHorizontalTab(
            modifier = modifier,
            totalSteps = style.totalSteps,
            currentStep = style.currentStep,
            stepStyle = style.stepStyle
        )

        is HorizontalStepperStyle.Icon -> RenderHorizontalIcon(
            modifier = modifier,
            totalSteps = style.totalSteps,
            currentStep = style.currentStep,
            icons = style.icons,
            stepStyle = style.stepStyle
        )

        is HorizontalStepperStyle.Number -> RenderHorizontalNumber(
            modifier = modifier,
            totalSteps = style.totalSteps,
            currentStep = style.currentStep,
            stepStyle = style.stepStyle
        )

        is HorizontalStepperStyle.Dashed -> RenderHorizontalDashed(
            modifier = modifier,
            totalSteps = style.totalSteps,
            currentStep = style.currentStep,
            stepStyle = style.stepStyle
        )

//        is HorizontalStepperStyle.Fleet -> RenderHorizontalFleet(
//            style.totalSteps,
//            style.currentStep,
//            style.fleetItemContent
//        )
    }
}