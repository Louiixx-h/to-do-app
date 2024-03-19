package com.luislabs.todoapp.navigation

import androidx.navigation.NavHostController
import com.luislabs.todoapp.util.Action
import com.luislabs.todoapp.util.Constants.LIST_SCREEN

class Screens(navController: NavHostController) {

    val list: (Action) -> Unit = { action ->
        navController.navigate(route = "list/${action.name}") {
            popUpTo(LIST_SCREEN) { inclusive = true }
        }
    }

    val task: (Int) -> Unit = { taskId ->
        navController.navigate(route = "task/${taskId}")
    }
}