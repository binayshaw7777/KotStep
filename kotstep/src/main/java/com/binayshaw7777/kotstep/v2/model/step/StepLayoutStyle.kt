package com.binayshaw7777.kotstep.v2.model.step

/**
 * [StepLayoutStyle] is an enum class used to define the layout style of the KotStep.
 *
 * @property VerticalCentered: Is a vertical orientation stepper where the step circle is in the center
 * @property Vertical: Is a vertical orientation stepper where the step circle is in the top
 * @property Horizontal: Is a horizontal orientation stepper where the step circle is in the start
 * @property HorizontalCentered: Is a horizontal orientation stepper where the step circle is in the center
 *
 * @since 2.4.0
 */
enum class StepLayoutStyle {
    VerticalCentered,
    Vertical,
    HorizontalCentered,
    Horizontal,
}