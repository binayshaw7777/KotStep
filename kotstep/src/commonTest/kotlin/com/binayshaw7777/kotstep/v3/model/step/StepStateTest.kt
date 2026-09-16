package com.binayshaw7777.kotstep.v3.model.step

import com.binayshaw7777.kotstep.v3.util.ExperimentalKotStep
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertSame

@OptIn(ExperimentalKotStep::class)
class StepStateTest {

    @Test
    fun eachStateIsASingleton() {
        assertSame(StepState.Todo, StepState.Todo)
        assertSame(StepState.Current, StepState.Current)
        assertSame(StepState.Done, StepState.Done)
    }

    @Test
    fun statesAreDistinct() {
        assertNotEquals<StepState>(StepState.Todo, StepState.Current)
        assertNotEquals<StepState>(StepState.Todo, StepState.Done)
        assertNotEquals<StepState>(StepState.Current, StepState.Done)
    }

    @Test
    fun progressesInProcessOrder() {
        assertEquals(StepState.Current, StepState.Todo.let { nextState(it) })
        assertEquals(StepState.Done, StepState.Current.let { nextState(it) })
    }

    private fun nextState(state: StepState): StepState {
        return when (state) {
            StepState.Todo -> StepState.Current
            StepState.Current -> StepState.Done
            StepState.Done -> StepState.Done
        }
    }
}