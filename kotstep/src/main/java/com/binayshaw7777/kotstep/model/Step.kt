package com.binayshaw7777.kotstep.model

import androidx.compose.runtime.Composable

/**
 * A data class for Step
 *
 * @param content The main content of the step
 * @param supportingContent The supporting content of the step
 */
data class StepComposable(
    val content: @Composable () -> Unit,
    val supportingContent: @Composable (() -> Unit)? = null
)

/**
 * A data class for Step
 *
 * @param text The main text of the step
 * @param supportingContent The supporting content of the step
 */
data class Step(
    val text: String,
    val supportingContent: @Composable (() -> Unit)? = null
)
