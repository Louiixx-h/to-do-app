package com.luislabs.todoapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.luislabs.todoapp.util.Constants

@Entity(tableName = Constants.DATABASE_TABLE)
data class ToDoTask(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val priority: Priority
)
