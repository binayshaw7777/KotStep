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
 * Please contact Binay Shaw, by visiting https://binayshaw.me or https://binay-shaw.onrender.com/ if you need additional information or have any
 * questions or directly reach out to me via mail: binayshaw7777@gmail.com
 *
 * @author Binay Shaw
 *
 */

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.binayshaw7777.kotstep.model.HorizontalStepperStyle
import com.binayshaw7777.kotstep.ui.horizontal.step.RenderHorizontalDashed
import com.binayshaw7777.kotstep.ui.horizontal.step.RenderHorizontalIcon
import com.binayshaw7777.kotstep.ui.horizontal.step.RenderHorizontalNumber
import com.binayshaw7777.kotstep.ui.horizontal.step.RenderHorizontalTab


/**
 * A composable function that renders a horizontal stepper based on the provided style.
 *
 * This function acts as a router, delegating the rendering to specific Composable(s)
 * based on the type of [HorizontalStepperStyle] provided.
 *
 * @param modifier A [Modifier] to be applied to the stepper. Defaults to [Modifier].
 * @param style The [HorizontalStepperStyle] that defines the appearance and behavior of the stepper.
 * @param onStepClick A lambda function that is invoked when a step is clicked. Defaults to an empty lambda.
 *
 * Usage example:
 * ```
 * HorizontalStepper(
 *    style = numberedHorizontal(
 *        totalSteps = 5,
 *        currentStep = 2, // Third step is active
 *        stepStyle = StepStyle(
 *            stepSize = 28.dp,
 *            lineSize = 2.dp
 *            // ... other style properties
 *        )
 *    )
 *) { // Do something }
 * ```
 *
 * @see HorizontalStepperStyle
 * @see RenderHorizontalTab
 * @see RenderHorizontalIcon
 * @see RenderHorizontalNumber
 * @see RenderHorizontalDashed
 */
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