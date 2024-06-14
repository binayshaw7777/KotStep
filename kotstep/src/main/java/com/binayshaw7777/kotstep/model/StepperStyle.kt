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
     */
    class Tab(totalSteps: Int, currentStep: Int) : HorizontalStepperStyle(totalSteps, currentStep)

    /**
     * An icon-based horizontal stepper style.
     *
     * @param totalSteps The total number of steps in the stepper.
     * @param currentStep The current active step in the stepper (zero-based index).
     * @param iconSize The size of the icons in the stepper.
     * @param icons The list of icons to be displayed in the stepper.
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

    /**
     * A number-with-label horizontal stepper style.
     *
     * @param totalSteps The total number of steps in the stepper.
     * @param currentStep The current active step in the stepper (zero-based index).
     * @param textStyle The text style for the step numbers.
     * @param labelTextStyle The text style for the step labels.
     */
    class NumberWithLabel(
        totalSteps: Int,
        currentStep: Int,
        val textStyle: TextStyle,
        val labelTextStyle: TextStyle
    ) : HorizontalStepperStyle(totalSteps, currentStep)

    /**
     * A dashed horizontal stepper style.
     *
     * @param totalSteps The total number of steps in the stepper.
     * @param currentStep The current active step in the stepper (zero-based index).
     * @param stepStyle The style for the step numbers.
     */
    class Dashed(totalSteps: Int, currentStep: Int, val stepStyle: StepStyle) :
        HorizontalStepperStyle(totalSteps, currentStep)

    /**
     * A fleet-style horizontal stepper.
     *
     * @param totalSteps The total number of steps in the stepper.
     * @param currentStep The current active step in the stepper (zero-based index).
     * @param fleetItemContent The composable content for each fleet item.
     */
    class Fleet(
        totalSteps: Int,
        currentStep: Int,
        val fleetItemContent: @Composable (Step, StepState) -> Unit
    ) : HorizontalStepperStyle(totalSteps, currentStep)
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
    class Tab(totalSteps: Int, currentStep: Int) : VerticalStepperStyle(totalSteps, currentStep)

    /**
     * A number-based vertical stepper style.
     *
     * @param totalSteps The total number of steps in the stepper.
     * @param currentStep The current active step in the stepper (zero-based index).
     * @param textStyle The text style for the step numbers.
     */
    class Number(totalSteps: Int, currentStep: Int, val textStyle: TextStyle) :
        VerticalStepperStyle(totalSteps, currentStep)

    /**
     * A number-with-label vertical stepper style.
     *
     * @param totalSteps The total number of steps in the stepper.
     * @param currentStep The current active step in the stepper (zero-based index).
     * @param textStyle The text style for the step numbers.
     * @param labelTextStyle The text style for the step labels.
     */
    class NumberWithLabel(
        totalSteps: Int,
        currentStep: Int,
        val textStyle: TextStyle,
        val labelTextStyle: TextStyle
    ) : VerticalStepperStyle(totalSteps, currentStep)

    /**
     * An icon-based vertical stepper style.
     *
     * @param totalSteps The total number of steps in the stepper.
     * @param currentStep The current active step in the stepper (zero-based index).
     * @param iconSize The size of the icons in the stepper.
     */
    class Icon(totalSteps: Int, currentStep: Int, val iconSize: Dp) :
        VerticalStepperStyle(totalSteps, currentStep)

    /**
     * A number-with-composable-label vertical stepper style.
     *
     * @param totalSteps The total number of steps in the stepper.
     * @param currentStep The current active step in the stepper (zero-based index).
     * @param textStyle The text style for the step numbers.
     * @param labelContent The composable content for the step labels.
     */
    class NumberWithComposableLabel(
        totalSteps: Int,
        currentStep: Int,
        val textStyle: TextStyle,
        val labelContent: @Composable () -> Unit
    ) : VerticalStepperStyle(totalSteps, currentStep)
}
