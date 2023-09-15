package com.binayshaw7777.kotstep.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.atLeast

/**
 * Create a composable representing a single step in a vertical sequenced stepper with customizable appearance and behavior.
 *
 * Displays a single step in a vertical sequenced stepper, allowing customization of colors,
 * labels, and icons based on its state (current, visited, or completed).
 *
 * @param modifier The modifier for styling the composable. (Optional)
 * @param stepName The label or number associated with the step. (Required)
 * @param stepTitle The title or description of the step. (Optional)
 * @param isCurrent Whether the step is currently active or not. (Required)
 * @param isVisited Whether the step has been visited (prior to the current step) or not. (Required)
 * @param isCompleted Whether the step is completed or not. (Required)
 * @param lineThickness The thickness of the connecting line between steps. Defaults to 1.dp. (Optional)
 * @param stepSize The size of the step circle. Defaults to 28.dp. (Optional)
 * @param incompleteColor The color for incomplete steps. Defaults to [Color.Gray]. (Optional)
 * @param completedColor The color for completed steps. Defaults to [Color.Blue]. (Optional)
 * @param checkMarkColor The color of the checkmark symbol for completed steps. Defaults to [Color.White]. (Optional)
 * @param stepTitleOnIncompleteColor The color of step titles on incomplete steps. Defaults to [checkMarkColor]. (Optional)
 * @param stepTitleOnCompleteColor The color of step titles on completed steps. Defaults to [completedColor]. (Optional)
 * @param stepNameOnIncompleteColor The color of step names on incomplete steps. Defaults to [checkMarkColor]. (Optional)
 * @param stepNameOnCompleteColor The color of step names on completed steps. Defaults to [completedColor]. (Optional)
 */
@Composable
fun VerticalIconStep(
    modifier: Modifier = Modifier,
    stepTitle: String?,
    isCurrent: Boolean,
    isVisited: Boolean,
    isCompleted: Boolean,
    lineThickness: Dp = 1.dp,
    stepSize: Dp = 28.dp,
    stepIcon: ImageVector,
    incompleteColor: Color = Color.Gray,
    completedColor: Color = Color.Blue,
    checkMarkColor: Color = Color.White,
    stepTitleOnIncompleteColor: Color = checkMarkColor,
    stepTitleOnCompleteColor: Color = completedColor,
    stepIconsColorOnComplete: Color = checkMarkColor,
    stepIconsColorOnIncomplete: Color = completedColor,
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

        // Display is continuous line if not completed
        if (isCompleted.not()) {
            Divider(
                modifier = Modifier.width(lineThickness).constrainAs(line) {
                    height = Dimension.fillToConstraints.atLeast(20.dp)
                    top.linkTo(circleBoxItem.bottom)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(circleBoxItem.start)
                    end.linkTo(circleBoxItem.end)
                },
                color = itemColor
            )
        }

        // Display Step Title if available
        stepTitle?.let {
            Text(
                modifier = Modifier.constrainAs(text) {
                    top.linkTo(circleBoxItem.top)
                    start.linkTo(circleBoxItem.end, margin = 3.dp)
                    end.linkTo(parent.end)
                    bottom.linkTo(circleBoxItem.bottom)
                },
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                text = it,
                color = titleColor
            )
        }

        Surface(
            modifier = Modifier
                .size(stepSize)
                .constrainAs(circleBoxItem) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
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
    }
}