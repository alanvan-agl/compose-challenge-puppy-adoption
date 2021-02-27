package com.example.androiddevchallenge.ui.screens.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.ui.theme.PuppyTheme
import com.example.androiddevchallenge.ui.theme.elevatedSurface

@Composable
fun PuppyAppBar(
    modifier: Modifier = Modifier,
    showNavIcon: Boolean,
    onNavIconPressed: () -> Unit = { },
    title: @Composable RowScope.() -> Unit
) {
    val backgroundColor = MaterialTheme.colors.elevatedSurface(3.dp)
    Column(
        Modifier.background(backgroundColor.copy(alpha = 0.95f))
    ) {
        if (showNavIcon) {
            TopAppBar(
                modifier = modifier,
                backgroundColor = MaterialTheme.colors.background,
                elevation = 0.dp, // No shadow needed
                contentColor = MaterialTheme.colors.onSurface,
                title = { Row { title() } },
                navigationIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                        contentDescription = stringResource(id = R.string.back),
                        modifier = Modifier
                            .clickable(onClick = onNavIconPressed)
                            .padding(horizontal = 16.dp)
                    )
                }
            )
        } else {
            TopAppBar(
                modifier = modifier,
                backgroundColor = MaterialTheme.colors.background,
                elevation = 0.dp, // No shadow needed
                contentColor = MaterialTheme.colors.onSurface,
                title = { Row { title() } },
            )
        }
        Divider()
    }
}

@Preview
@Composable
fun PuppyAppBarPreview() {
    PuppyTheme {
        PuppyAppBar(
            title = { Text("Preview") },
            showNavIcon = true
        )
    }
}
