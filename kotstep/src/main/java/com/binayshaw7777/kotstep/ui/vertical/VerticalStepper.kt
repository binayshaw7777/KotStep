package com.binayshaw7777.kotstep.ui.vertical

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
fun VerticalStepper(modifier: Modifier = Modifier, style: VerticalStepperStyle) {
    when (style) {


        is VerticalStepperStyle.Tab -> RenderVerticalTab(
            modifier = modifier,
            totalSteps = style.totalSteps,
            currentStep = style.currentStep,
            stepStyle = style.stepStyle
        )

        is VerticalStepperStyle.Icon -> RenderVerticalIcon(
            modifier = modifier,
            totalSteps = style.totalSteps,
            currentStep = style.currentStep,
            icons = style.icons,
            stepStyle = style.stepStyle
        )

        is VerticalStepperStyle.Number -> RenderVerticalNumber(
            modifier = modifier,
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