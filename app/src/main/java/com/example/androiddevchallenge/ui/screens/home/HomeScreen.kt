package com.example.androiddevchallenge.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.di.SingletonInjector
import com.example.androiddevchallenge.ui.screens.components.PuppyScaffold

@Composable
fun HomeScreen(singletonInjector: SingletonInjector, navController: NavController) {
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
        topBarAction = { navController.navigateUp() }
    ) {
        homeViewModel.state.collectAsState().value.let { state ->
            when (state) {
                is HomeViewModel.State.Loading -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is HomeViewModel.State.Error -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(stringResource(id = R.string.error))
                        Spacer(modifier = Modifier.height(16.dp))
                        Image(
                            painter = painterResource(id = R.drawable.ic_baseline_replay_56),
                            contentDescription = stringResource(id = R.string.retry),
                            modifier = Modifier.clickable {
                                homeViewModel.getAllPuppies()
                            }
                        )
                    }
                }
                is HomeViewModel.State.Success -> {
                    LazyColumn(modifier = Modifier.padding(16.dp)) {
                        items(
                            items = state.value,
                            key = { it.id }
                        ) {
                            Spacer(modifier = Modifier.height(16.dp))
                            PuppyRow(puppy = it)
                        }
                    }
                }
            }
        }
    }
}
