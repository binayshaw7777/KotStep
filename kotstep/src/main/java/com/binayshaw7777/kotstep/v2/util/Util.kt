package com.binayshaw7777.kotstep.v2.util

import com.binayshaw7777.kotstep.v2.model.step.StepLayoutStyle
import com.binayshaw7777.kotstep.v2.model.style.KotStepStyle
import com.binayshaw7777.kotstep.v2.model.style.StepStyles

object Util {

    fun getKotStepStyle(): KotStepStyle {
        return KotStepStyle(
            stepLayoutStyle = StepLayoutStyle.Vertical,
            stepStyle = StepStyles.default(),
            isScrollable = true,
            showCheckMarkOnDone = true,
            ignoreCurrentState = false
        )
    }
}