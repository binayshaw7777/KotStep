package com.binayshaw7777.kotstep.model


/**
 * An Enum class for StepState
 *
 * TODO -> Step is yet to be started
 *
 * CURRENT -> Step is currently being worked on
 *
 * DONE -> Step is completed
 *
 */
@Deprecated(
    message = "Use the new KotStep component package: import com.binayshaw7777.kotstep.v2.kotstep which provides more functionality with version 3.0.0.",
    replaceWith = ReplaceWith("com.binayshaw7777.kotstep.v2.kotstep"),
    level = DeprecationLevel.WARNING
)
enum class StepState {
    TODO,
    CURRENT,
    DONE
}