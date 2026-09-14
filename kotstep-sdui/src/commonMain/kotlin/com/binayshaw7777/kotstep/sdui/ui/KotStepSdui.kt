package com.binayshaw7777.kotstep.sdui.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.binayshaw7777.kotstep.sdui.model.*
import com.binayshaw7777.kotstep.sdui.parser.SduiParser
import com.binayshaw7777.kotstep.sdui.parser.SduiStyleMapper
import com.binayshaw7777.kotstep.sdui.resolver.*
import com.binayshaw7777.kotstep.sdui.state.SduiStateManager
import com.binayshaw7777.kotstep.sdui.state.computeCurrentStepFloat
import com.binayshaw7777.kotstep.v3.KotStep
import com.binayshaw7777.kotstep.v3.util.ExperimentalKotStep

/**
 * 1-line entry point: Renders a Server-Driven Stepper directly from a JSON string.
 */
@OptIn(ExperimentalKotStep::class)
@Composable
fun KotStepSdui(
    json: String,
    modifier: Modifier = Modifier,
    iconResolver: SduiIconResolver = DefaultSduiIconResolver,
    contentResolver: SduiContentResolver = DefaultSduiContentResolver,
    colorResolver: SduiColorResolver = DefaultSduiColorResolver,
    onStepClick: ((stepId: String) -> Unit)? = null,
    onAction: ((SduiAction) -> Unit)? = null
) {
    val flow = remember(json) { SduiParser.parseFlow(json) }
    val manager = remember(flow) { SduiStateManager(flow) }

    KotStepSdui(
        manager = manager,
        modifier = modifier,
        iconResolver = iconResolver,
        contentResolver = contentResolver,
        colorResolver = colorResolver,
        onStepClick = onStepClick,
        onAction = onAction
    )
}

/**
 * State-managed entry point: Full reactive support with [SduiStateManager].
 */
@OptIn(ExperimentalKotStep::class)
@Composable
fun KotStepSdui(
    manager: SduiStateManager,
    modifier: Modifier = Modifier,
    iconResolver: SduiIconResolver = DefaultSduiIconResolver,
    contentResolver: SduiContentResolver = DefaultSduiContentResolver,
    colorResolver: SduiColorResolver = DefaultSduiColorResolver,
    onStepClick: ((stepId: String) -> Unit)? = null,
    onAction: ((SduiAction) -> Unit)? = null
) {
    val flow by manager.flow.collectAsState()
    val kotStepStyle = remember(flow.style, flow.orientation, colorResolver) {
        SduiStyleMapper.mapStyle(flow.style, flow.orientation, colorResolver)
    }

    KotStep(
        modifier = modifier,
        currentStep = { flow.computeCurrentStepFloat() },
        style = kotStepStyle
    ) {
        flow.steps.forEach { step ->
            val stepLeadingLabel: (@Composable () -> Unit) = {
                if (!step.leadingText.isNullOrBlank()) {
                    Text(
                        text = step.leadingText,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }

            val stepTrailingLabel: (@Composable () -> Unit) = {
                SduiStepLabel(
                    title = step.title,
                    subtitle = step.subtitle,
                    errorMessage = step.errorMessage,
                    isDimmed = step.state == SduiStepState.LOCKED || step.state == SduiStepState.SKIPPED,
                    isError = step.state == SduiStepState.ERROR
                )
            }

            val handleClick: () -> Unit = {
                if (step.state != SduiStepState.LOCKED) {
                    onStepClick?.invoke(step.id)
                    step.action?.let { onAction?.invoke(it) }
                }
            }

            when {
                // ERROR state decorator
                step.state == SduiStepState.ERROR -> {
                    step(
                        content = { SduiErrorBadge() },
                        leadingLabel = stepLeadingLabel,
                        trailingLabel = stepTrailingLabel,
                        onClick = handleClick,
                        isCollapsible = step.isCollapsible
                    )
                }

                // LOCKED state decorator
                step.state == SduiStepState.LOCKED -> {
                    step(
                        content = { SduiLockBadge() },
                        leadingLabel = stepLeadingLabel,
                        trailingLabel = stepTrailingLabel,
                        onClick = { /* Non-interactive when locked */ },
                        isCollapsible = false
                    )
                }

                // SKIPPED state decorator
                step.state == SduiStepState.SKIPPED -> {
                    step(
                        content = { SduiSkippedBadge() },
                        leadingLabel = stepLeadingLabel,
                        trailingLabel = stepTrailingLabel,
                        onClick = handleClick,
                        isCollapsible = step.isCollapsible
                    )
                }

                // Custom content resolution
                step.indicator.type == SduiIndicatorType.CUSTOM -> {
                    val customComposable = contentResolver.resolve(
                        step.indicator.value ?: "",
                        step.metadata
                    )
                    step(
                        content = customComposable,
                        leadingLabel = stepLeadingLabel,
                        trailingLabel = stepTrailingLabel,
                        onClick = handleClick,
                        isCollapsible = step.isCollapsible
                    )
                }

                // Icon indicator
                step.indicator.type == SduiIndicatorType.ICON -> {
                    val vector = step.indicator.value?.let { iconResolver.resolve(it) }
                    if (vector != null) {
                        step(
                            icon = vector,
                            leadingLabel = stepLeadingLabel,
                            trailingLabel = stepTrailingLabel,
                            onClick = handleClick,
                            isCollapsible = step.isCollapsible
                        )
                    } else {
                        step(
                            title = "${step.ordinal + 1}",
                            leadingLabel = stepLeadingLabel,
                            trailingLabel = stepTrailingLabel,
                            onClick = handleClick,
                            isCollapsible = step.isCollapsible
                        )
                    }
                }

                // Number / Text indicator
                step.indicator.type == SduiIndicatorType.NUMBER || step.indicator.type == SduiIndicatorType.TEXT -> {
                    step(
                        title = step.indicator.value?.ifBlank { "${step.ordinal + 1}" } ?: "${step.ordinal + 1}",
                        leadingLabel = stepLeadingLabel,
                        trailingLabel = stepTrailingLabel,
                        onClick = handleClick,
                        isCollapsible = step.isCollapsible
                    )
                }

                // Default dot/checkmark indicator
                else -> {
                    step(
                        content = null,
                        leadingLabel = stepLeadingLabel,
                        trailingLabel = stepTrailingLabel,
                        onClick = handleClick,
                        isCollapsible = step.isCollapsible
                    )
                }
            }
        }
    }
}
