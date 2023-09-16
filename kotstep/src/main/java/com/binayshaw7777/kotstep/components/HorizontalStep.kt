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
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout


/**
 * Create a single step in a sequenced stepper with customizable appearance and behavior.
 *
 * Displays a single step in a horizontal sequenced stepper, allowing customization of colors,
 * labels, and icons based on its state (current, visited, or completed).
 *
 * @param modifier The modifier for styling the composable. (Optional)
 * @param stepName The label or number associated with the step. (Required)
 * @param stepTitle The title or description of the step. (Optional)
 * @param isCurrent Whether the step is currently active or not. (Required)
 * @param isVisited Whether the step has been visited (prior to the current step) or not. (Required)
 * @param isCompleted Whether the step is completed or not. (Required)
 * @param incompleteColor The color for incomplete steps. Defaults to [Color.Gray]. (Optional)
 * @param completedColor The color for completed steps. Defaults to [Color.Blue]. (Optional)
 * @param checkMarkColor The color of the checkmark symbol for completed steps. Defaults to [Color.White]. (Optional)
 * @param stepTitleOnIncompleteColor The color of step titles on incomplete steps. Defaults to [checkMarkColor]. (Optional)
 * @param stepTitleOnCompleteColor The color of step titles on completed steps. Defaults to [completedColor]. (Optional)
 * @param stepNameOnIncompleteColor The color of step names on incomplete steps. Defaults to [checkMarkColor]. (Optional)
 * @param stepNameOnCompleteColor The color of step names on completed steps. Defaults to [completedColor]. (Optional)
 */
@Composable
fun HorizontalStep(
    modifier: Modifier = Modifier,
    stepName: String,
    stepTitle: String?,
    isCurrent: Boolean,
    isVisited: Boolean,
    isCompleted: Boolean,
    lineThickness: Dp = 1.dp,
    stepSize: Dp = 28.dp,
    stepShape: Shape,
    incompleteColor: Color = Color.Gray,
    completedColor: Color = Color.Blue,
    checkMarkColor: Color = Color.White,
    stepTitleOnIncompleteColor: Color = checkMarkColor,
    stepTitleOnCompleteColor: Color = completedColor,
    stepNameOnIncompleteColor: Color = checkMarkColor,
    stepNameOnCompleteColor: Color = completedColor
) {

    val transition = updateTransition(targetState = isVisited, label = "")

    val itemColor: Color by transition.animateColor(label = "itemColor") {
        if (it) completedColor else incompleteColor
    }

    val titleColor: Color by transition.animateColor(label = "titleColor") {
        if (it || isCurrent) stepTitleOnCompleteColor else stepTitleOnIncompleteColor
    }

    val stepNameColor: Color by transition.animateColor(label = "stepNameColor") {
        if (it || isCurrent) stepNameOnCompleteColor else stepNameOnIncompleteColor
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
            shape = stepShape,
            border = borderStrokeColor,
            color = itemColor,
        ) {

            // Defines Text or Tick/Done Icon
            Box(contentAlignment = Alignment.Center) {
                if (isVisited) {
                    Icon(
                        imageVector = Icons.Default.Done,
                        tint = checkMarkColor,
                        contentDescription = "Done"
                    )
                } else {
                    Text(text = stepName, color = stepNameColor, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
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