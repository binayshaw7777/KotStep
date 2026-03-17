package com.binayshaw7777.kotstep.v3

import androidx.activity.ComponentActivity
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.getUnclippedBoundsInRoot
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.binayshaw7777.kotstep.v3.model.step.StepLayoutStyle
import com.binayshaw7777.kotstep.v3.model.style.KotStepStyle
import com.binayshaw7777.kotstep.v3.util.ExperimentalKotStep
import kotlin.math.abs
import org.junit.Assert.assertTrue
import org.junit.Test

@OptIn(ExperimentalKotStep::class)
class KotStepV3Test {

    @get:org.junit.Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun horizontal_stepIndicatorsExposeExpectedSemantics() {
        composeTestRule.setContent {
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

        composeTestRule.onNodeWithTag("kotstep_indicator_0")
            .assert(hasContentDescription("Step: Account"))
            .assert(hasStateDescription("Completed"))

        composeTestRule.onNodeWithTag("kotstep_indicator_1")
            .assert(hasContentDescription("Step: Verify"))
            .assert(hasStateDescription("Current step"))

        composeTestRule.onNodeWithTag("kotstep_indicator_2")
            .assert(hasContentDescription("Step: Done"))
            .assert(hasStateDescription("Not completed"))
    }

    @Test
    fun vertical_stepIndicatorsExposeExpectedSemantics() {
        composeTestRule.setContent {
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

        composeTestRule.onNodeWithTag("kotstep_indicator_0")
            .assert(hasContentDescription("Step"))
            .assert(hasStateDescription("Completed"))

        composeTestRule.onNodeWithTag("kotstep_indicator_1")
            .assert(hasContentDescription("Step"))
            .assert(hasStateDescription("Completed"))

        composeTestRule.onNodeWithTag("kotstep_indicator_2")
            .assert(hasContentDescription("Step: Final"))
            .assert(hasStateDescription("Current step"))
    }

    @Test
    fun horizontal_rendersLeadingAndTrailingLabels() {
        composeTestRule.setContent {
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

        composeTestRule.onAllNodesWithText("Lead One").assertCountEquals(1)
        composeTestRule.onAllNodesWithText("Trail One").assertCountEquals(1)
        composeTestRule.onAllNodesWithText("Lead Two").assertCountEquals(1)
        composeTestRule.onAllNodesWithText("Trail Two").assertCountEquals(1)
    }

    @Test
    fun vertical_rendersLeadingAndTrailingLabels() {
        composeTestRule.setContent {
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

        composeTestRule.onAllNodesWithText("Before One").assertCountEquals(1)
        composeTestRule.onAllNodesWithText("After One").assertCountEquals(1)
        composeTestRule.onAllNodesWithText("Before Two").assertCountEquals(1)
        composeTestRule.onAllNodesWithText("After Two").assertCountEquals(1)
    }

    @Test
    fun horizontal_collapsingStepPreservesIndicatorAlignment() {
        composeTestRule.setContent {
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

        val beforeY0 = composeTestRule.onNodeWithTag("kotstep_indicator_0").getUnclippedBoundsInRoot().top.value
        val beforeY1 = composeTestRule.onNodeWithTag("kotstep_indicator_1").getUnclippedBoundsInRoot().top.value
        assertTrue(abs(beforeY0 - beforeY1) <= 1f)

        composeTestRule.onNodeWithTag("kotstep_step_0").performClick()
        composeTestRule.waitForIdle()

        composeTestRule.onAllNodesWithText("Lead").assertCountEquals(0)
        composeTestRule.onAllNodesWithText("A much taller trailing label\nwith two lines").assertCountEquals(0)

        val afterY0 = composeTestRule.onNodeWithTag("kotstep_indicator_0").getUnclippedBoundsInRoot().top.value
        val afterY1 = composeTestRule.onNodeWithTag("kotstep_indicator_1").getUnclippedBoundsInRoot().top.value
        assertTrue(abs(afterY0 - afterY1) <= 1f)
    }

    @Test
    fun vertical_collapsingStepPreservesIndicatorAlignment() {
        composeTestRule.setContent {
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

        val beforeX0 = composeTestRule.onNodeWithTag("kotstep_indicator_0").getUnclippedBoundsInRoot().left.value
        val beforeX1 = composeTestRule.onNodeWithTag("kotstep_indicator_1").getUnclippedBoundsInRoot().left.value
        assertTrue(abs(beforeX0 - beforeX1) <= 1f)

        composeTestRule.onNodeWithTag("kotstep_step_0").performClick()
        composeTestRule.waitForIdle()

        composeTestRule.onAllNodesWithText("Very wide leading label").assertCountEquals(0)
        composeTestRule.onAllNodesWithText("Wide trailing label").assertCountEquals(0)

        val afterX0 = composeTestRule.onNodeWithTag("kotstep_indicator_0").getUnclippedBoundsInRoot().left.value
        val afterX1 = composeTestRule.onNodeWithTag("kotstep_indicator_1").getUnclippedBoundsInRoot().left.value
        assertTrue(abs(afterX0 - afterX1) <= 1f)
    }

    private fun hasContentDescription(value: String): SemanticsMatcher {
        return SemanticsMatcher.expectValue(SemanticsProperties.ContentDescription, listOf(value))
    }

    private fun hasStateDescription(value: String): SemanticsMatcher {
        return SemanticsMatcher.expectValue(SemanticsProperties.StateDescription, value)
    }
}
