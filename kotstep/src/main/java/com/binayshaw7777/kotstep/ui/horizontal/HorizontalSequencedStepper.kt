package com.binayshaw7777.kotstep.ui.horizontal

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.binayshaw7777.kotstep.components.HorizontalStep

/**
 * Create a Horizontal Sequenced Stepper in a Jetpack Compose layout. [HorizontalStep]
 *
 * Displays a horizontal sequence of steps with customizable options for styling and colors.
 *
 * @param modifier The modifier for styling the composable. (Optional)
 * @param totalSteps The total number of steps in the sequence. (Required)
 * @param currentStep The currently active step. Defaults to the first step (1). (Optional)
 * @param completedColor The color for completed steps. Defaults to [Color.Blue]. (Optional)
 * @param incompleteColor The color for incomplete steps. Defaults to [Color.Gray]. (Optional)
 * @param checkMarkColor The color of the checkmark symbol for completed steps. Defaults to [Color.White]. (Optional)
 * @param stepTitleOnIncompleteColor The color of step titles on incomplete steps. Defaults to [checkMarkColor]. (Optional)
 * @param stepTitleOnCompleteColor The color of step titles on completed steps. Defaults to [completedColor]. (Optional)
 * @param stepNameOnIncompleteColor The color of step names on incomplete steps. Defaults to [checkMarkColor]. (Optional)
 * @param stepNameOnCompleteColor The color of step names on completed steps. Defaults to [completedColor]. (Optional)
 * @param stepDescription A list of descriptions for each step. The list size should match `totalSteps`. (Optional)
 *
 * Example usage:
 * ```
 * HorizontalSequencedStepper(
 *     modifier = Modifier.padding(16.dp),
 *     totalSteps = 5,
 *     currentStep = 3,
 *     completedColor = Color.Green,
 *     incompleteColor = Color.Gray,
 *     stepDescription = listOf(
 *         "Step 1",
 *         "Step 2",
 *         "Step 3",
 *         "Step 4",
 *         "Step 5"
 *     )
 * )
 * ```
 *
 * @see <a href="https://m1.material.io/components/steppers.html#steppers-types-of-steppers">Material Components - Stepper</a>
 */
@Composable
fun HorizontalSequencedStepper(
    modifier: Modifier = Modifier,
    totalSteps: Int,
    currentStep: Int = 1,
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

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (stepAtIt in 1..totalSteps) {

            HorizontalStep(
                modifier = Modifier.weight(1F),
                stepName = stepAtIt.toString(),
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