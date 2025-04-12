package com.binayshaw7777.kotstep.v2.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.binayshaw7777.kotstep.v2.model.step.Step

@DslMarker
annotation class KotStepDsl

@KotStepDsl
class KotStepScope {
    private val steps = mutableListOf<Step>()

    /**
     * Adds a step with a title.
     * @param title The title of the step. Must not be blank.
     * @param onClick Callback invoked when the step is clicked.
     * @param onDone Callback invoked when the step is completed.
     * @param label Composable content for the step's label.
     *
     * @throws IllegalArgumentException if [title] is blank.
     *
     * @sample com.binayshaw7777.kotstep.v2.samples.StepWithTitle
     */
    fun step(
        title: String,
        onClick: (() -> Unit) = {},
        onDone: () -> Unit = {},
        label: @Composable () -> Unit = {}
    ) {
        require(title.isNotBlank()) {
            "Step cannot have an empty title. Consider using other variants that support icons or composable content."
        }
        steps.add(Step(title = title, onClick = onClick, label = label, onDone = onDone))
    }


    /**
     * Adds a step with an image vector icon.
     * @param icon The image vector icon for the step.
     * @param onClick Callback invoked when the step is clicked.
     * @param label Composable content for the step's label.
     * @param onDone Callback invoked when the step is completed.
     *
     * @sample com.binayshaw7777.kotstep.v2.samples.StepWithImageVectorIcon
     */
    fun step(
        icon: ImageVector,
        onClick: (() -> Unit) = {},
        onDone: () -> Unit = {},
        label: @Composable () -> Unit = {}
    ) {
        steps.add(Step(imageVectorIcon = icon, onClick = onClick, label = label, onDone = onDone))
    }


    /**
     * Adds a step with custom composable content.
     * @param content Composable content for the step.
     * @param onClick Callback invoked when the step is clicked.
     * @param onDone Callback invoked when the step is completed.
     * @param label Composable content for the step's label.
     *
     * @sample com.binayshaw7777.kotstep.v2.samples.StepWithCustomContent
     */
    fun step(
        content: (@Composable () -> Unit)? = null,
        onClick: (() -> Unit) = {},
        onDone: () -> Unit = {},
        label: @Composable () -> Unit = {}
    ) {
        steps.add(Step(content = content, onClick = onClick, label = label, onDone = onDone))
    }

    internal fun buildSteps(): List<Step> = steps
}