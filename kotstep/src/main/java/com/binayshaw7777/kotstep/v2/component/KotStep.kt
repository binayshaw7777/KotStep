package com.binayshaw7777.kotstep.v2.component

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import com.binayshaw7777.kotstep.components.divider.KotStepVerticalDivider
import com.binayshaw7777.kotstep.util.noRippleClickable
import com.binayshaw7777.kotstep.v2.model.KotStepScope
import com.binayshaw7777.kotstep.v2.model.step.Step
import com.binayshaw7777.kotstep.v2.model.step.StepLayoutStyle
import com.binayshaw7777.kotstep.v2.model.step.StepState
import com.binayshaw7777.kotstep.v2.model.style.KotStepStyle
import com.binayshaw7777.kotstep.v2.model.style.LineStyle
import com.binayshaw7777.kotstep.v2.model.style.LineType
import com.binayshaw7777.kotstep.v2.model.style.StepStyle

@Composable
fun KotStep(
    modifier: Modifier = Modifier,
    currentStep: () -> Float,
    style: KotStepStyle = KotStepStyle(),
    content: KotStepScope.() -> Unit
) {
    val steps = KotStepScope().apply(content).buildSteps()

    when (style.stepLayoutStyle) {
        StepLayoutStyle.Vertical, StepLayoutStyle.VerticalCentered -> {
            VerticalKotStep(
                modifier = modifier,
                currentStep = currentStep,
                style = style,
                steps = steps,
                onClick = { index ->
                    steps[index].onClick?.invoke()
                }
            )
        }

        StepLayoutStyle.Horizontal, StepLayoutStyle.HorizontalCentered -> {
            HorizontalKotstep(
                modifier = modifier,
                currentStep = currentStep,
                style = style,
                steps = steps
            )
        }
    }
}

@Composable
fun VerticalKotStep(
    modifier: Modifier = Modifier,
    currentStep: () -> Float,
    style: KotStepStyle,
    steps: List<Step>,
    onClick: (Int) -> Unit = {}
) {


    require(steps.isNotEmpty()) { "Icons should not be empty" }
    require(currentStep() in -1f..(steps.size).toFloat()) { "Current step should be between 0 and total steps but it was ${currentStep()}" }

    if (style.isScrollable) {
        LazyColumn(
            modifier
                .fillMaxHeight()
                .then(modifier)
        ) {
            itemsIndexed(steps) { index, step ->

                val progress = if (index == currentStep().toInt()) {
                    currentStep() - currentStep().toInt()
                } else if (index < currentStep().toInt()) {
                    1f
                } else {
                    0f
                }

                val stepState = when {
                    style.ignoreCurrentState -> {
                        if (currentStep() >= index.toFloat()) StepState.Done else StepState.Todo
                    }

                    else -> {
                        when {
                            index < currentStep().toInt() -> StepState.Done
                            index == currentStep().toInt() -> StepState.Current
                            else -> StepState.Todo
                        }
                    }
                }

                VerticalStepItem(
                    style = style,
                    stepState = stepState,
                    progress = { progress },
                    isLastStep = index == steps.size - 1,
                    step = step,
                    onClick = { onClick(index) }
                )
            }
        }
    } else {
        Column(
            modifier = Modifier.then(modifier),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            for (index in steps.indices) {

                val progress = if (index == currentStep().toInt()) {
                    currentStep() - currentStep().toInt()
                } else if (index < currentStep().toInt()) {
                    1f
                } else {
                    0f
                }

                val stepState = when {
                    style.ignoreCurrentState -> {
                        if (currentStep() >= index.toFloat()) StepState.Done else StepState.Todo
                    }

                    else -> {
                        when {
                            index < currentStep().toInt() -> StepState.Done
                            index == currentStep().toInt() -> StepState.Current
                            else -> StepState.Todo
                        }
                    }
                }

                VerticalStepItem(
                    style = style,
                    stepState = stepState,
                    progress = { progress },
                    isLastStep = index == steps.size - 1,
                    step = steps[index],
                    onClick = { onClick(index) }
                )
            }
        }
    }
}

@Composable
fun VerticalStepItem(
    modifier: Modifier = Modifier,
    progress: () -> Float,
    step: Step,
    style: KotStepStyle,
    stepState: StepState,
    isLastStep: Boolean,
    onClick: () -> Unit = {}
) {
    val transition = updateTransition(targetState = stepState, label = "")

    val containerColor: Color by transition.animateColor(label = "itemColor") {
        when (it) {
            StepState.Todo -> style.stepStyle.onTodo.stepColor
            StepState.Current -> style.stepStyle.onCurrent.stepColor
            StepState.Done -> style.stepStyle.onDone.stepColor
        }
    }

    val lineColor: Color by transition.animateColor(label = "lineColor") {
        when (it) {
            StepState.Todo -> style.lineStyle.onTodo.lineColor
            StepState.Current -> style.lineStyle.onCurrent.lineColor
            StepState.Done -> style.lineStyle.onDone.lineColor
        }
    }

    val progressColor: Color by transition.animateColor(label = "progressColor") {
        when (it) {
            StepState.Todo -> style.lineStyle.onTodo.progressColor
            StepState.Current -> style.lineStyle.onCurrent.progressColor
            StepState.Done -> style.lineStyle.onDone.progressColor
        }
    }

    val stepStyle: StepStyle = when (stepState) {
        StepState.Todo -> style.stepStyle.onTodo
        StepState.Current -> style.stepStyle.onCurrent
        StepState.Done -> style.stepStyle.onDone
    }

    val lineStyle: LineStyle = when (stepState) {
        StepState.Todo -> style.lineStyle.onTodo
        StepState.Current -> style.lineStyle.onCurrent
        StepState.Done -> style.lineStyle.onDone
    }

    val lineTrackType: LineType = when (stepState) {
        StepState.Todo -> style.lineStyle.onTodo.lineType
        StepState.Current -> style.lineStyle.onCurrent.lineType
        StepState.Done -> style.lineStyle.onDone.lineType
    }

    val lineProgressType: LineType = when (stepState) {
        StepState.Todo -> style.lineStyle.onTodo.progressType
        StepState.Current -> style.lineStyle.onCurrent.progressType
        StepState.Done -> style.lineStyle.onDone.progressType
    }

    val trackStrokeCap: StrokeCap = when (stepState) {
        StepState.Todo -> style.lineStyle.onTodo.lineStrokeCap
        StepState.Current -> style.lineStyle.onCurrent.lineStrokeCap
        StepState.Done -> style.lineStyle.onDone.lineStrokeCap
    }

    val progressStrokeCap: StrokeCap = when (stepState) {
        StepState.Todo -> style.lineStyle.onTodo.progressStrokeCap
        StepState.Current -> style.lineStyle.onCurrent.progressStrokeCap
        StepState.Done -> style.lineStyle.onDone.progressStrokeCap
    }

    Column(
        modifier = Modifier
            .noRippleClickable { onClick() }
            .then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(stepStyle.stepSize)
                .clip(stepStyle.stepShape)
                .background(containerColor)
                .border(stepStyle.border)
                .then(modifier)
        ) {
            if (stepState == StepState.Done && style.showCheckMarkOnDone) {
                Icon(
                    imageVector = Icons.Default.Done,
                    tint = stepStyle.iconStyle.iconTint,
                    modifier = Modifier.size(stepStyle.iconStyle.iconSize),
                    contentDescription = "Done"
                )
            } else {
                when {
                    step.title.isNullOrEmpty().not() -> {
                        step.title?.let { text ->
                            Text(text = text, style = stepStyle.textStyle)
                        }
                    }

//                    step.painterIcon != null -> {
//                        step.painterIcon?.let { painter ->
//                            Icon(
//                                painter = painter,
//                                contentDescription = null,
//                                modifier = Modifier.size(stepStyle.iconStyle.iconSize),
//                                tint = stepStyle.iconStyle.iconTint
//                            )
//                        }
//                    }

                    step.imageVectorIcon != null -> {
                        step.imageVectorIcon?.let { imageVector ->
                            Icon(
                                imageVector = imageVector,
                                contentDescription = null,
                                modifier = Modifier.size(stepStyle.iconStyle.iconSize),
                                tint = stepStyle.iconStyle.iconTint
                            )
                        }
                    }

                    step.content != null -> {
                        step.content?.let { content ->
                            content()
                        }
                    }

                    else -> {
                        // TODO -> Tab
                    }
                }
            }
        }

        if (!isLastStep) {
            KotStepVerticalDivider(
                modifier = Modifier.padding(
                    top = lineStyle.linePadding.calculateTopPadding(),
                    bottom = lineStyle.linePadding.calculateBottomPadding()
                ),
                height = lineStyle.lineLength,
                width = lineStyle.lineThickness,
                lineTrackColor = lineColor,
                lineProgressColor = progressColor,
                lineTrackStyle = lineTrackType,
                lineProgressStyle = lineProgressType,
                progress = progress,
                trackStrokeCap = trackStrokeCap,
                progressStrokeCap = progressStrokeCap
            )
        }
    }
}


@Composable
fun HorizontalKotstep(
    modifier: Modifier = Modifier,
    currentStep: () -> Float,
    style: KotStepStyle,
    steps: List<Step>
) {

}

@Composable
fun HorizontalStepItem(
    modifier: Modifier = Modifier,
    progress: () -> Float,
    step: Step,
    style: KotStepStyle,
    stepState: StepState,
    isLastStep: Boolean,
    onClick: () -> Unit = {}
) {

}
