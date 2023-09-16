package com.binayshaw7777.kotstep.utils

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape

enum class StepperItemShape {
    RECTANGLE_SHAPE,
    CIRCLE_SHAPE,
    ROUNDED_CORNER_SHAPE,
    CUT_CORNER_SHAPE;

    companion object {
        fun getShapeFromEnum(shape: StepperItemShape): Shape {
            return when (shape) {
                RECTANGLE_SHAPE -> {
                    RectangleShape
                }

                CIRCLE_SHAPE -> {
                    CircleShape
                }

                ROUNDED_CORNER_SHAPE -> {
                    RoundedCornerShape(20)
                }

                CUT_CORNER_SHAPE -> {
                    CutCornerShape(20)
                }
            }
        }
    }
}