package com.binayshaw7777.kotstep.v3.model.step

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.binayshaw7777.kotstep.v3.model.style.KotStepStyle
import com.binayshaw7777.kotstep.v3.model.style.LineStyle
import com.binayshaw7777.kotstep.v3.model.style.LineStyles
import com.binayshaw7777.kotstep.v3.model.style.LineType
import com.binayshaw7777.kotstep.v3.model.style.StepStyle
import com.binayshaw7777.kotstep.v3.model.style.StepStyles
import com.binayshaw7777.kotstep.v3.util.ExperimentalKotStep
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalKotStep::class, ExperimentalTestApi::class)
class StaticStepPropertiesTest {

    private val style = KotStepStyle(
        stepStyle = StepStyles(
            onTodo = StepStyle(stepColor = Color(0xFF111111), stepSize = 20.dp),
            onCurrent = StepStyle(stepColor = Color(0xFF222222), stepSize = 30.dp),
            onDone = StepStyle(stepColor = Color(0xFF333333), stepSize = 40.dp)
        ),
        lineStyle = LineStyles(
            onTodo = LineStyle(
                lineLength = 8.dp,
                lineType = LineType.Dashed(dashLength = 2.dp, gapLength = 4.dp)
            ),
            onCurrent = LineStyle(lineLength = 12.dp, lineType = LineType.Dotted(gapLength = 3.dp)),
            onDone = LineStyle(lineLength = 16.dp, progressType = LineType.Dashed())
        )
    )

    @Test
    fun maxSizeIsLargestStepSizeAcrossStates() = runComposeUiTest {
        assertEquals(40.dp, capture(StepState.Todo).maxSize)
    }

    @Test
    fun todoStateSelectsTodoStyles() = runComposeUiTest {
        val props = capture(StepState.Todo)
        assertEquals(20.dp, props.stepStyle.stepSize)
        assertEquals(Color(0xFF111111), props.stepStyle.stepColor)
        assertEquals(8.dp, props.lineStyle.lineLength)
        assertEquals(LineType.Dashed(dashLength = 2.dp, gapLength = 4.dp), props.lineTrackType)
        assertEquals(LineType.Solid, props.lineProgressType)
    }

    @Test
    fun currentStateSelectsCurrentStyles() = runComposeUiTest {
        val props = capture(StepState.Current)
        assertEquals(30.dp, props.stepStyle.stepSize)
        assertEquals(Color(0xFF222222), props.stepStyle.stepColor)
        assertEquals(12.dp, props.lineStyle.lineLength)
        assertEquals(LineType.Dotted(gapLength = 3.dp), props.lineTrackType)
    }

    @Test
    fun doneStateSelectsDoneStyles() = runComposeUiTest {
        val props = capture(StepState.Done)
        assertEquals(40.dp, props.stepStyle.stepSize)
        assertEquals(Color(0xFF333333), props.stepStyle.stepColor)
        assertEquals(16.dp, props.lineStyle.lineLength)
        assertEquals(LineType.Solid, props.lineTrackType)
        assertEquals(LineType.Dashed(), props.lineProgressType)
    }

    private fun androidx.compose.ui.test.ComposeUiTest.capture(
        stepState: StepState
    ): StaticStepProperties {
        var result: StaticStepProperties? = null
        setContent {
            result = calculateStaticStepProperties(style, stepState)
        }
        waitForIdle()
        return checkNotNull(result) { "calculateStaticStepProperties did not produce a result" }
    }
}