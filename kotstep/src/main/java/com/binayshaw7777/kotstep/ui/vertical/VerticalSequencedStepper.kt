package com.binayshaw7777.kotstep.ui.vertical

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.binayshaw7777.kotstep.components.VerticalStep

/**
 * Create a composable representing a vertical sequenced stepper with customizable appearance and behavior.
 *
 * Displays a vertical sequenced stepper with a specified number of steps, allowing customization
 * of colors, labels, and icons for each step based on its state (current, visited, or completed).
 *
 * @param modifier The modifier for styling the composable. (Optional)
 * @param totalSteps The total number of steps in the stepper. (Required)
 * @param currentStep The current active step. Defaults to the first step (0). (Optional)
 * @param lineThickness The thickness of the connecting line between steps. Defaults to 1.dp. (Optional)
 * @param stepSize The size of the step circles. Defaults to 28.dp. (Optional)
 * @param completedColor The color for completed steps. Defaults to [Color.Blue]. (Optional)
 * @param incompleteColor The color for incomplete steps. Defaults to [Color.Gray]. (Optional)
 * @param checkMarkColor The color of the checkmark symbol for completed steps. Defaults to [Color.White]. (Optional)
 * @param stepTitleOnIncompleteColor The color of step titles on incomplete steps. Defaults to [checkMarkColor]. (Optional)
 * @param stepTitleOnCompleteColor The color of step titles on completed steps. Defaults to [completedColor]. (Optional)
 * @param stepNameOnIncompleteColor The color of step names on incomplete steps. Defaults to [checkMarkColor]. (Optional)
 * @param stepNameOnCompleteColor The color of step names on completed steps. Defaults to [completedColor]. (Optional)
 * @param stepDescription A list of step descriptions. Each description corresponds to a step. (Optional)
 *
 * @see <a href="https://m1.material.io/components/steppers.html#steppers-types-of-steppers">Material Components - Stepper</a>
 */
@Composable
fun VerticalSequencedStepper(
    modifier: Modifier = Modifier,
    totalSteps: Int,
    currentStep: Int = 0,
    lineThickness: Dp = 1.dp,
    stepSize: Dp = 28.dp,
    stepShape: Shape = CircleShape,
    completedColor: Color = Color.Blue,
    incompleteColor: Color = Color.Gray,
    checkMarkColor: Color = Color.White,
    stepTitleOnIncompleteColor: Color = checkMarkColor,
    stepTitleOnCompleteColor: Color = completedColor,
    stepNameOnIncompleteColor: Color = checkMarkColor,
    stepNameOnCompleteColor: Color = completedColor,
    stepDescription: List<String> = List(totalSteps) { "" }
) {

    val descriptionList = MutableList(totalSteps) { "" }

    stepDescription.forEachIndexed { index, element ->
        if (index < totalSteps)
            descriptionList[index] = element
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        for (stepAtIt in 1..totalSteps) {

            VerticalStep(
                stepName = stepAtIt.toString(),
                lineThickness = lineThickness,
                stepSize = stepSize,
                stepShape = stepShape,
                stepTitle = descriptionList[stepAtIt - 1],
                isCurrent = stepAtIt == currentStep,
                isVisited = stepAtIt < currentStep,
                isCompleted = stepAtIt == totalSteps,
                incompleteColor = incompleteColor,
                completedColor = completedColor,
                checkMarkColor = checkMarkColor,
                stepTitleOnIncompleteColor = stepTitleOnIncompleteColor,
                stepTitleOnCompleteColor = stepTitleOnCompleteColor,
                stepNameOnCompleteColor = stepNameOnCompleteColor,
                stepNameOnIncompleteColor = stepNameOnIncompleteColor
            )
        }
    }
}