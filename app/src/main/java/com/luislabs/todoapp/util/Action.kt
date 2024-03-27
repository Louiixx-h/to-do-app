package com.luislabs.todoapp.util

enum class Action {
    Add,
    Update,
    Delete,
    DeleteAll,
    Undo,
    NoAction
}

fun String?.toAction(): Action {
    return when {
        this == "Add" -> {
            Action.Add
        }
        this == "Update" -> {
            Action.Update
        }
        this == "DeleteAll" -> {
            Action.DeleteAll
        }
        this == "Undo" -> {
            Action.Undo
        }
        this == "Delete" -> {
            Action.Delete
        }
        else -> {
            Action.NoAction
        }
    }
}