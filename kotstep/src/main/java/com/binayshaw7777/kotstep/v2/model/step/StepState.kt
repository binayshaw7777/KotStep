package com.binayshaw7777.kotstep.v2.model.step

// Step state for styling
sealed class StepState {
    data object Todo : StepState()
    data object Current : StepState()
    data object Done : StepState()
}