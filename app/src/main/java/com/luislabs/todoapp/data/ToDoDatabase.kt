package com.luislabs.todoapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.luislabs.todoapp.data.models.ToDoTask

@Database(entities = [ToDoTask::class], version = 1, exportSchema = false)
abstract class ToDoDatabase : RoomDatabase() {

    abstract fun toDoDAO(): ToDoDAO
}