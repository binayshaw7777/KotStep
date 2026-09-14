package com.binayshaw7777.kotstep.sdui.parser

import com.binayshaw7777.kotstep.sdui.model.*
import kotlin.test.*

class SduiParserTest {

    @Test
    fun deserializeCompleteUnifiedFlow() {
        val jsonString = """
        {
          "schemaVersion": "1.0",
          "flowId": "checkout-order-9876",
          "title": "Checkout",
          "stateModel": "SERVER_AUTHORITATIVE",
          "orientation": "HORIZONTAL",
          "currentStepId": "step-payment",
          "currentStepProgress": 0.5,
          "stateVersion": 2,
          "style": {
            "itemPaddingDp": 8,
            "showCheckMarkOnDone": true,
            "ignoreCurrentState": false,
            "stepStyle": {
              "todo": { "colorHex": "#E0E0E0", "sizeDp": 28, "shape": "CIRCLE", "borderWidthDp": 0, "borderColorHex": null },
              "current": { "colorHex": "#1E88E5", "sizeDp": 28, "shape": "ROUNDED_SQUARE", "borderWidthDp": 2, "borderColorHex": "#1565C0" },
              "done": { "colorHex": "#43A047", "sizeDp": 28, "shape": "SQUARE", "borderWidthDp": 0, "borderColorHex": null },
              "error": { "colorHex": "#E53935", "sizeDp": 28, "shape": "CIRCLE", "borderWidthDp": 2, "borderColorHex": "#B71C1C" },
              "locked": { "colorHex": "#BDBDBD", "sizeDp": 28, "shape": "CIRCLE", "borderWidthDp": 0, "borderColorHex": null }
            },
            "lineStyle": {
              "todo": { "lineColorHex": "#E0E0E0", "progressColorHex": "#E0E0E0", "thicknessDp": 2, "lengthDp": 24, "lineType": "SOLID", "progressType": "SOLID" },
              "current": { "lineColorHex": "#E0E0E0", "progressColorHex": "#1E88E5", "thicknessDp": 2, "lengthDp": 24, "lineType": "DASHED", "progressType": "SOLID" },
              "done": { "lineColorHex": "#43A047", "progressColorHex": "#43A047", "thicknessDp": 2, "lengthDp": 24, "lineType": "DOTTED", "progressType": "SOLID" }
            }
          },
          "steps": [
            {
              "id": "step-cart",
              "ordinal": 0,
              "state": "DONE",
              "indicator": { "type": "NUMBER", "value": "1" },
              "title": "Cart",
              "subtitle": "2 items ($45.00)",
              "leadingText": "Step 1",
              "errorMessage": null,
              "isCollapsible": false,
              "action": {
                "type": "NAVIGATE",
                "target": "cart_summary",
                "params": { "cartId": "c123" }
              },
              "metadata": { "analyticsId": "checkout_cart" }
            },
            {
              "id": "step-payment",
              "ordinal": 1,
              "state": "CURRENT",
              "indicator": { "type": "ICON", "value": "payment" },
              "title": "Payment",
              "subtitle": "Credit Card",
              "leadingText": "Step 2",
              "errorMessage": null,
              "isCollapsible": true,
              "action": {
                "type": "SUBMIT",
                "target": "process_payment",
                "params": {}
              },
              "metadata": {}
            },
            {
              "id": "step-review",
              "ordinal": 2,
              "state": "ERROR",
              "indicator": { "type": "CUSTOM", "value": "custom_review" },
              "title": "Review",
              "subtitle": "Confirm order",
              "leadingText": "Step 3",
              "errorMessage": "Card verification failed",
              "isCollapsible": false,
              "action": {
                "type": "CUSTOM",
                "target": "retry_action",
                "params": { "reason": "declined" }
              },
              "metadata": { "retryAllowed": "true" }
            }
          ],
          "flowStatus": "IN_PROGRESS"
        }
        """.trimIndent()

        val flow = SduiParser.parseFlow(jsonString)

        assertEquals("1.0", flow.schemaVersion)
        assertEquals("checkout-order-9876", flow.flowId)
        assertEquals("Checkout", flow.title)
        assertEquals(SduiStateModel.SERVER_AUTHORITATIVE, flow.stateModel)
        assertEquals(SduiOrientation.HORIZONTAL, flow.orientation)
        assertEquals("step-payment", flow.currentStepId)
        assertEquals(0.5f, flow.currentStepProgress)
        assertEquals(2, flow.stateVersion)
        assertEquals(SduiFlowStatus.IN_PROGRESS, flow.flowStatus)

        // Style assertions
        assertEquals(8, flow.style.itemPaddingDp)
        assertTrue(flow.style.showCheckMarkOnDone)
        assertFalse(flow.style.ignoreCurrentState)
        assertEquals("#1E88E5", flow.style.stepStyle.current?.colorHex)
        assertEquals(SduiShape.ROUNDED_SQUARE, flow.style.stepStyle.current?.shape)
        assertEquals(2, flow.style.stepStyle.current?.borderWidthDp)
        assertEquals("#1565C0", flow.style.stepStyle.current?.borderColorHex)
        assertEquals(SduiLineType.DASHED, flow.style.lineStyle.current?.lineType)
        assertEquals(SduiLineType.DOTTED, flow.style.lineStyle.done?.lineType)

        // Steps assertions
        assertEquals(3, flow.steps.size)

        val stepCart = flow.steps[0]
        assertEquals("step-cart", stepCart.id)
        assertEquals(0, stepCart.ordinal)
        assertEquals(SduiStepState.DONE, stepCart.state)
        assertEquals(SduiIndicatorType.NUMBER, stepCart.indicator.type)
        assertEquals("1", stepCart.indicator.value)
        assertEquals("Cart", stepCart.title)
        assertEquals("2 items ($45.00)", stepCart.subtitle)
        assertEquals("Step 1", stepCart.leadingText)
        assertNull(stepCart.errorMessage)
        assertFalse(stepCart.isCollapsible)
        assertNotNull(stepCart.action)
        assertEquals(SduiActionType.NAVIGATE, stepCart.action?.type)
        assertEquals("cart_summary", stepCart.action?.target)
        assertEquals("c123", stepCart.action?.params?.get("cartId"))
        assertEquals("checkout_cart", stepCart.metadata["analyticsId"])

        val stepReview = flow.steps[2]
        assertEquals("step-review", stepReview.id)
        assertEquals(SduiStepState.ERROR, stepReview.state)
        assertEquals("Card verification failed", stepReview.errorMessage)
        assertEquals(SduiIndicatorType.CUSTOM, stepReview.indicator.type)
        assertEquals(SduiActionType.CUSTOM, stepReview.action?.type)
        assertEquals("true", stepReview.metadata["retryAllowed"])
    }

    @Test
    fun deserializeMinimalJsonWithDefaults() {
        val minimalJson = """
        {
          "flowId": "min-flow",
          "currentStepId": "step-0"
        }
        """.trimIndent()

        val flow = SduiParser.parseFlow(minimalJson)

        assertEquals("min-flow", flow.flowId)
        assertEquals("step-0", flow.currentStepId)
        assertEquals("1.0", flow.schemaVersion)
        assertNull(flow.title)
        assertEquals(SduiStateModel.SERVER_AUTHORITATIVE, flow.stateModel)
        assertEquals(SduiOrientation.HORIZONTAL, flow.orientation)
        assertEquals(0f, flow.currentStepProgress)
        assertEquals(1, flow.stateVersion)
        assertEquals(SduiFlowStatus.IN_PROGRESS, flow.flowStatus)
        assertTrue(flow.steps.isEmpty())
        assertEquals(8, flow.style.itemPaddingDp)
        assertTrue(flow.style.showCheckMarkOnDone)
        assertFalse(flow.style.ignoreCurrentState)
    }

    @Test
    fun faultToleranceIgnoresUnknownKeys() {
        val jsonWithUnknownKeys = """
        {
          "flowId": "fault-tolerant-flow",
          "currentStepId": "s1",
          "unknownTopLevelField": "unexpected",
          "unrecognizedObject": { "nested": 123, "flag": true },
          "steps": [
            {
              "id": "s1",
              "title": "Step 1",
              "futureFeatureFlag": true,
              "unexpectedList": [1, 2, 3],
              "indicator": {
                "type": "NUMBER",
                "value": "1",
                "unknownIndicatorProp": "ignore_me"
              },
              "action": {
                "type": "NAVIGATE",
                "target": "home",
                "unsupportedField": 42
              }
            }
          ]
        }
        """.trimIndent()

        val flow = SduiParser.parseFlow(jsonWithUnknownKeys)
        assertEquals("fault-tolerant-flow", flow.flowId)
        assertEquals(1, flow.steps.size)
        assertEquals("s1", flow.steps[0].id)
        assertEquals("Step 1", flow.steps[0].title)
        assertEquals(SduiIndicatorType.NUMBER, flow.steps[0].indicator.type)
        assertEquals("1", flow.steps[0].indicator.value)
        assertEquals("home", flow.steps[0].action?.target)
    }

    @Test
    fun serializationRoundtrip() {
        val originalFlow = SduiFlow(
            schemaVersion = "1.0",
            flowId = "roundtrip-flow",
            title = "Roundtrip Test",
            stateModel = SduiStateModel.CLIENT_OPTIMISTIC,
            orientation = SduiOrientation.VERTICAL,
            currentStepId = "step-2",
            currentStepProgress = 0.75f,
            stateVersion = 5,
            style = SduiStyle(
                itemPaddingDp = 16,
                showCheckMarkOnDone = false,
                ignoreCurrentState = true,
                stepStyle = SduiStepStyleConfig(
                    todo = SduiStepStyleItem(colorHex = "#AAAAAA", sizeDp = 20, shape = SduiShape.CIRCLE),
                    current = SduiStepStyleItem(colorHex = "#00FF00", sizeDp = 24, shape = SduiShape.ROUNDED_SQUARE, borderWidthDp = 1, borderColorHex = "#008800"),
                    done = SduiStepStyleItem(colorHex = "#0000FF", sizeDp = 20, shape = SduiShape.SQUARE)
                ),
                lineStyle = SduiLineStyleConfig(
                    todo = SduiLineStyleItem(lineColorHex = "#111111", thicknessDp = 2, lengthDp = 16, lineType = SduiLineType.DASHED),
                    current = SduiLineStyleItem(lineColorHex = "#222222", progressColorHex = "#333333", thicknessDp = 3, lineType = SduiLineType.SOLID),
                    done = SduiLineStyleItem(lineColorHex = "#444444", thicknessDp = 2, lineType = SduiLineType.DOTTED)
                )
            ),
            steps = listOf(
                SduiStep(
                    id = "step-1",
                    ordinal = 0,
                    state = SduiStepState.DONE,
                    indicator = SduiIndicator(type = SduiIndicatorType.NUMBER, value = "1"),
                    title = "First Step",
                    subtitle = "Done",
                    leadingText = "Start",
                    action = SduiAction(type = SduiActionType.NAVIGATE, target = "screen_1", params = mapOf("key" to "value")),
                    metadata = mapOf("env" to "prod")
                ),
                SduiStep(
                    id = "step-2",
                    ordinal = 1,
                    state = SduiStepState.CURRENT,
                    indicator = SduiIndicator(type = SduiIndicatorType.ICON, value = "settings"),
                    title = "Second Step",
                    subtitle = "In Progress",
                    isCollapsible = true
                )
            ),
            flowStatus = SduiFlowStatus.IN_PROGRESS
        )

        val encodedJson = SduiParser.encodeFlow(originalFlow)
        val decodedFlow = SduiParser.parseFlow(encodedJson)

        assertEquals(originalFlow, decodedFlow)
    }

    @Test
    fun parseMutationPayload() {
        val mutationJson = """
        {
          "flowId": "checkout-flow",
          "stateVersion": 3,
          "mutations": [
            {
              "type": "INSERT_STEP",
              "afterStepId": "step-1",
              "step": {
                "id": "step-inserted",
                "ordinal": 1,
                "state": "TODO",
                "indicator": { "type": "ICON", "value": "badge" },
                "title": "Inserted Step",
                "subtitle": "Extra verification"
              }
            },
            {
              "type": "REMOVE_STEP",
              "stepId": "step-old"
            },
            {
              "type": "UPDATE_STEP",
              "stepId": "step-2",
              "patch": {
                "title": "Updated Step Title",
                "subtitle": "Updated Subtitle",
                "state": "DONE",
                "errorMessage": "Temporary error",
                "isCollapsible": true,
                "metadata": { "updated": "true" }
              }
            },
            {
              "type": "UPDATE_STYLE",
              "patch": {
                "itemPaddingDp": 14,
                "showCheckMarkOnDone": false,
                "ignoreCurrentState": true
              }
            }
          ]
        }
        """.trimIndent()

        val payload = SduiParser.parseMutationPayload(mutationJson)

        assertEquals("checkout-flow", payload.flowId)
        assertEquals(3, payload.stateVersion)
        assertEquals(4, payload.mutations.size)

        // 1. InsertStep
        val insert = assertIs<SduiMutation.InsertStep>(payload.mutations[0])
        assertEquals("step-1", insert.afterStepId)
        assertEquals("step-inserted", insert.step.id)
        assertEquals("Inserted Step", insert.step.title)
        assertEquals(SduiIndicatorType.ICON, insert.step.indicator.type)
        assertEquals("badge", insert.step.indicator.value)

        // 2. RemoveStep
        val remove = assertIs<SduiMutation.RemoveStep>(payload.mutations[1])
        assertEquals("step-old", remove.stepId)

        // 3. UpdateStep
        val update = assertIs<SduiMutation.UpdateStep>(payload.mutations[2])
        assertEquals("step-2", update.stepId)
        assertEquals("Updated Step Title", update.patch.title)
        assertEquals("Updated Subtitle", update.patch.subtitle)
        assertEquals(SduiStepState.DONE, update.patch.state)
        assertEquals("Temporary error", update.patch.errorMessage)
        assertEquals(true, update.patch.isCollapsible)
        assertEquals("true", update.patch.metadata?.get("updated"))

        // 4. UpdateStyle
        val updateStyle = assertIs<SduiMutation.UpdateStyle>(payload.mutations[3])
        assertEquals(14, updateStyle.patch.itemPaddingDp)
        assertEquals(false, updateStyle.patch.showCheckMarkOnDone)
        assertEquals(true, updateStyle.patch.ignoreCurrentState)
    }
}
