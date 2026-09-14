package com.binayshaw7777.kotstep.sdui.model

import kotlinx.serialization.Serializable

@Serializable
data class SduiFlow(
    val schemaVersion: String = "1.0",
    val flowId: String = "",
    val title: String? = null,
    val stateModel: SduiStateModel = SduiStateModel.SERVER_AUTHORITATIVE,
    val orientation: SduiOrientation = SduiOrientation.HORIZONTAL,
    val currentStepId: String = "",
    val currentStepProgress: Float = 0f,
    val stateVersion: Int = 1,
    val style: SduiStyle = SduiStyle(),
    val steps: List<SduiStep> = emptyList(),
    val flowStatus: SduiFlowStatus = SduiFlowStatus.IN_PROGRESS
)

@Serializable
data class SduiStep(
    val id: String,
    val ordinal: Int = 0,
    val state: SduiStepState = SduiStepState.TODO,
    val indicator: SduiIndicator = SduiIndicator(),
    val title: String = "",
    val subtitle: String? = null,
    val leadingText: String? = null,
    val errorMessage: String? = null,
    val isCollapsible: Boolean = false,
    val action: SduiAction? = null,
    val metadata: Map<String, String> = emptyMap()
)

@Serializable
data class SduiIndicator(
    val type: SduiIndicatorType = SduiIndicatorType.DEFAULT,
    val value: String? = null
)

@Serializable
data class SduiAction(
    val type: SduiActionType = SduiActionType.NAVIGATE,
    val target: String = "",
    val params: Map<String, String> = emptyMap()
)

@Serializable
data class SduiStyle(
    val itemPaddingDp: Int = 8,
    val showCheckMarkOnDone: Boolean = true,
    val ignoreCurrentState: Boolean = false,
    val stepStyle: SduiStepStyleConfig = SduiStepStyleConfig(),
    val lineStyle: SduiLineStyleConfig = SduiLineStyleConfig()
)

@Serializable
data class SduiStepStyleConfig(
    val todo: SduiStepStyleItem? = null,
    val current: SduiStepStyleItem? = null,
    val done: SduiStepStyleItem? = null,
    val error: SduiStepStyleItem? = null,
    val locked: SduiStepStyleItem? = null
)

@Serializable
data class SduiStepStyleItem(
    val colorHex: String? = null,
    val sizeDp: Int? = null,
    val shape: SduiShape? = null,
    val borderWidthDp: Int? = null,
    val borderColorHex: String? = null
)

@Serializable
data class SduiLineStyleConfig(
    val todo: SduiLineStyleItem? = null,
    val current: SduiLineStyleItem? = null,
    val done: SduiLineStyleItem? = null
)

@Serializable
data class SduiLineStyleItem(
    val lineColorHex: String? = null,
    val progressColorHex: String? = null,
    val thicknessDp: Int? = null,
    val lengthDp: Int? = null,
    val lineType: SduiLineType? = null,
    val progressType: SduiLineType? = null
)
