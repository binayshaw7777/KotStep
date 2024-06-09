package com.binayshaw7777.kotstep.utils

import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.binayshaw7777.kotstep.model.Step
import com.binayshaw7777.kotstep.model.StepComposable

object Utils {

    @Composable
    fun getStepComposables(): List<StepComposable> {
        return listOf(
            StepComposable(
                content = {
                    Icon(imageVector = Icons.Default.AccountCircle, contentDescription = null)
                },
                supportingContent = {
                    Text(text = "Step 1 supporting content")
                }
            ),
            StepComposable(
                content = {
                    Text(text = "Step 2")
                },
                supportingContent = {
                    Text(text = "Step 2 supporting content")
                }
            ),
            StepComposable(
                content = {
                    Text(text = "Step 3")
                },
                supportingContent = {
                    Text(text = "Step 3 supporting")
                }
            ),
        )
    }

    @Composable
    fun getStepsWithSupportingContent(): List<Step> {
        return listOf(
            Step(text = "Step 1", supportingContent = {
                Text(text = "1")
            }),
            Step(text = "Step 2 supporting"),
            Step(text = "Step 3", supportingContent = {
                Image(imageVector = Icons.Default.AccountCircle, contentDescription = null)
            }),
            Step(text = "Step 4"),
        )
    }

    @Composable
    fun getSteps(): List<Step> {
        return listOf(
            Step(text = "Step 1"),
            Step(text = "Step 2"),
            Step(text = "Step 3"),
            Step(text = "Step 4"),
        )
    }
}