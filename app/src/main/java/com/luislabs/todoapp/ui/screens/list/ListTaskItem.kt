package com.luislabs.todoapp.ui.screens.list

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.luislabs.todoapp.data.models.Priority
import com.luislabs.todoapp.data.models.ToDoTask
import com.luislabs.todoapp.ui.theme.LargePadding
import com.luislabs.todoapp.ui.theme.PriorityIndicatorSize
import com.luislabs.todoapp.ui.theme.TaskItemElevation
import com.luislabs.todoapp.ui.theme.taskItemTitleColor

@Composable
fun ListTaskItem(
    toDoTask: ToDoTask,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RectangleShape,
        shadowElevation = TaskItemElevation,
        onClick = {
            navigateToTaskScreen(toDoTask.id)
        }
    ) {
        Column(modifier = Modifier
            .padding(all = LargePadding)
            .fillMaxWidth()
        ) {
            Row {
                Text(
                    modifier = Modifier.weight(8f),
                    text = toDoTask.title,
                    color = MaterialTheme.colorScheme.taskItemTitleColor,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Canvas(modifier = Modifier
                        .width(PriorityIndicatorSize)
                        .height(PriorityIndicatorSize)
                    ) {
                        drawCircle(
                            color = toDoTask.priority.color
                        )
                    }
                }
            }
            Text(
                text = toDoTask.description,
                color = MaterialTheme.colorScheme.taskItemTitleColor,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
@Preview
fun ListTaskItemPreview() {
    val toDoTask = ToDoTask(
        id = 0,
        title = "Title",
        description = "Lorem Ipsum",
        priority = Priority.HIGH
    )
    ListTaskItem(
        toDoTask = toDoTask,
        navigateToTaskScreen = {}
    )
}