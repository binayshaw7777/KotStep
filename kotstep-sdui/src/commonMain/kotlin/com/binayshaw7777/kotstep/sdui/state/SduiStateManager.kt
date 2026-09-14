package com.binayshaw7777.kotstep.sdui.state

import com.binayshaw7777.kotstep.sdui.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * State manager for KotStep SDUI.
 * Provides reactive flow updates, optimistic step advancement, version conflict protection,
 * rollback capability, and dynamic mid-flow mutations.
 */
class SduiStateManager(
    initialFlow: SduiFlow
) {
    private val _flow = MutableStateFlow(normalizeOrdinals(initialFlow, sortByOrdinal = true))
    val flow: StateFlow<SduiFlow> = _flow.asStateFlow()

    private var lastConfirmedServerFlow: SduiFlow = _flow.value

    /**
     * Applies state received from the server.
     * Drops stale updates using version gating.
     */
    fun applyServerFlow(newFlow: SduiFlow): SduiApplyResult {
        if (newFlow.stateVersion <= _flow.value.stateVersion) {
            return SduiApplyResult.Ignored("Stale version: ${newFlow.stateVersion} <= current ${_flow.value.stateVersion}")
        }
        val normalized = normalizeOrdinals(newFlow, sortByOrdinal = true)
        lastConfirmedServerFlow = normalized
        _flow.value = normalized
        return SduiApplyResult.Applied
    }

    /**
     * Applies incremental mid-flow mutations.
     */
    fun applyMutations(mutations: List<SduiMutation>, newVersion: Int): SduiApplyResult {
        if (newVersion <= _flow.value.stateVersion) {
            return SduiApplyResult.Ignored("Stale mutation version: $newVersion <= current ${_flow.value.stateVersion}")
        }
        val currentSteps = _flow.value.steps.toMutableList()
        var currentStyle = _flow.value.style

        for (mutation in mutations) {
            when (mutation) {
                is SduiMutation.InsertStep -> {
                    val index = currentSteps.indexOfFirst { it.id == mutation.afterStepId }
                    if (index != -1) {
                        currentSteps.add(index + 1, mutation.step)
                    } else {
                        currentSteps.add(mutation.step)
                    }
                }
                is SduiMutation.RemoveStep -> {
                    currentSteps.removeAll { it.id == mutation.stepId }
                }
                is SduiMutation.UpdateStep -> {
                    val index = currentSteps.indexOfFirst { it.id == mutation.stepId }
                    if (index != -1) {
                        currentSteps[index] = mutation.patch.applyTo(currentSteps[index])
                    }
                }
                is SduiMutation.UpdateStyle -> {
                    currentStyle = mutation.patch.applyTo(currentStyle)
                }
            }
        }

        val updated = _flow.value.copy(
            stateVersion = newVersion,
            steps = currentSteps,
            style = currentStyle
        )
        val normalized = normalizeOrdinals(updated, sortByOrdinal = false)
        _flow.value = normalized
        return SduiApplyResult.Applied
    }

    /**
     * Optimistically advances or rewinds to the target step immediately.
     * Sets prior steps to DONE, target step to CURRENT, and subsequent steps to TODO.
     */
    fun optimisticAdvance(toStepId: String) {
        val current = _flow.value
        val targetStep = current.steps.find { it.id == toStepId } ?: return
        val updatedSteps = current.steps.map { step ->
            when {
                step.ordinal < targetStep.ordinal -> {
                    if (step.state == SduiStepState.LOCKED || step.state == SduiStepState.SKIPPED) step
                    else step.copy(state = SduiStepState.DONE)
                }
                step.id == toStepId -> step.copy(state = SduiStepState.CURRENT)
                else -> {
                    if (step.state == SduiStepState.LOCKED || step.state == SduiStepState.SKIPPED) step
                    else step.copy(state = SduiStepState.TODO)
                }
            }
        }
        _flow.value = current.copy(
            currentStepId = toStepId,
            currentStepProgress = 0f,
            steps = updatedSteps
        )
    }

    /**
     * Rolls back optimistic state to the last confirmed server flow.
     */
    fun rollback() {
        _flow.value = lastConfirmedServerFlow
    }

    /**
     * Computes the current step as a Float for KotStep v3 core.
     * E.g. index 1 with 0.5 progress -> 1.5f.
     */
    fun computeCurrentStepFloat(): Float = _flow.value.computeCurrentStepFloat()

    private fun normalizeOrdinals(flow: SduiFlow, sortByOrdinal: Boolean = false): SduiFlow {
        val ordered = if (sortByOrdinal) flow.steps.sortedBy { it.ordinal } else flow.steps
        val reindexed = ordered.mapIndexed { index, step -> step.copy(ordinal = index) }
        return flow.copy(steps = reindexed)
    }
}

/**
 * Computes the current step as a Float for KotStep v3 core.
 * E.g. index 1 with 0.5 progress -> 1.5f.
 * Uses currentStepId if present, otherwise falls back to the first step in CURRENT state.
 */
fun SduiFlow.computeCurrentStepFloat(): Float {
    val targetId = if (currentStepId.isNotBlank()) {
        currentStepId
    } else {
        steps.firstOrNull { it.state == SduiStepState.CURRENT }?.id
    }
    val currentIndex = steps.indexOfFirst { it.id == targetId }
    if (currentIndex == -1) return 0f
    return currentIndex.toFloat() + currentStepProgress.coerceIn(0f, 0.999f)
}

