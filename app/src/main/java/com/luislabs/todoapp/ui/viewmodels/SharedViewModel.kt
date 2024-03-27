package com.luislabs.todoapp.ui.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luislabs.todoapp.data.models.Priority
import com.luislabs.todoapp.data.models.ToDoTask
import com.luislabs.todoapp.data.repository.DataStoreRepository
import com.luislabs.todoapp.data.repository.ToDoRepository
import com.luislabs.todoapp.util.Action
import com.luislabs.todoapp.util.Action.*
import com.luislabs.todoapp.util.Constants.MAX_TITLE_LENGTH
import com.luislabs.todoapp.util.RequestState
import com.luislabs.todoapp.util.SearchAppBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val toDoRepository: ToDoRepository,
    private val dataStoreRepository: DataStoreRepository
): ViewModel() {

    val action: MutableState<Action> = mutableStateOf(NoAction)

    val id = mutableIntStateOf(0)
    val title = mutableStateOf("")
    val description = mutableStateOf("")
    val priority = mutableStateOf(Priority.NONE)

    val searchAppBarState = mutableStateOf(SearchAppBarState.Closed)
    val searchTextState = mutableStateOf("")

    private val _searchedTasks = MutableStateFlow<RequestState<List<ToDoTask>>>(RequestState.Idle)
    val searchedTasks: StateFlow<RequestState<List<ToDoTask>>> = _searchedTasks

    private val _allTasks = MutableStateFlow<RequestState<List<ToDoTask>>>(RequestState.Idle)
    val allTasks: StateFlow<RequestState<List<ToDoTask>>> = _allTasks

    private val _selectedTasks = MutableStateFlow<ToDoTask?>(null)
    val selectedTasks: StateFlow<ToDoTask?> = _selectedTasks

    fun getAllTasks() {
        _allTasks.value = RequestState.Loading
        try {
            viewModelScope.launch {
                toDoRepository.getAllTasks.collect {
                    _allTasks.value = RequestState.Success(it)
                }
            }
        } catch (e: Exception) {
            _allTasks.value = RequestState.Error(e)
        }
    }

    fun sortByLowPriority() {
        _allTasks.value = RequestState.Loading
        try {
            viewModelScope.launch {
                toDoRepository.sortByLowPriority.collect {
                    _allTasks.value = RequestState.Success(it)
                }
            }
        } catch (e: Exception) {
            _allTasks.value = RequestState.Error(e)
        }
    }

    fun sortByHighPriority() {
        _allTasks.value = RequestState.Loading
        try {
            viewModelScope.launch {
                toDoRepository.sortByHighPriority.collect {
                    _allTasks.value = RequestState.Success(it)
                }
            }
        } catch (e: Exception) {
            _allTasks.value = RequestState.Error(e)
        }
    }

    fun searchByQuery(searchQuery: String) {
        _searchedTasks.value = RequestState.Loading
        try {
            viewModelScope.launch {
                toDoRepository.searchByQuery("%$searchQuery%").collect {
                    _searchedTasks.value = RequestState.Success(it)
                }
            }
        } catch (e: Exception) {
            _searchedTasks.value = RequestState.Error(e)
        }
        searchAppBarState.value = SearchAppBarState.Triggered
    }

    fun getSelectedTask(taskId: Int) {
        runCatching {
            viewModelScope.launch {
                toDoRepository.getSelectedTask(taskId).collect {
                    _selectedTasks.value = it
                }
            }
        }
    }

    private fun addTask() {
        val toDoTask = ToDoTask(
            id = id.intValue,
            title = title.value,
            description = description.value,
            priority = priority.value
        )
        viewModelScope.launch {
            toDoRepository.addTask(toDoTask)
        }
        searchAppBarState.value = SearchAppBarState.Closed
    }

    private fun updateTask() {
        val toDoTask = ToDoTask(
            id = id.intValue,
            title = title.value,
            description = description.value,
            priority = priority.value
        )
        viewModelScope.launch {
            toDoRepository.updateTask(toDoTask)
        }
    }

    private fun deleteTask() {
        val toDoTask = ToDoTask(
            id = id.intValue,
            title = title.value,
            description = description.value,
            priority = priority.value
        )
        viewModelScope.launch {
            toDoRepository.deleteTask(toDoTask)
        }
    }

    private fun deleteAllTask() {
        viewModelScope.launch {
            toDoRepository.deleteAllTask()
        }
    }

    fun onCloseSearchClicked() {
        if (searchTextState.value.isNotEmpty()) {
            searchTextState.value = ""
        } else {
            searchAppBarState.value = SearchAppBarState.Closed
        }
    }

    fun updateTaskFields(selectedTask: ToDoTask?) {
        if (selectedTask != null) {
            id.intValue = selectedTask.id
            title.value = selectedTask.title
            description.value = selectedTask.description
            priority.value = selectedTask.priority
        } else {
            id.intValue = 0
            title.value = ""
            description.value = ""
            priority.value = Priority.NONE
        }
    }

    fun updateTitle(newTitle: String) {
        if (newTitle.length < MAX_TITLE_LENGTH) {
            title.value = newTitle
        }
    }

    fun validateFields(): Boolean {
        return title.value.isNotEmpty()
    }

    fun handleDatabaseActions(action: Action) {
        when (action) {
            Add -> {
                addTask()
            }
            Update -> {
                updateTask()
            }
            Delete -> {
                deleteTask()
            }
            DeleteAll -> {
                deleteAllTask()
            }
            Undo -> {
                addTask()
            }
            else -> {}
        }
        this.action.value = NoAction
    }

    fun persistSortingState(priority: Priority) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.persistSortState(priority = priority)
        }
    }
}