package com.binayshaw7777.kotstep.utils

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.binayshaw7777.kotstep.v2.model.step.StepLayoutStyle
import com.binayshaw7777.kotstep.v2.model.style.BorderStyle
import com.binayshaw7777.kotstep.v2.model.style.KotStepStyle
import com.binayshaw7777.kotstep.v2.model.style.LineStyle
import com.binayshaw7777.kotstep.v2.model.style.LineStyles
import com.binayshaw7777.kotstep.v2.model.style.LineType
import com.binayshaw7777.kotstep.v2.model.style.StepStyle
import com.binayshaw7777.kotstep.v2.model.style.StepStyles

object Utils {

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

    fun getDuration(limit: Int) : List<Long> {
        return listOf(
            5_000L,
            10_000L,
            5_000,
            25_000L,
            12_000L,
            26_000L,
            15_000L,
            11_000L,
            26_000L,
            18_000L
        ).subList(0, limit)
    }

    fun getLabels(limit: Int) = listOf<@Composable (() -> Unit)?>(
        { Text("Ordered") },
        {
            Column(verticalArrangement = Arrangement.Top) {
                Text("Shipped")
                Text("Reached the facility X.", fontSize = 14.sp)
                Text("Reached the facility X.", fontSize = 14.sp)
                Text("Reached the facility X.", fontSize = 14.sp)
                Text("Reached the facility X.", fontSize = 14.sp)
                Text("Reached the facility X.", fontSize = 14.sp)
            }
        },
        {
            Text("Reached the facility Y.", fontSize = 14.sp)
        },
        null,
        null,
        {

            Text("Reached the facility Z", fontSize = 14.sp)
            
        },
        { Text("Out for Delivery", fontSize = 14.sp) },
        {
            Text("Delivery delayed due to rain", fontSize = 14.sp)
        },
        {
            Text(text = "Item delivered.")
        },
        null
    )
        .subList(0, limit)

    internal fun getKotStepStyle(): KotStepStyle {
        return KotStepStyle(
            stepLayoutStyle = StepLayoutStyle.Vertical,
            showCheckMarkOnDone = false,
            ignoreCurrentState = false,
            stepStyle = StepStyles.default().copy(
                onTodo = StepStyle.defaultTodo().copy(
                    stepSize = 50.dp,
                    stepColor = Color.Gray,
                    borderStyle = BorderStyle(width = 2.dp, color = Color.Red)
                ),
                onCurrent = StepStyle.defaultTodo().copy(
                    stepSize = 60.dp,
                    stepColor = Color.DarkGray,
                    borderStyle = BorderStyle(width = 2.dp, color = Color.Gray)
                ),
                onDone = StepStyle.defaultTodo().copy(
                    stepSize = 50.dp,
                    stepColor = Color.Green,
                    borderStyle = BorderStyle(width = 2.dp, color = Color.DarkGray)
                )
            ),
            lineStyle = LineStyles.default().copy(
                onTodo = LineStyle.defaultTodo().copy(
                    lineThickness = 10.dp,
                    lineLength = 100.dp,
                    linePadding = PaddingValues(2.dp),
                ),
                onCurrent = LineStyle.defaultCurrent().copy(
                    lineThickness = 4.dp,
                    lineLength = 100.dp,
                    linePadding = PaddingValues(2.dp),
                    progressStrokeCap = StrokeCap.Butt,
                    lineStrokeCap = StrokeCap.Butt,
                    lineType = LineType.Dashed(dashLength = 20.dp, gapLength = 10.dp),
                    progressType = LineType.Dashed(dashLength = 20.dp, gapLength = 10.dp)
                ),
                onDone = LineStyle.defaultDone().copy(
                    lineThickness = 5.dp,
                    lineLength = 100.dp,
                    linePadding = PaddingValues(2.dp),
                    lineType = LineType.Dotted(gapLength = 10.dp),
                    progressType = LineType.Dotted(gapLength = 10.dp)
                )
            )
        )
    }
}

fun Int.toast(context: Context) {
    Toast.makeText(context, "Clicked on item: $this", Toast.LENGTH_SHORT).show()
}