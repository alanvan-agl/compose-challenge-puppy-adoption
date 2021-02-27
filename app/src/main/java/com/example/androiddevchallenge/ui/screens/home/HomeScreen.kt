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
package com.example.androiddevchallenge.ui.screens.home

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androiddevchallenge.NavigationActions
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.di.SingletonInjector
import com.example.androiddevchallenge.ui.screens.components.ErrorState
import com.example.androiddevchallenge.ui.screens.components.LoadingState
import com.example.androiddevchallenge.ui.screens.components.PuppyScaffold

@Composable
fun HomeScreen(singletonInjector: SingletonInjector, actions: NavigationActions) {
    val homeViewModel: HomeViewModel = viewModel(
        factory = HomeViewModelFactory(singletonInjector)
    )
    PuppyScaffold(
        topBarTitle = {
            Text(
                text = stringResource(id = R.string.home),
                color = MaterialTheme.colors.primary
            )
        },
        showNavIcon = false
    ) {
        homeViewModel.state.collectAsState().value.let { state ->
            when (state) {
                is HomeViewModel.State.Loading -> LoadingState()
                is HomeViewModel.State.Error -> ErrorState(onRetry = { homeViewModel.getAllPuppies() })
                is HomeViewModel.State.Success -> SuccessState(
                    state = state,
                    onPuppySelected = { id, name ->
                        actions.navigateToPuppyDetails(id, name)
                    }
                )
            }
        }
    }
}

@Composable
fun SuccessState(state: HomeViewModel.State.Success, onPuppySelected: (Long, String) -> Unit) {
    val puppies = remember { state.value }
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(
            items = puppies,
            key = { it.id }
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            PuppyRow(puppy = it, onPuppySelected = onPuppySelected)
        }
    }
}
