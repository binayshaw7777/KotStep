package com.binayshaw7777.kotstep.v3.component.steps

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
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


/**
 * Displays a single step item in a vertical stepper.
 *
 * This composable renders a step indicator, a connecting line (if not the last step),
 * and an optional label. It dynamically updates its appearance based on the `stepState`.
 *
 * @param modifier The modifier to be applied to the step item.
 * @param progress A lambda that returns the progress value (between 0.0 and 1.0) for the connecting line.
 *                 This determines how much of the line is filled.
 * @param step The [Step] data representing the current step's information, including the optional label.
 * @param style The [KotStepStyle] defining the visual style of the stepper.
 * @param stepState The [StepState] indicating the current state of the step (Todo, Current, or Done).
 * @param isLastStep Boolean flag that indicates if it's the last step in the sequence.
 * @param onClick A callback function invoked when the step item is clicked. Defaults to an empty function.
 *
 * @since 3.0.0
 */
@OptIn(ExperimentalKotStep::class)
@Composable
internal fun VerticalStepItem(
    modifier: Modifier = Modifier,
    progress: () -> Float,
    step: Step,
    style: KotStepStyle,
    stepState: StepState,
    isLastStep: Boolean,
    onClick: () -> Unit = {}
) {
    val transition = updateTransition(targetState = stepState, label = "")

    val staticProperties = calculateStaticStepProperties(style, stepState)

    val containerColor by transition.animateColor(label = "containerColor") {
        style.stepStyle.getColorForState(it)
    }
    val lineColor by transition.animateColor(label = "lineColor") {
        style.lineStyle.getLineColorForState(it)
    }
    val progressColor by transition.animateColor(label = "progressColor") {
        style.lineStyle.getProgressColorForState(it)
    }
    val stepSize by transition.animateDp(label = "stepSize") {
        style.stepStyle.getSizeForState(it)
    }
    val lineLength by transition.animateDp(label = "lineLength") {
        style.lineStyle.getLineLengthForState(it)
    }
    var isContentVisible by rememberSaveable(step) { mutableStateOf(true) }

    var labelHeight by remember { mutableStateOf(0.dp) }
    var isLabelMeasured by remember { mutableStateOf(false) }
    val density = LocalDensity.current

    val lineHeight by remember {
        derivedStateOf {
            if (isLabelMeasured) maxOf(labelHeight - stepSize, lineLength) else lineLength
        }
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth() // TODO: Wrap
            .then(modifier)
    ) {
        val (stepContent, labelContent) = createRefs()

        Column(
            modifier = Modifier
                .width(staticProperties.maxSize)
                .onClick {
                    if (step.isCollapsible) {
                        isContentVisible = isContentVisible.not()
                    }
                    onClick()
                }
                .constrainAs(stepContent) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            StepIndicator(
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
                        modifier = Modifier
                            .padding(
                                top = staticProperties.lineStyle.linePadding.calculateTopPadding() + staticProperties.stepStyle.borderStyle.width,
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

        step.label?.let { label ->
            AnimatedVisibility(
                visible = isContentVisible,
                enter = AnimationConstants.Vertical.labelEnter,
                exit = AnimationConstants.Vertical.labelExit,
                modifier = Modifier.constrainAs(labelContent) {
                    top.linkTo(stepContent.top)
                    start.linkTo(stepContent.end)
                    width = Dimension.wrapContent
                }
            ) {
                LabelContent(
                    modifier = Modifier.wrapContentHeight(),
                    label = label,
                    onSizeChanged = { size ->
                        labelHeight = with(density) { size.height.toDp() }
                        isLabelMeasured = true
                    }
                )
            }
        }
    }
}