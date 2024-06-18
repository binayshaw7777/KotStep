package com.binayshaw7777.kotstep.ui.vertical

import androidx.compose.runtime.Composable
import com.binayshaw7777.kotstep.model.VerticalStepperStyle
import com.binayshaw7777.kotstep.ui.vertical.step.RenderVerticalIcon
import com.binayshaw7777.kotstep.ui.vertical.step.RenderVerticalNumber
import com.binayshaw7777.kotstep.ui.vertical.step.RenderVerticalTab


/**
 * Render the vertical stepper based on the style
 *
 * @param style The style of the vertical stepper
 */
@Composable
fun VerticalStepper(style: VerticalStepperStyle) {
    when (style) {


        is VerticalStepperStyle.Tab -> RenderVerticalTab(
            totalSteps = style.totalSteps,
            currentStep = style.currentStep,
            stepStyle = style.stepStyle
        )

        is VerticalStepperStyle.Icon -> RenderVerticalIcon(
            totalSteps = style.totalSteps,
            currentStep = style.currentStep,
            icons = style.icons,
            stepStyle = style.stepStyle
        )

        is VerticalStepperStyle.Number -> RenderVerticalNumber(
            totalSteps = style.totalSteps,
            currentStep = style.currentStep,
            stepStyle = style.stepStyle
        )

//        is VerticalStepperStyle.NumberWithLabel -> RenderVerticalNumberWithLabel(
//            totalSteps = style.totalSteps,
//            currentStep = style.currentStep,
//            textStyle = style.textStyle,
//            labelTextStyle = style.labelTextStyle
//        )
//
//        is VerticalStepperStyle.NumberWithComposableLabel -> RenderVerticalNumberWithComposableLabel(
//            totalSteps = style.totalSteps,
//            currentStep = style.currentStep,
//            textStyle = style.textStyle,
//            labelContent = style.labelContent
//        )
    }
}