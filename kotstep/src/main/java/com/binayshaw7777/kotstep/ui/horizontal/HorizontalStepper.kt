package com.binayshaw7777.kotstep.ui.horizontal

import androidx.compose.runtime.Composable
import com.binayshaw7777.kotstep.model.HorizontalStepperStyle
import com.binayshaw7777.kotstep.ui.horizontal.step.RenderHorizontalDashed
import com.binayshaw7777.kotstep.ui.horizontal.step.RenderHorizontalFleet
import com.binayshaw7777.kotstep.ui.horizontal.step.RenderHorizontalIcon
import com.binayshaw7777.kotstep.ui.horizontal.step.RenderHorizontalNumber
import com.binayshaw7777.kotstep.ui.horizontal.step.RenderHorizontalNumberWithLabel
import com.binayshaw7777.kotstep.ui.horizontal.step.RenderHorizontalTab


/**
 * Render the horizontal stepper
 *
 * @param style HorizontalStepperStyle
 */
@Composable
fun HorizontalStepper(style: HorizontalStepperStyle) {
    when (style) {
        is HorizontalStepperStyle.Tab -> RenderHorizontalTab(
            style.totalSteps,
            style.currentStep
        )

        is HorizontalStepperStyle.Icon -> RenderHorizontalIcon(
            totalSteps = style.totalSteps,
            currentStep = style.currentStep,
            icons = style.icons,
            stepStyle = style.stepStyle
        )

        is HorizontalStepperStyle.Number -> RenderHorizontalNumber(
            totalSteps = style.totalSteps,
            currentStep = style.currentStep,
            stepStyle = style.stepStyle
        )

        is HorizontalStepperStyle.NumberWithLabel -> RenderHorizontalNumberWithLabel(
            style.totalSteps,
            style.currentStep,
            style.textStyle,
            style.labelTextStyle
        )

        is HorizontalStepperStyle.Dashed -> RenderHorizontalDashed(
            totalSteps = style.totalSteps,
            currentStep = style.currentStep,
            stepStyle = style.stepStyle
        )

        is HorizontalStepperStyle.Fleet -> RenderHorizontalFleet(
            style.totalSteps,
            style.currentStep,
            style.fleetItemContent
        )
    }
}