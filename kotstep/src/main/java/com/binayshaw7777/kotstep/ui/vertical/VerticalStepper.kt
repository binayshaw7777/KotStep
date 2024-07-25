package com.binayshaw7777.kotstep.ui.vertical

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
import com.binayshaw7777.kotstep.model.VerticalStepperStyle
import com.binayshaw7777.kotstep.ui.vertical.step.RenderVerticalIcon
import com.binayshaw7777.kotstep.ui.vertical.step.RenderVerticalIconWithLabel
import com.binayshaw7777.kotstep.ui.vertical.step.RenderVerticalNumber
import com.binayshaw7777.kotstep.ui.vertical.step.RenderVerticalNumberWithLabel
import com.binayshaw7777.kotstep.ui.vertical.step.RenderVerticalTab
import com.binayshaw7777.kotstep.ui.vertical.step.RenderVerticalTabWithLabel


/**
 * A composable function that renders a vertical stepper based on the provided style.
 *
 * This function acts as a router, delegating the rendering to specific Composable(s)
 * based on the type of [VerticalStepperStyle] provided.
 *
 * @param modifier A [Modifier] to be applied to the stepper. Defaults to [Modifier].
 * @param style The [VerticalStepperStyle] that defines the appearance and behavior of the stepper.
 * @param onStepClick A lambda function that is invoked when a step is clicked. Defaults to an empty lambda.
 *
 * Usage example:
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
 * ) { // Do something }
 * ```
 *
 * @see VerticalStepperStyle
 * @see RenderVerticalTab
 * @see RenderVerticalIcon
 * @see RenderVerticalNumber
 * @see RenderVerticalNumberWithLabel
 * @see RenderVerticalIconWithLabel
 * @see RenderVerticalTabWithLabel
 */
@Composable
fun VerticalStepper(
    modifier: Modifier = Modifier,
    style: VerticalStepperStyle,
    onStepClick: (Int) -> Unit = {}
) {
    when (style) {

        is VerticalStepperStyle.Tab -> RenderVerticalTab(
            modifier = modifier,
            totalSteps = style.totalSteps,
            currentStep = style.currentStep,
            stepStyle = style.stepStyle,
            onStepClick = { onStepClick(it) }
        )

        is VerticalStepperStyle.Icon -> RenderVerticalIcon(
            modifier = modifier,
            totalSteps = style.totalSteps,
            currentStep = style.currentStep,
            icons = style.icons,
            stepStyle = style.stepStyle,
            onStepClick = { onStepClick(it) }
        )

        is VerticalStepperStyle.Number -> RenderVerticalNumber(
            modifier = modifier,
            totalSteps = style.totalSteps,
            currentStep = style.currentStep,
            stepStyle = style.stepStyle,
            onStepClick = { onStepClick(it) }
        )

        is VerticalStepperStyle.NumberWithLabel -> RenderVerticalNumberWithLabel(
            modifier = modifier,
            totalSteps = style.totalSteps,
            currentStep = style.currentStep,
            trailingLabels = style.trailingLabels,
            stepStyle = style.stepStyle,
            onStepClick = { onStepClick(it) }
        )

        is VerticalStepperStyle.IconWithLabel -> RenderVerticalIconWithLabel(
            modifier = modifier,
            totalSteps = style.totalSteps,
            currentStep = style.currentStep,
            icons = style.icons,
            trailingLabels = style.trailingLabels,
            stepStyle = style.stepStyle,
            onStepClick = { onStepClick(it) }
        )

        is VerticalStepperStyle.TabWithLabel -> RenderVerticalTabWithLabel(
            modifier = modifier,
            totalSteps = style.totalSteps,
            currentStep = style.currentStep,
            trailingLabels = style.trailingLabels,
            stepStyle = style.stepStyle,
            onStepClick = { onStepClick(it) }
        )
    }
}