package com.binayshaw7777.kotstep.v3.util

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class AnimationConstantsTest {

    @Test
    fun defaultAnimationDurationIs300Millis() {
        assertEquals(300, AnimationConstants.ANIMATION_DURATION_MS)
    }

    @Test
    fun horizontalTransitionsAreAvailable() {
        val horizontal = AnimationConstants.Horizontal
        horizontal.progressLineEnter
        horizontal.progressLineExit
        horizontal.labelEnter
        horizontal.labelExit
    }

    @Test
    fun verticalTransitionsAreAvailable() {
        val vertical = AnimationConstants.Vertical
        vertical.progressLineEnter
        vertical.progressLineExit
        vertical.labelEnter
        vertical.labelExit
    }

    @Test
    fun horizontalAndVerticalLineEntrancesDiffer() {
        assertNotEquals(
            AnimationConstants.Horizontal.progressLineEnter,
            AnimationConstants.Vertical.progressLineEnter
        )
    }
}