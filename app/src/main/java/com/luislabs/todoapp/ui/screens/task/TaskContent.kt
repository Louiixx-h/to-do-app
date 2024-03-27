package com.luislabs.todoapp.ui.screens.task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.luislabs.todoapp.R
import com.luislabs.todoapp.components.PriorityDropDown
import com.luislabs.todoapp.data.models.Priority
import com.luislabs.todoapp.ui.theme.MediumPadding

@Composable
fun TaskContent(
    modifier: Modifier,
    title: String,
    onTitleChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    priority: Priority,
    onPrioritySelected: (Priority) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = MediumPadding)
            .padding(bottom = MediumPadding)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = title,
            onValueChange = { text ->
                onTitleChange(text)
            },
            label = {
                Text(text = stringResource(R.string.task_title_label))
            },
            textStyle = MaterialTheme.typography.bodyMedium,
            singleLine = true
        )
        HorizontalDivider(
            modifier = Modifier.height(MediumPadding)
        )
        PriorityDropDown(
            priority = priority,
            onPrioritySelected = onPrioritySelected
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxSize(),
            value = description,
            onValueChange = { text ->
                onDescriptionChange(text)
            },
            label = {
                Text(text = stringResource(R.string.task_description_label))
            },
            textStyle = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
@Preview
fun TaskContentPreview() {
    TaskContent(
        modifier = Modifier,
        title = "",
        onTitleChange = {},
        description = "",
        onDescriptionChange = {},
        priority = Priority.NONE,
        onPrioritySelected = {}
    )
}