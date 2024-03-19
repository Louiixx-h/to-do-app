package com.luislabs.todoapp.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.luislabs.todoapp.data.models.Priority
import com.luislabs.todoapp.ui.theme.LargePadding
import com.luislabs.todoapp.ui.theme.PriorityIndicatorSize
import com.luislabs.todoapp.ui.theme.Typography

@Composable
fun PriorityItem(priority: Priority) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Canvas(
            modifier = Modifier.size(PriorityIndicatorSize)
        ) {
            drawCircle(color = priority.color)
        }
        Text(
            modifier = Modifier.padding(LargePadding),
            text = priority.name,
            style = Typography.bodyMedium,
            color = Color.DarkGray
        )
    }
}

@Composable
@Preview
fun PriorityItemPreview() {
    PriorityItem(priority = Priority.LOW)
}