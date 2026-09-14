package com.binayshaw7777.kotstep.sdui.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class SduiIndicatorType {
    @SerialName("DEFAULT") DEFAULT,
    @SerialName("NUMBER") NUMBER,
    @SerialName("TEXT") TEXT,
    @SerialName("ICON") ICON,
    @SerialName("CUSTOM") CUSTOM
}

@Serializable
enum class SduiStepState {
    @SerialName("TODO") TODO,
    @SerialName("CURRENT") CURRENT,
    @SerialName("DONE") DONE,
    @SerialName("ERROR") ERROR,
    @SerialName("LOCKED") LOCKED,
    @SerialName("SKIPPED") SKIPPED
}

@Serializable
enum class SduiStateModel {
    @SerialName("SERVER_AUTHORITATIVE") SERVER_AUTHORITATIVE,
    @SerialName("CLIENT_OPTIMISTIC") CLIENT_OPTIMISTIC,
    @SerialName("LOCAL_ONLY") LOCAL_ONLY
}

@Serializable
enum class SduiOrientation {
    @SerialName("HORIZONTAL") HORIZONTAL,
    @SerialName("VERTICAL") VERTICAL
}

@Serializable
enum class SduiFlowStatus {
    @SerialName("IN_PROGRESS") IN_PROGRESS,
    @SerialName("COMPLETED") COMPLETED,
    @SerialName("ABANDONED") ABANDONED,
    @SerialName("PAUSED") PAUSED
}

@Serializable
enum class SduiActionType {
    @SerialName("NAVIGATE") NAVIGATE,
    @SerialName("SUBMIT") SUBMIT,
    @SerialName("SKIP") SKIP,
    @SerialName("CUSTOM") CUSTOM
}

@Serializable
enum class SduiLineType {
    @SerialName("SOLID") SOLID,
    @SerialName("DASHED") DASHED,
    @SerialName("DOTTED") DOTTED
}

@Serializable
enum class SduiShape {
    @SerialName("CIRCLE") CIRCLE,
    @SerialName("ROUNDED_SQUARE") ROUNDED_SQUARE,
    @SerialName("SQUARE") SQUARE
}

@Serializable
enum class SduiMutationType {
    @SerialName("INSERT_STEP") INSERT_STEP,
    @SerialName("REMOVE_STEP") REMOVE_STEP,
    @SerialName("UPDATE_STEP") UPDATE_STEP,
    @SerialName("UPDATE_STYLE") UPDATE_STYLE
}
