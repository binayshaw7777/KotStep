package com.binayshaw7777.kotstep.v2.model.step

/**
 * Represents the state of a step in a multi-step process.
 *
 * This sealed class defines the possible states a step can be in:
 * - [Todo]: The step has not yet been started.
 * - [Current]: The step is currently being performed.
 * - [Done]: The step has been completed.
 *
 * @since 3.0.0
 */
sealed class StepState {
    data object Todo : StepState()
    data object Current : StepState()
    data object Done : StepState()
}