package com.luislabs.todoapp.ui.screens.task

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.luislabs.todoapp.R
import com.luislabs.todoapp.data.models.Priority
import com.luislabs.todoapp.ui.viewmodels.SharedViewModel
import com.luislabs.todoapp.util.Action

@Composable
fun TaskScreen(
    taskId: Int,
    navigateToListScreen: (Action) -> Unit,
    sharedViewModel: SharedViewModel
) {
    LaunchedEffect(key1 = true) {
        sharedViewModel.getSelectedTask(taskId)
    }

    val selectedTask = sharedViewModel.selectedTasks.collectAsState().value

    LaunchedEffect(key1 = selectedTask) {
        if (selectedTask != null || taskId == -1) {
            sharedViewModel.updateTaskFields(selectedTask)
        }
    }

    val title: String by sharedViewModel.title
    val description: String by sharedViewModel.description
    val priority: Priority by sharedViewModel.priority

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TaskAppBar(
                selectedTask = selectedTask,
                navigateToListScreen = { action ->
                    if (action == Action.NoAction) {
                        navigateToListScreen(action)
                        return@TaskAppBar
                    }

                    if (sharedViewModel.validateFields()) {
                        navigateToListScreen(action)
                        return@TaskAppBar
                    }

                    displayToast(context = context)
                }
            )
        },
        content = { paddingValues ->
            TaskContent(
                modifier = Modifier.padding(paddingValues = paddingValues),
                title = title,
                onTitleChange = { title ->
                    sharedViewModel.updateTitle(title)
                },
                description = description,
                onDescriptionChange = { description ->
                    sharedViewModel.description.value = description
                },
                priority = priority,
                onPrioritySelected = { priority ->
                    sharedViewModel.priority.value = priority
                }
            )
        }
    )
}

fun displayToast(context: Context) {
    Toast.makeText(
        context,
        context.getString(R.string.fields_are_empty_label),
        Toast.LENGTH_SHORT
    ).show()
}