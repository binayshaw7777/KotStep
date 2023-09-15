package com.binayshaw7777.kotstep.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout

/**
 * Create a composable representing a single step in a horizontal icon-based stepper with customizable appearance and behavior.
 *
 * Displays a single step in a horizontal icon-based stepper, allowing customization of colors,
 * icons, labels, and icons based on its state (current, visited, or completed).
 *
 * @param modifier The modifier for styling the composable. (Optional)
 * @param stepTitle The title or description of the step. (Optional)
 * @param isCurrent Whether the step is currently active or not. (Required)
 * @param isVisited Whether the step has been visited (prior to the current step) or not. (Required)
 * @param isCompleted Whether the step is completed or not. (Required)
 * @param lineThickness The thickness of the connecting line between steps. Defaults to 1.dp. (Optional)
 * @param stepSize The size of the step icon. Defaults to 28.dp. (Optional)
 * @param incompleteColor The color for incomplete steps. Defaults to [Color.Gray]. (Optional)
 * @param completedColor The color for completed steps. Defaults to [Color.Blue]. (Optional)
 * @param checkMarkColor The color of the checkmark symbol for completed steps. Defaults to [Color.White]. (Optional)
 * @param stepTitleOnIncompleteColor The color of step titles on incomplete steps. Defaults to [checkMarkColor]. (Optional)
 * @param stepTitleOnCompleteColor The color of step titles on completed steps. Defaults to [completedColor]. (Optional)
 * @param stepIcon The icon to display for the step. (Required)
 * @param stepIconsColorOnIncomplete The color of step icons on incomplete steps. Defaults to [checkMarkColor]. (Optional)
 * @param stepIconsColorOnComplete The color of step icons on completed steps. Defaults to [incompleteColor]. (Optional)
 * @param showCheckMarkOnDone Whether to display a checkmark icon for completed steps. (Optional)
 */
@Composable
fun HorizontalIconStep(
    modifier: Modifier = Modifier,
    stepTitle: String?,
    isCurrent: Boolean,
    isVisited: Boolean,
    isCompleted: Boolean,
    lineThickness: Dp = 1.dp,
    stepSize: Dp = 28.dp,
    incompleteColor: Color = Color.Gray,
    completedColor: Color = Color.Blue,
    checkMarkColor: Color = Color.White,
    stepTitleOnIncompleteColor: Color = checkMarkColor,
    stepTitleOnCompleteColor: Color = completedColor,
    stepIcon: ImageVector,
    stepIconsColorOnIncomplete: Color = checkMarkColor,
    stepIconsColorOnComplete: Color = incompleteColor,
    showCheckMarkOnDone: Boolean = false
) {

    val transition = updateTransition(targetState = isVisited, label = "")

    val itemColor: Color by transition.animateColor(label = "itemColor") {
        if (it) completedColor else incompleteColor
    }

    val titleColor: Color by transition.animateColor(label = "titleColor") {
        if (it || isCurrent) stepTitleOnCompleteColor else stepTitleOnIncompleteColor
    }

    val stepIconTintColor: Color by transition.animateColor(label = "stepIconTintColor") {
        if (it || isCurrent) stepIconsColorOnComplete else stepIconsColorOnIncomplete
    }

    val borderStrokeColor: BorderStroke = if (isCurrent || isVisited) {
        BorderStroke(2.dp, completedColor)
    } else {
        BorderStroke(2.dp, incompleteColor)
    }

    ConstraintLayout(modifier = modifier) {

        val (circleBoxItem, text, line) = createRefs()

        Surface(
            modifier = Modifier
                .size(stepSize)
                .constrainAs(circleBoxItem) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                },
            shape = CircleShape,
            border = borderStrokeColor,
            color = itemColor,
        ) {

            // Defines Text or Tick/Done Icon
            Box(contentAlignment = Alignment.Center) {

                Icon(
                    imageVector = if (isVisited && showCheckMarkOnDone) Icons.Default.Done else stepIcon,
                    tint = if (isVisited && showCheckMarkOnDone) checkMarkColor else stepIconTintColor,
                    contentDescription = "Done"
                )
            }
        }

        // Display Step Title if available
        stepTitle?.let {
            Text(
                modifier = Modifier.constrainAs(text) {
                    top.linkTo(circleBoxItem.bottom, margin = 3.dp)
                    start.linkTo(circleBoxItem.start)
                    end.linkTo(circleBoxItem.end)
                    bottom.linkTo(parent.bottom)
                },
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                text = it,
                color = titleColor
            )
        }

        // Display is continuous line if not completed
        if (isCompleted.not()) {
            Divider(
                modifier = Modifier.constrainAs(line) {
                    top.linkTo(circleBoxItem.top)
                    bottom.linkTo(circleBoxItem.bottom)
                    start.linkTo(circleBoxItem.end)
                },
                color = itemColor,
                thickness = lineThickness
            )
        }
    }
}