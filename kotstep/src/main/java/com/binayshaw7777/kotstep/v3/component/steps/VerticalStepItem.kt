package com.binayshaw7777.kotstep.v3.component.steps

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.binayshaw7777.kotstep.v3.component.label.LabelContent
import com.binayshaw7777.kotstep.v3.component.progress_bar.KotStepVerticalProgress
import com.binayshaw7777.kotstep.v3.model.step.Step
import com.binayshaw7777.kotstep.v3.model.step.StepState
import com.binayshaw7777.kotstep.v3.model.step.calculateStaticStepProperties
import com.binayshaw7777.kotstep.v3.model.style.KotStepStyle
import com.binayshaw7777.kotstep.v3.model.style.getColorForState
import com.binayshaw7777.kotstep.v3.model.style.getLineColorForState
import com.binayshaw7777.kotstep.v3.model.style.getLineLengthForState
import com.binayshaw7777.kotstep.v3.model.style.getProgressColorForState
import com.binayshaw7777.kotstep.v3.model.style.getSizeForState
import com.binayshaw7777.kotstep.v3.util.AnimationConstants
import com.binayshaw7777.kotstep.v3.util.ExperimentalKotStep
import com.binayshaw7777.kotstep.v3.util.Util.onClick

@OptIn(ExperimentalKotStep::class)
@Composable
internal fun VerticalStepItem(
    modifier: Modifier = Modifier,
    progress: () -> Float,
    step: Step,
    style: KotStepStyle,
    stepState: StepState,
    stepIndex: Int,
    isLastStep: Boolean,
    reservedLeadingLabelWidth: Dp,
    reservedTrailingLabelWidth: Dp,
    onLeadingLabelMeasured: (IntSize) -> Unit,
    onTrailingLabelMeasured: (IntSize) -> Unit,
    onClick: () -> Unit = {}
) {
    val transition = updateTransition(targetState = stepState, label = "")
    val staticProperties = calculateStaticStepProperties(style, stepState)

    val lineColor by transition.animateColor(label = "lineColor") {
        style.lineStyle.getLineColorForState(it)
    }
    val progressColor by transition.animateColor(label = "progressColor") {
        style.lineStyle.getProgressColorForState(it)
    }
    val containerColor by transition.animateColor(label = "containerColor") {
        style.stepStyle.getColorForState(it)
    }
    val stepSize by transition.animateDp(label = "stepSize") {
        style.stepStyle.getSizeForState(it)
    }
    val lineLength by transition.animateDp(label = "lineLength") {
        style.lineStyle.getLineLengthForState(it)
    }

    var isContentVisible by rememberSaveable(step) { mutableStateOf(true) }
    var trailingLabelHeight by remember { mutableStateOf(0.dp) }
    var isTrailingLabelMeasured by remember { mutableStateOf(false) }
    val density = LocalDensity.current

    val lineHeight by remember {
        derivedStateOf {
            if (isTrailingLabelMeasured) maxOf(trailingLabelHeight - stepSize, lineLength) else lineLength
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth().then(modifier),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start
    ) {
        VerticalLabelSlot(
            reservedWidth = reservedLeadingLabelWidth,
            label = step.leadingLabel,
            visible = isContentVisible,
            testTag = "kotstep_leading_label_$stepIndex",
            onSizeChanged = onLeadingLabelMeasured
        )

        Column(
            modifier = Modifier
                .testTag("kotstep_step_$stepIndex")
                .onClick {
                    if (step.isCollapsible) {
                        isContentVisible = isContentVisible.not()
                    }
                    onClick()
                },
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Start
            ) {
                Column(
                    modifier = Modifier.width(staticProperties.maxSize),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    StepIndicator(
                        modifier = Modifier.testTag("kotstep_indicator_$stepIndex"),
                        size = stepSize,
                        shape = staticProperties.stepStyle.stepShape,
                        containerColor = containerColor,
                        borderStyle = staticProperties.stepStyle.borderStyle,
                        stepState = { stepState },
                        step = { step },
                        stepStyle = { staticProperties.stepStyle },
                        showCheckMark = { style.showCheckMarkOnDone }
                    )

                    if (!isLastStep) {
                        AnimatedVisibility(
                            visible = isContentVisible,
                            enter = AnimationConstants.Vertical.progressLineEnter,
                            exit = AnimationConstants.Vertical.progressLineExit
                        ) {
                            KotStepVerticalProgress(
                                modifier = Modifier.padding(
                                    top = staticProperties.lineStyle.linePadding.calculateTopPadding() +
                                        staticProperties.stepStyle.borderStyle.width,
                                    bottom = staticProperties.lineStyle.linePadding.calculateBottomPadding()
                                ),
                                height = { lineHeight },
                                width = { staticProperties.lineStyle.lineThickness },
                                lineTrackColor = lineColor,
                                lineProgressColor = progressColor,
                                lineTrackStyle = staticProperties.lineTrackType,
                                lineProgressStyle = staticProperties.lineProgressType,
                                progress = progress,
                                stepState = { stepState },
                                trackStrokeCap = staticProperties.trackStrokeCap,
                                progressStrokeCap = staticProperties.progressStrokeCap
                            )
                        }
                    }
                }

                VerticalLabelSlot(
                    reservedWidth = reservedTrailingLabelWidth,
                    label = step.trailingLabel,
                    visible = isContentVisible,
                    testTag = "kotstep_trailing_label_$stepIndex",
                    onSizeChanged = { size ->
                        trailingLabelHeight = with(density) { size.height.toDp() }
                        isTrailingLabelMeasured = true
                        onTrailingLabelMeasured(size)
                    }
                )
            }
        }
    }
}

@Composable
private fun VerticalLabelSlot(
    reservedWidth: Dp,
    label: (@Composable () -> Unit)?,
    visible: Boolean,
    testTag: String,
    onSizeChanged: (IntSize) -> Unit
) {
    when {
        label != null -> {
            Box(modifier = Modifier.testTag(testTag).widthIn(min = reservedWidth)) {
                AnimatedVisibility(
                    visible = visible,
                    enter = AnimationConstants.Vertical.labelEnter,
                    exit = AnimationConstants.Vertical.labelExit
                ) {
                    LabelContent(
                        modifier = Modifier.wrapContentHeight(),
                        label = label,
                        onSizeChanged = onSizeChanged
                    )
                }
            }
        }

        reservedWidth > 0.dp -> {
            Box(modifier = Modifier.testTag(testTag).width(reservedWidth))
        }
    }
}
