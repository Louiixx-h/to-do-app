package com.luislabs.todoapp.ui.screens.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.luislabs.todoapp.R
import com.luislabs.todoapp.components.DisplayAlertDialog
import com.luislabs.todoapp.components.PriorityItem
import com.luislabs.todoapp.data.models.Priority
import com.luislabs.todoapp.data.models.Priority.*
import com.luislabs.todoapp.ui.theme.LargePadding
import com.luislabs.todoapp.ui.theme.TopAppBarElevation
import com.luislabs.todoapp.ui.theme.TopAppBarHeight
import com.luislabs.todoapp.ui.theme.Typography
import com.luislabs.todoapp.ui.theme.appBarContentColor
import com.luislabs.todoapp.ui.theme.appBarSearchColor
import com.luislabs.todoapp.ui.viewmodels.SharedViewModel
import com.luislabs.todoapp.util.Action
import com.luislabs.todoapp.util.SearchAppBarState
import com.luislabs.todoapp.util.SearchAppBarState.*

@Composable
fun ListTopBar(
    sharedViewModel: SharedViewModel,
    searchAppBarState: SearchAppBarState,
    searchTextState: String
) {
    when(searchAppBarState) {
        Closed -> {
            DefaultListAppBar(
                onSearchClicked = {
                    sharedViewModel.searchAppBarState.value = Opened
                },
                onSortClicked = { priority ->
                    sharedViewModel.persistSortingState(priority)
                    when (priority) {
                        HIGH -> sharedViewModel.sortByHighPriority()
                        LOW -> sharedViewModel.sortByLowPriority()
                        else -> sharedViewModel.getAllTasks()
                    }
                },
                onDeleteAllConfirmClicked = {
                    sharedViewModel.action.value = Action.DeleteAll
                }
            )
        }
        else -> {
            SearchAppBar(
                text = searchTextState,
                onTextChange = { newText ->
                   sharedViewModel.searchTextState.value = newText
                },
                onCloseClicked = {
                    sharedViewModel.onCloseSearchClicked()
                },
                onSearchClicked = { query ->
                    sharedViewModel.searchByQuery(searchQuery = query)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultListAppBar(
    onSearchClicked: () -> Unit,
    onSortClicked: (Priority) -> Unit,
    onDeleteAllConfirmClicked: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.tasks_label),
                color = MaterialTheme.colorScheme.appBarContentColor
            )
        },
        actions = {
            ListAppBarActions(
                onSearchClicked = onSearchClicked,
                onSortClicked = onSortClicked,
                onDeleteAllConfirmClicked = onDeleteAllConfirmClicked
            )
        },
        colors = TopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            scrolledContainerColor = MaterialTheme.colorScheme.primary,
            navigationIconContentColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.appBarContentColor,
            actionIconContentColor = MaterialTheme.colorScheme.primary
        )
    )
}

@Composable
fun ListAppBarActions(
    onSearchClicked: () -> Unit,
    onSortClicked: (Priority) -> Unit,
    onDeleteAllConfirmClicked: () -> Unit,
) {
    var openDialog by remember { mutableStateOf(false) }

    DisplayAlertDialog(
        title = stringResource(R.string.delete_all_task),
        message = stringResource(R.string.delete_all_task_confirmation),
        openDialog = openDialog,
        closeDialog = { openDialog = false },
        onYesClicked = { onDeleteAllConfirmClicked() }
    )

    SearchAction(onSearchClicked = onSearchClicked)
    SortAction(onSortClicked = onSortClicked)
    DeleteAllAction(onDeleteClicked = { openDialog = true })
}

@Composable
fun SearchAction(
    onSearchClicked: () -> Unit
) {
    IconButton(onClick = { onSearchClicked() }) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = stringResource(R.string.search_tasks),
            tint = MaterialTheme.colorScheme.appBarContentColor
        )
    }
}

@Composable
fun SortAction(
    onSortClicked: (Priority) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = { expanded = true }) {
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_filter_list_24),
            contentDescription = stringResource(R.string.sort_tasks),
            tint = MaterialTheme.colorScheme.appBarContentColor
        )
        DropdownMenu(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = {
                    PriorityItem(priority = LOW)
                },
                onClick = {
                    expanded = false
                    onSortClicked(LOW)
                }
            )
            DropdownMenuItem(
                text = {
                    PriorityItem(priority = HIGH)
                },
                onClick = {
                    expanded = false
                    onSortClicked(HIGH)
                }
            )
            DropdownMenuItem(
                text = {
                    PriorityItem(priority = NONE)
                },
                onClick = {
                    expanded = false
                    onSortClicked(NONE)
                }
            )
        }
    }
}

@Composable
fun DeleteAllAction(
    onDeleteClicked: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = { expanded = true }) {
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_more_vert_24),
            contentDescription = stringResource(R.string.delete_all),
            tint = MaterialTheme.colorScheme.appBarContentColor
        )
        DropdownMenu(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = {
                    Row {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = stringResource(R.string.delete_all)
                        )
                        Text(
                            modifier = Modifier.padding(start = LargePadding),
                            text = stringResource(R.string.delete_all),
                            style = Typography.bodyMedium
                        )
                    }
                },
                onClick = { 
                    expanded = false
                    onDeleteClicked()
                }
            )
        }
    }
}

@Composable
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(TopAppBarHeight),
        shadowElevation = TopAppBarElevation,
        color = MaterialTheme.colorScheme.primary
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = {
                onTextChange(it)
            },
            placeholder = {
                Text(
                    modifier = Modifier.alpha(0.5f),
                    text = stringResource(R.string.search_hint),
                    color = MaterialTheme.colorScheme.appBarSearchColor
                )
            },
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.appBarSearchColor,
                fontSize = MaterialTheme.typography.bodyMedium.fontSize
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(
                    onClick = {}
                ) {
                    Icon(
                        modifier = Modifier.alpha(0.5f),
                        imageVector = Icons.Filled.Search,
                        contentDescription = stringResource(R.string.search_icon),
                        tint = MaterialTheme.colorScheme.appBarSearchColor
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        onCloseClicked()
                    }
                ) {
                    Icon(
                        modifier = Modifier.alpha(0.5f),
                        imageVector = Icons.Filled.Close,
                        contentDescription = stringResource(R.string.close_button),
                        tint = MaterialTheme.colorScheme.appBarSearchColor
                    )
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text)
                }
            )
        )
    }
}

@Composable
@Preview
private fun DefaultListAppBarPreview() {
    DefaultListAppBar(
        onSearchClicked = {},
        onSortClicked = {},
        onDeleteAllConfirmClicked = {}
    )
}

@Composable
@Preview
private fun SearchAppBarPreview() {
    SearchAppBar(
        text = "Search",
        onTextChange = {},
        onCloseClicked = {},
        onSearchClicked = {}
    )
}