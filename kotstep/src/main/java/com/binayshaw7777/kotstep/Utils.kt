package com.binayshaw7777.kotstep

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