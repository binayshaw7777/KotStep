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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.binayshaw7777.kotstep.ui.horizontal.HorizontalIconStepper
import com.binayshaw7777.kotstep.ui.horizontal.HorizontalSequencedStepper
import com.binayshaw7777.kotstep.ui.theme.KotStepTheme
import com.binayshaw7777.kotstep.ui.vertical.VerticalSequencedStepper
import com.binayshaw7777.kotstep.utils.StepperTypes

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
        val totalSteps = 5

        var currentStep by rememberSaveable { mutableStateOf(0) }

        var expanded by remember { mutableStateOf(false) }

        var currentStepperType by remember {
            mutableStateOf(StepperTypes.HORIZONTAL_SEQUENCED_STEPPER)
        }


        Column(
            modifier = Modifier
                .fillMaxHeight(0.7f)
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.TopStart)
            ) {
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Localized description")
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Horizontal Sequenced Stepper") },
                        onClick = {
                            currentStepperType = StepperTypes.HORIZONTAL_SEQUENCED_STEPPER
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Outlined.KeyboardArrowRight,
                                contentDescription = null
                            )
                        })
                    DropdownMenuItem(
                        text = { Text("Vertical Sequenced Stepper") },
                        onClick = { currentStepperType = StepperTypes.VERTICAL_SEQUENCED_STEPPER },
                        leadingIcon = {
                            Icon(
                                Icons.Outlined.KeyboardArrowDown,
                                contentDescription = null
                            )
                        })
                    DropdownMenuItem(
                        text = { Text("Vertical Sequenced Stepper") },
                        onClick = { currentStepperType = StepperTypes.HORIZONTAL_ICON_STEPPER },
                        leadingIcon = {
                            Icon(
                                Icons.Outlined.ArrowForward,
                                contentDescription = null
                            )
                        })
                }
            }

            when (currentStepperType) {
                StepperTypes.HORIZONTAL_SEQUENCED_STEPPER -> {
                    HorizontalSequencedStepper(
                        totalSteps = totalSteps,
                        currentStep = currentStep,
                        stepSize = 35.dp,
                        lineThickness = 3.dp,
                        completedColor = MaterialTheme.colorScheme.primary,
                        incompleteColor = Color.Gray,
                        checkMarkColor = Color.White,
                        stepNameOnIncompleteColor = Color.White,
                        stepNameOnCompleteColor = Color.White
                    )
                }

                StepperTypes.VERTICAL_SEQUENCED_STEPPER -> {
                    VerticalSequencedStepper(
                        totalSteps = totalSteps,
                        currentStep = currentStep,
                        stepSize = 35.dp,
                        lineThickness = 3.dp,
                        completedColor = MaterialTheme.colorScheme.primary,
                        incompleteColor = Color.Gray,
                        checkMarkColor = Color.White,
                        stepNameOnIncompleteColor = Color.White,
                        stepNameOnCompleteColor = Color.White
                    )
                }

                StepperTypes.HORIZONTAL_ICON_STEPPER -> {
                    HorizontalIconStepper(
                        totalSteps = totalSteps,
                        currentStep = currentStep,
                        stepSize = 35.dp,
                        lineThickness = 3.dp,
                        completedColor = MaterialTheme.colorScheme.primary,
                        incompleteColor = Color.Gray,
                        checkMarkColor = Color.White,
                        stepIconsColorOnIncomplete = Color.White,
                        stepIconsColorOnComplete = Color.White,
                        stepIconsList = listOf(
                            Icons.Default.AccountBox,
                            Icons.Default.AddCircle,
                            Icons.Default.Build,
                            Icons.Default.Face,
                            Icons.Default.Home
                        )
                    )
                }
            }


            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {

                AnimatedVisibility(
                    visible = currentStep >= 1,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Button(
                        onClick = { currentStep-- },
                        enabled = currentStep >= 1
                    ) {
                        Text(text = "Previous")
                    }
                }

                AnimatedVisibility(
                    visible = currentStep <= totalSteps,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Button(
                        onClick = { currentStep++ },
                        enabled = currentStep <= totalSteps
                    ) {
                        Text(
                            text =
                            if (currentStep == 0) "Start"
                            else if (currentStep >= totalSteps) "Finish"
                            else "Next"
                        )
                    }
                }
            }
        }
    }
}