package com.binayshaw7777.kotstep.sdui.state

import com.binayshaw7777.kotstep.sdui.model.*
import kotlin.test.*

class SduiStateManagerTest {

    private fun createSampleFlow(
        flowId: String = "test-flow",
        version: Int = 1,
        currentStepId: String = "step-1",
        currentStepProgress: Float = 0f
    ): SduiFlow {
        return SduiFlow(
            flowId = flowId,
            stateVersion = version,
            currentStepId = currentStepId,
            currentStepProgress = currentStepProgress,
            steps = listOf(
                SduiStep(id = "step-1", ordinal = 10, state = SduiStepState.CURRENT, title = "Step 1"),
                SduiStep(id = "step-2", ordinal = 20, state = SduiStepState.TODO, title = "Step 2"),
                SduiStep(id = "step-3", ordinal = 30, state = SduiStepState.TODO, title = "Step 3"),
                SduiStep(id = "step-4", ordinal = 40, state = SduiStepState.TODO, title = "Step 4")
            )
        )
    }

    @Test
    fun initialStateAndComputeCurrentStepFloat() {
        val flow = createSampleFlow(currentStepId = "step-1", currentStepProgress = 0f)
        val manager = SduiStateManager(flow)

        // Verifies ordinals normalized on init: 10, 20, 30, 40 -> 0, 1, 2, 3
        val steps = manager.flow.value.steps
        assertEquals(0, steps[0].ordinal)
        assertEquals(1, steps[1].ordinal)
        assertEquals(2, steps[2].ordinal)
        assertEquals(3, steps[3].ordinal)

        // Float at step-1 (index 0) with progress 0 -> 0f
        assertEquals(0f, manager.computeCurrentStepFloat())

        // Float at step-2 (index 1) with progress 0.5 -> 1.5f
        manager.applyServerFlow(flow.copy(stateVersion = 2, currentStepId = "step-2", currentStepProgress = 0.5f))
        assertEquals(1.5f, manager.computeCurrentStepFloat())

        // Float at step-3 (index 2) with progress 0.25 -> 2.25f
        manager.applyServerFlow(flow.copy(stateVersion = 3, currentStepId = "step-3", currentStepProgress = 0.25f))
        assertEquals(2.25f, manager.computeCurrentStepFloat())

        // Float for unknown step ID -> 0f
        manager.applyServerFlow(flow.copy(stateVersion = 4, currentStepId = "unknown-step"))
        assertEquals(0f, manager.computeCurrentStepFloat())

        // Progress coercion: progress >= 1.0f coerced to 0.999f
        manager.applyServerFlow(flow.copy(stateVersion = 5, currentStepId = "step-2", currentStepProgress = 1.5f))
        assertEquals(1.999f, manager.computeCurrentStepFloat(), 0.001f)

        // Progress coercion: progress < 0.0f coerced to 0.0f
        manager.applyServerFlow(flow.copy(stateVersion = 6, currentStepId = "step-2", currentStepProgress = -0.5f))
        assertEquals(1.0f, manager.computeCurrentStepFloat(), 0.001f)
    }

    @Test
    fun optimisticAdvanceUpdatesStatesAndFloat() {
        val flow = createSampleFlow(currentStepId = "step-1")
        val manager = SduiStateManager(flow)

        // Optimistically advance to step-3 (ordinal 2)
        manager.optimisticAdvance("step-3")

        val currentFlow = manager.flow.value
        assertEquals("step-3", currentFlow.currentStepId)
        assertEquals(0f, currentFlow.currentStepProgress)

        val steps = currentFlow.steps
        // Steps with ordinal < 2 are DONE
        assertEquals(SduiStepState.DONE, steps[0].state) // step-1 (ordinal 0)
        assertEquals(SduiStepState.DONE, steps[1].state) // step-2 (ordinal 1)
        // Target step is CURRENT
        assertEquals(SduiStepState.CURRENT, steps[2].state) // step-3 (ordinal 2)
        // Steps with ordinal > 2 are unchanged
        assertEquals(SduiStepState.TODO, steps[3].state) // step-4 (ordinal 3)

        // computeCurrentStepFloat reflects new currentStepId
        assertEquals(2.0f, manager.computeCurrentStepFloat())

        // Optimistically rewind to step-2 (ordinal 1)
        manager.optimisticAdvance("step-2")
        val rewindFlow = manager.flow.value
        assertEquals("step-2", rewindFlow.currentStepId)
        val rewindSteps = rewindFlow.steps
        assertEquals(SduiStepState.DONE, rewindSteps[0].state) // step-1 (ordinal 0 < 1)
        assertEquals(SduiStepState.CURRENT, rewindSteps[1].state) // step-2 (target)
        assertEquals(SduiStepState.TODO, rewindSteps[2].state) // step-3 (ordinal 2 > 1 reset to TODO)
        assertEquals(SduiStepState.TODO, rewindSteps[3].state) // step-4 (ordinal 3 > 1 reset to TODO)
        assertEquals(1.0f, manager.computeCurrentStepFloat())

        // Optimistic advance with invalid step ID does nothing
        manager.optimisticAdvance("non-existent-id")
        assertEquals("step-2", manager.flow.value.currentStepId)
    }

    @Test
    fun rollbackRevertsToLastConfirmedServerState() {
        val initialFlow = createSampleFlow(version = 1, currentStepId = "step-1")
        val manager = SduiStateManager(initialFlow)

        // Optimistically advance to step-3
        manager.optimisticAdvance("step-3")
        assertEquals("step-3", manager.flow.value.currentStepId)
        assertEquals(SduiStepState.CURRENT, manager.flow.value.steps[2].state)

        // Rollback reverts back to initial confirmed state
        manager.rollback()
        assertEquals("step-1", manager.flow.value.currentStepId)
        assertEquals(SduiStepState.CURRENT, manager.flow.value.steps[0].state)
        assertEquals(SduiStepState.TODO, manager.flow.value.steps[2].state)

        // Confirm a newer server flow (version 2)
        val serverFlowV2 = initialFlow.copy(
            stateVersion = 2,
            currentStepId = "step-2",
            steps = initialFlow.steps.map {
                if (it.id == "step-1") it.copy(state = SduiStepState.DONE)
                else if (it.id == "step-2") it.copy(state = SduiStepState.CURRENT)
                else it
            }
        )
        manager.applyServerFlow(serverFlowV2)
        assertEquals("step-2", manager.flow.value.currentStepId)

        // Optimistically advance to step-4
        manager.optimisticAdvance("step-4")
        assertEquals("step-4", manager.flow.value.currentStepId)

        // Rollback reverts back to serverFlowV2 (not initialFlow)
        manager.rollback()
        assertEquals("step-2", manager.flow.value.currentStepId)
        assertEquals(SduiStepState.DONE, manager.flow.value.steps[0].state)
        assertEquals(SduiStepState.CURRENT, manager.flow.value.steps[1].state)
        assertEquals(SduiStepState.TODO, manager.flow.value.steps[3].state)
    }

    @Test
    fun applyServerFlowVersionGating() {
        val flowV2 = createSampleFlow(version = 2)
        val manager = SduiStateManager(flowV2)
        assertEquals(2, manager.flow.value.stateVersion)

        // Older version ignored
        val flowV1 = createSampleFlow(version = 1, currentStepId = "step-2")
        val resultV1 = manager.applyServerFlow(flowV1)
        assertIs<SduiApplyResult.Ignored>(resultV1)
        assertEquals(2, manager.flow.value.stateVersion)
        assertEquals("step-1", manager.flow.value.currentStepId)

        // Equal version ignored
        val flowV2Dup = createSampleFlow(version = 2, currentStepId = "step-2")
        val resultV2 = manager.applyServerFlow(flowV2Dup)
        assertIs<SduiApplyResult.Ignored>(resultV2)
        assertEquals(2, manager.flow.value.stateVersion)
        assertEquals("step-1", manager.flow.value.currentStepId)

        // Newer version applied
        val flowV3 = createSampleFlow(version = 3, currentStepId = "step-2")
        val resultV3 = manager.applyServerFlow(flowV3)
        assertIs<SduiApplyResult.Applied>(resultV3)
        assertEquals(3, manager.flow.value.stateVersion)
        assertEquals("step-2", manager.flow.value.currentStepId)
    }

    @Test
    fun applyMutationsVersionGating() {
        val flow = createSampleFlow(version = 2)
        val manager = SduiStateManager(flow)

        val mutation = SduiMutation.RemoveStep(stepId = "step-4")

        // Older version ignored
        val resultOlder = manager.applyMutations(listOf(mutation), newVersion = 1)
        assertIs<SduiApplyResult.Ignored>(resultOlder)
        assertEquals(4, manager.flow.value.steps.size)

        // Equal version ignored
        val resultEqual = manager.applyMutations(listOf(mutation), newVersion = 2)
        assertIs<SduiApplyResult.Ignored>(resultEqual)
        assertEquals(4, manager.flow.value.steps.size)

        // Newer version applied
        val resultNewer = manager.applyMutations(listOf(mutation), newVersion = 3)
        assertIs<SduiApplyResult.Applied>(resultNewer)
        assertEquals(3, manager.flow.value.stateVersion)
        assertEquals(3, manager.flow.value.steps.size)
    }

    @Test
    fun applyMutationInsertStepAfterTargetId() {
        val flow = createSampleFlow(version = 1)
        val manager = SduiStateManager(flow)

        val insertedStep = SduiStep(id = "step-1b", title = "Verification")
        val mutation = SduiMutation.InsertStep(afterStepId = "step-1", step = insertedStep)

        val result = manager.applyMutations(listOf(mutation), newVersion = 2)
        assertIs<SduiApplyResult.Applied>(result)

        val steps = manager.flow.value.steps
        assertEquals(5, steps.size)
        assertEquals("step-1", steps[0].id)
        assertEquals(0, steps[0].ordinal)

        assertEquals("step-1b", steps[1].id)
        assertEquals(1, steps[1].ordinal)

        assertEquals("step-2", steps[2].id)
        assertEquals(2, steps[2].ordinal)

        assertEquals("step-3", steps[3].id)
        assertEquals(3, steps[3].ordinal)

        assertEquals("step-4", steps[4].id)
        assertEquals(4, steps[4].ordinal)
    }

    @Test
    fun applyMutationInsertStepAtEnd() {
        val flow = createSampleFlow(version = 1)
        val manager = SduiStateManager(flow)

        val insertedStep = SduiStep(id = "step-5", title = "Completion")
        val mutation = SduiMutation.InsertStep(afterStepId = null, step = insertedStep)

        val result = manager.applyMutations(listOf(mutation), newVersion = 2)
        assertIs<SduiApplyResult.Applied>(result)

        val steps = manager.flow.value.steps
        assertEquals(5, steps.size)
        assertEquals("step-5", steps[4].id)
        assertEquals(4, steps[4].ordinal)

        // Also test non-existent afterStepId inserts at end
        val step6 = SduiStep(id = "step-6", title = "Post-finish")
        manager.applyMutations(listOf(SduiMutation.InsertStep(afterStepId = "non-existent", step = step6)), newVersion = 3)
        val stepsAfter6 = manager.flow.value.steps
        assertEquals(6, stepsAfter6.size)
        assertEquals("step-6", stepsAfter6[5].id)
        assertEquals(5, stepsAfter6[5].ordinal)
    }

    @Test
    fun applyMutationRemoveStep() {
        val flow = createSampleFlow(version = 1)
        val manager = SduiStateManager(flow)

        val mutation = SduiMutation.RemoveStep(stepId = "step-2")
        val result = manager.applyMutations(listOf(mutation), newVersion = 2)
        assertIs<SduiApplyResult.Applied>(result)

        val steps = manager.flow.value.steps
        assertEquals(3, steps.size)
        assertEquals("step-1", steps[0].id)
        assertEquals(0, steps[0].ordinal)

        assertEquals("step-3", steps[1].id)
        assertEquals(1, steps[1].ordinal)

        assertEquals("step-4", steps[2].id)
        assertEquals(2, steps[2].ordinal)
    }

    @Test
    fun applyMutationUpdateStep() {
        val flow = createSampleFlow(version = 1)
        val manager = SduiStateManager(flow)

        val patch = SduiStepPatch(
            title = "Updated Title",
            subtitle = "New Subtitle",
            state = SduiStepState.DONE,
            errorMessage = "No error",
            isCollapsible = true,
            metadata = mapOf("source" to "mutation")
        )
        val mutation = SduiMutation.UpdateStep(stepId = "step-2", patch = patch)

        val result = manager.applyMutations(listOf(mutation), newVersion = 2)
        assertIs<SduiApplyResult.Applied>(result)

        val updatedStep = manager.flow.value.steps.first { it.id == "step-2" }
        assertEquals("Updated Title", updatedStep.title)
        assertEquals("New Subtitle", updatedStep.subtitle)
        assertEquals(SduiStepState.DONE, updatedStep.state)
        assertEquals("No error", updatedStep.errorMessage)
        assertTrue(updatedStep.isCollapsible)
        assertEquals("mutation", updatedStep.metadata["source"])
    }

    @Test
    fun applyMutationUpdateStyle() {
        val flow = createSampleFlow(version = 1)
        val manager = SduiStateManager(flow)

        val stylePatch = SduiStylePatch(
            itemPaddingDp = 16,
            showCheckMarkOnDone = false,
            ignoreCurrentState = true
        )
        val mutation = SduiMutation.UpdateStyle(patch = stylePatch)

        val result = manager.applyMutations(listOf(mutation), newVersion = 2)
        assertIs<SduiApplyResult.Applied>(result)

        val updatedStyle = manager.flow.value.style
        assertEquals(16, updatedStyle.itemPaddingDp)
        assertFalse(updatedStyle.showCheckMarkOnDone)
        assertTrue(updatedStyle.ignoreCurrentState)
    }
}
