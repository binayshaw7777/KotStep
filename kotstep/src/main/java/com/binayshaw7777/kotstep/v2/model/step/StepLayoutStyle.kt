package com.binayshaw7777.kotstep.v2.model.step

import androidx.compose.runtime.Immutable

/**
 * [StepLayoutStyle] is an enum class used to define the layout style of the KotStep.
 *
 * @property Vertical: Is a vertical orientation stepper where the step circle is in the top
 * @property Horizontal: Is a horizontal orientation stepper where the step circle is in the start
 *
 * @since 3.0.0
 */
@Immutable
enum class StepLayoutStyle {
//    VerticalCentered,
    Vertical,
//    HorizontalCentered,
    Horizontal,
}