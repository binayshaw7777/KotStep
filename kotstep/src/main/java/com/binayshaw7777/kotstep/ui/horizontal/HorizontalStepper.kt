package com.binayshaw7777.kotstep.ui.horizontal

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
 * Please contact Binay Shaw, by visiting https://binay-shaw.onrender.com/ if you need additional information or have any
 * questions or directly reach out to me via mail: binayshaw7777@gmail.com
 *
 * @author Binay Shaw
 *
 */

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.binayshaw7777.kotstep.model.HorizontalStepperStyle
import com.binayshaw7777.kotstep.ui.horizontal.step.RenderHorizontalDashed
import com.binayshaw7777.kotstep.ui.horizontal.step.RenderHorizontalFleet
import com.binayshaw7777.kotstep.ui.horizontal.step.RenderHorizontalIcon
import com.binayshaw7777.kotstep.ui.horizontal.step.RenderHorizontalNumber
import com.binayshaw7777.kotstep.ui.horizontal.step.RenderHorizontalTab



/**
 *
 * This composable function renders a horizontal stepper with different styles based on the provided [HorizontalStepperStyle].
 * It provides visual feedback on the progress of a multi-step process.
 *
 * This function is now deprecated and it's recommended to use `KotStep` for new implementations.
 *
 * @param modifier Modifier for styling and layout customization of the stepper.
 * @param style The [HorizontalStepperStyle] that determines the appearance and behavior of the stepper.
 *              It can be one of the following:
 *              - [HorizontalStepperStyle.Tab]: Renders a stepper with tabs for each step.
 *              - [HorizontalStepperStyle.Fleet]: Renders a stepper with a "fleet" or animated progress bar.
 *              - [HorizontalStepperStyle.Icon]: Renders a stepper with custom icons for each step.
 *              - [HorizontalStepperStyle.Number]: Renders a stepper with numbered steps.
 *              - [HorizontalStepperStyle.Dashed]: Renders a stepper with a dashed line between steps.
 * @param onStepClick Callback function invoked when a step is clicked. It provides the index of the clicked step.
 *
 * @see HorizontalStepperStyle
 * @see com.binayshaw7777.kotstep.v2.KotStep
 */
//@Deprecated(
//    message = "HorizontalStepper is deprecated since KotStep 3.0.0. Please use the try the new KotStep Component.",
//    replaceWith = ReplaceWith(
//        expression = "KotStep(...)",
//        imports = ["com.binayshaw7777.kotstep.v2.KotStep"]
//    ),
//    level = DeprecationLevel.WARNING
//)
@Composable
fun HorizontalStepper(
    modifier: Modifier = Modifier,
    style: HorizontalStepperStyle,
    onStepClick: (Int) -> Unit = {}
) {
    when (style) {
        is HorizontalStepperStyle.Tab -> RenderHorizontalTab(
            modifier = modifier,
            totalSteps = style.totalSteps,
            currentStep = style.currentStep,
            stepStyle = style.stepStyle,
            onStepClick = { onStepClick(it) }
        )

        is HorizontalStepperStyle.Fleet -> RenderHorizontalFleet(
            modifier = modifier,
            totalSteps = style.totalSteps,
            currentStep = style.currentStep,
            stepStyle = style.stepStyle,
            duration = style.duration,
            isPlaying = style.isPlaying,
            onStepComplete = style.onStepComplete,
            onStepClick = { onStepClick(it) },
        )

        is HorizontalStepperStyle.Icon -> RenderHorizontalIcon(
            modifier = modifier,
            totalSteps = style.totalSteps,
            currentStep = style.currentStep,
            icons = style.icons,
            stepStyle = style.stepStyle,
            onStepClick = { onStepClick(it) }
        )

        is HorizontalStepperStyle.Number -> RenderHorizontalNumber(
            modifier = modifier,
            totalSteps = style.totalSteps,
            currentStep = style.currentStep,
            stepStyle = style.stepStyle,
            onStepClick = { onStepClick(it) }
        )

        is HorizontalStepperStyle.Dashed -> RenderHorizontalDashed(
            modifier = modifier,
            totalSteps = style.totalSteps,
            currentStep = style.currentStep,
            stepStyle = style.stepStyle,
            onStepClick = { onStepClick(it) }
        )

//        is HorizontalStepperStyle.Fleet -> RenderHorizontalFleet(
//            style.totalSteps,
//            style.currentStep,
//            style.fleetItemContent
//        )
    }
}