package com.binayshaw7777.kotstep.v2.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.binayshaw7777.kotstep.v2.model.step.Step
import com.binayshaw7777.kotstep.v2.util.ExperimentalKotStep
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

@DslMarker
annotation class KotStepDsl

/**
 * `KotStepScope` is a Kotlin DSL (Domain Specific Language) class designed for creating a sequence of steps
 * within a process or workflow. It provides a fluent and expressive way to define steps,
 * each potentially having a title, icon, or custom content, along with associated actions
 * like `onClick` and `onDone`.
 *
 * This class is part of a larger system that likely renders these steps as part of a user interface,
 * enabling the user to navigate and interact with a multi-step process.

 * @see Step
 * @see KotStepDsl
 *
 * @since 3.0.0
 */
@KotStepDsl
class KotStepScope {


    /**
     * A mutable list of [Step] objects representing the sequence of operations or actions
     * to be performed. Each [Step] defines a specific task or phase in a process.
     *
     * The order of steps in this list determines the order in which they will be executed.
     * Steps can be added, removed, or reordered as needed.
     */
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
     *
     * @since 3.0.0
     */
    @ExperimentalKotStep
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
     *
     * @since 3.0.0
     */
    @ExperimentalKotStep
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
     *
     * @since 3.0.0
     */
    @ExperimentalKotStep
    fun step(
        content: (@Composable () -> Unit)? = null,
        onClick: (() -> Unit) = {},
        onDone: () -> Unit = {},
        label: @Composable () -> Unit = {}
    ) {
        steps.add(Step(content = content, onClick = onClick, label = label, onDone = onDone))
    }


    /**
     * Builds and returns a list of steps.
     *
     * This function simply returns the internally stored list of steps. It does not perform
     * any transformation or modification of the steps. It acts as a getter for the steps data.
     *
     * @return A list of [Step] objects representing the defined steps.
     *
     * @since 3.0.0
     */
    internal fun buildSteps(): PersistentList<Step> = steps.toPersistentList()
}