package com.binayshaw7777.kotstep.demo

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.binayshaw7777.kotstep.v3.KotStep
import com.binayshaw7777.kotstep.v3.model.step.StepLayoutStyle
import com.binayshaw7777.kotstep.v3.model.style.BorderStyle
import com.binayshaw7777.kotstep.v3.model.style.KotStepStyle
import com.binayshaw7777.kotstep.v3.model.style.LineStyle
import com.binayshaw7777.kotstep.v3.model.style.LineStyles
import com.binayshaw7777.kotstep.v3.model.style.LineType
import com.binayshaw7777.kotstep.v3.model.style.StepStyle
import com.binayshaw7777.kotstep.v3.model.style.StepStyles
import com.binayshaw7777.kotstep.v3.util.ExperimentalKotStep

private val demoIcons = listOf(
    Icons.Default.Home,
    Icons.Default.Search,
    Icons.Default.Person,
    Icons.Default.Favorite,
    Icons.Default.Settings,
    Icons.Default.Star,
    Icons.Default.Check
)

private val demoTitles = listOf(
    "Start",
    "Profile",
    "Address",
    "Billing",
    "Payment",
    "Review",
    "Finish"
)

@OptIn(ExperimentalKotStep::class)
@Composable
fun PlaygroundDemo(modifier: Modifier = Modifier) {
    var totalSteps by remember { mutableIntStateOf(5) }
    var currentStep by remember { mutableFloatStateOf(1f) }
    var isHorizontal by remember { mutableStateOf(true) }
    var selectedShape by remember { mutableStateOf(DemoStepShape.CIRCLE) }
    var useIcons by remember { mutableStateOf(false) }

    var stepSizeDp by remember { mutableFloatStateOf(40f) }
    var lineLengthDp by remember { mutableFloatStateOf(40f) }
    var lineThicknessDp by remember { mutableFloatStateOf(4f) }

    var showCheckmark by remember { mutableStateOf(true) }
    var showStrokeOnCurrent by remember { mutableStateOf(true) }
    var ignoreCurrentState by remember { mutableStateOf(false) }

    val strokeCapOptions = listOf(StrokeCap.Round, StrokeCap.Square, StrokeCap.Butt)
    var selectedStrokeCap by remember { mutableStateOf(StrokeCap.Round) }

    val activeKotStepStyle = remember(
        isHorizontal, selectedShape, stepSizeDp, lineLengthDp,
        lineThicknessDp, showCheckmark, showStrokeOnCurrent, ignoreCurrentState, selectedStrokeCap
    ) {
        KotStepStyle(
            stepLayoutStyle = if (isHorizontal) StepLayoutStyle.Horizontal else StepLayoutStyle.Vertical,
            showCheckMarkOnDone = showCheckmark,
            ignoreCurrentState = ignoreCurrentState,
            stepStyle = StepStyles.default().copy(
                onTodo = StepStyle(
                    stepSize = stepSizeDp.dp,
                    stepShape = selectedShape.toShape(),
                    stepColor = Color(0xFF374151),
                    textStyle = androidx.compose.ui.text.TextStyle(color = Color.White, fontSize = 13.sp)
                ),
                onCurrent = StepStyle(
                    stepSize = (stepSizeDp + 4).dp,
                    stepShape = selectedShape.toShape(),
                    stepColor = Color(0xFF3B82F6),
                    textStyle = androidx.compose.ui.text.TextStyle(color = Color.White, fontSize = 13.sp),
                    borderStyle = if (showStrokeOnCurrent) BorderStyle(width = 2.dp, color = Color(0xFF93C5FD)) else BorderStyle()
                ),
                onDone = StepStyle(
                    stepSize = stepSizeDp.dp,
                    stepShape = selectedShape.toShape(),
                    stepColor = Color(0xFF10B981),
                    textStyle = androidx.compose.ui.text.TextStyle(color = Color.White, fontSize = 13.sp)
                )
            ),
            lineStyle = LineStyles.default().copy(
                onTodo = LineStyle(
                    lineLength = lineLengthDp.dp,
                    lineThickness = lineThicknessDp.dp,
                    lineColor = Color(0xFF4B5563),
                    lineType = LineType.Dashed(),
                    lineStrokeCap = selectedStrokeCap
                ),
                onCurrent = LineStyle(
                    lineLength = lineLengthDp.dp,
                    lineThickness = lineThicknessDp.dp,
                    lineColor = Color(0xFF4B5563),
                    progressColor = Color(0xFF3B82F6),
                    lineStrokeCap = selectedStrokeCap,
                    progressStrokeCap = selectedStrokeCap
                ),
                onDone = LineStyle(
                    lineLength = lineLengthDp.dp,
                    lineThickness = lineThicknessDp.dp,
                    lineColor = Color(0xFF10B981),
                    progressColor = Color(0xFF10B981),
                    lineStrokeCap = selectedStrokeCap,
                    progressStrokeCap = selectedStrokeCap
                )
            )
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Interactive Playground",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        // Live Stepper Preview Container
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1F2937)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                if (isHorizontal) {
                    Box(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                        KotStep(
                            currentStep = { currentStep },
                            style = activeKotStepStyle
                        ) {
                            for (i in 0 until totalSteps) {
                                if (useIcons) {
                                    step(
                                        icon = demoIcons.getOrElse(i) { Icons.Default.Check },
                                        trailingLabel = {
                                            Text(
                                                demoTitles.getOrElse(i) { "${i + 1}" },
                                                fontSize = 11.sp,
                                                color = Color.LightGray
                                            )
                                        },
                                        onClick = {
                                            currentStep = if (currentStep.toInt() == i && currentStep < totalSteps.toFloat()) {
                                                (i + 1).toFloat()
                                            } else {
                                                i.toFloat()
                                            }
                                        }
                                    )
                                } else {
                                    step(
                                        title = "${i + 1}",
                                        trailingLabel = {
                                            Text(
                                                demoTitles.getOrElse(i) { "${i + 1}" },
                                                fontSize = 11.sp,
                                                color = Color.LightGray
                                            )
                                        },
                                        onClick = {
                                            currentStep = if (currentStep.toInt() == i && currentStep < totalSteps.toFloat()) {
                                                (i + 1).toFloat()
                                            } else {
                                                i.toFloat()
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                } else {
                    KotStep(
                        currentStep = { currentStep },
                        style = activeKotStepStyle
                    ) {
                        for (i in 0 until totalSteps) {
                            if (useIcons) {
                                step(
                                    icon = demoIcons.getOrElse(i) { Icons.Default.Check },
                                    trailingLabel = {
                                        Text(
                                            demoTitles.getOrElse(i) { "${i + 1}" },
                                            fontSize = 11.sp,
                                            color = Color.LightGray
                                        )
                                    },
                                    onClick = {
                                        currentStep = if (currentStep.toInt() == i && currentStep < totalSteps.toFloat()) {
                                            (i + 1).toFloat()
                                        } else {
                                            i.toFloat()
                                        }
                                    }
                                )
                            } else {
                                step(
                                    title = "${i + 1}",
                                    trailingLabel = {
                                        Text(
                                            demoTitles.getOrElse(i) { "${i + 1}" },
                                            fontSize = 11.sp,
                                            color = Color.LightGray
                                        )
                                    },
                                    onClick = {
                                        currentStep = if (currentStep.toInt() == i && currentStep < totalSteps.toFloat()) {
                                            (i + 1).toFloat()
                                        } else {
                                            i.toFloat()
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }

        // Stepper Progress Controls
        val progressText = when {
            currentStep < 0f -> "Progress: Not started (-1)"
            currentStep >= totalSteps.toFloat() -> "Progress: All $totalSteps of $totalSteps steps finished! ✓"
            else -> "Progress: Step ${(currentStep + 1).toInt()} of $totalSteps (active)"
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = progressText,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(
                    onClick = { if (currentStep > -1f) currentStep = (currentStep - 0.5f).coerceAtLeast(-1f) },
                    enabled = currentStep > -1f
                ) { Text("-0.5") }

                Button(
                    onClick = { if (currentStep < totalSteps.toFloat()) currentStep = (currentStep + 0.5f).coerceAtMost(totalSteps.toFloat()) },
                    enabled = currentStep < totalSteps.toFloat()
                ) { Text("+0.5") }
            }
        }

        Slider(
            value = currentStep.coerceIn(-1f, totalSteps.toFloat()),
            onValueChange = { currentStep = it },
            valueRange = -1f..totalSteps.toFloat(),
            modifier = Modifier.fillMaxWidth()
        )

        HorizontalDivider(color = Color(0xFF374151))

        // Total Steps Selector
        Text(text = "Total Steps: $totalSteps", fontWeight = FontWeight.SemiBold)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            for (count in 3..7) {
                FilterChip(
                    selected = totalSteps == count,
                    onClick = {
                        totalSteps = count
                        if (currentStep > count.toFloat()) currentStep = count.toFloat()
                    },
                    label = { Text("$count") }
                )
            }
        }

        // Layout Selector
        Text(text = "Orientation", fontWeight = FontWeight.SemiBold)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FilterChip(
                selected = isHorizontal,
                onClick = { isHorizontal = true },
                label = { Text("Horizontal") }
            )
            FilterChip(
                selected = !isHorizontal,
                onClick = { isHorizontal = false },
                label = { Text("Vertical") }
            )
        }

        // Shape Selector
        Text(text = "Step Indicator Shape", fontWeight = FontWeight.SemiBold)
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            for (shape in DemoStepShape.entries) {
                FilterChip(
                    selected = selectedShape == shape,
                    onClick = { selectedShape = shape },
                    label = { Text(shape.displayName) }
                )
            }
        }

        // Step Content Selector
        Text(text = "Step Content", fontWeight = FontWeight.SemiBold)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FilterChip(
                selected = !useIcons,
                onClick = { useIcons = false },
                label = { Text("Numbers") }
            )
            FilterChip(
                selected = useIcons,
                onClick = { useIcons = true },
                label = { Text("Icons") }
            )
        }

        HorizontalDivider(color = Color(0xFF374151))

        // Dimension Sliders
        Text(text = "Step Item Size: ${stepSizeDp.toInt()} dp", fontSize = 13.sp)
        Slider(
            value = stepSizeDp,
            onValueChange = { stepSizeDp = it },
            valueRange = 24f..56f,
            modifier = Modifier.fillMaxWidth()
        )

        Text(text = "Line Length: ${lineLengthDp.toInt()} dp", fontSize = 13.sp)
        Slider(
            value = lineLengthDp,
            onValueChange = { lineLengthDp = it },
            valueRange = 20f..70f,
            modifier = Modifier.fillMaxWidth()
        )

        Text(text = "Line Thickness: ${lineThicknessDp.toInt()} dp", fontSize = 13.sp)
        Slider(
            value = lineThicknessDp,
            onValueChange = { lineThicknessDp = it },
            valueRange = 2f..12f,
            modifier = Modifier.fillMaxWidth()
        )

        HorizontalDivider(color = Color(0xFF374151))

        // Boolean Feature Toggles
        Text(text = "Behavior & Style Toggles", fontWeight = FontWeight.SemiBold)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Show Checkmark on Done", fontSize = 13.sp)
            Switch(checked = showCheckmark, onCheckedChange = { showCheckmark = it })
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Show Stroke on Current", fontSize = 13.sp)
            Switch(checked = showStrokeOnCurrent, onCheckedChange = { showStrokeOnCurrent = it })
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Ignore Current State", fontSize = 13.sp)
            Switch(checked = ignoreCurrentState, onCheckedChange = { ignoreCurrentState = it })
        }

        // StrokeCap selector
        Text(text = "Line Stroke Cap", fontWeight = FontWeight.SemiBold)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            for (cap in strokeCapOptions) {
                FilterChip(
                    selected = selectedStrokeCap == cap,
                    onClick = { selectedStrokeCap = cap },
                    label = {
                        Text(
                            when (cap) {
                                StrokeCap.Round -> "Round"
                                StrokeCap.Square -> "Square"
                                else -> "Butt"
                            }
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
