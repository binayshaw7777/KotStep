package com.binayshaw7777.kotstep.sdui.state

sealed interface SduiApplyResult {
    data object Applied : SduiApplyResult
    data class Ignored(val reason: String) : SduiApplyResult
    data class Rejected(val reason: String) : SduiApplyResult
}
