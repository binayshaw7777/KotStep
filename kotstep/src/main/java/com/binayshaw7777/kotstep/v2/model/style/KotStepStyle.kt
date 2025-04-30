package com.binayshaw7777.kotstep.v2.model.style

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.binayshaw7777.kotstep.v2.model.step.StepLayoutStyle
import com.binayshaw7777.kotstep.v2.util.ExperimentalKotStep

/**
 * Defines the style for the KotStep component, providing customization options for layout,
 * padding, checkmark visibility, scroll behavior, state handling, step appearance, and line appearance.
 *
 * @property stepLayoutStyle The layout style for the steps. Defaults to [StepLayoutStyle.Vertical].
 * @property itemPadding The padding around each step item. Defaults to 8.dp.
 * @property showCheckMarkOnDone Whether to show a checkmark icon when a step is marked as done.
 *   Defaults to `true`.
 * @property ignoreCurrentState Whether to ignore the `currentStep` state and display all steps as
 *   enabled. This is useful for displaying a static step flow where the current step
 *   doesn't matter. Defaults to `false`.
 * @property stepStyle The style for the individual steps. Allows customizing the appearance of the step
 *   number, title, and other step-related elements. Defaults to [StepStyles.default].
 * @property lineStyle The style for the lines connecting the steps. Allows customizing the color,
 *   thickness, and other attributes of the lines. Defaults to [LineStyles.default].
 *
 * @since 3.0.0
 */
@ExperimentalKotStep
@Immutable
data class KotStepStyle(
    val stepLayoutStyle: StepLayoutStyle = StepLayoutStyle.Vertical,
    val itemPadding: Dp = 8.dp,
    val showCheckMarkOnDone: Boolean = true,
    val ignoreCurrentState: Boolean = false,
    val stepStyle: StepStyles = StepStyles.default(),
    val lineStyle: LineStyles = LineStyles.default()
)