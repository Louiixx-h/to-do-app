package com.luislabs.todoapp.ui.screens.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.luislabs.todoapp.R
import com.luislabs.todoapp.ui.theme.EmptyIconSize
import com.luislabs.todoapp.ui.theme.MediumGray

@Composable
fun ListEmptyContent() {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(EmptyIconSize),
            painter = painterResource(id = R.drawable.ic_baseline_hourglass_empty_24),
            contentDescription = stringResource(R.string.empty_icon),
            tint = MediumGray
        )
        Text(
            text = stringResource(R.string.no_tasks_found),
            color = MediumGray,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
@Preview
fun ListEmptyContentPreview() {
    ListEmptyContent()
}