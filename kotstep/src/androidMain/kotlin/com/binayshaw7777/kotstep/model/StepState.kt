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
//@Deprecated(
//    message = "As of KotStep 3.0.0, this StepState model is deprecated. Please use the new KotStep component package: import com.binayshaw7777.kotstep.v2.kotstep which provides more functionality which works with KotStep new component.",
//    replaceWith = ReplaceWith("com.binayshaw7777.kotstep.v2.kotstep"),
//    level = DeprecationLevel.WARNING
//)
enum class StepState {
    TODO,
    CURRENT,
    DONE
}