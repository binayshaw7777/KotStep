package com.binayshaw7777.kotstep.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp


/**
 * Represents the base style for a stepper component.
 *
 * @param totalSteps The total number of steps in the stepper.
 * @param currentStep The current active step in the stepper (zero-based index).
 */
sealed class BaseStepperStyle(val totalSteps: Int, val currentStep: Int)


/**
 * Represents a horizontal stepper style.
 *
 * @param totalSteps The total number of steps in the stepper.
 * @param currentStep The current active step in the stepper (zero-based index).
 */
sealed class HorizontalStepperStyle(totalSteps: Int, currentStep: Int) :
    BaseStepperStyle(totalSteps, currentStep) {

    /**
     * A tab-based horizontal stepper style.
     *
     * @param totalSteps The total number of steps in the stepper.
     * @param currentStep The current active step in the stepper (zero-based index).
     * @param stepStyle The style for the step numbers.
     */
    class Tab(totalSteps: Int, currentStep: Int, val stepStyle: StepStyle) :
        HorizontalStepperStyle(totalSteps, currentStep)

    /**
     * An icon-based horizontal stepper style.
     *
     * @param totalSteps The total number of steps in the stepper.
     * @param currentStep The current active step in the stepper (zero-based index).
     * @param icons The list of icons to be displayed in the stepper.
     * @param stepStyle The style for the step numbers.
     */
    class Icon(
        totalSteps: Int,
        currentStep: Int,
        val icons: List<ImageVector>,
        val stepStyle: StepStyle
    ) :
        HorizontalStepperStyle(totalSteps, currentStep)

    /**
     * A number-based horizontal stepper style.
     *
     * @param totalSteps The total number of steps in the stepper.
     * @param currentStep The current active step in the stepper (zero-based index).
     * @param stepStyle The style for the step numbers.
     */
    class Number(totalSteps: Int, currentStep: Int, val stepStyle: StepStyle) :
        HorizontalStepperStyle(totalSteps, currentStep)

//    /**
//     * A number-with-label horizontal stepper style.
//     *
//     * @param totalSteps The total number of steps in the stepper.
//     * @param currentStep The current active step in the stepper (zero-based index).
//     * @param textStyle The text style for the step numbers.
//     * @param labelTextStyle The text style for the step labels.
//     */
//    class NumberWithLabel(
//        totalSteps: Int,
//        currentStep: Int,
//        val textStyle: TextStyle,
//        val labelTextStyle: TextStyle
//    ) : HorizontalStepperStyle(totalSteps, currentStep)

    /**
     * A dashed horizontal stepper style.
     *
     * @param totalSteps The total number of steps in the stepper.
     * @param currentStep The current active step in the stepper (zero-based index).
     * @param stepStyle The style for the step numbers.
     */
    class Dashed(totalSteps: Int, currentStep: Int, val stepStyle: StepStyle) :
        HorizontalStepperStyle(totalSteps, currentStep)

//    /**
//     * A fleet-style horizontal stepper.
//     *
//     * @param totalSteps The total number of steps in the stepper.
//     * @param currentStep The current active step in the stepper (zero-based index).
//     * @param fleetItemContent The composable content for each fleet item.
//     */
//    class Fleet(
//        totalSteps: Int,
//        currentStep: Int,
//        val fleetItemContent: @Composable (Step, StepState) -> Unit
//    ) : HorizontalStepperStyle(totalSteps, currentStep)
}


/**
 * Represents a vertical stepper style.
 *
 * @param totalSteps The total number of steps in the stepper.
 * @param currentStep The current active step in the stepper (zero-based index).
 */
sealed class VerticalStepperStyle(totalSteps: Int, currentStep: Int) :
    BaseStepperStyle(totalSteps, currentStep) {

    /**
     * A tab-based vertical stepper style.
     *
     * @param totalSteps The total number of steps in the stepper.
     * @param currentStep The current active step in the stepper (zero-based index).
     */
    class Tab(totalSteps: Int, currentStep: Int, val stepStyle: StepStyle) :
        VerticalStepperStyle(totalSteps, currentStep)

    /**
     * A number-based vertical stepper style.
     *
     * @param totalSteps The total number of steps in the stepper.
     * @param currentStep The current active step in the stepper (zero-based index).
     * @param stepStyle The style for the step numbers.
     */
    class Number(totalSteps: Int, currentStep: Int, val stepStyle: StepStyle) :
        VerticalStepperStyle(totalSteps, currentStep)

//    /**
//     * A number-with-label vertical stepper style.
//     *
//     * @param totalSteps The total number of steps in the stepper.
//     * @param currentStep The current active step in the stepper (zero-based index).
//     * @param textStyle The text style for the step numbers.
//     * @param labelTextStyle The text style for the step labels.
//     */
//    class NumberWithLabel(
//        totalSteps: Int,
//        currentStep: Int,
//        val textStyle: TextStyle,
//        val labelTextStyle: TextStyle
//    ) : VerticalStepperStyle(totalSteps, currentStep)

    /**
     * An icon-based vertical stepper style.
     *
     * @param totalSteps The total number of steps in the stepper.
     * @param currentStep The current active step in the stepper (zero-based index).
     * @param icons The list of icons to be displayed in the stepper.
     */
    class Icon(
        totalSteps: Int, currentStep: Int,
        val icons: List<ImageVector>,
        val stepStyle: StepStyle
    ) :
        VerticalStepperStyle(totalSteps, currentStep)

//    /**
//     * A number-with-composable-label vertical stepper style.
//     *
//     * @param totalSteps The total number of steps in the stepper.
//     * @param currentStep The current active step in the stepper (zero-based index).
//     * @param textStyle The text style for the step numbers.
//     * @param labelContent The composable content for the step labels.
//     */
//    class NumberWithComposableLabel(
//        totalSteps: Int,
//        currentStep: Int,
//        val textStyle: TextStyle,
//        val labelContent: @Composable () -> Unit
//    ) : VerticalStepperStyle(totalSteps, currentStep)
}


/**
 * Represents the style for a step in a stepper component.
 *
 * @param totalSteps The total number of steps in the stepper.
 * @param currentStep The current active step in the stepper (zero-based index).
 * @param stepStyle The style for the step numbers.
 *
 * @return A dashed horizontal stepper style.
 */
fun dashed(
    totalSteps: Int,
    currentStep: Int,
    stepStyle: StepStyle = StepStyle()
): HorizontalStepperStyle.Dashed {
    return HorizontalStepperStyle.Dashed(
        totalSteps = totalSteps,
        currentStep = currentStep,
        stepStyle = stepStyle
    )
}


/**
 * Represents the style for a step in a stepper component.
 *
 * @param totalSteps The total number of steps in the stepper.
 * @param currentStep The current active step in the stepper (zero-based index).
 * @param stepStyle The style for the step numbers.
 *
 * @return A tab-based horizontal stepper style.
 */
fun horizontalTab(
    totalSteps: Int,
    currentStep: Int,
    stepStyle: StepStyle = StepStyle()
): HorizontalStepperStyle.Tab {
    return HorizontalStepperStyle.Tab(
        totalSteps = totalSteps,
        currentStep = currentStep,
        stepStyle = stepStyle
    )
}


/**
 * Represents the style for a step in a stepper component.
 *
 * @param totalSteps The total number of steps in the stepper.
 * @param currentStep The current active step in the stepper (zero-based index).
 *
 * @return A tab-based vertical stepper style.
 */
fun verticalTab(
    totalSteps: Int,
    currentStep: Int,
    stepStyle: StepStyle = StepStyle()
): VerticalStepperStyle.Tab {
    return VerticalStepperStyle.Tab(
        totalSteps = totalSteps,
        currentStep = currentStep,
        stepStyle = stepStyle
    )
}


/**
 * Represents the style for a step in a stepper component.
 *
 * @param totalSteps The total number of steps in the stepper.
 * @param currentStep The current active step in the stepper (zero-based index).
 * @param stepStyle The style for the step numbers.
 *
 * @return A number-based horizontal stepper style.
 */
fun numberedHorizontal(
    totalSteps: Int,
    currentStep: Int,
    stepStyle: StepStyle = StepStyle()
): HorizontalStepperStyle.Number {
    return HorizontalStepperStyle.Number(
        totalSteps = totalSteps,
        currentStep = currentStep,
        stepStyle = stepStyle
    )
}


/**
 * Represents the style for a step in a stepper component.
 *
 * @param totalSteps The total number of steps in the stepper.
 * @param currentStep The current active step in the stepper (zero-based index).
 * @param stepStyle The style for the step numbers.
 *
 * @return A number-based vertical stepper style.
 */
fun numberedVertical(
    totalSteps: Int,
    currentStep: Int,
    stepStyle: StepStyle = StepStyle()
): VerticalStepperStyle.Number {
    return VerticalStepperStyle.Number(
        totalSteps = totalSteps,
        currentStep = currentStep,
        stepStyle = stepStyle
    )
}

/**
 * Represents the style for a step in a stepper component.
 *
 * @param currentStep The current active step in the stepper (zero-based index).
 * @param icons The list of icons to be displayed in the stepper.
 * @param stepStyle The style for the step numbers.
 *
 * @return A icon-based horizontal stepper style.
 */
fun iconHorizontal(
    currentStep: Int,
    icons: List<ImageVector>,
    stepStyle: StepStyle = StepStyle()
): HorizontalStepperStyle.Icon {
    return HorizontalStepperStyle.Icon(
        totalSteps = icons.size,
        currentStep = currentStep,
        icons = icons,
        stepStyle = stepStyle
    )
}

/**
 * Represents the style for a step in a stepper component.
 *
 * @param currentStep The current active step in the stepper (zero-based index).
 * @param icons The list of icons to be displayed in the stepper.
 * @param stepStyle The style for the step numbers.
 *
 * @return A icon-based vertical stepper style.
 */
fun iconVertical(
    currentStep: Int,
    icons: List<ImageVector>,
    stepStyle: StepStyle = StepStyle()
): VerticalStepperStyle.Icon {
    return VerticalStepperStyle.Icon(
        totalSteps = icons.size,
        currentStep = currentStep,
        icons = icons,
        stepStyle = stepStyle
    )
}
