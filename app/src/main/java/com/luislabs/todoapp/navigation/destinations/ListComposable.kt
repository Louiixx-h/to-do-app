package com.luislabs.todoapp.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.luislabs.todoapp.ui.screens.list.ListScreen
import com.luislabs.todoapp.util.Constants.LIST_ARGUMENT_KEY
import com.luislabs.todoapp.util.Constants.LIST_SCREEN

fun NavGraphBuilder.listComposable(
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    val firstArgument = navArgument(LIST_ARGUMENT_KEY) { type = NavType.StringType }
    composable(
        route = LIST_SCREEN,
        arguments = listOf(firstArgument)
    ) {
        ListScreen(navigateToTaskScreen = navigateToTaskScreen)
    }
}