package com.binayshaw7777.kotstep.sdui.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed interface SduiMutation {

    @Serializable
    @SerialName("INSERT_STEP")
    data class InsertStep(
        val afterStepId: String? = null,
        val step: SduiStep
    ) : SduiMutation

    @Serializable
    @SerialName("REMOVE_STEP")
    data class RemoveStep(
        val stepId: String
    ) : SduiMutation

    @Serializable
    @SerialName("UPDATE_STEP")
    data class UpdateStep(
        val stepId: String,
        val patch: SduiStepPatch
    ) : SduiMutation

    @Serializable
    @SerialName("UPDATE_STYLE")
    data class UpdateStyle(
        val patch: SduiStylePatch
    ) : SduiMutation
}

@Serializable
data class SduiStepPatch(
    val title: String? = null,
    val subtitle: String? = null,
    val state: SduiStepState? = null,
    val errorMessage: String? = null,
    val indicator: SduiIndicator? = null,
    val isCollapsible: Boolean? = null,
    val action: SduiAction? = null,
    val metadata: Map<String, String>? = null
) {
    fun applyTo(target: SduiStep): SduiStep {
        return target.copy(
            title = title ?: target.title,
            subtitle = subtitle ?: target.subtitle,
            state = state ?: target.state,
            errorMessage = errorMessage ?: target.errorMessage,
            indicator = indicator ?: target.indicator,
            isCollapsible = isCollapsible ?: target.isCollapsible,
            action = action ?: target.action,
            metadata = metadata ?: target.metadata
        )
    }
}

@Serializable
data class SduiStylePatch(
    val itemPaddingDp: Int? = null,
    val showCheckMarkOnDone: Boolean? = null,
    val ignoreCurrentState: Boolean? = null
) {
    fun applyTo(target: SduiStyle): SduiStyle {
        return target.copy(
            itemPaddingDp = itemPaddingDp ?: target.itemPaddingDp,
            showCheckMarkOnDone = showCheckMarkOnDone ?: target.showCheckMarkOnDone,
            ignoreCurrentState = ignoreCurrentState ?: target.ignoreCurrentState
        )
    }
}

@Serializable
data class SduiMutationPayload(
    val flowId: String,
    val stateVersion: Int,
    val mutations: List<SduiMutation> = emptyList()
)
