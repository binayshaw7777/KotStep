package com.binayshaw7777.kotstep.v3.util

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
         * Slides in from left, expands horizontally, and fades in.
         */
        val progressLineEnter: EnterTransition
            get() = slideInHorizontally(
                animationSpec = tween(ANIMATION_DURATION_MS),
                initialOffsetX = { -it / 2 }
            ) + expandHorizontally(
                animationSpec = tween(ANIMATION_DURATION_MS),
                expandFrom = Alignment.Start
            ) + fadeIn(animationSpec = tween(ANIMATION_DURATION_MS))

        /**
         * Exit transition for progress line in horizontal stepper.
         * Slides out to left, shrinks horizontally, and fades out.
         */
        val progressLineExit: ExitTransition
            get() = slideOutHorizontally(
                animationSpec = tween(ANIMATION_DURATION_MS),
                targetOffsetX = { -it / 2 }
            ) + shrinkHorizontally(
                animationSpec = tween(ANIMATION_DURATION_MS),
                shrinkTowards = Alignment.Start
            ) + fadeOut(animationSpec = tween(ANIMATION_DURATION_MS))

        /**
         * Enter transition for label in horizontal stepper.
         * Slides in from left with gentler offset, expands horizontally, and fades in.
         */
        val labelEnter: EnterTransition
            get() = slideInHorizontally(
                animationSpec = tween(ANIMATION_DURATION_MS),
                initialOffsetX = { -it / 3 }
            ) + expandHorizontally(
                animationSpec = tween(ANIMATION_DURATION_MS),
                expandFrom = Alignment.Start
            ) + fadeIn(animationSpec = tween(ANIMATION_DURATION_MS))

        /**
         * Exit transition for label in horizontal stepper.
         * Slides out to left with gentler offset, shrinks horizontally, and fades out.
         */
        val labelExit: ExitTransition
            get() = slideOutHorizontally(
                animationSpec = tween(ANIMATION_DURATION_MS),
                targetOffsetX = { -it / 3 }
            ) + shrinkHorizontally(
                animationSpec = tween(ANIMATION_DURATION_MS),
                shrinkTowards = Alignment.Start
            ) + fadeOut(animationSpec = tween(ANIMATION_DURATION_MS))
    }

    /**
     * Vertical animations for progress lines and labels in vertical steppers.
     */
    object Vertical {

        /**
         * Enter transition for progress line in vertical stepper.
         * Slides in from top, expands vertically, and fades in.
         */
        val progressLineEnter: EnterTransition
            get() = slideInVertically(
                animationSpec = tween(ANIMATION_DURATION_MS),
                initialOffsetY = { -it / 2 }
            ) + expandVertically(
                animationSpec = tween(ANIMATION_DURATION_MS),
                expandFrom = Alignment.Top
            ) + fadeIn(animationSpec = tween(ANIMATION_DURATION_MS))

        /**
         * Exit transition for progress line in vertical stepper.
         * Slides out to top, shrinks vertically, and fades out.
         */
        val progressLineExit: ExitTransition
            get() = slideOutVertically(
                animationSpec = tween(ANIMATION_DURATION_MS),
                targetOffsetY = { -it / 2 }
            ) + shrinkVertically(
                animationSpec = tween(ANIMATION_DURATION_MS),
                shrinkTowards = Alignment.Top
            ) + fadeOut(animationSpec = tween(ANIMATION_DURATION_MS))

        /**
         * Enter transition for label in vertical stepper.
         * Slides in from top with gentler offset, expands vertically, and fades in.
         */
        val labelEnter: EnterTransition
            get() = slideInVertically(
                animationSpec = tween(ANIMATION_DURATION_MS),
                initialOffsetY = { -it / 3 }
            ) + expandVertically(
                animationSpec = tween(ANIMATION_DURATION_MS),
                expandFrom = Alignment.Top
            ) + fadeIn(animationSpec = tween(ANIMATION_DURATION_MS))

        /**
         * Exit transition for label in vertical stepper.
         * Slides out to top with gentler offset, shrinks vertically, and fades out.
         */
        val labelExit: ExitTransition
            get() = slideOutVertically(
                animationSpec = tween(ANIMATION_DURATION_MS),
                targetOffsetY = { -it / 3 }
            ) + shrinkVertically(
                animationSpec = tween(ANIMATION_DURATION_MS),
                shrinkTowards = Alignment.Top
            ) + fadeOut(animationSpec = tween(ANIMATION_DURATION_MS))
    }
}
