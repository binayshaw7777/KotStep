package com.binayshaw7777.kotstep.v3.util

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.binayshaw7777.kotstep.v3.util.Util.onClick
import kotlin.test.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalTestApi::class)
class UtilTest {

    @Test
    fun onClickInvokesCallback() = runComposeUiTest {
        var invoked = false
        setContent {
            Box(Modifier.onClick { invoked = true }.size(100.dp).testTag("clickable"))
        }
        onNodeWithTag("clickable").performClick()
        waitForIdle()
        assertTrue(invoked)
    }
}