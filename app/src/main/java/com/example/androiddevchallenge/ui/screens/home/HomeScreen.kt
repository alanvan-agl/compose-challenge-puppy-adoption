package com.example.androiddevchallenge.ui.screens.home

import androidx.compose.foundation.layout.*
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
