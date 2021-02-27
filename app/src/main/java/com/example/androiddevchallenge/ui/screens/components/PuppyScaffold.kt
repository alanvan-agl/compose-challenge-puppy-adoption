package com.example.androiddevchallenge.ui.screens.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

import com.example.androiddevchallenge.ui.theme.PuppyTheme

@Composable
fun PuppyScaffold(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    topBarTitle: @Composable RowScope.() -> Unit,
    showNavIcon: Boolean = true,
    onNavIconPressed: () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    PuppyTheme {
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                PuppyAppBar(
                    title = topBarTitle,
                    showNavIcon = showNavIcon,
                    onNavIconPressed = onNavIconPressed
                )
            },
            backgroundColor = MaterialTheme.colors.background,
            content = content
        )
    }
}
