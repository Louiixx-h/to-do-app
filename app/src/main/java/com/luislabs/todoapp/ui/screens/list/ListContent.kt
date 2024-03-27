package com.luislabs.todoapp.ui.screens.list

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.luislabs.todoapp.R
import com.luislabs.todoapp.data.models.ToDoTask
import com.luislabs.todoapp.ui.theme.HighPriorityColor
import com.luislabs.todoapp.ui.theme.LargestPadding
import com.luislabs.todoapp.util.Action
import com.luislabs.todoapp.util.RequestState
import com.luislabs.todoapp.util.SearchAppBarState

@Composable
fun ListContent(
    modifier: Modifier,
    allTasks: RequestState<List<ToDoTask>>,
    searchedTasks: RequestState<List<ToDoTask>>,
    searchAppBarState: SearchAppBarState,
    navigateToTaskScreen: (taskId: Int) -> Unit,
    onDeleteTask: (ToDoTask, Action) -> Unit
) {
    if (searchAppBarState == SearchAppBarState.Triggered) {
        if (searchedTasks is RequestState.Success) {
            HandleListContent(
                modifier = modifier,
                tasks = searchedTasks.data,
                navigateToTaskScreen = navigateToTaskScreen,
                onDeleteTask = onDeleteTask
            )
        }
    } else {
        if (allTasks is RequestState.Success) {
            HandleListContent(
                modifier = modifier,
                tasks = allTasks.data,
                navigateToTaskScreen = navigateToTaskScreen,
                onDeleteTask = onDeleteTask
            )
        }
    }
}

@Composable
fun HandleListContent(
    modifier: Modifier,
    tasks: List<ToDoTask>,
    navigateToTaskScreen: (taskId: Int) -> Unit,
    onDeleteTask: (ToDoTask, Action) -> Unit
) {
    if (tasks.isEmpty()) {
        ListEmptyContent()
    } else {
        DisplayTasks(
            modifier = modifier,
            tasks = tasks,
            navigateToTaskScreen = navigateToTaskScreen,
            onDeleteTask = onDeleteTask
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplayTasks(
    modifier: Modifier,
    tasks: List<ToDoTask>,
    navigateToTaskScreen: (taskId: Int) -> Unit,
    onDeleteTask: (ToDoTask, Action) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(
            items = tasks,
            key = { task -> task.id }
        ) { task ->
            val swipeState = rememberSwipeToDismissBoxState()

            LaunchedEffect(key1 = swipeState.currentValue) {
                if (swipeState.currentValue == SwipeToDismissBoxValue.EndToStart) {
                    onDeleteTask(task, Action.Delete)
                }
            }

            val degrees = if (swipeState.targetValue == SwipeToDismissBoxValue.Settled) {
                0f
            } else {
                -45f
            }

            SwipeToDismissBox(
                state = swipeState,
                backgroundContent = { RedBackground(degrees = degrees) },
                enableDismissFromStartToEnd = false
            ) {
                ListTaskItem(toDoTask = task, navigateToTaskScreen = navigateToTaskScreen)
            }
        }
    }
}

@Composable
fun RedBackground(degrees: Float) {
    val angle: Float by animateFloatAsState(
        targetValue = degrees,
        label = "delete_icon_rotate"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(HighPriorityColor),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            modifier = Modifier
                .rotate(degrees = angle)
                .padding(horizontal = LargestPadding),
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(id = R.string.delete_task_description),
            tint = Color.White
        )
    }
}