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
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.binayshaw7777.kotstep.v2.component.progress_bar.KotStepVerticalProgress
import com.binayshaw7777.kotstep.v2.model.step.Step
import com.binayshaw7777.kotstep.v2.model.step.StepState
import com.binayshaw7777.kotstep.v2.model.style.BorderStyle
import com.binayshaw7777.kotstep.v2.model.style.KotStepStyle
import com.binayshaw7777.kotstep.v2.model.style.LineStyle
import com.binayshaw7777.kotstep.v2.model.style.LineType
import com.binayshaw7777.kotstep.v2.model.style.StepStyle
import com.binayshaw7777.kotstep.v2.model.style.getColorForState
import com.binayshaw7777.kotstep.v2.model.style.getLineColorForState
import com.binayshaw7777.kotstep.v2.model.style.getLineLengthForState
import com.binayshaw7777.kotstep.v2.model.style.getProgressColorForState
import com.binayshaw7777.kotstep.v2.model.style.getSizeForState
import com.binayshaw7777.kotstep.v2.util.Util.onClick


/**
 * Calculates the static properties of a step based on the given style and step state.
 *
 * This function determines the visual attributes of a step, such as its maximum width,
 * style, and line properties, based on the current [StepState] and the provided [KotStepStyle].
 * The calculated properties are meant to be static and remain consistent unless the style
 * or the step state changes.
 *
 * @param style The [KotStepStyle] that defines the styling for the step and its lines.
 * @param stepState The [StepState] representing the current state of the step (Todo, Current, or Done).
 * @return A [StepProperties] object containing the calculated properties for the step.
 *
 * @see KotStepStyle
 * @see StepState
 * @see StepProperties
 */
@Composable
fun calculateStaticStepProperties(style: KotStepStyle, stepState: StepState): StepProperties {
    return remember(style.stepStyle, style.lineStyle, stepState) {
        derivedStateOf {
            StepProperties(
                maxWidth = maxOf(
                    style.stepStyle.onTodo.stepSize,
                    style.stepStyle.onCurrent.stepSize,
                    style.stepStyle.onDone.stepSize
                ),
                stepStyle = when (stepState) {
                    StepState.Todo -> style.stepStyle.onTodo
                    StepState.Current -> style.stepStyle.onCurrent
                    StepState.Done -> style.stepStyle.onDone
                },
                lineStyle = when (stepState) {
                    StepState.Todo -> style.lineStyle.onTodo
                    StepState.Current -> style.lineStyle.onCurrent
                    StepState.Done -> style.lineStyle.onDone
                },
                lineTrackType = when (stepState) {
                    StepState.Todo -> style.lineStyle.onTodo.lineType
                    StepState.Current -> style.lineStyle.onCurrent.lineType
                    StepState.Done -> style.lineStyle.onDone.lineType
                },
                lineProgressType = when (stepState) {
                    StepState.Todo -> style.lineStyle.onTodo.progressType
                    StepState.Current -> style.lineStyle.onCurrent.progressType
                    StepState.Done -> style.lineStyle.onDone.progressType
                },
                trackStrokeCap = when (stepState) {
                    StepState.Todo -> style.lineStyle.onTodo.lineStrokeCap
                    StepState.Current -> style.lineStyle.onCurrent.lineStrokeCap
                    StepState.Done -> style.lineStyle.onDone.lineStrokeCap
                },
                progressStrokeCap = when (stepState) {
                    StepState.Todo -> style.lineStyle.onTodo.progressStrokeCap
                    StepState.Current -> style.lineStyle.onCurrent.progressStrokeCap
                    StepState.Done -> style.lineStyle.onDone.progressStrokeCap
                }
            )
        }
    }.value
}


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
 */
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
            .fillMaxWidth()
            .then(modifier)
    ) {
        val (stepContent, labelContent) = createRefs()

        Column(
            modifier = Modifier
                .width(staticProperties.maxWidth)
                .onClick { onClick() }
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
                stepStyle = { staticProperties.stepStyle }
            )

            if (!isLastStep) {
                KotStepVerticalProgress(
                    modifier = Modifier
                        .padding(
                            top = staticProperties.lineStyle.linePadding.calculateTopPadding(),
                            bottom = staticProperties.lineStyle.linePadding.calculateBottomPadding()
                        ),
                    height = { lineHeight },
                    width = { staticProperties.lineStyle.lineThickness },
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
                    top.linkTo(stepContent.top)
                    start.linkTo(stepContent.end)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                },
                label = label,
                onSizeChanged = { size ->
                    labelHeight = with(density) { size.height.toDp() }
                    isLabelMeasured = true
                }
            )
        }
    }
}

/**
 * Composable function that displays a labeled content within a Box.
 *
 * This function provides a layout for displaying a label positioned at the top start
 * of a Box with a specified padding and allows observing the size changes of the content area.
 *
 * @param modifier The [Modifier] to be applied to the Box containing the label.
 *                 It's applied after the internal padding and size change listener.
 * @param label A composable lambda that defines the label content. This content will be
 *              placed at the top start of the Box.
 * @param onSizeChanged A callback function that is invoked when the size of the content area changes.
 *                      It provides the new [IntSize] of the content area.
 *
 * Example usage:
 * ```
 *  LabelContent(
 *      modifier = Modifier.background(Color.LightGray),
 *      label = { Text("My Label", color = Color.Black) },
 *      onSizeChanged = { size ->
 *          println("Content size changed: $size")
 *      }
 *  )
 * ```
 */
@Composable
fun LabelContent(
    modifier: Modifier,
    label: @Composable () -> Unit,
    onSizeChanged: (IntSize) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(start = 16.dp)
            .onSizeChanged { onSizeChanged(it) }
            .then(modifier),
        contentAlignment = Alignment.TopStart
    ) {
        label()
    }
}

/**
 * Represents the properties that define the visual appearance of a step in a stepper or similar component.
 *
 * @property maxWidth The maximum width allocated for the step and its associated line.
 *                   This helps in controlling the overall size and layout of the steps.
 * @property stepStyle The style properties for the step's circular or custom shape.
 *                     This includes color, size, and any other visual aspects of the step itself.
 * @property lineStyle The style properties for the line connecting the steps.
 *                     This includes color, thickness, and any other visual attributes of the connecting line.
 * @property lineTrackType The type of the line representing the uncompleted portion of the step progression.
 *                           This could be a solid line, dashed line, etc. See [LineType].
 * @property lineProgressType The type of the line representing the completed portion of the step progression.
 *                            This could be a solid line, dashed line, etc. See [LineType].
 * @property trackStrokeCap The type of stroke cap applied to the end of the line track (uncompleted portion).
 *                         This controls the shape of the line's end, e.g., round, square, or butt. See [StrokeCap].
 * @property progressStrokeCap The type of stroke cap applied to the end of the line progress (completed portion).
 *                            This controls the shape of the line's end, e.g., round, square, or butt. See [StrokeCap].
 */
data class StepProperties(
    val maxWidth: Dp,
    val stepStyle: StepStyle,
    val lineStyle: LineStyle,
    val lineTrackType: LineType,
    val lineProgressType: LineType,
    val trackStrokeCap: StrokeCap,
    val progressStrokeCap: StrokeCap
)

/**
 * A composable function that represents a step indicator in a multi-step process.
 * It displays the current state of a step, including its title, icon, content, or a default indicator.
 *
 * The StepIndicator provides a visual representation of a step's progress and content within a multi-step workflow.
 * It dynamically adapts its appearance based on the provided [StepState] and [Step] data.
 *
 * The behavior of the StepIndicator is determined by the following logic:
 *
 * 1. **Done State (with Checkmark):**
 *    - If the `stepState` is `StepState.Done` and no title, icon, or content are provided within the `step` lambda,
 *      a checkmark icon (using `Icons.Default.Done`) is displayed in white.
 *
 * 2. **Title Display:**
 *    - If the `step` lambda returns a `Step` object with a non-null and non-empty `title`, that title is displayed as text.
 *    - The text style is determined by the `stepStyle` lambda's `textStyle`.
 *
 * 3. **Icon Display:**
 *    - If the `step` lambda returns a `Step` object with a non-null `icon` (an `ImageVector`), that icon is displayed.
 *
 * 4. **Content Display:**
 *    - If the `step` lambda returns a `Step` object with a non-null `content` (a lambda representing content),
 *
 * 5. **Default Indicator:**
 *    - If all the above are not satisfied, it shows the default indicator.
 *
 *  */
@Stable
@Composable
fun StepIndicator(
    modifier: Modifier = Modifier,
    size: Dp,
    shape: Shape,
    containerColor: Color,
    borderStyle: BorderStyle,
    stepState: () -> StepState,
    step: () -> Step,
    stepStyle: () -> StepStyle
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(size)
            .clip(shape)
            .background(containerColor)
            .border(
                width = borderStyle.width,
                color = borderStyle.color,
                shape = borderStyle.shape
            )
            .zIndex(1f)
            .semantics { contentDescription = "Step Indicator" }
            .then(modifier)
    ) {
        when {
            stepState() == StepState.Done && step().title == null &&
                    step().icon == null && step().content == null -> {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = "Done",
                    modifier = Modifier.size(stepStyle().iconStyle.iconSize),
                    tint = Color.White
                )
            }

            !step().title.isNullOrEmpty() -> {
                Text(
                    text = step().title!!,
                    style = stepStyle().textStyle
                )
            }

            step().icon != null -> {
                Icon(
                    imageVector = step().icon!!,
                    contentDescription = null,
                    modifier = Modifier.size(stepStyle().iconStyle.iconSize)
                )
            }

            step().content != null -> {
                step().content?.invoke()
            }

            else -> {
                Box(
                    modifier = Modifier
                        .size((size.value * 0.75f).dp)
                        .background(stepStyle().iconStyle.iconTint)
                )
            }
        }
    }
}