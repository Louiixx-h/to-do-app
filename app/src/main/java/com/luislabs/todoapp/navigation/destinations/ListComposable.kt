package com.luislabs.todoapp.navigation.destinations

import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.luislabs.todoapp.ui.screens.list.ListScreen
import com.luislabs.todoapp.ui.viewmodels.SharedViewModel
import com.luislabs.todoapp.util.Constants.LIST_ARGUMENT_KEY
import com.luislabs.todoapp.util.Constants.LIST_SCREEN
import com.luislabs.todoapp.util.toAction

fun NavGraphBuilder.listComposable(
    navigateToTaskScreen: (taskId: Int) -> Unit,
    sharedViewModel: SharedViewModel
) {
    val firstArgument = navArgument(LIST_ARGUMENT_KEY) { type = NavType.StringType }
    composable(
        route = LIST_SCREEN,
        arguments = listOf(firstArgument)
    ) { navBackStackEntry ->
        val action = navBackStackEntry.arguments?.getString(LIST_ARGUMENT_KEY).toAction()

        LaunchedEffect(key1 = action) {
            sharedViewModel.action.value = action
        }

        ListScreen(
            navigateToTaskScreen = navigateToTaskScreen,
            sharedViewModel = sharedViewModel
        )
    }
}