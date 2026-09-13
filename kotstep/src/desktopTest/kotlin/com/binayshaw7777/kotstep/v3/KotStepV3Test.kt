package com.binayshaw7777.kotstep.v3

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.getUnclippedBoundsInRoot
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import com.binayshaw7777.kotstep.v3.model.step.StepLayoutStyle
import com.binayshaw7777.kotstep.v3.model.style.KotStepStyle
import com.binayshaw7777.kotstep.v3.util.ExperimentalKotStep
import kotlin.math.abs
import kotlin.test.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalKotStep::class, ExperimentalTestApi::class)
class KotStepV3Test {

    @Test
    fun horizontal_stepIndicatorsExposeExpectedSemantics() = runComposeUiTest {
        setContent {
            MaterialTheme {
                KotStep(
                    currentStep = { 1f },
                    style = KotStepStyle(stepLayoutStyle = StepLayoutStyle.Horizontal)
                ) {
                    step(title = "Account")
                    step(title = "Verify")
                    step(title = "Done")
                }
            }
        }

        onNodeWithTag("kotstep_indicator_0", useUnmergedTree = true)
            .assert(hasContentDescription("Step: Account"))
            .assert(hasStateDescription("Completed"))

        onNodeWithTag("kotstep_indicator_1", useUnmergedTree = true)
            .assert(hasContentDescription("Step: Verify"))
            .assert(hasStateDescription("Current step"))

        onNodeWithTag("kotstep_indicator_2", useUnmergedTree = true)
            .assert(hasContentDescription("Step: Done"))
            .assert(hasStateDescription("Not completed"))
    }

    @Test
    fun vertical_stepIndicatorsExposeExpectedSemantics() = runComposeUiTest {
        setContent {
            MaterialTheme {
                KotStep(
                    currentStep = { 2f },
                    style = KotStepStyle(stepLayoutStyle = StepLayoutStyle.Vertical)
                ) {
                    step(icon = Icons.Default.Star)
                    step(content = { Text("C") })
                    step(title = "Final")
                }
            }
        }

        onNodeWithTag("kotstep_indicator_0", useUnmergedTree = true)
            .assert(hasContentDescription("Step"))
            .assert(hasStateDescription("Completed"))

        onNodeWithTag("kotstep_indicator_1", useUnmergedTree = true)
            .assert(hasContentDescription("Step"))
            .assert(hasStateDescription("Completed"))

        onNodeWithTag("kotstep_indicator_2", useUnmergedTree = true)
            .assert(hasContentDescription("Step: Final"))
            .assert(hasStateDescription("Current step"))
    }

    @Test
    fun horizontal_rendersLeadingAndTrailingLabels() = runComposeUiTest {
        setContent {
            MaterialTheme {
                KotStep(
                    currentStep = { 0f },
                    style = KotStepStyle(stepLayoutStyle = StepLayoutStyle.Horizontal)
                ) {
                    step(
                        title = "One",
                        leadingLabel = { Text("Lead One") },
                        trailingLabel = { Text("Trail One") }
                    )
                    step(
                        title = "Two",
                        leadingLabel = { Text("Lead Two") },
                        trailingLabel = { Text("Trail Two") }
                    )
                }
            }
        }

        onAllNodesWithText("Lead One").assertCountEquals(1)
        onAllNodesWithText("Trail One").assertCountEquals(1)
        onAllNodesWithText("Lead Two").assertCountEquals(1)
        onAllNodesWithText("Trail Two").assertCountEquals(1)
    }

    @Test
    fun vertical_rendersLeadingAndTrailingLabels() = runComposeUiTest {
        setContent {
            MaterialTheme {
                KotStep(
                    currentStep = { 0f },
                    style = KotStepStyle(stepLayoutStyle = StepLayoutStyle.Vertical)
                ) {
                    step(
                        title = "One",
                        leadingLabel = { Text("Before One") },
                        trailingLabel = { Text("After One") }
                    )
                    step(
                        title = "Two",
                        leadingLabel = { Text("Before Two") },
                        trailingLabel = { Text("After Two") }
                    )
                }
            }
        }

        onAllNodesWithText("Before One").assertCountEquals(1)
        onAllNodesWithText("After One").assertCountEquals(1)
        onAllNodesWithText("Before Two").assertCountEquals(1)
        onAllNodesWithText("After Two").assertCountEquals(1)
    }

    @Test
    fun horizontal_collapsingStepPreservesIndicatorAlignment() = runComposeUiTest {
        setContent {
            MaterialTheme {
                KotStep(
                    currentStep = { 0f },
                    style = KotStepStyle(stepLayoutStyle = StepLayoutStyle.Horizontal)
                ) {
                    step(
                        title = "One",
                        leadingLabel = { Text("Lead") },
                        trailingLabel = { Text("A much taller trailing label\nwith two lines") },
                        isCollapsible = true
                    )
                    step(
                        title = "Two",
                        trailingLabel = { Text("Short") }
                    )
                }
            }
        }

        val beforeY0 = onNodeWithTag("kotstep_indicator_0", useUnmergedTree = true).getUnclippedBoundsInRoot().top.value
        val beforeY1 = onNodeWithTag("kotstep_indicator_1", useUnmergedTree = true).getUnclippedBoundsInRoot().top.value
        assertTrue(abs(beforeY0 - beforeY1) <= 1f)

        onNodeWithTag("kotstep_step_0", useUnmergedTree = true).performClick()
        waitForIdle()

        onAllNodesWithText("Lead").assertCountEquals(0)
        onAllNodesWithText("A much taller trailing label\nwith two lines").assertCountEquals(0)

        val afterY0 = onNodeWithTag("kotstep_indicator_0", useUnmergedTree = true).getUnclippedBoundsInRoot().top.value
        val afterY1 = onNodeWithTag("kotstep_indicator_1", useUnmergedTree = true).getUnclippedBoundsInRoot().top.value
        assertTrue(abs(afterY0 - afterY1) <= 1f)
    }

    @Test
    fun vertical_collapsingStepPreservesIndicatorAlignment() = runComposeUiTest {
        setContent {
            MaterialTheme {
                KotStep(
                    currentStep = { 0f },
                    style = KotStepStyle(stepLayoutStyle = StepLayoutStyle.Vertical)
                ) {
                    step(
                        title = "One",
                        leadingLabel = { Text("Very wide leading label") },
                        trailingLabel = { Text("Wide trailing label") },
                        isCollapsible = true
                    )
                    step(
                        title = "Two",
                        trailingLabel = { Text("Short") }
                    )
                }
            }
        }

        val beforeX0 = onNodeWithTag("kotstep_indicator_0", useUnmergedTree = true).getUnclippedBoundsInRoot().left.value
        val beforeX1 = onNodeWithTag("kotstep_indicator_1", useUnmergedTree = true).getUnclippedBoundsInRoot().left.value
        assertTrue(abs(beforeX0 - beforeX1) <= 1f)

        onNodeWithTag("kotstep_step_0", useUnmergedTree = true).performClick()
        waitForIdle()

        onAllNodesWithText("Very wide leading label").assertCountEquals(0)
        onAllNodesWithText("Wide trailing label").assertCountEquals(0)

        val afterX0 = onNodeWithTag("kotstep_indicator_0", useUnmergedTree = true).getUnclippedBoundsInRoot().left.value
        val afterX1 = onNodeWithTag("kotstep_indicator_1", useUnmergedTree = true).getUnclippedBoundsInRoot().left.value
        assertTrue(abs(afterX0 - afterX1) <= 1f)
    }

    @Test
    fun horizontal_allStepsCompletedWhenCurrentStepEqualsTotalSteps() = runComposeUiTest {
        setContent {
            MaterialTheme {
                KotStep(
                    currentStep = { 4f },
                    style = KotStepStyle(stepLayoutStyle = StepLayoutStyle.Horizontal)
                ) {
                    step(title = "1")
                    step(title = "2")
                    step(title = "3")
                    step(title = "4")
                }
            }
        }

        for (i in 0..3) {
            onNodeWithTag("kotstep_indicator_$i", useUnmergedTree = true)
                .assert(hasStateDescription("Completed"))
        }
    }

    @Test
    fun horizontal_negativeCurrentStepMarksAllAsNotCompleted() = runComposeUiTest {
        setContent {
            MaterialTheme {
                KotStep(
                    currentStep = { -0.5f },
                    style = KotStepStyle(stepLayoutStyle = StepLayoutStyle.Horizontal)
                ) {
                    step(title = "1")
                    step(title = "2")
                    step(title = "3")
                    step(title = "4")
                }
            }
        }

        for (i in 0..3) {
            onNodeWithTag("kotstep_indicator_$i", useUnmergedTree = true)
                .assert(hasStateDescription("Not completed"))
        }
    }

    private fun hasContentDescription(value: String): SemanticsMatcher {
        return SemanticsMatcher.expectValue(SemanticsProperties.ContentDescription, listOf(value))
    }

    private fun hasStateDescription(value: String): SemanticsMatcher {
        return SemanticsMatcher.expectValue(SemanticsProperties.StateDescription, value)
    }
}