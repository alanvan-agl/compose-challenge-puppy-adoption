package com.example.androiddevchallenge.ui.screens.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
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
                is DetailViewModel.State.Error -> ErrorState(onRetry = {
                    detailViewModel.getPuppy(puppyId)
                })
                is DetailViewModel.State.Success -> SuccessState(state = state)
            }
        }
    }
}

@Composable
fun SuccessState(state: DetailViewModel.State.Success) {
    val puppy = remember { state.value }
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Box(
                modifier = Modifier
                    .wrapContentSize(Alignment.Center)
                    .clip(RoundedCornerShape(10.dp))
            ) {
                GlideImage(
                    modifier = Modifier.size(300.dp, 300.dp),
                    data = puppy.photoUrl,
                    contentDescription = "",
                    error = {
                        Image(
                            painter = painterResource(id = R.drawable.ic_puppy_placeholder_purple_300),
                            contentDescription = ""
                        )
                    }
                )
            }
        }
    }
}
