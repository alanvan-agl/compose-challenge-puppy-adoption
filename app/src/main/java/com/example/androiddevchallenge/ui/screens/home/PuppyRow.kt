package com.example.androiddevchallenge.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.request.RequestOptions
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.ui.screens.model.Puppy
import dev.chrisbanes.accompanist.glide.GlideImage

@Composable
fun PuppyRow(puppy: Puppy) {
    Row(Modifier.height(56.dp)) {
        Box(modifier = Modifier.weight(1f)) {
            GlideImage(
                modifier = Modifier.size(56.dp, 56.dp),
                data = puppy.photoUrl,
                contentDescription = "",
                error = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_puppy_placeholder_purple),
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
        Column(
            modifier = Modifier.weight(2f).fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = puppy.name)
        }
    }
}
