package com.binayshaw7777.kotstep.v2.component.steps

import android.util.Log
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.binayshaw7777.kotstep.v2.component.label.LabelContent
import com.binayshaw7777.kotstep.v2.component.progress_bar.KotStepHorizontalProgress
import com.binayshaw7777.kotstep.v2.model.step.Step
import com.binayshaw7777.kotstep.v2.model.step.StepState
import com.binayshaw7777.kotstep.v2.model.step.calculateStaticStepProperties
import com.binayshaw7777.kotstep.v2.model.style.KotStepStyle
import com.binayshaw7777.kotstep.v2.model.style.getColorForState
import com.binayshaw7777.kotstep.v2.model.style.getLineColorForState
import com.binayshaw7777.kotstep.v2.model.style.getLineLengthForState
import com.binayshaw7777.kotstep.v2.model.style.getProgressColorForState
import com.binayshaw7777.kotstep.v2.model.style.getSizeForState
import com.binayshaw7777.kotstep.v2.util.Util.onClick


/**
 * Displays a single step item in a horizontal stepper.
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
 */
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

    var labelWidth by remember { mutableStateOf(0.dp) }
    var isLabelMeasured by remember { mutableStateOf(false) }
    val density = LocalDensity.current


    val lineWidth by remember {
        derivedStateOf {
            if (isLabelMeasured) maxOf(labelWidth - stepSize, lineLength) else lineLength
        }
    }
    LaunchedEffect(isLabelMeasured) {
        Log.d("", "isLabelMeasured: $isLabelMeasured labelWidth: $labelWidth lineLength: $lineLength lineWidth: $lineWidth")
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    ) {
        val (stepContent, labelContent) = createRefs()

        Row(
            modifier = Modifier
                .height(staticProperties.maxSize)
                .onClick { onClick() }
                .constrainAs(stepContent) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
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
                KotStepHorizontalProgress(
                    modifier = Modifier
                        .padding(
                            start = staticProperties.lineStyle.linePadding.calculateStartPadding(LayoutDirection.Rtl) + staticProperties.stepStyle.borderStyle.width,
                            end = staticProperties.lineStyle.linePadding.calculateEndPadding(LayoutDirection.Rtl)
                        ),
                    width = { lineWidth },
                    height = { staticProperties.lineStyle.lineThickness },
                    lineTrackColor = lineColor,
                    lineProgressColor = progressColor,
                    lineTrackStyle = staticProperties.lineTrackType,
                    lineProgressStyle = staticProperties.lineProgressType,
                    progress = progress,
                    trackStrokeCap = staticProperties.trackStrokeCap,
                    progressStrokeCap = staticProperties.progressStrokeCap
                )
            }
        }

        step.label?.let { label ->
            LabelContent(
                modifier = Modifier.constrainAs(labelContent) {
                    start.linkTo(parent.start)
                    top.linkTo(stepContent.bottom)
                    width = Dimension.wrapContent
                },
                label = label,
                onSizeChanged = { size ->
                    labelWidth = with(density) { size.width.toDp() }
                    isLabelMeasured = true
                }
            )
        }
    }
}