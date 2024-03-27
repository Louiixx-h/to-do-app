package com.luislabs.todoapp.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.luislabs.todoapp.ui.screens.task.TaskScreen
import com.luislabs.todoapp.ui.viewmodels.SharedViewModel
import com.luislabs.todoapp.util.Action
import com.luislabs.todoapp.util.Constants.TASK_ARGUMENT_KEY
import com.luislabs.todoapp.util.Constants.TASK_SCREEN

fun NavGraphBuilder.taskComposable(
    navigateToListScreen: (Action) -> Unit,
    sharedViewModel: SharedViewModel
) {
    val firstArgument = navArgument(TASK_ARGUMENT_KEY) { type = NavType.IntType }
    composable(
        route = TASK_SCREEN,
        arguments = listOf(firstArgument)
    ) { navBackStackEntry ->
        val taskId = navBackStackEntry.arguments?.getInt(TASK_ARGUMENT_KEY) ?: -1
        TaskScreen(
            taskId = taskId,
            navigateToListScreen = navigateToListScreen,
            sharedViewModel = sharedViewModel
        )
    }
}