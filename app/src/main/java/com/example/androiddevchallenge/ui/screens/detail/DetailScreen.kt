/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.ui.screens.detail

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.request.RequestOptions
import com.example.androiddevchallenge.NavigationActions
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.di.SingletonInjector
import com.example.androiddevchallenge.ui.screens.components.ErrorState
import com.example.androiddevchallenge.ui.screens.components.LoadingState
import com.example.androiddevchallenge.ui.screens.components.PuppyScaffold
import dev.chrisbanes.accompanist.glide.GlideImage

@Composable
fun DetailScreen(
    singletonInjector: SingletonInjector,
    actions: NavigationActions,
    puppyId: Long,
    puppyName: String
) {
    val detailViewModel: DetailViewModel = viewModel(
        key = puppyId.toString(),
        factory = DetailViewModelFactory(singletonInjector, puppyId)
    )
    PuppyScaffold(
        topBarTitle = {
            Text(
                text = puppyName,
                color = MaterialTheme.colors.primary
            )
        },
        onNavIconPressed = { actions.upPress() }
    ) {
        detailViewModel.state.collectAsState().value.let { state ->
            when (state) {
                is DetailViewModel.State.Loading -> LoadingState()
                is DetailViewModel.State.Error -> ErrorState(
                    onRetry = {
                        detailViewModel.getPuppy(puppyId)
                    }
                )
                is DetailViewModel.State.Success -> SuccessState(state = state)
            }
        }
    }
}

@Composable
fun SuccessState(state: DetailViewModel.State.Success) {
    val puppy = remember { state.value }
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        item {
            Spacer(
                modifier = Modifier.height(36.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.Center
            ) {
                GlideImage(
                    modifier = Modifier.size(200.dp, 200.dp),
                    data = puppy.photoUrl,
                    contentDescription = "",
                    error = {
                        Image(
                            painter = painterResource(id = R.drawable.ic_puppy_placeholder_purple_200),
                            contentDescription = ""
                        )
                    },
                    requestBuilder = {
                        val options = RequestOptions()
                        options.circleCrop()
                        apply(options)
                    }
                )
            }
            Spacer(modifier = Modifier.height(36.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                PuppyDescription(R.string.gender, puppy.gender)
                PuppyDescription(R.string.size, puppy.size)
                PuppyDescription(R.string.color, puppy.color)
                PuppyDescription(R.string.description, puppy.description)
            }
        }
    }
}

@Composable
fun PuppyDescription(@StringRes stringRes: Int, value: String) {
    if (value.isNotEmpty()) {
        Text(
            text = stringResource(id = stringRes, value),
            color = MaterialTheme.colors.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}
