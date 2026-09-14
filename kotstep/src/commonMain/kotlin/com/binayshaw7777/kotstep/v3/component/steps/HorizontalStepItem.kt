package com.binayshaw7777.kotstep.v3.component.steps

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.binayshaw7777.kotstep.v3.component.label.LabelContent
import com.binayshaw7777.kotstep.v3.component.progress_bar.KotStepHorizontalProgress
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
import kotlin.math.roundToInt
import kotlinx.coroutines.delay

@OptIn(ExperimentalKotStep::class)
@Composable
internal fun HorizontalStepItem(
    modifier: Modifier = Modifier,
    currentStepState: State<Float>,
    staggerDelayMs: Long = 0L,
    step: Step,
    style: KotStepStyle,
    stepIndex: Int,
    isLastStep: Boolean,
    maxLeadingLabelHeight: State<Dp>,
    maxTrailingLabelHeight: State<Dp>,
    onLeadingLabelMeasured: (IntSize) -> Unit,
    onTrailingLabelMeasured: (IntSize) -> Unit,
    onClick: (Int) -> Unit = {}
) {
    val delayedCurrent = remember { mutableFloatStateOf(currentStepState.value) }
    LaunchedEffect(currentStepState.value, staggerDelayMs) {
        if (staggerDelayMs > 0L) delay(staggerDelayMs)
        delayedCurrent.value = currentStepState.value
    }
    val activeCurrent: State<Float> = if (staggerDelayMs == 0L) currentStepState else delayedCurrent

    val stepState by remember(stepIndex, style.ignoreCurrentState, activeCurrent) {
        derivedStateOf {
            val current = activeCurrent.value
            if (style.ignoreCurrentState) {
                if (current >= stepIndex.toFloat()) StepState.Done else StepState.Todo
            } else {
                when {
                    current < 0f -> StepState.Todo
                    stepIndex < current.toInt() -> StepState.Done
                    stepIndex == current.toInt() -> StepState.Current
                    else -> StepState.Todo
                }
            }
        }
    }
    val progress by remember(stepIndex, activeCurrent) {
        derivedStateOf {
            val current = activeCurrent.value
            when {
                current < 0f -> 0f
                stepIndex == current.toInt() -> current - current.toInt()
                stepIndex < current.toInt() -> 1f
                else -> 0f
            }
        }
    }

    val transition = updateTransition(targetState = stepState, label = "step_state_transition_$stepIndex")
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

    var isContentVisible by rememberSaveable(stepIndex) { mutableStateOf(true) }
    var trailingLabelWidth by remember { mutableStateOf(0.dp) }
    var isTrailingLabelMeasured by remember { mutableStateOf(false) }
    val density = LocalDensity.current

    val lineWidth by remember {
        derivedStateOf {
            if (isTrailingLabelMeasured) maxOf(trailingLabelWidth - stepSize, lineLength) else lineLength
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth().then(modifier),
        horizontalAlignment = Alignment.Start
    ) {
        HorizontalLabelSlot(
            maxHeightState = maxLeadingLabelHeight,
            label = step.leadingLabel,
            visible = isContentVisible,
            testTag = "kotstep_leading_label_$stepIndex",
            onSizeChanged = onLeadingLabelMeasured
        )

        Row(
            modifier = Modifier
                .testTag("kotstep_step_$stepIndex")
                .minimumInteractiveComponentSize()
                .onClick {
                    if (step.isCollapsible) {
                        isContentVisible = isContentVisible.not()
                    }
                    onClick(stepIndex)
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            StepIndicator(
                modifier = Modifier.testTag("kotstep_indicator_$stepIndex"),
                size = stepSize,
                shape = staticProperties.stepStyle.stepShape,
                containerColor = containerColor,
                borderStyle = staticProperties.stepStyle.borderStyle,
                stepState = stepState,
                step = step,
                stepStyle = staticProperties.stepStyle,
                showCheckMark = style.showCheckMarkOnDone
            )

            if (!isLastStep) {
                AnimatedVisibility(
                    visible = isContentVisible,
                    enter = AnimationConstants.Horizontal.progressLineEnter,
                    exit = AnimationConstants.Horizontal.progressLineExit
                ) {
                    KotStepHorizontalProgress(
                        modifier = Modifier.padding(
                            start = staticProperties.lineStyle.linePadding.calculateStartPadding(LayoutDirection.Ltr) +
                                staticProperties.stepStyle.borderStyle.width,
                            end = staticProperties.lineStyle.linePadding.calculateEndPadding(LayoutDirection.Ltr)
                        ),
                        width = lineWidth,
                        height = staticProperties.lineStyle.lineThickness,
                        lineTrackColor = lineColor,
                        lineProgressColor = progressColor,
                        lineTrackStyle = staticProperties.lineTrackType,
                        lineProgressStyle = staticProperties.lineProgressType,
                        progress = progress,
                        stepState = stepState,
                        trackStrokeCap = staticProperties.trackStrokeCap,
                        progressStrokeCap = staticProperties.progressStrokeCap
                    )
                }
            }
        }

        HorizontalLabelSlot(
            maxHeightState = maxTrailingLabelHeight,
            label = step.trailingLabel,
            visible = isContentVisible,
            testTag = "kotstep_trailing_label_$stepIndex",
            onSizeChanged = { size ->
                trailingLabelWidth = with(density) { size.width.toDp() }
                isTrailingLabelMeasured = true
                onTrailingLabelMeasured(size)
            }
        )
    }
}

@Composable
private fun HorizontalLabelSlot(
    maxHeightState: State<Dp>,
    label: (@Composable () -> Unit)?,
    visible: Boolean,
    testTag: String,
    onSizeChanged: (IntSize) -> Unit
) {
    val density = LocalDensity.current
    val enforceMinHeight = remember(maxHeightState, density) {
        Modifier.layout { measurable, constraints ->
            val minHeightPx = with(density) { maxHeightState.value.toPx().roundToInt() }
            val targetMinHeight = maxOf(constraints.minHeight, minHeightPx).coerceAtMost(constraints.maxHeight)
            val placeable = measurable.measure(
                constraints.copy(minHeight = targetMinHeight)
            )
            val targetHeight = maxOf(placeable.height, minHeightPx).coerceIn(constraints.minHeight, constraints.maxHeight)
            layout(placeable.width, targetHeight) {
                placeable.place(0, 0)
            }
        }
    }

    Box(modifier = Modifier.testTag(testTag).then(enforceMinHeight)) {
        if (label != null) {
            AnimatedVisibility(
                visible = visible,
                enter = AnimationConstants.Horizontal.labelEnter,
                exit = AnimationConstants.Horizontal.labelExit
            ) {
                LabelContent(
                    modifier = Modifier.wrapContentWidth(),
                    label = label,
                    onSizeChanged = onSizeChanged
                )
            }
        }
    }
}