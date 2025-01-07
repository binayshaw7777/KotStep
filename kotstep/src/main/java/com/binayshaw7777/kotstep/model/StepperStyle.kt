package com.binayshaw7777.kotstep.model

/**
 * Copyright 2023 binayshaw7777
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Please contact Binay Shaw, by visiting https://binayshaw.me or https://binay-shaw.onrender.com/ if you need additional information or have any
 * questions or directly reach out to me via mail: binayshaw7777@gmail.com
 *
 * @author Binay Shaw
 *
 */

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector


typealias StepIndex = Int
typealias StepLabel = (@Composable () -> Unit)?

/**
 * Base class for all stepper styles.
 *
 * @property totalSteps The total number of steps in the stepper.
 * @property currentStep The current active step in the stepper. (-1 .. totalSteps).
 * @property onStepClick Returns the index of the step clicked or tapped.
 */
sealed class BaseStepperStyle(
    val totalSteps: Int,
    val currentStep: Number,
    val onStepClick: (StepIndex) -> Unit = {}
)

/**
 * Sealed class representing various styles for horizontal steppers.
 *
 * @property totalSteps The total number of steps in the stepper.
 * @property currentStep The current active step in the stepper (-1 .. totalSteps).
 * @property onStepClick Returns the index of the step clicked or tapped.
 */
sealed class HorizontalStepperStyle(
    totalSteps: Int,
    currentStep: kotlin.Number,
    onStepClick: (StepIndex) -> Unit
) :
    BaseStepperStyle(totalSteps, currentStep, onStepClick) {

    /**
     * Represents a tab-style horizontal stepper.
     *
     * @property totalSteps The total number of steps in the stepper.
     * @property currentStep The current active step in the stepper (-1 .. totalSteps).
     * @property stepStyle The style for the step numbers.
     */
    class Tab(totalSteps: Int, currentStep: kotlin.Number, val stepStyle: StepStyle) :
        HorizontalStepperStyle(totalSteps, currentStep, {})


    /**
     * Represents a fleet-style horizontal stepper.
     *
     * @property totalSteps The total number of steps in the stepper.
     * @property currentStep The current active step in the stepper (-1 .. totalSteps).
     * @property stepStyle The style for the step numbers.
     * @property duration A list of durations for each step in the fleet.
     */
    class Fleet(
        totalSteps: Int,
        currentStep: kotlin.Number,
        val stepStyle: StepStyle,
        val duration: List<Long>,
        var isPlaying: Boolean = true,
        val onStepComplete: (Int) -> Unit = {}
    ) :
        HorizontalStepperStyle(totalSteps, currentStep, {})


    /**
     * Represents an icon-based horizontal stepper.
     *
     * @property totalSteps The total number of steps in the stepper.
     * @property currentStep The current active step in the stepper (-1 .. totalSteps).
     * @property icons The list of icons to be displayed in the stepper.
     * @property stepStyle The style for the step numbers.
     */
    class Icon(
        totalSteps: Int,
        currentStep: kotlin.Number,
        val icons: List<ImageVector>,
        val stepStyle: StepStyle
    ) :
        HorizontalStepperStyle(totalSteps, currentStep, {})

    /**
     * Represents a number-based horizontal stepper style.
     *
     * @property totalSteps The total number of steps in the stepper.
     * @property currentStep The current active step in the stepper (-1 .. totalSteps).
     * @property stepStyle The style for the step numbers.
     */
    class Number(totalSteps: Int, currentStep: kotlin.Number, val stepStyle: StepStyle) :
        HorizontalStepperStyle(totalSteps, currentStep, {})


    /**
     * Represents a dashed horizontal stepper style.
     *
     * @property totalSteps The total number of steps in the stepper.
     * @property currentStep The current active step in the stepper (-1 .. totalSteps).
     * @property stepStyle The style for the step numbers.
     */
    class Dashed(totalSteps: Int, currentStep: kotlin.Number, val stepStyle: StepStyle) :
        HorizontalStepperStyle(totalSteps, currentStep, {})

}


/**
 * Sealed class representing various styles for vertical steppers.
 *
 * @property totalSteps The total number of steps in the stepper.
 * @property currentStep The current active step in the stepper (-1 .. totalSteps).
 * @property onStepClick Returns the index of the step clicked or tapped.
 */
sealed class VerticalStepperStyle(
    totalSteps: Int,
    currentStep: kotlin.Number,
    onStepClick: (StepIndex) -> Unit
) :
    BaseStepperStyle(totalSteps, currentStep, onStepClick) {

    /**
     * Represents a tab-style vertical stepper.
     *
     * @property totalSteps The total number of steps in the stepper.
     * @property currentStep The current active step in the stepper (-1 .. totalSteps).
     * @property stepStyle The style for the step numbers.
     */
    class Tab(totalSteps: Int, currentStep: kotlin.Number, val stepStyle: StepStyle) :
        VerticalStepperStyle(totalSteps, currentStep, {})

    /**
     * Represent a number-based vertical stepper style.
     *
     * @property totalSteps The total number of steps in the stepper.
     * @property currentStep The current active step in the stepper (-1 .. totalSteps).
     * @property stepStyle The style for the step numbers.
     */
    class Number(totalSteps: Int, currentStep: kotlin.Number, val stepStyle: StepStyle) :
        VerticalStepperStyle(totalSteps, currentStep, {})


    /**
     * Represent an icon-based vertical stepper style.
     *
     * @property totalSteps The total number of steps in the stepper.
     * @property currentStep The current active step in the stepper (-1 .. totalSteps).
     * @property icons The list of icons to be displayed in the stepper.
     */
    class Icon(
        totalSteps: Int, currentStep: kotlin.Number,
        val icons: List<ImageVector>,
        val stepStyle: StepStyle,
    ) :
        VerticalStepperStyle(totalSteps, currentStep, {})


    /**
     * Represents a numbered vertical stepper with trailingLabels.
     *
     * @property totalSteps The total number of steps in the stepper.
     * @property currentStep The current active step in the stepper (-1 .. totalSteps).
     * @property stepStyle The style for the step numbers.
     * @property trailingLabels The composable content for the supporting content.
     *
     */
    class NumberWithLabel(
        totalSteps: Int, currentStep: kotlin.Number,
        val stepStyle: StepStyle,
        val trailingLabels: List<StepLabel>
    ) :
        VerticalStepperStyle(totalSteps, currentStep, {})


    /**
     * Represents an icon-based vertical stepper with trailingLabels.
     *
     * @property totalSteps The total number of steps in the stepper.
     * @property currentStep The current active step in the stepper (-1 .. totalSteps).
     * @property icons The list of icons to be displayed in the stepper.
     * @property trailingLabels The composable content for the supporting content.
     * @property stepStyle The style for the step numbers.
     *
     */
    class IconWithLabel(
        totalSteps: Int,
        currentStep: kotlin.Number,
        val icons: List<ImageVector>,
        val stepStyle: StepStyle,
        val trailingLabels: List<StepLabel>
    ) :
        VerticalStepperStyle(totalSteps, currentStep, {})

    /**
     * Represents a tab-style vertical stepper with trailingLabels.
     *
     * @property totalSteps The total number of steps in the stepper.
     * @property currentStep The current active step in the stepper (-1 .. totalSteps).
     * @property stepStyle The style configuration for each step.
     * @property trailingLabels List of optional composable functions to render trailingLabels for each step.
     */
    class TabWithLabel(
        totalSteps: Int, currentStep: kotlin.Number,
        val stepStyle: StepStyle,
        val trailingLabels: List<StepLabel>
    ) :
        VerticalStepperStyle(totalSteps, currentStep, {})
}


/**
 * Creates a horizontal stepper with a dashed line style.
 *
 * @param totalSteps The total number of steps in the stepper.
 *                   Must be greater than 0.
 * @param currentStep The current active step in the stepper.
 *                    Must be in the range [-1, N], where N is the total number of steps.
 *                    A value of -1 indicates that no step is currently active.
 *                    Values 1 to N represent the active step number.
 * @param stepStyle A StepStyle object that defines the visual appearance of the steps.
 *                  Uses default values if not provided.
 * @return A HorizontalStepperStyle.Dashed object representing the configured horizontal stepper.
 *
 * @throws IllegalArgumentException if currentStep is out of the valid range or if totalSteps is less than 1.
 *
 * This function creates a horizontal stepper with the following features:
 * - Dashed line connecting the steps
 * - Customizable step appearance through StepStyle
 * - Ability to highlight the current active step
 *
 * Usage Example:
 * ```
 * HorizontalStepper(
 *     style = dashed(
 *         totalSteps = 5,
 *         currentStep = 2, // Third step is active
 *         stepStyle = StepStyle(
 *             stepSize = 28.dp,
 *             lineSize = 2.dp
 *             // ... other style properties
 *         )
 *     )
 * )
 * ```
 *
 * @see HorizontalStepperStyle.Dashed
 * @see StepStyle
 *
 * @since 2.0.2
 */
fun dashed(
    totalSteps: Int,
    currentStep: Number,
    stepStyle: StepStyle = StepStyle()
): HorizontalStepperStyle.Dashed {
    return HorizontalStepperStyle.Dashed(
        totalSteps = totalSteps,
        currentStep = currentStep,
        stepStyle = stepStyle
    )
}


/**
 * Creates a horizontal stepper with a tab style.
 *
 * @param totalSteps The total number of steps in the stepper.
 *                   Must be greater than 0.
 * @param currentStep The current active step in the stepper.
 *                    Must be in the range [-1, N], where N is the total number of steps.
 *                    A value of -1 indicates that no step is currently active.
 *                    Values 1 to N represent the active step number.
 * @param stepStyle A StepStyle object that defines the visual appearance of the steps.
 *                  Uses default values if not provided.
 * @return A HorizontalStepperStyle.Tab object representing the configured horizontal stepper.
 *
 * @throws IllegalArgumentException if currentStep is out of the valid range or if totalSteps is less than 1.
 *
 * This function creates a horizontal stepper with the following features:
 * - Tab-style steps
 * - Customizable step appearance through StepStyle
 * - Ability to highlight the current active step
 *
 * Usage Example:
 * ```
 * HorizontalStepper(
 *     style = tabHorizontal(
 *         totalSteps = 5,
 *         currentStep = 2, // Third step is active
 *         stepStyle = StepStyle(
 *             stepSize = 28.dp,
 *             lineSize = 2.dp
 *             // ... other style properties
 *         )
 *     )
 * )
 * ```
 *
 * @see HorizontalStepperStyle.Tab
 * @see StepStyle
 *
 * @since 2.0.2
 */
fun tabHorizontal(
    totalSteps: Int,
    currentStep: Number,
    stepStyle: StepStyle = StepStyle()
): HorizontalStepperStyle.Tab {
    return HorizontalStepperStyle.Tab(
        totalSteps = totalSteps,
        currentStep = currentStep,
        stepStyle = stepStyle
    )
}


/**
 * Creates a vertical stepper with a tab style.
 *
 * @param totalSteps The total number of steps in the stepper.
 *                   Must be greater than 0.
 * @param currentStep The current active step in the stepper.
 *                    Must be in the range [-1, N], where N is the total number of steps.
 *                    A value of -1 indicates that no step is currently active.
 *                    Values 1 to N represent the active step number.
 * @param stepStyle A StepStyle object that defines the visual appearance of the steps.
 *                  Uses default values if not provided.
 * @return A VerticalStepperStyle.Tab object representing the configured vertical stepper.
 *
 * @throws IllegalArgumentException if currentStep is out of the valid range or if totalSteps is less than 1.
 *
 * This function creates a vertical stepper with the following features:
 * - Tab-style steps
 * - Customizable step appearance through StepStyle
 * - Ability to highlight the current active step
 *
 * Usage Example:
 * ```
 * VerticalStepper(
 *     style = tabVertical(
 *         totalSteps = 5,
 *         currentStep = 2, // Third step is active
 *         stepStyle = StepStyle(
 *             stepSize = 28.dp,
 *             lineSize = 2.dp
 *             // ... other style properties
 *         )
 *     )
 * )
 * ```
 *
 * @see VerticalStepperStyle.Tab
 * @see StepStyle
 *
 * @since 2.0.2
 */
fun tabVertical(
    totalSteps: Int,
    currentStep: Number,
    stepStyle: StepStyle = StepStyle()
): VerticalStepperStyle.Tab {
    return VerticalStepperStyle.Tab(
        totalSteps = totalSteps,
        currentStep = currentStep,
        stepStyle = stepStyle
    )
}


/**
 * Creates a horizontal stepper with a fleet style.
 *
 * @param totalSteps The total number of steps in the stepper.
 *                  Must be greater than 0.
 * @param currentStep The current active step in the stepper.
 *                  Must be in the range [-1, N], where N is the total number of steps.
 *                  A value of -1 indicates that no step is currently active.
 *                  Values 1 to N represent the active step number.
 * @param stepStyle A StepStyle object that defines the visual appearance of the steps.
 *                 Uses default values if not provided.
 * @param duration A list of durations for each step in the fleet.
 *                Must have the same size as totalSteps.
 *                Each duration must be greater than 0.
 *
 *  @return A HorizontalStepperStyle.Fleet object representing the configured horizontal stepper.
 *
 *  @throws IllegalArgumentException if currentStep is out of the valid range, if totalSteps is less than 1,
 *
 *  This function creates a horizontal stepper with the following features:
 *  - Fleet-style steps
 *  - Customizable step appearance through StepStyle
 *  - Ability to highlight the current active step
 *  - Customizable duration for each step in the fleet
 *
 *  Usage Example:
 *  ```
 *  HorizontalStepper(
 *    style = fleet(
 *      totalSteps = 5,
 *      currentStep = 2, // Third step is active
 *      stepStyle = StepStyle(
 *        stepSize = 28.dp,
 *        lineSize = 2.dp
 *        // ... other style properties
 *      ),
 *      duration = listOf(1000, 2000, 3000, 4000, 5000)
 *    )
 *  )
 *  ```
 *
 * @see HorizontalStepperStyle.Fleet
 * @see StepStyle
 * @see List
 */
fun fleet(
    totalSteps: Int,
    currentStep: Number,
    stepStyle: StepStyle = StepStyle(),
    duration: List<Long>,
    isPlaying: Boolean = true,
    onStepComplete: (Int) -> Unit = {}
): HorizontalStepperStyle.Fleet {
    return HorizontalStepperStyle.Fleet(
        totalSteps = totalSteps,
        currentStep = currentStep,
        stepStyle = stepStyle,
        duration = duration,
        isPlaying = isPlaying,
        onStepComplete = onStepComplete
    )
}

/**
 * Creates a vertical stepper with a tab style and optional trailingLabels.
 *
 * @param totalSteps The total number of steps in the stepper.
 *                   Must be greater than 0.
 * @param currentStep The current active step in the stepper.
 *                    Must be in the range [-1, N], where N is the total number of steps.
 *                    A value of -1 indicates that no step is currently active.
 *                    Values 1 to N represent the active step number.
 * @param trailingLabels A list of optional composable functions that render the trailingLabels for each step.
 *               Should have the same size as totalSteps.
 * @param stepStyle A StepStyle object that defines the visual appearance of the steps.
 *                  Uses default values if not provided.
 * @return A VerticalStepperStyle.TabWithLabel object representing the configured vertical stepper.
 *
 * @throws IllegalArgumentException if currentStep is out of the valid range, if totalSteps is less than 1,
 *         or if the size of trailingLabels doesn't match totalSteps.
 *
 * This function creates a vertical stepper with the following features:
 * - Tab-style steps
 * - Optional trailingLabels for each step
 * - Customizable step appearance through StepStyle
 * - Ability to highlight the current active step
 *
 * Usage Example:
 * ```
 * val trailingLabels = listOf<(@Composable () -> Unit)?>(
 *     { Text(text = "Home") },
 *     ...
 *     { Text(text = "Complete") }
 * )
 *
 * VerticalStepper(
 *     style = tabVerticalWithLabel(
 *         totalSteps = 5,
 *         currentStep = 2, // Third step is active
 *         trailingLabels = trailingLabels,
 *         stepStyle = StepStyle(
 *             stepSize = 28.dp,
 *             lineSize = 2.dp
 *             // ... other style properties
 *         )
 *     )
 * )
 * ```
 *
 * @see VerticalStepperStyle.TabWithLabel
 * @see StepStyle
 * @see List
 *
 * @since 2.1.0
 */
fun tabVerticalWithLabel(
    totalSteps: Int,
    currentStep: Number,
    trailingLabels: List<(@Composable () -> Unit)?>,
    stepStyle: StepStyle = StepStyle()
): VerticalStepperStyle.TabWithLabel {
    return VerticalStepperStyle.TabWithLabel(
        totalSteps = totalSteps,
        currentStep = currentStep,
        stepStyle = stepStyle,
        trailingLabels = trailingLabels
    )
}


/**
 * Creates a horizontal stepper with numbered steps.
 *
 * @param totalSteps The total number of steps in the stepper.
 *                   Must be greater than 0.
 * @param currentStep The current active step in the stepper.
 *                    Must be in the range [-1, N], where N is the total number of steps.
 *                    A value of -1 indicates that no step is currently active.
 *                    Values 1 to N represent the active step number.
 * @param stepStyle A StepStyle object that defines the visual appearance of the steps.
 *                  Uses default values if not provided.
 * @return A HorizontalStepperStyle.Number object representing the configured horizontal stepper.
 *
 * @throws IllegalArgumentException if currentStep is out of the valid range or if totalSteps is less than 1.
 *
 * This function creates a horizontal stepper with the following features:
 * - Numbered steps from 1 to N
 * - Customizable step appearance through StepStyle
 * - Ability to highlight the current active step
 *
 * Usage Example:
 * ```
 * HorizontalStepper(
 *     style = numberedHorizontal(
 *         totalSteps = 5,
 *         currentStep = 2, // Third step is active
 *         stepStyle = StepStyle(
 *             stepSize = 28.dp,
 *             lineSize = 2.dp
 *             // ... other style properties
 *         )
 *     )
 * )
 * ```
 *
 * @see HorizontalStepperStyle.Number
 * @see StepStyle
 *
 * @since 2.0.2
 */
fun numberedHorizontal(
    totalSteps: Int,
    currentStep: Number,
    stepStyle: StepStyle = StepStyle()
): HorizontalStepperStyle.Number {
    return HorizontalStepperStyle.Number(
        totalSteps = totalSteps,
        currentStep = currentStep,
        stepStyle = stepStyle
    )
}


/**
 * Creates a vertical stepper with numbered steps.
 *
 * @param totalSteps The total number of steps in the stepper.
 *                   Must be greater than 0.
 * @param currentStep The current active step in the stepper.
 *                    Must be in the range [-1, N], where N is the total number of steps.
 *                    A value of -1 indicates that no step is currently active.
 *                    Values 1 to N represent the active step number.
 * @param stepStyle A StepStyle object that defines the visual appearance of the steps.
 *                  Uses default values if not provided.
 * @return A VerticalStepperStyle.Number object representing the configured vertical stepper.
 *
 * @throws IllegalArgumentException if currentStep is out of the valid range or if totalSteps is less than 1.
 *
 * This function creates a vertical stepper with the following features:
 * - Numbered steps from 1 to N
 * - Customizable step appearance through StepStyle
 * - Ability to highlight the current active step
 *
 * Usage Example:
 * ```
 * VerticalStepper(
 *     style = numberedVertical(
 *         totalSteps = 5,
 *         currentStep = 2, // Third step is active
 *         stepStyle = StepStyle(
 *             stepSize = 28.dp,
 *             lineSize = 2.dp
 *             // ... other style properties
 *         )
 *     )
 * )
 * ```
 *
 * @see VerticalStepperStyle.Number
 * @see StepStyle
 *
 * @since 2.0.2
 */
fun numberedVertical(
    totalSteps: Int,
    currentStep: Number,
    stepStyle: StepStyle = StepStyle()
): VerticalStepperStyle.Number {
    return VerticalStepperStyle.Number(
        totalSteps = totalSteps,
        currentStep = currentStep,
        stepStyle = stepStyle
    )
}

/**
 * Creates a horizontal stepper with custom icons.
 *
 * @param currentStep The current active step in the stepper.
 *                    Must be in the range [-1, N], where N is the total number of steps.
 *                    A value of -1 indicates that no step is currently active.
 *                    Values 1 to N represent the active step number.
 * @param icons A list of ImageVector objects representing the icons for each step.
 *              The size of this list determines the total number of steps.
 * @param stepStyle A StepStyle object that defines the visual appearance of the steps.
 *                  Uses default values if not provided.
 * @return A HorizontalStepperStyle.Icon object representing the configured horizontal stepper.
 *
 * @throws IllegalArgumentException if currentStep is out of the valid range or if the icons list is empty.
 *
 * This function creates a horizontal stepper with the following features:
 * - Custom icons for each step
 * - Customizable step appearance through StepStyle
 * - Ability to highlight the current active step
 *
 * Usage Example:
 * ```
 * val icons = listOf(
 *     Icons.Default.Home,
 *     ...
 *     Icons.Default.Done
 * )
 *
 * HorizontalStepper(
 *     style = iconHorizontal(
 *         currentStep = 2, // Third step is active
 *         icons = icons,
 *         stepStyle = StepStyle(
 *             stepSize = 28.dp,
 *             lineSize = 2.dp
 *             // ... other style properties
 *         )
 *     )
 * )
 * ```
 *
 * @see HorizontalStepperStyle.Icon
 * @see StepStyle
 * @see ImageVector
 * @see List
 *
 * @since 2.0.2
 */
fun iconHorizontal(
    currentStep: Number,
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
 * Creates a vertical stepper with custom icons.
 *
 * @param currentStep The current active step in the stepper.
 *                    Must be in the range [-1, N], where N is the total number of steps.
 *                    A value of -1 indicates that no step is currently active.
 *                    Values 1 to N represent the active step number.
 * @param icons A list of ImageVector objects representing the icons for each step.
 *              The size of this list determines the total number of steps.
 * @param stepStyle A StepStyle object that defines the visual appearance of the steps.
 *                  Uses default values if not provided.
 * @return A VerticalStepperStyle.Icon object representing the configured vertical stepper.
 *
 * @throws IllegalArgumentException if currentStep is out of the valid range or if the icons list is empty.
 *
 * This function creates a vertical stepper with the following features:
 * - Custom icons for each step
 * - Customizable step appearance through StepStyle
 * - Ability to highlight the current active step
 *
 * Usage Example:
 * ```
 * val icons = listOf(
 *    Icons.Default.Home,
 *    ...
 *    Icons.Default.Done
 * )
 *
 * VerticalStepper(
 *    style = iconVertical(
 *    currentStep = 2, // Third step is active
 *    icons = icons,
 *    stepStyle = StepStyle(
 *      stepSize = 28.dp,
 *      lineSize = 2.dp
 *      // ... other style properties
 *      )
 *    )
 * )
 * ```
 *
 * @see VerticalStepperStyle.Icon
 * @see StepStyle
 * @see ImageVector
 * @see List
 *
 * @since 2.0.2
 */
fun iconVertical(
    currentStep: Number,
    icons: List<ImageVector>,
    stepStyle: StepStyle = StepStyle(),
): VerticalStepperStyle.Icon {
    return VerticalStepperStyle.Icon(
        totalSteps = icons.size,
        currentStep = currentStep,
        icons = icons,
        stepStyle = stepStyle
    )
}

/**
 * Creates a composable function that renders a vertical stepper with icons and optional trailingLabels.
 *
 * @param currentStep The current active step in the stepper.
 *                    Must be in the range [-1, N], where N is the total number of steps.
 *                    A value of -1 indicates that no step is currently active.
 *                    Values 1 to N represent the active step number.
 * @param icons A list of ImageVector objects representing the icons for each step.
 *              The size of this list determines the default value for totalSteps.
 * @param trailingLabels A list of optional composable functions that render the trailingLabels for each step.
 *               Should have the same size as the icons list.
 * @param totalSteps The total number of steps in the stepper. Defaults to the size of the icons list.
 *                   Must be greater than 0 and equal to the size of the icons list.
 * @param stepStyle A StepStyle object that defines the visual appearance of the steps.
 *                  Uses default values if not provided.
 * @return A VerticalStepperStyle.IconWithLabel object representing the configured vertical stepper.
 *
 * @throws IllegalArgumentException if currentStep is out of the valid range, if totalSteps is less than 1,
 *         or if the sizes of icons and totalSteps do not match.
 *
 * This function creates a vertical stepper with the following features:
 * - Custom icons for each step
 * - Optional trailingLabels for each step
 * - Customizable step appearance through StepStyle
 * - Ability to highlight the current active step, including a "no active step" state
 *
 * Usage Example:
 * ```
 * val icons = listOf(
 *     Icons.Default.Home,
 *     ...
 *     Icons.Default.Done
 * )
 *
 * val trailingLabels = listOf<(@Composable () -> Unit)?>(
 *     { Text(text = "Home") },
 *     ...
 *     { Text(text = "Complete") }
 * )
 *
 * VerticalStepper(
 *     style = iconVerticalWithLabel(
 *         currentStep = 2, // Third step is active
 *         icons = icons, // Determines totalSteps
 *         trailingLabels = trailingLabels, // List size must be <= totalSteps
 *         stepStyle = StepStyle(
 *             stepSize = 28.dp,
 *             lineSize = 8.dp
 *             // ... other style properties
 *         )
 *     )
 * )
 * ```
 *
 * @see VerticalStepperStyle.IconWithLabel
 * @see StepStyle
 * @see ImageVector
 * @see List
 *
 * @since 2.1.0
 */
fun iconVerticalWithLabel(
    currentStep: Number,
    icons: List<ImageVector>,
    trailingLabels: List<(@Composable () -> Unit)?>,
    totalSteps: Int = icons.size,
    stepStyle: StepStyle = StepStyle()
): VerticalStepperStyle.IconWithLabel {
    return VerticalStepperStyle.IconWithLabel(
        totalSteps = totalSteps,
        currentStep = currentStep,
        stepStyle = stepStyle,
        icons = icons,
        trailingLabels = trailingLabels
    )
}

/**
 * Creates a composable function that renders a vertical stepper with numbered steps and optional trailingLabels.
 *
 * @param currentStep The current active step in the stepper.
 *                    Must be in the range [-1, N], where N is the total number of steps.
 *                    A value of -1 indicates that no step is currently active.
 *                    Values 1 to N represent the active step number.
 * @param trailingLabels A list of optional composable functions that render the trailingLabels for each step.
 *               The size of this list determines the default value for totalSteps.
 * @param totalSteps The total number of steps in the stepper. Defaults to the size of the trailingLabels list.
 *                   Must be greater than 0.
 * @param stepStyle A StepStyle object that defines the visual appearance of the steps.
 *                  Uses default values if not provided.
 * @return A VerticalStepperStyle.NumberWithLabel object representing the configured vertical stepper.
 *
 * @throws IllegalArgumentException if currentStep is out of the valid range or if totalSteps is less than 1.
 *
 * This function creates a vertical stepper with the following features:
 * - Numbered steps from 1 to n, where n is the total number of steps
 * - Optional trailingLabels for each step
 * - Customizable step appearance through StepStyle
 * - Ability to highlight the current active step
 *
 * Usage Example:
 * ```
 * val trailingLabels = listOf<(@Composable () -> Unit)?>(
 *     { Text(text = "Step 1") },
 *     ...
 *     { Text(text = "Step 5") }
 * )
 *
 * VerticalStepper(
 *     style = numberedVerticalWithLabel(
 *         totalSteps = 5,
 *         currentStep = 2, // Third step is active
 *         trailingLabels = trailingLabels, // List size must be <= totalSteps
 *         stepStyle = StepStyle(
 *             stepSize = 28.dp,
 *             lineSize = 8.dp
 *             // ... other style properties
 *         )
 *     )
 * )
 * ```
 *
 * @see VerticalStepperStyle.NumberWithLabel
 * @see StepStyle
 * @see List
 *
 * @since 2.1.0
 */
fun numberedVerticalWithLabel(
    currentStep: Number,
    trailingLabels: List<(@Composable () -> Unit)?>,
    totalSteps: Int = trailingLabels.size,
    stepStyle: StepStyle = StepStyle()
): VerticalStepperStyle.NumberWithLabel {
    return VerticalStepperStyle.NumberWithLabel(
        totalSteps = totalSteps,
        currentStep = currentStep,
        stepStyle = stepStyle,
        trailingLabels = trailingLabels
    )
}