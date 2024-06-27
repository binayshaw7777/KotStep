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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        var totalSteps by rememberSaveable {
            mutableIntStateOf(5)
        }

        var currentStep by rememberSaveable { mutableIntStateOf(-1) }

        var expanded by remember { mutableStateOf(false) }

        var expandedShapeMenu by remember { mutableStateOf(false) }

        var currentStepperType by remember {
            mutableStateOf(StepperOptions.HORIZONTAL_DASHED_STEPPER)
        }

        var currentStepperItemShape by remember {
            mutableStateOf(StepperItemShape.CIRCLE_SHAPE)
        }

        var stepItemSize by remember {
            mutableIntStateOf(35)
        }

        var lineThickness by rememberSaveable {
            mutableIntStateOf(6)
        }

        val icons = remember { mutableStateListOf<ImageVector>() }
        val labels = remember {
            mutableStateListOf<(@Composable () -> Unit)?>()
        }

        LaunchedEffect(totalSteps) {
            icons.clear()
            icons.addAll(Utils.getIcons(totalSteps))
            labels.clear()
            labels.addAll(Utils.getLabels(totalSteps))
        }

        val stepStyle = StepStyle(
            lineThickness = lineThickness.dp,
            showCheckMarkOnDone = true,
            showStrokeOnCurrent = true,
            stepSize = stepItemSize.dp,
            stepShape = getShapeFromEnum(currentStepperItemShape)
        )

        Column(
            modifier = Modifier
                .fillMaxHeight(0.7f)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
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

                Box {
                    IconButton(onClick = { expandedShapeMenu = true }) {
                        Icon(Icons.Default.Menu, contentDescription = "Localized description")
                    }
                    DropdownMenu(
                        expanded = expandedShapeMenu,
                        onDismissRequest = { expandedShapeMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("RectangleShape") },
                            onClick = { currentStepperItemShape = StepperItemShape.RECTANGLE_SHAPE }
                        )
                        DropdownMenuItem(
                            text = { Text("CircleShape") },
                            onClick = { currentStepperItemShape = StepperItemShape.CIRCLE_SHAPE }
                        )
                        DropdownMenuItem(
                            text = { Text("CutCornerShape") },
                            onClick = {
                                currentStepperItemShape = StepperItemShape.CUT_CORNER_SHAPE
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("RoundedCornerShape") },
                            onClick = {
                                currentStepperItemShape = StepperItemShape.ROUNDED_CORNER_SHAPE
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
                    )
                }

                StepperOptions.HORIZONTAL_NUMBERED_STEPPER -> {
                    HorizontalStepper(
                        style = numberedHorizontal(
                            totalSteps = totalSteps,
                            currentStep = currentStep,
                            stepStyle = stepStyle
                        )
                    )
                }

                StepperOptions.HORIZONTAL_ICON_STEPPER -> {
                    HorizontalStepper(
                        style = iconHorizontal(
                            currentStep = currentStep,
                            icons = icons,
                            stepStyle = stepStyle
                        )
                    )
                }

                StepperOptions.HORIZONTAL_DASHED_STEPPER -> {
                    HorizontalStepper(
                        style = dashed(
                            totalSteps = totalSteps,
                            currentStep = currentStep,
                            stepStyle = stepStyle
                        )
                    )
                }

                StepperOptions.VERTICAL_ICON_STEPPER -> {
                    VerticalStepper(
                        style = iconVertical(
                            currentStep = currentStep,
                            icons = icons,
                            stepStyle = stepStyle
                        )
                    )
                }

                StepperOptions.VERTICAL_TAB_STEPPER -> {

                    VerticalStepper(
                        style = tabVertical(
                            totalSteps = totalSteps,
                            currentStep = currentStep,
                            stepStyle = stepStyle
                        )
                    )
                }

                StepperOptions.VERTICAL_NUMBERED_STEPPER -> {
                    VerticalStepper(
                        style = numberedVertical(
                            totalSteps = totalSteps,
                            currentStep = currentStep,
                            stepStyle = stepStyle
                        )
                    )
                }

                StepperOptions.VERTICAL_NUMBERED_LABEL_STEPPER -> {
                    VerticalStepper(
                        style =
                        numberedVerticalWithLabel(
                            totalSteps = totalSteps,
                            currentStep = currentStep,
                            labels = labels,
                            stepStyle = stepStyle
                        )
                    )
                }

                StepperOptions.VERTICAL_ICON_LABEL_STEPPER -> {
                    VerticalStepper(
                        style =
                        iconVerticalWithLabel(
                            totalSteps = totalSteps,
                            currentStep = currentStep,
                            labels = labels,
                            icons = icons,
                            stepStyle = stepStyle
                        )
                    )
                }

                StepperOptions.VERTICAL_TAB_LABEL_STEPPER -> {
                    VerticalStepper(
                        style =
                        tabVerticalWithLabel(
                            totalSteps = totalSteps,
                            currentStep = currentStep,
                            stepStyle = stepStyle,
                            labels = labels
                        )
                    )
                }
            }

            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(20.dp)
            ) {
                Text(
                    text = "Total Steps: : $totalSteps",
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Slider(
                    value = totalSteps.toFloat(),
                    onValueChange = { newValue ->
                        totalSteps = newValue.toInt()

                        // Because the current step should be less than the total steps
                        if (currentStep >= totalSteps) {
                            currentStep = totalSteps - 1
                        }
                    },
                    valueRange = 1f..10f, // Set the range of Total Steps
                    steps = 10, // Divide the range into 10 steps
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Step Item Size in DP: $stepItemSize",
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Slider(
                    value = stepItemSize.toFloat(),
                    onValueChange = { newValue ->
                        stepItemSize = newValue.toInt()
                    },
                    valueRange = 35f..55f, // Set the range of Step Item size
                    steps = 20, // Divide the range into 20 steps
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Line Thickness in DP: $lineThickness",
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Slider(
                    value = lineThickness.toFloat(),
                    onValueChange = { newValue ->
                        lineThickness = newValue.toInt()
                    },
                    valueRange = 3f..10f, // Set the range of Line Thickness
                    steps = 7, // Divide the range into 7 steps
                    modifier = Modifier.fillMaxWidth()
                )
            }


            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {

                AnimatedVisibility(
                    visible = currentStep >= 0,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Button(
                        onClick = { currentStep-- },
                        enabled = currentStep >= 0
                    ) {
                        Text(text = "Previous")
                    }
                }

                Spacer(Modifier.weight(1f))

                AnimatedVisibility(
                    visible = currentStep < totalSteps,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Button(
                        onClick = { currentStep++ },
                        enabled = currentStep < totalSteps
                    ) {
                        Text(
                            text =
                            if (currentStep == -1) "Start"
                            else if (currentStep >= totalSteps) "Finish"
                            else "Next"
                        )
                    }
                }
            }
        }
    }
}