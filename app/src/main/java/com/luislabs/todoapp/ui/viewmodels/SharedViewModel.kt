package com.luislabs.todoapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luislabs.todoapp.data.models.ToDoTask
import com.luislabs.todoapp.data.repository.ToDoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(private val repository: ToDoRepository): ViewModel() {

    private val _allTasks = MutableStateFlow<List<ToDoTask>>(emptyList())
    val allTasks: StateFlow<List<ToDoTask>> = _allTasks

    fun getAllTasks() {
        viewModelScope.launch {
            repository.getAllTasks.collect {
                _allTasks.value = it
            }
        }
    }

    fun sortByLowPriority() {
        viewModelScope.launch {
            repository.sortByLowPriority.collect {
                _allTasks.value = it
            }
        }
    }

    fun sortByHighPriority() {
        viewModelScope.launch {
            repository.sortByHighPriority.collect {
                _allTasks.value = it
            }
        }
    }

    fun searchByQuery(searchQuery: String) {
        viewModelScope.launch {
            repository.searchByQuery(searchQuery).collect {
                _allTasks.value = it
            }
        }
    }

    fun getSelectedTask(taskId: Int) {
        viewModelScope.launch {
            repository.getSelectedTask(taskId).collect {
                _allTasks.value = listOf(it)
            }
        }
    }

    fun addTask(toDoTask: ToDoTask) {
        viewModelScope.launch {
            repository.addTask(toDoTask)
        }
    }

    fun updateTask(toDoTask: ToDoTask) {
        viewModelScope.launch {
            repository.updateTask(toDoTask)
        }
    }

    fun deleteTask(toDoTask: ToDoTask) {
        viewModelScope.launch {
            repository.deleteTask(toDoTask)
        }
    }

    fun deleteAllTask() {
        viewModelScope.launch {
            repository.deleteAllTask()
        }
    }
}