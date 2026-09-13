package com.binayshaw7777.kotstep.v3.util

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.ui.Alignment

/**
 * Constants for collapsible step animations.
 *
 * Provides reusable animation configurations for horizontal and vertical steppers
 * when steps are collapsed or expanded.
 *
 * @since 3.0.0
 */
internal object AnimationConstants {

    /**
     * Duration for collapse/expand animations in milliseconds.
     */
    const val ANIMATION_DURATION_MS = 300

    /**
     * Horizontal animations for progress lines and labels in horizontal steppers.
     */
    object Horizontal {

        /**
         * Enter transition for progress line in horizontal stepper.
         * Slides in from left and expands horizontally.
         */
        val progressLineEnter: EnterTransition
            get() = slideInHorizontally(
                animationSpec = tween(ANIMATION_DURATION_MS),
                initialOffsetX = { -it / 2 }
            ) + expandHorizontally(
                animationSpec = tween(ANIMATION_DURATION_MS),
                expandFrom = Alignment.Start
            )

        /**
         * Exit transition for progress line in horizontal stepper.
         * Slides out to left and shrinks horizontally.
         */
        val progressLineExit: ExitTransition
            get() = slideOutHorizontally(
                animationSpec = tween(ANIMATION_DURATION_MS),
                targetOffsetX = { -it / 2 }
            ) + shrinkHorizontally(
                animationSpec = tween(ANIMATION_DURATION_MS),
                shrinkTowards = Alignment.Start
            )

        /**
         * Enter transition for label in horizontal stepper.
         * Slides in from left with gentler offset and expands horizontally.
         */
        val labelEnter: EnterTransition
            get() = slideInHorizontally(
                animationSpec = tween(ANIMATION_DURATION_MS),
                initialOffsetX = { -it / 3 }
            ) + expandHorizontally(
                animationSpec = tween(ANIMATION_DURATION_MS),
                expandFrom = Alignment.Start
            )

        /**
         * Exit transition for label in horizontal stepper.
         * Slides out to left with gentler offset and shrinks horizontally.
         */
        val labelExit: ExitTransition
            get() = slideOutHorizontally(
                animationSpec = tween(ANIMATION_DURATION_MS),
                targetOffsetX = { -it / 3 }
            ) + shrinkHorizontally(
                animationSpec = tween(ANIMATION_DURATION_MS),
                shrinkTowards = Alignment.Start
            )
    }

    /**
     * Vertical animations for progress lines and labels in vertical steppers.
     */
    object Vertical {

        /**
         * Enter transition for progress line in vertical stepper.
         * Slides in from top and expands vertically.
         */
        val progressLineEnter: EnterTransition
            get() = slideInVertically(
                animationSpec = tween(ANIMATION_DURATION_MS),
                initialOffsetY = { -it / 2 }
            ) + expandVertically(
                animationSpec = tween(ANIMATION_DURATION_MS),
                expandFrom = Alignment.Top
            )

        /**
         * Exit transition for progress line in vertical stepper.
         * Slides out to top and shrinks vertically.
         */
        val progressLineExit: ExitTransition
            get() = slideOutVertically(
                animationSpec = tween(ANIMATION_DURATION_MS),
                targetOffsetY = { -it / 2 }
            ) + shrinkVertically(
                animationSpec = tween(ANIMATION_DURATION_MS),
                shrinkTowards = Alignment.Top
            )

        /**
         * Enter transition for label in vertical stepper.
         * Slides in from top with gentler offset and expands vertically.
         */
        val labelEnter: EnterTransition
            get() = slideInVertically(
                animationSpec = tween(ANIMATION_DURATION_MS),
                initialOffsetY = { -it / 3 }
            ) + expandVertically(
                animationSpec = tween(ANIMATION_DURATION_MS),
                expandFrom = Alignment.Top
            )

        /**
         * Exit transition for label in vertical stepper.
         * Slides out to top with gentler offset and shrinks vertically.
         */
        val labelExit: ExitTransition
            get() = slideOutVertically(
                animationSpec = tween(ANIMATION_DURATION_MS),
                targetOffsetY = { -it / 3 }
            ) + shrinkVertically(
                animationSpec = tween(ANIMATION_DURATION_MS),
                shrinkTowards = Alignment.Top
            )
    }
}
