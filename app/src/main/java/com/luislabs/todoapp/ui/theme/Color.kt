package com.luislabs.todoapp.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val LightWhite = Color(0xFFF2F2F2)
val LightGray = Color(0xFFFCFCFC)
val MediumGray = Color(0xFF9C9C9C)
val DarkGray = Color(0xFF141414)

val LowPriorityColor = Color(0xFF00C980)
val MediumPriorityColor = Color(0xFFFFC114)
val HighPriorityColor = Color(0xFFEB5757)
val NonePriorityColor = Color(0xFFBDBDBD)

val ColorScheme.taskItemTitleColor: Color
    @Composable
    get(): Color {
        return if (MaterialTheme.colorScheme.isLight()) DarkGray else DarkGray
    }

val ColorScheme.taskItemBackground: Color
    @Composable
    get(): Color {
        return if (MaterialTheme.colorScheme.isLight()) Purple80 else PurpleGrey80
    }

val ColorScheme.appBarContentColor: Color
    @Composable
    get(): Color {
        return if (MaterialTheme.colorScheme.isLight()) LightGray else DarkGray
    }

val ColorScheme.appBarSearchColor: Color
    @Composable
    get(): Color {
        return if (MaterialTheme.colorScheme.isLight()) DarkGray else LightGray
    }

val ColorScheme.fabIconColor: Color
    @Composable
    get(): Color {
        return if (MaterialTheme.colorScheme.isLight()) DarkGray else LightGray
    }

@Composable
fun ColorScheme.isLight() = this.background.luminance() > 0.5