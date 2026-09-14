package com.binayshaw7777.kotstep.sdui.ui

import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.*
import androidx.compose.ui.unit.dp
import com.binayshaw7777.kotstep.sdui.model.*
import com.binayshaw7777.kotstep.sdui.state.SduiStateManager
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

@OptIn(ExperimentalTestApi::class)
class KotStepSduiUiTest {

    private val sampleJson = """
    {
      "flowId": "checkout-test",
      "orientation": "VERTICAL",
      "currentStepId": "step-address",
      "steps": [
        {
          "id": "step-cart",
          "ordinal": 0,
          "state": "DONE",
          "title": "Cart",
          "subtitle": "2 items",
          "action": { "type": "NAVIGATE", "target": "cart_screen" }
        },
        {
          "id": "step-address",
          "ordinal": 1,
          "state": "CURRENT",
          "title": "Delivery",
          "subtitle": "Home",
          "action": { "type": "NAVIGATE", "target": "address_screen" }
        },
        {
          "id": "step-payment",
          "ordinal": 2,
          "state": "LOCKED",
          "title": "Payment",
          "subtitle": "Cards",
          "action": { "type": "NAVIGATE", "target": "payment_screen" }
        }
      ]
    }
    """.trimIndent()

    @Test
    fun kotStepSdui_rendersTitlesAndSubtitlesFromRawJson() = runComposeUiTest {
        var clickedStepId: String? = null
        var triggeredAction: SduiAction? = null

        setContent {
            MaterialTheme {
                KotStepSdui(
                    json = sampleJson,
                    modifier = Modifier.width(400.dp),
                    onStepClick = { clickedStepId = it },
                    onAction = { triggeredAction = it }
                )
            }
        }

        onAllNodesWithText("Cart").assertCountEquals(1)
        onAllNodesWithText("2 items").assertCountEquals(1)
        onAllNodesWithText("Delivery").assertCountEquals(1)
        onAllNodesWithText("Home").assertCountEquals(1)
        onAllNodesWithText("Payment").assertCountEquals(1)
        onAllNodesWithText("Cards").assertCountEquals(1)

        // Click active step (index 1 = step-address)
        onNodeWithTag("kotstep_step_1", useUnmergedTree = true).performClick()
        assertEquals("step-address", clickedStepId)
        assertEquals(SduiActionType.NAVIGATE, triggeredAction?.type)
        assertEquals("address_screen", triggeredAction?.target)

        // Click locked step (index 2 = step-payment) -> must NOT fire callbacks
        clickedStepId = null
        triggeredAction = null
        onNodeWithTag("kotstep_step_2", useUnmergedTree = true).performClick()
        assertNull(clickedStepId)
        assertNull(triggeredAction)
    }

    @Test
    fun kotStepSdui_rendersErrorStateAndBadge() = runComposeUiTest {
        val errorJson = """
        {
          "flowId": "error-test",
          "orientation": "VERTICAL",
          "currentStepId": "step-1",
          "steps": [
            {
              "id": "step-1",
              "ordinal": 0,
              "state": "ERROR",
              "title": "KYC Verification",
              "errorMessage": "Document expired"
            }
          ]
        }
        """.trimIndent()

        setContent {
            MaterialTheme {
                KotStepSdui(json = errorJson)
            }
        }

        onAllNodesWithText("KYC Verification").assertCountEquals(1)
        onAllNodesWithText("Document expired").assertCountEquals(1)
        onAllNodesWithText("!").assertCountEquals(1) // Error badge
    }

    @Test
    fun kotStepSdui_reactiveStateUpdatesOnManagerAdvanceAndMutation() = runComposeUiTest {
        val initialFlow = SduiFlow(
            flowId = "reactive-test",
            orientation = SduiOrientation.VERTICAL,
            currentStepId = "step-1",
            steps = listOf(
                SduiStep(id = "step-1", ordinal = 0, state = SduiStepState.CURRENT, title = "Step One"),
                SduiStep(id = "step-2", ordinal = 1, state = SduiStepState.TODO, title = "Step Two")
            )
        )
        val manager = SduiStateManager(initialFlow)

        setContent {
            MaterialTheme {
                KotStepSdui(
                    manager = manager,
                    modifier = Modifier.width(400.dp)
                )
            }
        }

        onAllNodesWithText("Step One").assertCountEquals(1)
        onAllNodesWithText("Step Two").assertCountEquals(1)

        // Insert step mid-flow
        manager.applyMutations(
            mutations = listOf(
                SduiMutation.InsertStep(
                    afterStepId = "step-1",
                    step = SduiStep(id = "step-injected", ordinal = 1, state = SduiStepState.TODO, title = "Injected Step")
                )
            ),
            newVersion = 2
        )
        waitForIdle()

        // Verify injected step now visible in UI
        onAllNodesWithText("Injected Step").assertCountEquals(1)

        // Optimistic advance to step-2
        manager.optimisticAdvance("step-2")
        waitForIdle()
        assertEquals(2f, manager.computeCurrentStepFloat())

        // Rollback
        manager.rollback()
        waitForIdle()
        assertEquals(0f, manager.computeCurrentStepFloat())
    }
}
