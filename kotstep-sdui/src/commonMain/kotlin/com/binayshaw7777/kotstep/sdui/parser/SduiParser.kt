package com.binayshaw7777.kotstep.sdui.parser

import com.binayshaw7777.kotstep.sdui.model.SduiFlow
import com.binayshaw7777.kotstep.sdui.model.SduiMutationPayload
import kotlinx.serialization.json.Json

/**
 * Parser for KotStep SDUI JSON documents.
 * Configured to be lenient, ignore unknown/future keys, and coerce input values for fault tolerance.
 */
object SduiParser {

    val json: Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = true
        coerceInputValues = true
    }

    /**
     * Parses a JSON string into an [SduiFlow].
     */
    fun parseFlow(jsonString: String): SduiFlow {
        return json.decodeFromString(SduiFlow.serializer(), jsonString)
    }

    /**
     * Parses a JSON string into an [SduiMutationPayload].
     */
    fun parseMutationPayload(jsonString: String): SduiMutationPayload {
        return json.decodeFromString(SduiMutationPayload.serializer(), jsonString)
    }

    /**
     * Serializes an [SduiFlow] to a JSON string.
     */
    fun encodeFlow(flow: SduiFlow): String {
        return json.encodeToString(SduiFlow.serializer(), flow)
    }
}
