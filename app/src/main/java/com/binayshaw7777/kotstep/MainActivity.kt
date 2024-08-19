package com.binayshaw7777.kotstep

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.binayshaw7777.kotstep.model.LineDefault
import com.binayshaw7777.kotstep.model.LineStyle
import com.binayshaw7777.kotstep.model.StepDefaults
import com.binayshaw7777.kotstep.model.StepStyle
import com.binayshaw7777.kotstep.model.dashed
import com.binayshaw7777.kotstep.model.iconHorizontal
import com.binayshaw7777.kotstep.model.iconVertical
import com.binayshaw7777.kotstep.model.iconVerticalWithLabel
import com.binayshaw7777.kotstep.model.numberedHorizontal
import com.binayshaw7777.kotstep.model.numberedVertical
import com.binayshaw7777.kotstep.model.numberedVerticalWithLabel
import com.binayshaw7777.kotstep.model.tabHorizontal
import com.binayshaw7777.kotstep.model.tabVertical
import com.binayshaw7777.kotstep.model.tabVerticalWithLabel
import com.binayshaw7777.kotstep.ui.horizontal.HorizontalStepper
import com.binayshaw7777.kotstep.ui.theme.KotStepTheme
import com.binayshaw7777.kotstep.ui.vertical.VerticalStepper
import com.binayshaw7777.kotstep.utils.StepperItemShape
import com.binayshaw7777.kotstep.utils.StepperItemShape.Companion.getShapeFromEnum
import com.binayshaw7777.kotstep.utils.StepperOptions
import com.binayshaw7777.kotstep.utils.Utils
import com.binayshaw7777.kotstep.utils.toast

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KotStepTheme(darkTheme = false) {
                MainPreview()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun MainPreview() {

    val context = LocalContext.current

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        var totalSteps by rememberSaveable { mutableIntStateOf(5) }
        var currentStep by rememberSaveable { mutableFloatStateOf(-1f) }
        var showCheckMark by remember { mutableStateOf(true) }
        var showStepStroke by remember { mutableStateOf(true) }
        var expanded by remember { mutableStateOf(false) }
        var expandedShapeMenu by remember { mutableStateOf(false) }
        var currentStepperType by remember { mutableStateOf(StepperOptions.HORIZONTAL_DASHED_STEPPER) }
        var currentStepperItemShape by remember { mutableStateOf(StepperItemShape.CIRCLE_SHAPE) }
        var stepItemSize by remember { mutableIntStateOf(35) }
        var lineThickness by rememberSaveable { mutableIntStateOf(6) }
        val icons = remember { mutableStateListOf<ImageVector>() }
        val trailingLabels = remember { mutableStateListOf<(@Composable () -> Unit)?>() }
        var lineSize by remember { mutableIntStateOf(20) }
        var showLineStyleSheet by remember { mutableStateOf(false) }
        var lineTopPadding by remember { mutableIntStateOf(0) }
        var lineBottomPadding by remember { mutableIntStateOf(0) }
        var lineStartPadding by remember { mutableIntStateOf(0) }
        var lineEndPadding by remember { mutableIntStateOf(0) }
        val strokeCapOptions = listOf("Rounded", "Butt", "Solid")
        val strokeCapOptionsMapped = listOf(StrokeCap.Round, StrokeCap.Butt, StrokeCap.Square)
        var strokeCap by remember { mutableStateOf(strokeCapOptionsMapped[0]) }
        val (selectedStrokeCapOption, onOptionSelected) = remember { mutableStateOf(strokeCapOptions[0] ) }

        LaunchedEffect(totalSteps) {
            icons.clear()
            icons.addAll(Utils.getIcons(totalSteps))
            trailingLabels.clear()
            trailingLabels.addAll(Utils.getLabels(totalSteps))
        }

        LaunchedEffect(currentStep) {
            println("Current Step: $currentStep")
        }

        val stepStyle = StepStyle(
            lineStyle = LineDefault(
                lineThickness = lineThickness.dp,
                lineSize = lineSize.dp,
                linePaddingStart = lineStartPadding.dp,
                linePaddingEnd = lineEndPadding.dp,
                linePaddingTop = lineTopPadding.dp,
                linePaddingBottom = lineBottomPadding.dp,
                strokeCap = strokeCap,
                todoLineStyle = LineStyle.DOTTED,
                currentLineStyle = LineStyle.DASHED,
                doneLineStyle = LineStyle.SOLID
            ),
            showCheckMarkOnDone = showCheckMark,
            showStrokeOnCurrent = showStepStroke,
            stepSize = stepItemSize.dp,
            stepShape = getShapeFromEnum(currentStepperItemShape),
            colors = StepDefaults(
                doneContainerColor = Color(0xFF00E676),
                doneContentColor = Color(0xFF212121),
                doneLineColor = Color(0xFF00E676),
                currentContainerColor = Color(0xFF4B81F4),
                currentLineColor = Color(0xFF4B81F4),
                todoContainerColor = Color(0xFF50596C),
                todoContentColor = Color.White,
                todoLineColor = Color(0xFF50596C)
            )
        )

        if (showLineStyleSheet) {
            ModalBottomSheet(
                onDismissRequest = { showLineStyleSheet = false },
                sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
            ) {
                Column(Modifier.padding(16.dp).verticalScroll(rememberScrollState())) {
                    Text(
                        text = "Line Size in DP: $lineSize",
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                    Slider(
                        value = lineSize.toFloat(),
                        onValueChange = { newValue ->
                            lineSize = newValue.toInt()
                        },
                        valueRange = 20f..50f, // Set the range of Step Item size
                        steps = 30, // Divide the range into 20 steps
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Line Thickness in DP: $lineThickness",
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                    Slider(
                        value = lineThickness.toFloat(),
                        onValueChange = { newValue ->
                            lineThickness = newValue.toInt()
                        },
                        valueRange = 1f..10f, // Set the range of Line Thickness
                        steps = 10, // Divide the range into 7 steps
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))


                    Text(
                        text = "Line Padding Top in DP: $lineTopPadding",
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                    Slider(
                        value = lineTopPadding.toFloat(),
                        onValueChange = { newValue ->
                            lineTopPadding = newValue.toInt()
                        },
                        valueRange = 0f..10f,
                        steps = 10,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))


                    Text(
                        text = "Line Padding Bottom in DP: $lineBottomPadding",
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                    Slider(
                        value = lineBottomPadding.toFloat(),
                        onValueChange = { newValue ->
                            lineBottomPadding = newValue.toInt()
                        },
                        valueRange = 0f..10f, // Set the range of Line Thickness
                        steps = 11, // Divide the range into 7 steps
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Line Padding Start in DP: $lineStartPadding",
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                    Slider(
                        value = lineStartPadding.toFloat(),
                        onValueChange = { newValue ->
                            lineStartPadding = newValue.toInt()
                        },
                        valueRange = 0f..10f,
                        steps = 11,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))


                    Text(
                        text = "Line Padding End in DP: $lineEndPadding",
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                    Slider(
                        value = lineEndPadding.toFloat(),
                        onValueChange = { newValue ->
                            lineEndPadding = newValue.toInt()
                        },
                        valueRange = 0f..10f, // Set the range of Line Thickness
                        steps = 11, // Divide the range into 7 steps
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Stroke Cap: $selectedStrokeCapOption",
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                    Column {
                        strokeCapOptions.forEachIndexed { index, text ->
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .selectable(
                                        selected = (text == selectedStrokeCapOption),
                                        onClick = {
                                            onOptionSelected(text)
                                            strokeCap = strokeCapOptionsMapped[index]
                                        }
                                    )
                                    .padding(horizontal = 16.dp)
                            ) {
                                RadioButton(
                                    selected = (text == selectedStrokeCapOption),
                                    onClick = {
                                        onOptionSelected(text)
                                        strokeCap = strokeCapOptionsMapped[index]
                                    }
                                )
                                Text(
                                    text = text,
                                    style = MaterialTheme.typography.bodySmall,
                                    modifier = Modifier.padding(start = 16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxHeight(0.7f)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box {
                    IconButton(onClick = { expanded = true }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Localized description")
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Horizontal TAB Stepper") },
                            onClick = {
                                currentStepperType = StepperOptions.HORIZONTAL_TAB_STEPPER
                                expanded = false
                            },
                            leadingIcon = {
                                Icon(
                                    Icons.Outlined.KeyboardArrowRight,
                                    contentDescription = null
                                )
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Horizontal NUMBERED Stepper") },
                            onClick = {
                                currentStepperType = StepperOptions.HORIZONTAL_NUMBERED_STEPPER
                                expanded = false
                            },
                            leadingIcon = {
                                Icon(
                                    Icons.Outlined.KeyboardArrowRight,
                                    contentDescription = null
                                )
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Horizontal ICON Stepper") },
                            onClick = {
                                currentStepperType = StepperOptions.HORIZONTAL_ICON_STEPPER
                                expanded = false
                            },
                            leadingIcon = {
                                Icon(
                                    Icons.Outlined.KeyboardArrowRight,
                                    contentDescription = null
                                )
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Horizontal DASHED Stepper") },
                            onClick = {
                                currentStepperType = StepperOptions.HORIZONTAL_DASHED_STEPPER
                                expanded = false
                            },
                            leadingIcon = {
                                Icon(
                                    Icons.Outlined.KeyboardArrowRight,
                                    contentDescription = null
                                )
                            }
                        )


                        DropdownMenuItem(
                            text = { Text("Vertical TAB Stepper") },
                            onClick = {
                                currentStepperType = StepperOptions.VERTICAL_TAB_STEPPER
                                expanded = false
                            },
                            leadingIcon = {
                                Icon(
                                    Icons.Outlined.KeyboardArrowRight,
                                    contentDescription = null
                                )
                            }
                        )

                        DropdownMenuItem(
                            text = { Text("Vertical NUMBERED Stepper") },
                            onClick = {
                                currentStepperType = StepperOptions.VERTICAL_NUMBERED_STEPPER
                                expanded = false
                            },
                            leadingIcon = {
                                Icon(
                                    Icons.Outlined.KeyboardArrowRight,
                                    contentDescription = null
                                )
                            }
                        )

                        DropdownMenuItem(
                            text = { Text("Vertical ICON Stepper") },
                            onClick = {
                                currentStepperType = StepperOptions.VERTICAL_ICON_STEPPER
                                expanded = false
                            },
                            leadingIcon = {
                                Icon(
                                    Icons.Outlined.KeyboardArrowRight,
                                    contentDescription = null
                                )
                            }
                        )

                        DropdownMenuItem(
                            text = { Text("Vertical Tab LABEL Stepper") },
                            onClick = {
                                currentStepperType = StepperOptions.VERTICAL_TAB_LABEL_STEPPER
                                expanded = false
                            },
                            leadingIcon = {
                                Icon(
                                    Icons.Outlined.KeyboardArrowRight,
                                    contentDescription = null
                                )
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Vertical Numbered LABEL Stepper") },
                            onClick = {
                                currentStepperType = StepperOptions.VERTICAL_NUMBERED_LABEL_STEPPER
                                expanded = false
                            },
                            leadingIcon = {
                                Icon(
                                    Icons.Outlined.KeyboardArrowRight,
                                    contentDescription = null
                                )
                            }
                        )

                        DropdownMenuItem(
                            text = { Text("Vertical Icon LABEL Stepper") },
                            onClick = {
                                currentStepperType = StepperOptions.VERTICAL_ICON_LABEL_STEPPER
                                expanded = false
                            },
                            leadingIcon = {
                                Icon(
                                    Icons.Outlined.KeyboardArrowRight,
                                    contentDescription = null
                                )
                            }
                        )
                    }
                }

                Text(
                    text = "Current Step: $currentStep",
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium)
                )

                Box {
                    IconButton(onClick = { expandedShapeMenu = true }) {
                        Icon(Icons.Default.Menu, contentDescription = "Localized description")
                    }
                    DropdownMenu(
                        expanded = expandedShapeMenu,
                        onDismissRequest = { expandedShapeMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Circle Shape") },
                            onClick = {
                                currentStepperItemShape = StepperItemShape.CIRCLE_SHAPE
                                expandedShapeMenu = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Rectangle Shape") },
                            onClick = {
                                currentStepperItemShape = StepperItemShape.RECTANGLE_SHAPE
                                expandedShapeMenu = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("CutCorner Shape") },
                            onClick = {
                                currentStepperItemShape = StepperItemShape.CUT_CORNER_SHAPE
                                expandedShapeMenu = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("RoundedCorner Shape") },
                            onClick = {
                                currentStepperItemShape = StepperItemShape.ROUNDED_CORNER_SHAPE
                                expandedShapeMenu = false
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Text(if (showCheckMark) "Hide Check Mark" else "Show Check Mark")
                            },
                            onClick = {
                                showCheckMark = showCheckMark.not()
                                expandedShapeMenu = false
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Text(if (showStepStroke) "Hide Step Stroke" else "Show Step Stroke")
                            }, onClick = {
                                showStepStroke = showStepStroke.not()
                                expandedShapeMenu = false
                            }
                        )
                    }
                }
            }

            when (currentStepperType) {
                StepperOptions.HORIZONTAL_TAB_STEPPER -> {

                    HorizontalStepper(
                        style = tabHorizontal(
                            totalSteps = totalSteps,
                            currentStep = currentStep,
                            stepStyle = stepStyle
                        )
                    ) { it.toast(context) }
                }

                StepperOptions.HORIZONTAL_NUMBERED_STEPPER -> {
                    HorizontalStepper(
                        style = numberedHorizontal(
                            totalSteps = totalSteps,
                            currentStep = currentStep,
                            stepStyle = stepStyle
                        )
                    ) { it.toast(context) }
                }

                StepperOptions.HORIZONTAL_ICON_STEPPER -> {
                    HorizontalStepper(
                        style = iconHorizontal(
                            currentStep = currentStep,
                            icons = icons,
                            stepStyle = stepStyle
                        )
                    ) { it.toast(context) }
                }

                StepperOptions.HORIZONTAL_DASHED_STEPPER -> {
                    HorizontalStepper(
                        style = dashed(
                            totalSteps = totalSteps,
                            currentStep = currentStep,
                            stepStyle = stepStyle
                        )
                    ) { it.toast(context) }
                }

                StepperOptions.VERTICAL_ICON_STEPPER -> {
                    VerticalStepper(
                        style = iconVertical(
                            currentStep = currentStep,
                            icons = icons,
                            stepStyle = stepStyle,
                        )
                    ) { it.toast(context) }
                }

                StepperOptions.VERTICAL_TAB_STEPPER -> {

                    VerticalStepper(
                        style = tabVertical(
                            totalSteps = totalSteps,
                            currentStep = currentStep,
                            stepStyle = stepStyle
                        )
                    ) { it.toast(context) }
                }

                StepperOptions.VERTICAL_NUMBERED_STEPPER -> {
                    VerticalStepper(
                        style = numberedVertical(
                            totalSteps = totalSteps,
                            currentStep = currentStep,
                            stepStyle = stepStyle
                        )
                    ) { it.toast(context) }
                }

                StepperOptions.VERTICAL_NUMBERED_LABEL_STEPPER -> {
                    VerticalStepper(
                        style =
                        numberedVerticalWithLabel(
                            totalSteps = totalSteps,
                            currentStep = currentStep,
                            trailingLabels = trailingLabels,
                            stepStyle = stepStyle
                        )
                    ) { it.toast(context) }
                }

                StepperOptions.VERTICAL_ICON_LABEL_STEPPER -> {
                    VerticalStepper(
                        style =
                        iconVerticalWithLabel(
                            totalSteps = totalSteps,
                            currentStep = currentStep,
                            trailingLabels = trailingLabels,
                            icons = icons,
                            stepStyle = stepStyle
                        )
                    ) { it.toast(context) }
                }

                StepperOptions.VERTICAL_TAB_LABEL_STEPPER -> {
                    VerticalStepper(
                        style =
                        tabVerticalWithLabel(
                            totalSteps = totalSteps,
                            currentStep = currentStep,
                            stepStyle = stepStyle,
                            trailingLabels = trailingLabels
                        )
                    ) { it.toast(context) }
                }
            }

            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(20.dp)
            ) {
                Text(
                    text = "Total Steps: : $totalSteps",
                    modifier = Modifier.padding(vertical = 2.dp)
                )
                Slider(
                    value = totalSteps.toFloat(),
                    onValueChange = { newValue ->
                        totalSteps = newValue.toInt()

                        // Because the current step should be less than the total steps
                        if (currentStep >= totalSteps) {
                            currentStep = newValue - 1f
                        }
                    },
                    valueRange = 1f..10f, // Set the range of Total Steps
                    steps = 10, // Divide the range into 10 steps
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Step Item Size in DP: $stepItemSize",
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                Slider(
                    value = stepItemSize.toFloat(),
                    onValueChange = { newValue ->
                        stepItemSize = newValue.toInt()
                    },
                    valueRange = 32f..55f, // Set the range of Step Item size
                    steps = 30, // Divide the range into 20 steps
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(onClick = { showLineStyleSheet = true }) {
                    Text("Modify Line Style")
                }
            }


            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {

                AnimatedVisibility(
                    visible = currentStep >= -0.75f,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Button(
                        onClick = {
                            currentStep -= 0.25f
                        }
                    ) {
                        Text(text = "Previous")
                    }
                }

                Spacer(Modifier.weight(1f))

                AnimatedVisibility(
                    visible = currentStep < totalSteps.toFloat(),
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Button(
                        onClick = {
                            if (currentStep == -1f) {
                                currentStep = 0f
                            } else {
                                currentStep += 0.25f
                            }
                        }
                    ) {
                        Text(
                            text =
                            if (currentStep == -1f) "Start"
                            else if (currentStep >= totalSteps) "Finish"
                            else "Next"
                        )
                    }
                }
            }
        }
    }
}