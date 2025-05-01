package com.binayshaw7777.kotstep.v3.component.steps

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.binayshaw7777.kotstep.v3.model.step.Step
import com.binayshaw7777.kotstep.v3.model.step.StepState
import com.binayshaw7777.kotstep.v3.model.style.BorderStyle
import com.binayshaw7777.kotstep.v3.model.style.StepStyle
import com.binayshaw7777.kotstep.v3.util.ExperimentalKotStep

private const val DEFAULT_INDICATOR_SCALE = 0.6f
private const val BORDER_SCALE_FACTOR = 0.1f
private const val STEP_INDICATOR_DESCRIPTION = "Step Indicator"

/**
 * A composable function that represents a step indicator in a multi-step process.
 * It displays the current state of a step, including its title, icon, content, or a default indicator.
 *
 * The StepIndicator provides a visual representation of a step's progress and content within a multi-step workflow.
 * It dynamically adapts its appearance based on the provided [StepState] and [Step] data.
 *
 * The behavior of the StepIndicator is determined by the following logic:
 *
 * 1. **Done State (with Checkmark):**
 *    - If the `stepState` is `StepState.Done` and no title, icon, or content are provided within the `step` lambda,
 *      a checkmark icon (using `Icons.Default.Done`) is displayed in white.
 *
 * 2. **Title Display:**
 *    - If the `step` lambda returns a `Step` object with a non-null and non-empty `title`, that title is displayed as text.
 *    - The text style is determined by the `stepStyle` lambda's `textStyle`.
 *
 * 3. **Icon Display:**
 *    - If the `step` lambda returns a `Step` object with a non-null `icon` (an `ImageVector`), that icon is displayed.
 *
 * 4. **Content Display:**
 *    - If the `step` lambda returns a `Step` object with a non-null `content` (a lambda representing content),
 *
 * 5. **Default Indicator:**
 *    - If all the above are not satisfied, it shows the default indicator.
 *
 * @since 3.0.0
 *
 *  */
@OptIn(ExperimentalKotStep::class)
@Stable
@Composable
internal fun StepIndicator(
    modifier: Modifier = Modifier,
    size: Dp,
    shape: Shape,
    containerColor: Color,
    borderStyle: BorderStyle,
    stepState: () -> StepState,
    step: () -> Step,
    stepStyle: () -> StepStyle,
    showCheckMark: () -> Boolean
) {

    val isDefaultIndicator by remember(step) {
        derivedStateOf { step().title.isNullOrEmpty() && step().icon == null && step().content == null }
    }

    val baseModifier = Modifier
        .size(size)
        .clip(shape)
        .zIndex(1f)
        .semantics { contentDescription = STEP_INDICATOR_DESCRIPTION }

    val styledModifier = if (isDefaultIndicator) {
        baseModifier
            .background(Color.Transparent)
            .border(width = size * BORDER_SCALE_FACTOR, color = containerColor, shape = shape)
    } else {
        baseModifier
            .background(containerColor)
            .border(width = borderStyle.width, color = borderStyle.color, shape = borderStyle.shape)
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = styledModifier
            .then(modifier)
    ) {
        when {
            stepState() == StepState.Done && showCheckMark() -> {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = "Done",
                    modifier = Modifier.size(stepStyle().iconStyle.iconSize),
                    tint = stepStyle().iconStyle.iconTint
                )
            }

            !step().title.isNullOrEmpty() -> {
                Text(
                    text = step().title!!,
                    style = stepStyle().textStyle
                )
            }

            step().icon != null -> {
                Icon(
                    imageVector = step().icon!!,
                    contentDescription = null,
                    modifier = Modifier.size(stepStyle().iconStyle.iconSize)
                )
            }

            step().content != null -> {
                step().content?.invoke()
            }

            else -> {
                DefaultIndicator(size, containerColor, shape)
            }
        }
    }
}

/**
 * Renders the default indicator for a step when no specific content is provided.
 *
 * @param size The size of the indicator.
 * @param color The background color of the indicator.
 * @param shape The shape of the indicator.
 */
@Composable
private fun DefaultIndicator(size: Dp, color: Color, shape: Shape) {
    Box(
        modifier = Modifier
            .size((size.value * DEFAULT_INDICATOR_SCALE).dp)
            .background(color, shape)
    )
}