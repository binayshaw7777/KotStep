package com.binayshaw7777.kotstep.utils

import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.binayshaw7777.kotstep.model.Step
import com.binayshaw7777.kotstep.model.StepComposable

object Utils {

    @Composable
    fun getStepComposables(numberOfItems: Int = -1): List<StepComposable> {
        return if (numberOfItems == - 1 ) listOf(
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
            StepComposable(
                content = {
                    Text(text = "Step 4")
                },
                supportingContent = {
                    Text(text = "Step 4 supporting")
                }
            ),
            StepComposable(
                content = {
                    Text(text = "Step 4")
                },
                supportingContent = {
                    Text(text = "Step 4 supporting")
                }
            ),
        ) else {
            val steps = mutableListOf<StepComposable>()
            for (i in 1..numberOfItems) {
                steps.add(
                    StepComposable(
                        content = {
                            Text(text = "Step $i")
                        },
                        supportingContent = {
                            Text(text = "Step $i supporting content")
                        }
                    )
                )
            }
            steps
        }
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

    fun getIcons(limit: Int): List<ImageVector> {
        return listOf(
            Icons.Default.AccountCircle,
            Icons.Default.Place,
            Icons.Default.Favorite,
            Icons.Default.Search,
            Icons.Default.Face,
            Icons.Default.Build,
            Icons.Default.Check,
            Icons.Default.Create,
            Icons.Default.DateRange,
            Icons.Default.MailOutline
        ).subList(0, limit)
    }
}