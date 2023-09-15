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

    val stepIconTintColor: Color by transition.animateColor(label = "stepNameColor") {
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