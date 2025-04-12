package com.binayshaw7777.kotstep.v2.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.binayshaw7777.kotstep.v2.model.step.Step
import com.binayshaw7777.kotstep.v2.model.step.StepLayoutStyle
import com.binayshaw7777.kotstep.v2.model.step.StepState
import com.binayshaw7777.kotstep.v2.model.style.KotStepStyle
import com.binayshaw7777.kotstep.v2.model.style.LineStyle
import com.binayshaw7777.kotstep.v2.model.style.LineStyles
import com.binayshaw7777.kotstep.v2.model.style.StepStyle
//
//@Composable
//fun StepItem(
//    step: Step,
//    index: Int,
//    currentStep: () -> Float,
//    style: KotStepStyle
//) {
//    val progress = currentStep() - index.toFloat()
//
//    val state = when {
//        index < currentStep().toInt() -> {
//            step.onDone()
//            StepState.Done
//        }
//        index == currentStep().toInt() -> StepState.Current(progress.coerceIn(0f, 1f))
//        else -> StepState.Todo
//    }
//
//    val stepStyle = when (state) {
//        is StepState.Todo -> style.stepStyle.onTodo
//        is StepState.Current -> style.stepStyle.onCurrent
//        is StepState.Done -> style.stepStyle.onDone
//    }
//
//    val lineStyle = when (state) {
//        is StepState.Todo -> style.lineStyle.onTodo
//        is StepState.Current -> style.lineStyle.onCurrent
//        is StepState.Done -> style.lineStyle.onDone
//    }















//    val stepStyle = baseStyle.copy(
//        isDashed = style.stepLayoutStyle == StepLayoutStyle.Horizontal && baseStyle.isDashed,
//        isTab = (style.stepLayoutStyle == StepLayoutStyle.Horizontal || style.stepLayoutStyle == StepLayoutStyle.Vertical) && baseStyle.isTab
//    )

//    Box(
//        modifier = Modifier
//            .clickable(enabled = step.onClick != null) { step.onClick?.invoke() }
//            .padding(4.dp)
//    ) {
//        when (style.stepLayoutStyle) {
//            StepLayoutStyle.VerticalCentered -> {
//                if (style.stepLayoutStyle.name.contains("Vertical")) {
//                    Row(verticalAlignment = Alignment.CenterVertically) {
//                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                            StepIndicator(state, stepStyle)
//                            LineVertical(state, stepStyle)
//                        }
//                        LabelRectangle(step, true)
//                        step.label?.let { it() }
//                    }
//                } else {
//                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                        StepIndicator(state, stepStyle)
//                        LineVertical(state, stepStyle)
//                        LabelRectangle(step, true)
//                        step.content()
//                    }
//                }
//            }
//
//            StepLayoutStyle.Vertical -> {
//                if (style.stepLayoutStyle.name.contains("Vertical")) {
//                    Row(verticalAlignment = Alignment.CenterVertically) {
//                        Column {
//                            StepIndicator(state, stepStyle)
//                            LineVertical(state, stepStyle)
//                        }
//                        LabelRectangle(step, false)
//                        step.content()
//                    }
//                } else {
//                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                        StepIndicator(state, stepStyle)
//                        LineVertical(state, stepStyle)
//                        LabelRectangle(step, false)
//                        step.content()
//                    }
//                }
//            }
//
//            StepLayoutStyle.HorizontalCentered -> {
//                if (style.stepLayoutStyle.name.contains("Horizontal")) {
//                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                        Row {
//                            StepIndicator(state, stepStyle)
//                            LineHorizontal(state, stepStyle)
//                        }
//                        LabelRectangle(step, true)
//                        step.content()
//                    }
//                } else {
//                    Row(verticalAlignment = Alignment.CenterVertically) {
//                        Column {
//                            StepIndicator(state, stepStyle)
//                            LineHorizontal(state, stepStyle)
//                        }
//                        LabelRectangle(step, true)
//                        step.content()
//                    }
//                }
//            }
//
//            StepLayoutStyle.Horizontal -> {
//                if (style.stepLayoutStyle.name.contains("Horizontal")) {
//                    Column(verticalArrangement = Arrangement.Center) {
//                        Row {
//                            StepIndicator(state, stepStyle)
//                            LineHorizontal(state, stepStyle)
//                        }
//                        LabelRectangle(step, false)
//                        step.content()
//                    }
//                } else {
//                    Row(verticalAlignment = Alignment.CenterVertically) {
//                        Column {
//                            StepIndicator(state, stepStyle)
//                            LineHorizontal(state, stepStyle)
//                        }
//                        LabelRectangle(step, false)
//                        step.content()
//                    }
//                }
//            }
//
//            StepLayoutStyle.HorizontalLine -> {
//                if (style.stepLayoutStyle.name.contains("Horizontal")) {
//                    Column {
//                        Row {
//                            LineHorizontal(state, stepStyle, isLeft = true)
//                            StepIndicator(state, stepStyle)
//                            LineHorizontal(state, stepStyle)
//                        }
//                        LabelRectangle(step, true)
//                        step.content()
//                    }
//                } else {
//                    Row {
//                        Column {
//                            LineHorizontal(state, lineStyle, isLeft = true)
//                            StepIndicator(state, stepStyle)
//                            LineHorizontal(state, stepStyle)
//                        }
//                        LabelRectangle(step, true)
//                        step.content()
//                    }
//                }
//            }
//        }
//    }
//}

//@Composable
//fun StepItem(
//    step: Step,
//    index: Int,
//    currentStep: Float,
//    style: KotStepStyle
//) {
//    // Calculate state with progress
//    val progress = currentStep - index.toFloat()
//    val state = when {
//        index < currentStep.toInt() -> StepState.Done
//        index == currentStep.toInt() -> StepState.Current(progress.coerceIn(0f, 1f)) // Pass progress here
//        else -> StepState.Todo
//    }
//
//    // Determine style based on state, then apply additional properties
//    val baseStyle = when (state) {
//        is StepState.Todo -> style.todo
//        is StepState.Current -> style.current
//        is StepState.Done -> style.done
//    }
//
//    val stepStyle = baseStyle.copy(
//        isDashed = style.stepLayoutStyle == StepLayoutStyle.Horizontal && baseStyle.isDashed,
//        isTab = (style.stepLayoutStyle == StepLayoutStyle.Horizontal || style.stepLayoutStyle == StepLayoutStyle.Vertical) && baseStyle.isTab
//    )
//
//    Box(
//        modifier = Modifier
//            .clickable(enabled = step.onClick != null) { step.onClick?.invoke() }
//            .padding(4.dp)
//    ) {
//        when (style.stepLayoutStyle) {
//            StepLayoutStyle.VerticalCentered -> VerticalCenteredLayout(step, state, stepStyle)
//            StepLayoutStyle.Vertical -> VerticalLayout(step, state, stepStyle)
//            StepLayoutStyle.HorizontalCentered -> HorizontalCenteredLayout(step, state, stepStyle)
//            StepLayoutStyle.Horizontal -> HorizontalLayout(step, state, stepStyle)
//            StepLayoutStyle.HorizontalLine -> HorizontalLineLayout(step, state, stepStyle)
//        }
//    }
//}
//
//@Composable
//private fun VerticalCenteredLayout(step: Step, state: StepState, style: StepStyle) {
//    Column(horizontalAlignment = Alignment.CenterHorizontally) {
//        StepIndicator(state, style)
//        LineVertical(state, style)
//        LabelRectangle(step, true)
//        step.content()
//    }
//}
//
//@Composable
//private fun VerticalLayout(step: Step, state: StepState, style: StepStyle) {
//    Row(verticalAlignment = Alignment.CenterVertically) {
//        Column {
//            StepIndicator(state, style)
//            LineVertical(state, style)
//        }
//        LabelRectangle(step, false)
//        step.content()
//    }
//}
//
//@Composable
//private fun HorizontalCenteredLayout(step: Step, state: StepState, style: StepStyle) {
//    Column(horizontalAlignment = Alignment.CenterHorizontally) {
//        Row {
//            StepIndicator(state, style)
//            LineHorizontal(state, style)
//        }
//        LabelRectangle(step, true)
//        step.content()
//    }
//}
//
//@Composable
//private fun HorizontalLayout(step: Step, state: StepState, style: StepStyle) {
//    Row(verticalAlignment = Alignment.CenterVertically) {
//        StepIndicator(state, style)
//        LineHorizontal(state, style)
//        LabelRectangle(step, false)
//        step.content()
//    }
//}
//
//@Composable
//private fun HorizontalLineLayout(step: Step, state: StepState, style: StepStyle) {
//    Column {
//        Row {
//            LineHorizontal(state, style, isLeft = true)
//            StepIndicator(state, style)
//            LineHorizontal(state, style)
//        }
//        LabelRectangle(step, true)
//        step.content()
//    }
//}
////
//@Composable
//private fun StepIndicator(state: StepState, style: StepStyle) {
//    Box(
//        modifier = Modifier
//            .size(style.stepSize)
//            .background(
//                when (state) {
//                    StepState.Todo -> style.stepColor
//                    is StepState.Current -> style.stepColor.copy(alpha = state.progress)
//                    StepState.Done -> style.stepColor
//                },
//                style.stepShape
//            )
//            .clip(style.stepShape)
//    ) {
//        if (state == StepState.Done) {
//            Text("✓", color = Color.White, modifier = Modifier.align(Alignment.Center))
//        }
//    }
//}
//
//
//
//
//@Composable
//private fun StepIndicator(
//    modifier: Modifier = Modifier,
//    text: String? = null,
//    iconPainter: Painter? = null,
//    iconImageVector: ImageVector? = null,
//    stepState: StepState,
//    stepStyle: StepStyle,
//    showCheckMarkIcon: Boolean = false,
//) {
//    Box(
//        contentAlignment = Alignment.Center,
//        modifier = Modifier
//            .size(stepStyle.stepSize)
//            .clip(stepStyle.stepShape)
//            .border(stepStyle.border)
//            .background(stepStyle.stepColor)
//            .then(modifier)
//    ) {
//        if (stepState is StepState.Done && showCheckMarkIcon) {
//            Icon(
//                imageVector = Icons.Default.Done,
//                tint = stepStyle.iconStyle.iconTint,
//                contentDescription = "Done"
//            )
//        } else {
//
//        }
//    }
//}
//
//
//
//
//
//
//
//
////
//@Composable
//private fun LineVertical(state: StepState, style: LineStyle) {
//    Box(
//        modifier = Modifier
//            .width(style.lineThickness)
//            .height(style.lineLength)
//            .background(
//                when (state) {
//                    StepState.Todo -> style.lineColor
//                    is StepState.Current -> style.lineColor.copy(alpha = state.progress)
//                    StepState.Done -> style.lineColor
//                }
//            )
//    )
//}
//
////
//@Composable
//private fun LineHorizontal(state: StepState, style: LineStyle, isLeft: Boolean = false) {
//    Box(
//        modifier = Modifier
//            .height(style.lineThickness)
//            .width(if (isLeft) style.lineLength / 2 else style.lineLength)
//            .background(
//                when (state) {
//                    StepState.Todo -> style.lineColor
//                    is StepState.Current -> style.lineColor.copy(alpha = state.progress)
//                    StepState.Done -> style.lineColor
//                }
//            )
//    )
//}
//
////
//@Composable
//private fun LabelRectangle(step: Step, isCentered: Boolean) {
//    Box(
//        modifier = Modifier
//            .background(Color.DarkGray, RoundedCornerShape(4.dp))
//            .padding(8.dp),
//        contentAlignment = if (isCentered) Alignment.Center else Alignment.CenterStart
//    ) {
//        if (step.title != null) {
//            Text(step.title!!, color = Color.White)
//        } else if (step.imageVectorIcon != null) {
//            step.imageVectorIcon
//        } else if (step.painterIcon != null) {
//            step.painterIcon
//        } else if (step.content != null) {
//            step.content!!.invoke()
//        }
//    }
//}