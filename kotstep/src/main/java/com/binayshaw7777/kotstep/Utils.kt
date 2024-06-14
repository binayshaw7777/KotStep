package com.binayshaw7777.kotstep

import androidx.compose.ui.graphics.vector.ImageVector
import com.binayshaw7777.kotstep.model.HorizontalStepperStyle
import com.binayshaw7777.kotstep.model.StepStyle

fun dashed(
    totalSteps: Int,
    currentStep: Int,
    stepStyle: StepStyle = StepStyle()
) : HorizontalStepperStyle.Dashed {
    return HorizontalStepperStyle.Dashed(
        totalSteps = totalSteps,
        currentStep = currentStep,
        stepStyle = stepStyle
    )
}


fun numbered(
    totalSteps: Int,
    currentStep: Int,
    stepStyle: StepStyle = StepStyle()
) : HorizontalStepperStyle.Number {
    return HorizontalStepperStyle.Number(
        totalSteps = totalSteps,
        currentStep = currentStep,
        stepStyle = stepStyle
    )
}

fun icon(
    currentStep: Int,
    icons: List<ImageVector>,
    stepStyle: StepStyle = StepStyle()
) : HorizontalStepperStyle.Icon {
    return HorizontalStepperStyle.Icon(
        totalSteps = icons.size,
        currentStep = currentStep,
        icons = icons,
        stepStyle = stepStyle
    )
}