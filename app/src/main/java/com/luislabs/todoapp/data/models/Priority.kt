package com.luislabs.todoapp.data.models

import androidx.compose.ui.graphics.Color
import com.luislabs.todoapp.ui.theme.HighPriorityColor
import com.luislabs.todoapp.ui.theme.LowPriorityColor
import com.luislabs.todoapp.ui.theme.MediumPriorityColor
import com.luislabs.todoapp.ui.theme.NonePriorityColor

enum class Priority(val color: Color) {
    HIGH(HighPriorityColor),
    MEDIUM(MediumPriorityColor),
    LOW(LowPriorityColor),
    NONE(NonePriorityColor)
}
