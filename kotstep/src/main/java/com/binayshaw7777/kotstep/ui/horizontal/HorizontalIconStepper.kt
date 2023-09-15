package com.binayshaw7777.kotstep.ui.horizontal

import android.widget.ImageView
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.binayshaw7777.kotstep.components.HorizontalIconStep
import com.binayshaw7777.kotstep.components.HorizontalStep

@Composable
fun HorizontalIconStepper(
    modifier: Modifier = Modifier,
    totalSteps: Int,
    currentStep: Int = 1,
    lineThickness: Dp = 1.dp,
    stepSize: Dp = 28.dp,
    completedColor: Color = Color.Blue,
    incompleteColor: Color = Color.Gray,
    checkMarkColor: Color = Color.White,
    stepTitleOnIncompleteColor: Color = checkMarkColor,
    stepTitleOnCompleteColor: Color = completedColor,
    stepDescription: List<String> = List(totalSteps) { "" },
    stepIconsList: List<ImageVector>,
    stepIconsColorOnIncomplete: Color = checkMarkColor,
    stepIconsColorOnComplete: Color = incompleteColor,
    showCheckMarkOnDone: Boolean = false
) {

    val descriptionList = MutableList(totalSteps) { "" }

    stepDescription.forEachIndexed { index, element ->
        if (index < totalSteps)
            descriptionList[index] = element
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (stepAtIt in 1..totalSteps) {

            HorizontalIconStep(
                modifier = Modifier.weight(1F),
                stepTitle = descriptionList[stepAtIt - 1],
                lineThickness = lineThickness,
                stepSize = stepSize,
                isCurrent = stepAtIt == currentStep,
                isVisited = stepAtIt < currentStep,
                isCompleted = stepAtIt == totalSteps,
                incompleteColor = incompleteColor,
                completedColor = completedColor,
                checkMarkColor = checkMarkColor,
                stepIcon = stepIconsList[stepAtIt - 1],
                stepTitleOnIncompleteColor = stepTitleOnIncompleteColor,
                stepTitleOnCompleteColor = stepTitleOnCompleteColor,
                stepIconsColorOnComplete = stepIconsColorOnComplete,
                stepIconsColorOnIncomplete = stepIconsColorOnIncomplete,
                showCheckMarkOnDone = showCheckMarkOnDone
            )
        }
    }
}