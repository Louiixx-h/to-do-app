package com.luislabs.todoapp.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.luislabs.todoapp.util.Action
import com.luislabs.todoapp.util.Constants
import com.luislabs.todoapp.util.Constants.TASK_ARGUMENT_KEY
import com.luislabs.todoapp.util.Constants.TASK_SCREEN

fun NavGraphBuilder.taskComposable(
    navigateToListScreen: (Action) -> Unit
) {
    val firstArgument = navArgument(TASK_ARGUMENT_KEY) { type = NavType.StringType }
    composable(
        route = TASK_SCREEN,
        arguments = listOf(firstArgument)
    ) {}
}