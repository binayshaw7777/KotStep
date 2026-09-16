package com.binayshaw7777.kotstep.v3.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.binayshaw7777.kotstep.v3.model.step.Step
import com.binayshaw7777.kotstep.v3.util.ExperimentalKotStep
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

@DslMarker
annotation class KotStepDsl

@KotStepDsl
class KotStepScope {

    private val steps = mutableListOf<Step>()

    @ExperimentalKotStep
    fun step(
        title: String,
        leadingLabel: @Composable () -> Unit = {},
        onClick: (() -> Unit) = {},
        onDone: () -> Unit = {},
        trailingLabel: @Composable () -> Unit = {},
        isCollapsible: Boolean = false
    ) {
        require(title.isNotBlank()) {
            "Step cannot have an empty title. Consider using other variants that support icons or composable content."
        }
        steps.add(
            Step(
                title = title,
                leadingLabel = leadingLabel,
                onClick = onClick,
                trailingLabel = trailingLabel,
                onDone = onDone,
                isCollapsible = isCollapsible
            )
        )
    }

    @ExperimentalKotStep
    fun step(
        icon: ImageVector,
        leadingLabel: @Composable () -> Unit = {},
        onClick: (() -> Unit) = {},
        onDone: () -> Unit = {},
        trailingLabel: @Composable () -> Unit = {},
        isCollapsible: Boolean = false
    ) {
        steps.add(
            Step(
                imageVectorIcon = icon,
                leadingLabel = leadingLabel,
                onClick = onClick,
                trailingLabel = trailingLabel,
                onDone = onDone,
                isCollapsible = isCollapsible
            )
        )
    }

    @ExperimentalKotStep
    fun step(
        content: (@Composable () -> Unit)? = null,
        leadingLabel: @Composable () -> Unit = {},
        onClick: (() -> Unit) = {},
        onDone: () -> Unit = {},
        trailingLabel: @Composable () -> Unit = {},
        isCollapsible: Boolean = false
    ) {
        steps.add(
            Step(
                content = content,
                leadingLabel = leadingLabel,
                onClick = onClick,
                trailingLabel = trailingLabel,
                onDone = onDone,
                isCollapsible = isCollapsible
            )
        )
    }

    internal fun buildSteps(): PersistentList<Step> = steps.toPersistentList()
}
