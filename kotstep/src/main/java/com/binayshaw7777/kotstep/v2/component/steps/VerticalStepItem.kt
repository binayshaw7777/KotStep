package com.binayshaw7777.kotstep.v2.component.steps

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.binayshaw7777.kotstep.v2.component.progress_bar.KotStepVerticalProgress
import com.binayshaw7777.kotstep.v2.model.step.Step
import com.binayshaw7777.kotstep.v2.model.step.StepState
import com.binayshaw7777.kotstep.v2.model.style.KotStepStyle
import com.binayshaw7777.kotstep.v2.model.style.LineStyle
import com.binayshaw7777.kotstep.v2.model.style.LineType
import com.binayshaw7777.kotstep.v2.model.style.StepStyle
import com.binayshaw7777.kotstep.v2.util.Util.onClick

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

    var labelHeight by remember { mutableStateOf(0.dp) }
    var isLabelMeasured by remember { mutableStateOf(false) }
    val density = LocalDensity.current

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    ) {
        val (stepContent, labelContent) = createRefs()

        Column(
            modifier = Modifier
                .width(maxWidth)
                .onClick { onClick() }
                .constrainAs(stepContent) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                },
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
                        color = stepStyle.borderStyle.color,
                        shape = stepStyle.stepShape
                    )
                    .zIndex(2f)
            ) {
                when {
                    stepState == StepState.Done && step.title == null &&
                            step.icon == null && step.content == null -> {
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = "Done",
                            modifier = Modifier.size(stepStyle.iconStyle.iconSize),
                            tint = Color.White
                        )
                    }

                    !step.title.isNullOrEmpty() -> {
                        Text(
                            text = step.title!!,
                            style = stepStyle.textStyle
                        )
                    }

                    step.icon != null -> {
                        Icon(
                            imageVector = step.icon!!,
                            contentDescription = null,
                            modifier = Modifier.size(stepStyle.iconStyle.iconSize)
                        )
                    }

                    step.content != null -> {
                        step.content?.invoke()
                    }

                    else -> {
                        Box(
                            modifier = Modifier
                                .size((stepSize.value * 0.75f).dp)
                                .background(stepStyle.iconStyle.iconTint)
                        )
                    }
                }
            }

            if (!isLastStep) {

                val measuredLabelHeight = if (isLabelMeasured) maxOf(
                    labelHeight - stepSize,
                    lineStyle.lineLength
                ) else lineStyle.lineLength

                KotStepVerticalProgress(
                    modifier = Modifier
                        .padding(
                            top = lineStyle.linePadding.calculateTopPadding(),
                            bottom = lineStyle.linePadding.calculateBottomPadding()
                        )
                        .zIndex(1f),
                    height = measuredLabelHeight,
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

        step.label?.let { label ->
            Box(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .onGloballyPositioned { coordinates ->
                        if (!isLabelMeasured) {
                            labelHeight = with(density) { coordinates.size.height.toDp() }
                            isLabelMeasured = true
                        }
                    }
                    .constrainAs(labelContent) {
                        top.linkTo(stepContent.top)
                        start.linkTo(stepContent.end)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    },
                contentAlignment = Alignment.TopStart
            ) {
                label()
            }
        }
    }
}