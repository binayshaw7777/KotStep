package com.binayshaw7777.kotstep.v2.component

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.binayshaw7777.kotstep.components.divider.KotStepVerticalDivider
import com.binayshaw7777.kotstep.components.tabs.CurrentTab
import com.binayshaw7777.kotstep.components.tabs.DoneTab
import com.binayshaw7777.kotstep.components.tabs.TodoTab
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
    require(steps.isNotEmpty()) { "Steps should not be empty" }
    require(currentStep() in -1f..(steps.size).toFloat()) { "Current step should be between 0 and total steps: ${steps.size} but it was ${currentStep()}" }

    if (style.isScrollable) {
        LazyColumn(
            modifier
                .fillMaxHeight()
                .then(modifier)
        ) {
            itemsIndexed(steps, key = { index, _ -> index }) { index, step ->

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

    LaunchedEffect(stepState) {
        println("Step State: $stepState")
    }
    val transition = updateTransition(targetState = stepState, label = "")

    var maxWidth by remember { mutableStateOf(0.dp) }

    LaunchedEffect(style.stepStyle) {
        maxWidth = maxOf(
            style.stepStyle.onTodo.stepSize,
            style.stepStyle.onCurrent.stepSize,
            style.stepStyle.onDone.stepSize
        )
    }

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

    val stepSize: Dp by transition.animateDp(label = "stepSize") {
        when (it) {
            StepState.Todo -> style.stepStyle.onTodo.stepSize
            StepState.Current -> style.stepStyle.onCurrent.stepSize
            StepState.Done -> style.stepStyle.onDone.stepSize
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
            .width(maxWidth)
            .noRippleClickable { onClick() }
            .then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(stepSize)
                .clip(stepStyle.stepShape)
                .background(containerColor)
                .border(
                    width = stepStyle.borderStyle.width,
                    shape = stepStyle.borderStyle.shape,
                    color = stepStyle.borderStyle.color
                )
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

                    step.icon != null -> {
                        step.icon?.let { icon ->
                            Icon(
                                imageVector = icon,
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
                        when (stepState) {
                            StepState.Todo -> {
                                TodoTab(
                                    strokeColor = containerColor,
                                    strokeThickness = stepStyle.borderStyle.width.value,
                                    stepShape = stepStyle.stepShape
                                )
                            }

                            StepState.Current -> {
                                CurrentTab(
                                    circleColor = containerColor,
                                    strokeThickness = stepStyle.borderStyle.width.value,
                                    stepShape = stepStyle.stepShape
                                )
                            }

                            StepState.Done -> {
                                DoneTab(
                                    circleColor = containerColor,
                                    showTick = style.showCheckMarkOnDone,
                                    checkMarkColor = stepStyle.iconStyle.iconTint,
                                    stepShape = stepStyle.stepShape
                                )
                            }
                        }
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
